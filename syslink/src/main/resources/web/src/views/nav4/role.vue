<template>
  <section>
    <!--工具条-->
    <!-- <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" style="height:50px;">
        <el-form-item>
          <el-button size="small" type="primary" @click="handleAdd"  >新建</el-button>
          <el-button size="small"  @click="handleEdit"  :disabled="this.currentRow===null">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel" :disabled="this.currentRow===null">删除</el-button>
        </el-form-item>
      </el-form>
       
    <!-- </el-col> -->
    <hr/>

    <!--列表-->
    <el-table :data="roles" highlight-current-row        style="width: 100%;"  @current-change="handleCurrentChange">
    <!--   <el-table-column type="selection" width="55">
      </el-table-column> -->

      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="name" label="名称" min-width="120" sortable>
      	<template slot-scope="scope">
          <span style="min-width:120px" v-bind:title="scope.row.name"  class="spanEllipsis">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" min-width="160" sortable  :formatter="dateFormatter"  >
       </el-table-column>
      <el-table-column prop="description" label="描述" min-width="160" sortable  >
      	<template slot-scope="scope">
          <span style="min-width:160px" v-bind:title="scope.row.description"  class="spanEllipsis">{{ scope.row.description }}</span>
        </template>
       </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
      <!--    <el-button size="small" type="primary"  @click="handlePermission(scope.$index, scope.row)">分配权限</el-button>
 -->
              <el-dropdown>
  <el-button type="primary" size="small">
    选项<i class="el-icon-caret-bottom el-icon--right"></i>
  </el-button>
  <el-dropdown-menu slot="dropdown">



    <el-dropdown-item style="width: inherit;" > 

  
      <div @click="handlePermission(scope.$index, scope.row)" style="font-size: 12px;text-align: center;color: #66b1ff">
        <span>
          分配权限
        </span>
      </div>
       </el-dropdown-item>
        <el-dropdown-item style="width: inherit;" > 
  
      <div @click="modelAuth(scope.$index, scope.row)" style="font-size: 12px;text-align: center;color: #66b1ff">
        <span>
         目录控制
        </span>
      </div>
       </el-dropdown-item>

  </el-dropdown-menu>
  </el-dropdown>

        </template>
      </el-table-column>
    </el-table>

   

    <!--编辑角色界面-->
    <el-dialog title="编辑角色" :visible.sync="editFormVisible" v-if="editFormVisible" :close-on-click-modal="false" 
       >
        <el-form :model="editForm" label-width="80px" ref="editForm"   :rules="editFormRules"   >
        <el-form-item label="名称" prop="name"  >
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
         <el-form-item label="描述" prop="description"   >
          <el-input   type="textarea"   :autosize="{ minRows: 4, maxRows: 10}"  placeholder="请添加相关描述" v-model="editForm.description" auto-complete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>
  

    <!--新增角色界面-->
    <el-dialog title="新建角色" :visible.sync="addFormVisible" v-if="addFormVisible" :close-on-click-modal="false"   >
      <el-form :model="addForm" label-width="80px"  ref="addForm"  :rules="addFormRules"  >
        <el-form-item label="名称" prop="name"   >
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
         <el-form-item label="描述" prop="description" >
          <el-input v-model="addForm.description" type="textarea"  :autosize="{ minRows: 4, maxRows: 10}"  placeholder="请添加相关描述"  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>




    
   <el-dialog title="分配权限" :visible.sync="permissionVisible" v-if="permissionVisible" ref="permissionDialog" :close-on-click-modal="false" :show-close="false" >
   <el-form :model="permission" label-width="80px"  ref="permissionForm"    >

       <el-tree :data="data2" show-checkbox  node-key="authId"  ref="tree"  highlight-current :props="defaultProps"  :default-checked-keys="roleTree">
    </el-tree>


   
      </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click.native="permissionVisible=false">取消</el-button>
        <el-button type="primary" @click.native="permissionSubmit" :loading="permissionLoading">提交</el-button>
    </div>
    </el-dialog>



    <el-dialog title="目录控制" :visible.sync="modelVisible" v-if="modelVisible"   >

 <!--    <div slot="title">    -->
    <el-form :model="directory" label-width="100px"  ref="directoryForm"    >

    <el-tree :data="data3" node-key="id"  
    ref="tree1"  highlight-current :props="defaultProps1"  
    :default-checked-keys="modelTree"   :default-expand-all="true"
    :expand-on-click-node="false"   :render-content="nodeRender">
    </el-tree>

    </el-form>
    <!-- </div> -->
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="modelVisible=false">取消</el-button>
        <el-button type="primary" @click.native="modelAuthSubmit" :loading="modelLoading">提交</el-button>
      </div>

    </el-dialog>

   
  </section>
</template>

