<template>

  <div class="kz-tree__wrapper">
    <!-- <div class="kz-tree__top">
      <el-button size="small" icon="plus" type="primary" @click="treeAdd({ id: 0 })">增加分类</el-button>
    </div> -->
    <p style="display: none;">{{getTreeData}}</p>
    <el-tree
      ref="kzTree"
      :data ="treeData"
      :props="data.defaultProps"
      :expand-on-click-node="false"
      lazy
      :load="loadTreeNode"
      @node-click="handleNodeClick"
      :render-content="nodeRender"
      class="kz-tree">
    </el-tree>
    <!--dialog-->
    <el-dialog :title="dialog.title" :visible.sync="dialog.dialogVisible" :close-on-click-modal="false">
      <el-form :model="dialog.form" ref="dialogForm" :rules="dialog.rules" >
        <el-form-item label="分类名称" prop="name" label-width="120" required>
          <el-input v-model="dialog.form.name" auto-complete="off" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelSubmit">取 消</el-button>
        <el-button type="primary" @click="submitForm" :loading="dialog.submiting">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getDirectoryList } from '../../api/api';
  import modelList from './model.vue' ;
  import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'
  import ModelContent from "./index";

//  上传文件
  import { mapState,mapGetters} from 'vuex'
  import '../../assets/iconCss/iconfont.css'
  export default {
    name: 'kz-tree',
    props: {
      data: {
        type: Object,
        required: true,
        default () {
          return {
            // 默认tree-node的字段名
            defaultProps: {
              children: 'children',
              label: 'name',
              isLeaf: 'isLeaf'
            },
            // 异步ajax地址
            // CURD
            url: {
              C: '',
              U: '',
              R: '',
              D: ''
            }
          }
        }
      }
    },
    data () {
      // 声明保存当前操作分类node对象
      this.__currentNode = null;
        var validateName = (rule, value, callback) => {
//                if (value.trim() == '') {
//                    callback(new Error('不能全为空格和空值'));
//                }else {
//                    callback();
//                }
            let re = new RegExp("^[ a-zA-Z0-9_\u4e00-\u9fa5]+$");
            console.log(value);

            if(!value)
            {
                callback(new Error('请输入分类目录名称'));
            }
            else
            {
                if (re.test(value))
                {
                    if(value.length>32)
                    	{
                    		callback(new Error('分类目录名称不得超过32个字符'));
                    	}
                    	else
                    	{
                    		if(value.trim())
                    		 {
                    		        callback();
                    		 }
                    		 else
                    		 {
                    		      callback(new Error('不能全为空格'));
                    		  }
                    	}
                }
                else
                {
                    callback(new Error('只允许输入中文、字母、数字下划线及空格'));
                }
            }
        };
      return {
        treeData: [],
        /* 弹框 */
        dialog: {
          title: '增加分类',
          dialogVisible: false,
          submiting: false,
          form: {
            name: '',
            id: '',
            parent_id: 0
          },
          rules: {
            name: {
                  required: true,
                  validator : validateName,
//                  message: '请输入分类名称',
                  trigger: 'blur'
              }
//              name: {
//                  required: true,
//                  validator : validateName,
////                  message: '请输入分类名称',
//                  trigger: 'blur'
//              }
          }
        },
      }
    },
      computed: {
          ...mapState({
              b:state =>state.b,
              a:state =>state.a
          }),
          ...mapGetters(['bmsg','amsg','treeData']),
          getTreeData(){
              console.log(this.$store.state.treeDatas)
              if(this.$store.state.treeDatas != ""){
//                  this.treeData = this.$store.state.treeDatas.data;
//                  this.loadTreeNode(this.$store.state.treeDatas[0]);
                  const url = this.data.url.R ;
                  var para = {parent_id: 0};
                  this.$emit("node-click",para);
                 return  new Promise((resolve, reject) => {
                      this.fetch(url, para)
                          .then(data => {
                              console.log(data)
                              this.treeData = data;
                              resolve(data);
                          });
                  })
              }
          }
      },
    methods: {
      /* 加载子分类 */
      loadTreeNode (treeItem, resolve) {
         const url = this.data.url.R ;
         if(treeItem.id != null && treeItem.id != "" && treeItem.id != 0 && treeItem.data.id != null){
             var para = { parent_id: treeItem.data.id };
            this.$emit("node-click",para);
         }
//         else if(this.amsg >=0 && this.amsg != null && this.amsg != ''){
//             var para = { parent_id: this.amsg };
//             this.$emit("node-click",para);
//         }
         else if(treeItem.data.id == null){
             var para = {parent_id: 0};
             this.$emit("node-click",para);
         }
         else{
             var para = {parent_id: treeItem.id};
             this.$emit("node-click",para);
         }
         this. fetch(url, para)
             .then(data => {
               resolve(data)
             });
      },
      /* 点击响应时间 */
      handleNodeClick (args) {
        this.$emit('node-click', args.id);
          this.$store.dispatch('sendA',args.id);
          this.$store.dispatch('sendB',args.id);
      },
      /* 构建分类title及工具 */
      nodeRender (h, { _self, node, data }) {
      	let level;
      	if(node.level<=5)
      	{
      		level = node.level;
      	}
      	else
      	{
      		level = 5;
      	}
        
        // @todo: 使用jsx插件更好理解
        const childrenNodes = data.id === 0 ? [h('span', data.name)] : [
        
        

            h('svg', {
                'class': 'icon',attrs:{'aria-hidden':'true'},domProps:{title:data.name},style:{'vertical-align':'baseline'}},[h('use',{attrs:{'xlink:href':'#icon-wenjianjiaweigongxiang'}})]
            ),
            

          h('span',
          {'class':'spanEllipsis',domProps:{title:data.name},style:{width:150-level*18+'px','margin-left':'0.1em'}},
          data.name),
          h('span',
            {
              'class': 'kz-tree-bar'
            },
            [
              // 编辑
              h('i', {
                'class': 'el-icon-edit',
                on: {
                  click: function (event) {
                    event.stopPropagation()
                      if(data.id == _self.$store.state.privateDirId.data.id || data.id == _self.$store.state.publicDirId.data.id){
                          _self.$message({ message: '该模型分类不允许修改', type: 'warning',duration: 2000 })
                      }else {
                          typeof _self.treeEdit === 'function' && _self.treeEdit(data, event, node)
                      }
                  }
                }
              }),
              // 添加
              h('i', {
                'class': 'el-icon-plus',
                on: {
                  click: function (event) {
                    event.stopPropagation()
                    if(node.level>=6)
                    {
                    	 _self.$message({ message: '模型目录层级不得超过六级', type: 'warning',duration: 2000 })
                    }
                    else
                    {
                    	 typeof _self.treeAdd === 'function' && _self.treeAdd(data, event, node)
                    }
                   
                  }
                }
              }),
              // 删除
              h('i', {
                'class': 'el-icon-close',
                props: {
                  'v-popover': 'delTreeConfirm'
                },
                on: {
                  click: function (event) {
                    event.stopPropagation()
                      if(data.name == _self.$store.state.privateDirId.data.name || data.name == _self.$store.state.publicDirId.data.name){
                          _self.$message({ message: '该模型分类不允许删除', type: 'warning',duration: 2000 })
                      }else{
                          typeof _self.treeDelete === 'function' && _self.treeDelete(data, event, node)
                      }
                  }
                }
              })
            ]
          )
        ]
      
       
         return h(
          'div',
          {
            'class': 'el-tree-node__label',
            prop: {
              children: '-'
            }
          },
          childrenNodes
        )
  

       
      },
      /* 添加 */
      treeAdd (treeItem, event, node) {
        this.__currentNode = node
        Object.assign(this.dialog, {
          title: '增加分类',
          dialogVisible: true,
          form: {
            name: '',
            id: '',
            parent_id: treeItem.id
          }
        })
      },
      /* 编辑 */
      treeEdit (treeItem, event, node) {
        this.__currentNode = node
        Object.assign(this.dialog, {
          title: '编辑分类',
          dialogVisible: true,
          form: {
            name: treeItem.name,
            id: treeItem.id,
            parent_id: treeItem.parent_id
          }
        })
      },
      /* 删除 */
      treeDelete (treeItem, event, node) {
        const fetchDelOk = () => {
          // ajax
          const url = this.data.url.D
          this.fetch(url, { id: treeItem.id }, 'post').then(data => {
            // 使用实例对象的removeChild方法
            // hack
            // https://github.com/ElemeFE/element/blob/dev/packages/tree/src/model/node.js#L187
            try {
              node.parent.removeChild(node)
                var url = this.data.url.R
                this.fetch(url,node.data.parentId)
            } catch (err) { console.error(err) }
            // 提示结果
            this.$message({ message: '删除成功', type: 'success',duration: 2000 })
          })
        }
        // 询问提示
        this.$confirm('是否删除此分类?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
            closeOnClickModal:false,
        }).then(fetchDelOk).catch(e => e)
      },
      /* ajax封装 */
        fetch (url, data, type = 'POST') {
            const success = (data, resolve, reject) => {
                if (data.status === 1) {
                    resolve(data.data)
                } else {
               //     console.error(data.data.code+":"+ data.data.message)
                    reject(data)
                }
            }
            return new Promise((resolve, reject) => {
                if (type.toLowerCase() === 'post') {
                    if (data) {
                        var dataUrl = Object.keys(data).map(item => item+"="+data[item])
                        url = url + (url.indexOf('?') > -1 ? '' : '?') + dataUrl.join('&')
                    }
                    this.$http.post(url)
                        .then(res => res)
                        .then(data => success(data.data, resolve, reject))
                } else {
                    if (data) {
                       var dataUrl = Object.keys(data).map(item => item+"="+data[item])
                        url = url + (url.indexOf('?') > -1 ? '' : '?') + dataUrl.join('&')
                }

                    this.$http.get(url)
                        .then(res => res)
                        .then(data => success(data.data, resolve, reject),
                        )
                }
            })
        },
      /* ######## */
      /* 其他 */
      cancelSubmit () {
        this.dialog.dialogVisible = false
        this.$refs.dialogForm.resetFields()
      },
      submitForm () {
        this.$refs.dialogForm.validate((valid) => {
          if (valid) { // 验证通过
              if(this.$refs.dialogForm.model.parent_id == null){
                  this.$refs.dialogForm.model.parent_id = -1;
              }
              if(this.__currentNode.data.name == this.$refs.dialogForm.model.name ){
                 this.dialog.dialogVisible = false;
              }
              else{
              var checkDirNameUrl = '/api/directory/checkDirName?parentId=' + this.__currentNode.data.parentId +'&dirName='+ this.$refs.dialogForm.model.name +'&dirId='+ this.$refs.dialogForm.model.id
                  +'&dirParentId='+ this.$refs.dialogForm.model.parent_id +'&userName='+ this.$store.state.userInfo.profile.name
              var _this = this;
              _this.$http.post(checkDirNameUrl)
                  .then(function (response) {
                      if(response.data.state == 1){                     	
                      		_this.fetchAddTreeNode()
                      }else{
                          _this.$message({
                              message: '名称重复,请重新输入模型分类名称！',
                              type: 'warning',
                              duration: 2000
                          });
                      }
                  })
                  .catch(function (error) {
                      console.log(error)
                      _this.$message({
                          message: '请重新输入模型分类名称！',
                          type: 'warning',
                          duration: 2000
                      });
                  })
              }
          } else {
            return false
          }
        })
      },
      fetchAddTreeNode () {
        const url = this.dialog.form.id ? this.data.url.U : this.data.url.C
        this.dialog.submiting = true
        this.fetch(url, this.dialog.form, 'post')
            .then(data => {
              /* 隐藏dialog */
              Object.assign(this.dialog, {
                submiting: false,
                dialogVisible: false
              })
              this.$refs.dialogForm.resetFields()
              /* 提示结果 */
              const message = this.dialog.form.id ? '编辑成功' : '添加成功'
              this.$message({ message: message, type: 'success' })

              if (this.dialog.form.id) { // 编辑
                this.__currentNode && this.$set(this.__currentNode, 'data', data)
              } else { // 新增
                /* treeNode api */
                if (this.__currentNode) { // 子分类添加子类
                  this.__currentNode.doCreateChildren([data])
                } else if (data.parentId === "0") { // 顶级添加子类
                  this.$refs.kzTree.root.doCreateChildren([data])
                }
              }
            })
      },
    },
      components:{
          modelList,
          WaterfallSlot,
          ModelContent
      },

  }
</script>

<style >
  .kz-tree__wrapper {
    min-width: 250px;
    text-align: left;
  }
  .kz-tree__top {
    padding: 10px;
    text-align: right;
    border-bottom: 1px solid #ddd;
  }
  .el-tree-node {
    position: relative;
  }
  .el-tree-node__content:hover .kz-tree-bar {
    display: block;
  }
  .kz-tree-bar {
    display: none;
    position: absolute;
    top: 0;
    right: 10px;
    font-size: 12px;
  }

  .kz-tree-bar > i {
    margin: 0 5px;
    color: #999;
  }
  .kz-tree-bar > i:hover {
    color: #20a0ff;
  }
  .el-tree {
  border: none;
  }


  .iconfont{
    font-family:"iconfont";
    font-size:24px;
    font-style:normal;
  }
</style>