<script>
  import util from '../../common/js/util'
  const   cityOptions=[9,10];
  //import NProgress from 'nprogress'
  export default {
    data() {
        var validateName = (rule, value, callback) => {
        let re = new RegExp("^[ a-zA-Z0-9_\u4e00-\u9fa5]+$");
        console.log(value);

        if(!value)
        {
            callback(new Error('请输入角色名称'));
        }
        else
        {
          if (re.test(value))
          {
             
             let para = {name:''};
              para.name=value;
            
              this.$http({
                url:'/api/role/nameExist',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
                if(res.data.flag)
                {
                  callback(new Error('角色名称重复'));
                }
                else
                {
                   callback();
                }
                
              });
          

               
          } 
          else
          {
              callback(new Error('只允许输入中文、英文、数字、下划线及空格'));
          }
        }
      };
        var validateName1 = (rule, value, callback) => {
        console.log(this.editForm.name);
        let re = new RegExp("^[ a-zA-Z0-9_\u4e00-\u9fa5]+$");
        console.log(value);

        if(!value)
        {
            callback(new Error('请输入角色名称'));
        }
        else
        {
          if (re.test(value))
          {

            if(value==this.roleName)
            {
              callback();
            }
            else
            {
               let para = {name:''};
              para.name=value;
            
              this.$http({
                url:'/api/role/nameExist',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
                if(res.data.flag)
                {
                  callback(new Error('角色名称重复'));
                }
                else
                {
                   callback();
                }
                
              });
          
            }
             
            

               
          } 
          else
          {
              callback(new Error('只允许输入中文、字母、数字、下划线及空格'));
          }
        }
      };
      return {
        currentRow:null,
        roleName:'',
        addFormRules:{
          name:[
            {validator:validateName,trigger:'blur'}
          ]
        },
         editFormRules:{
          name:[
            {validator:validateName1,trigger:'blur'}
          ]
        },

        data2:[],
           defaultProps: {
          children: 'children',
          label: 'authName'
        },

       auths:[],    
        ids:{
        authIds:[],
        roleId:''
        },
        
        addItem:{
          authName:'',
          authCode:'',
          description:''
        },
        editItem:{
          authName:'',
          authCode:'',
          description:''
        },
        groups:[],
     
        roles:[],
        listLoading: false,
        sels: [],//列表选中列
        perCheck:[],//权限列表选中列

        editFormVisible: false,//编辑界面是否显示
        editLoading: false,
        addItemLoading:false,
        addItemVisible:false,
        dialogVisible:false,
        editItemLoading:false,
        editItemVisible:false,

        permissionVisible:false,
        permissionLoading:false,

        modelVisible:false,
        modelLoading:false,
          defaultProps1:{
          label:'name',
          children:'children'
        },
         data3:[],
         modelTree:[],
          directory:{},
       
        //编辑界面数据
        editForm: {
     //     id: 0,
          name: '',
          description: '',
          
        },
        permission:{},
      
        addFormVisible: false,//新增界面是否显示
        addLoading: false,
       
        //新增界面数据
        addForm: {
         name:'',
         description:''

        },
         roleTree:[]
        ,   
      }
    },
    methods: {


        nodeRender(h, { node, data, store }) {
             if(data.parentId!=0)
            {
                return (
          <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
            <span>
            <i class ="iconfont icon-wenjianjia" />
              <span>{node.label}</span>
            </span>
            <span>
            <el-radio-group v-model={data.mode}>
            <el-radio  label={1}>可读</el-radio>
            <el-radio label={2}>读/写</el-radio>
            <el-radio label={3}>完全</el-radio>
            </el-radio-group>
            <el-button  size="small" type="text" icon="el-icon-close" on-click={ () => this.modeClear(data) }  style="margin-left:5px;"></el-button>
            </span>
          </span>  
          );
            }
            else
            {
                   return (
          <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
            <span>
            <i class ="iconfont icon-wenjianjia" />
              <span>{node.label}</span>
            </span>
          
          </span>  
          );
            }


      
      },
       modeClear(data)
      {
          data.mode=0;
      },
        modelAuth(index,row)
      {
        


         this.ids.roleId=row.id;
         this.modelVisible = true;

         let temp =  [];

         row.directoryAuth.forEach(x=>temp.push(x.directory_id));
         this.modelTree=temp;
         this.setMode(this.data3,row.directoryAuth);
         // console.log(this.data3);
         // console.log(row.directoryAuth);
      

      },


        selsChange: function (sels) {
        this.sels = sels;
        console.log(sels);
      },
        handleCurrentChange(val) {
        this.currentRow = val;
      },

      getCheckedNodes() {
        return this.$refs.tree.getCheckedNodes();
      },
      setCheckedNodes(nodes) {
         this.$refs.tree.setCheckedNodes(nodes);
      },
 
      getRoles() {
         var _this = this;
          _this.$http.post('/api/role/list')
              .then(function (response) {
                  _this.roles = response.data.role;
                 
              
          })
          .catch(function (error) {
     //         console.log(error);
          });
      },
      getGroups() {
         var _this = this;
          _this.$http.post('/api/auth/list')
              .then(function (response) {
                  _this.groups = response.data.group;
                  _this.data2  = response.data.group;
               


          })
          .catch(function (error) {
              console.log(error);
          });
      },


 
      //删除
      handleDel: function () {
      	
      	if(	this.currentRow.id==1)
      	{
      	 this.$message({
              message: '管理员角色不允许删除',
              type: 'error'
              });
      	
      	}
      	else
      	{
      		 this.$confirm('确认删除该角色吗?', '提示', {
          type: 'warning',
          closeOnClickModal:false
        }).then(() => {
          this.listLoading = true;
          let para = { id: this.currentRow.id};
          this.$http({
            url:'/api/role/delete',
            method:'post',
            data:para
          }).then((res) => {

            this.listLoading = false;
            //NProgress.done();
            if(res.data.flag)
            {
               this.$message({
              message: '删除成功',
              type: 'success'
            });
            }
            else
            {
              this.$message({
              message: '删除失败',
              type: 'error'
              });
            }
            this.getRoles();
          });
        }).catch(function (error) {
          this.listLoading = false;
          console.log(error);
          });
      	}
      	
       
      },

      modelAuthSubmit()
      {
       

        let para = {directoryAuth:'',roleId:''};
        para.directoryAuth=this.data3;
        para.roleId=this.ids.roleId;
         


        this.$http({method:'post',
                url:'api/DirectoryAuth/roleAdd',
                data:para}).then((res)=>{
                this.addLoading = false;
                if(res.data.flag)
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'success'
                });
                }
                else
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'error'
                });

                }
            
                this.modelVisible = false;
                 this.getRoles();
              }).catch(function (error) {
              	 this.addLoading = false;
                 console.log(error);
          });      


        





      },
      setMode(data3,directoryAuth)
      {
         for(var a of data3 )
         {

           let f = false;

             for(var b of directoryAuth)
            {
              if(a.id==b.directory_id)
              {
                 f= true;
                if(b.mode>0)
                {
                  a.mode=b.mode;
                }
                else
                {
                  a.mode=0;
                }
              }
            }
            if(!f)
            {
             a.mode=0;
            }
           if(a.children)
           {
            this.setMode(a.children,directoryAuth);
           }
         }
      },

       getDirectoryTree()
      {
          
          var _this = this;
          _this.$http.post('api/directory/getDirectoryTree')
              .then(function (response) {

                _this.data3 = response.data.directoryTree;
            
          })
          .catch(function (error) {
              console.log(error);
          });
      },
     
      //显示编辑界面
      handleEdit: function () {
        this.editFormVisible = true;
        this.roleName = this.currentRow.name;
        this.editForm = Object.assign({}, this.currentRow);

      
      },
    
      //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm = {
         name:'',
         description:''
          
        };
      },

      handlePermission(index,row)
      {

         this.ids.roleId=row.id;
         this.permissionVisible=true;
        
            let temp =  [];

         row.auths.forEach(x=>temp.push(x.auth_id));
        this.roleTree=temp;
      //   this.setCheckedNodes(row.permissions);
       
      },
      //编辑
      editSubmit: function () {
        this.$refs.editForm.validate((valid) => {
          if (valid) {

              this.editLoading = true;
              let para = Object.assign({}, this.editForm);
          
              this.$http({
                url:'/api/role/edit',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
                if(res.data.flag)
                {
                  this.$message({
                  message: '编辑成功',
                  type: 'success'
                });
                }
                else
                {
                   this.$message({
                  message: '编辑失败',
                  type: 'error'
                });
                }
                
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getRoles();
              }).catch(function (error) {
              	 this.editLoading = false;
                 console.log(error);
          });
          }
        });
      },
       permissionSubmit: function () {


        this.$refs.permissionForm.validate((valid) => {
          if (valid) {
        
              this.permissionLoading = true;
  
              this.ids.authIds=this.getCheckedNodes();
              let para = Object.assign({}, this.ids);
   
              this.$http({
                url:'/api/role/assign',
                method:'post',
                data:para
              })
               .then((res) => {
                this.permissionLoading = false;
                if(res.data.flag)
                {
                  this.$message({
                  message: '编辑成功',
                  type: 'success'
                });
                }
                else
                {
                   this.$message({
                  message: '编辑失败',
                  type: 'error'
                });
                } 
                this.$refs['permissionForm'].resetFields();
                this.permissionVisible = false;
                this.getGroups();
                this.getRoles();
              }).catch(function (error) {
              	 this.permissionLoading = false;
                 console.log(error);
          });
           
          }
        });
      },
      //新增
      addSubmit: function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
          
              this.addLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.addForm);
              
              this.$http({method:'post',
                url:'api/role/add',
                data:para}).then((res)=>{
                this.addLoading = false;
                //NProgress.done();
                if(res.data.flag)
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'success'
                });
                }
                else
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'error'
                });

                }
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getRoles();
              });

          }
        });
      },
    dateFormatter:function(row,column){
        if(row.createDate!=null)
        {
          return util.formatDate.format(new Date(row.createDate), 'yyyy-MM-dd ')
        }
        

    
    
    },
    },
    mounted() {
      this.getRoles();
      this.getGroups();
      this.getDirectoryTree();
  
    }
  }



</script>


<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 120px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 20%;
  }
</style>
