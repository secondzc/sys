<template>
  <section style="height: inherit;overflow-y: hidden;overflow-x: auto;width: inherit;display: flex;flex-direction: column;">

<!--工具条-->




   <!--  <el-col :span="24" class="toolbar" style="padding-bottom:0px;"> -->
  
      <div style="height: 50px;margin-top: 10px;min-width: 500px;">
          <el-form :inline="true" :model="filters" >
  <!--     
        <el-form-item>
          <el-input v-model="filters.name" placeholder="用户名/全名/邮箱"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" v-on:click="query">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" v-on:click="reset">重置</el-button>
        </el-form-item> -->
        
        <el-form-item>
          <el-button type="primary" size="small" @click="handleAdd"   >新增</el-button>
        </el-form-item>

      </el-form>
      </div>
    
      <hr/>
     

  <div  style="display: flex;height: inherit;">
    <el-tree :data="data3"   node-key="id"  
    ref="tree1"  highlight-current :props="defaultProps1"    @node-click="getUsers1"
     :expand-on-click-node="false"   :default-expand-all="true"   :render-content="nodeRender"  style="width: 300px;">
    </el-tree>
     
      <!--列表-->
    <el-table :data="users1" highlight-current-row  center    height="100%"@selection-change="selsChange1" class="tableWrapper">
      <el-table-column type="selection" width="55">
      </el-table-column>
  
      
 
      <el-table-column prop="name" label="用户名" min-width="120" sortable>
      </el-table-column>
      <el-table-column prop="name" label="全名" min-width="120" sortable>
      </el-table-column>
       <el-table-column prop="departName" label="部门" min-width="120" sortable  >
      </el-table-column>
     


      <el-table-column label="操作" width="500"     >
        <template slot-scope="scope">

       
   <el-radio-group v-model="scope.row.tempMode" :disabled="!scope.row.tempBool" >
    <el-radio :label="1" >可读</el-radio>
    <el-radio :label="2" >读/写</el-radio>
    <el-radio :label="3" >完全</el-radio>
  </el-radio-group>

    <el-button-group style="margin-left: 10px;">
         <el-tooltip class="item" effect="dark" content="编辑" placement="bottom">
  <el-button type="primary" size="mini" icon="el-icon-edit" @click="scope.row.tempBool=!scope.row.tempBool" :disabled="scope.row.tempBool"></el-button>
</el-tooltip>
   <el-tooltip class="item" effect="dark" content="提交" placement="bottom">
  <el-button type="primary" size="mini" icon="el-icon-upload2" :disabled="!scope.row.tempBool" @click="editSubmit(scope.$index, scope.row)" ></el-button>
</el-tooltip>
</el-button-group>


   
         
        </template>
      </el-table-column>
    </el-table>

  </div>

     
 <div>
  
  <div style="display: flex;height: 40px;margin-top: 10px;margin-bottom: 10px;width: 100%;text-align: justify;justify-content: space-between;">

      <div >
      <el-button type="danger" size="small" @click="batchRemove" :disabled="this.sels1.length===0">批量删除</el-button>
 
    </div>
     
    <div >
      <el-pagination  
      @size-change="handleSizeChange1"
      @current-change="handleCurrentChange1"
      :current-page="pager1.pageIndex"
      :page-sizes="[10, 30, 50, 100]"

      :page-size="pager1.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager1.total">
    </el-pagination>
    </div>
     
  
  </div>
     
   

 </div>

  
    

  

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" v-if="addFormVisible"   
     width="80%"  :close-on-click-modal="false"   >
      <hr/>
        <div style="display: flex;min-height: 300px;">
           
        <el-tree :data="data4" :props="defaultProps1"  :default-expand-all=true  :expand-on-click-node=false  @node-click="getUsers" ref="tree2" style="max-width: 200px;"></el-tree>
     
      <!--列表-->
        <el-table :data="users" highlight-current-row  center   height="100%" @selection-change="selsChange">
         <el-table-column type="selection" width="55">
         </el-table-column>
      
      <!--    <el-table-column prop="id" label="ID" width="80" sortable>
        </el-table-column> -->
        <el-table-column prop="name" label="用户名" min-width="120" sortable>
        </el-table-column>
         <el-table-column prop="departName" label="部门" min-width="120" sortable>
         </el-table-column>

      <el-table-column label="权限级别" width="500"   >
        <template slot-scope="scope">

       
   <el-radio-group v-model="scope.row.tempMode"  >
    <el-radio :label="1" >可读</el-radio>
    <el-radio :label="2" >读/写</el-radio>
    <el-radio :label="3" >完全</el-radio>
  </el-radio-group>


   
         
        </template>
      </el-table-column>
       </el-table>

        </div>
       






    <div style="display: flex;height: 40px;margin-top: 10px;margin-bottom: 10px;width: 100%;">
    <div >
      <el-pagination   small
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageIndex"
      :page-sizes="[10, 30, 50, 100]"

      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next"
      :total="pager.total">
    </el-pagination>
    </div>
   
     
  </div>

  

     


      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading"  :disabled="this.sels.length==0">提交</el-button>
      </div>
    </el-dialog>
  

    



  </section>
</template>

<script>
  import util from '../../common/js/util'

  //import NProgress from 'nprogress'
  import { getUserListPage } from '../../api/api';

  export default {
  
    data() {
      return {
        oldEmail:'',
        props:{value:'id',label:'name',children:'children'},
        options:[],
        departs:[],
        data4:[],
        data2:[],
        defaultProps: {
        children: 'children',
        label: 'authName'
        },

       auths:[],    

        permission:{},
      
        props: {
          label: 'name',
          children: 'children',
          value:'id'

        },

         ids:{
          authIds:[],
          uid:'',
          uids:[],
          roleId:'',
          directoryIds:[]
        },
        permissionVisible:false,
        permissionLoading:false,

        search:{
          label:'realName',
          value:'realName'
        },
         pager:{
          total:1,
          pageSize:10,
          pageIndex:1,
          currentPage:''
        },
        pager1:{
          total:1,
          pageSize:10,
          pageIndex:1,
          currentPage:''
        },
        filters: {
          name: ''
        },
        filters1:{
          departId:'',
          directoryId:''
        },
        state1:'',
       userSearch:[],
        abc:{
          a:true,
          b:true,
        },
        users: [],
        users1:[],

        listLoading: false,
        sels: [],//列表选中列
        sels1:[],
       
        editFormVisible: false,//编辑界面是否显示
        editLoading: false,
       
     

        addFormVisible: false,//新增界面是否显示
        addLoading: false,
      
        roleVisble:false,
        roleLoading:false,
        roles:[],
        roles1:[],
        userRole:{
           assigned:[],
           uid:'',
           uids:[]
        }
        ,
        directory:{},
        data3:[],
        modelVisible:false,
        modelLoading:false,
        model:false,
        defaultProps1:{
          label:'name',
          children:'children'
        }

      }
    },
    methods: {

       filterTag(value, row) {
        console.log(value);
        console.log(row);
        return row.tempMode === value;
      },

        

      nodeRender (h, { _self, node, data }) {
        // @todo: 使用jsx插件更好理解
        const childrenNodes = data.id === 0 ? [h('span', data.name)] : [
          h('i', {
                'class': data.icon,
              
              }),
          h('span', data.name),
          h('span',
            {
              'class': 'kz-tree-bar'
            },

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

      handleChange(value) {
        console.log(value);
        console.log(this.$refs.editDialog);
      },

      getCheckedNodes() {
        return this.$refs.tree.getCheckedNodes();
      },
      setCheckedNodes(nodes) {
         this.$refs.tree.setCheckedNodes(nodes);
      },
      reset(){
           this.filters={};
           this.getUsers();
      },
       handleCurrentChange(val) {
        this.pager.pageIndex = val;
        this.getUsers();
      },
      handleSizeChange(val){
        this.pager.pageSize = val;
        this.getUsers();
      },
       handleCurrentChange1(val) {
        this.pager1.pageIndex = val;
        this.getUsers1();
      },
      handleSizeChange1(val){
        this.pager1.pageSize = val;
        this.getUsers1();
      },
      searchSelect(){
         this.getUsers();

      },

    getUsers1(node)
      {
        
         var _this = this;
      
         if(node)
         {
            _this.filters1.directoryId=node.id;
         }
          let para = Object.assign({},_this.filters1,_this.pager1);
          _this.$http({method:'post',
            url:'/api/user/list1',
            data:para})
              .then(function (response) {
                  // _this.users1 = response.data.users;
                  _this.pager1.total=response.data.total;


                  for(let i = 0 ; i<response.data.users.length;i++)
                  {
                      let b =  response.data.users[i].directoryAuth;
                       for(var c of b)
                    {

                      if (c.directory_id == _this.filters1.directoryId)
                      {
                        // a.tempMode=c.mode;
                       response.data.users[i]  = Object.assign({},response.data.users[i] ,{
                      // tempMode : c.mode
                      tempMode : c.mode
                    });
                      }
                    }

                    response.data.users[i]  = Object.assign({},response.data.users[i] ,{
                      tempBool : false
                    });
                  }
                  _this.users1 = response.data.users;


                 
               

                
          })
          .catch(function (error) {
              console.log(error);
          });

      },
          getUsers(node)
      {

         var _this = this;
         console.log(node);
         if(node)
         {
            _this.filters1.departId=node.id;
         }
         else
         {
           _this.filters1.departId=1;
         }
       
          let para = Object.assign({},_this.filters1,_this.pager);
          _this.$http({method:'post',
            url:'/api/user/list2',
            data:para})
              .then(function (response) {
                  _this.users = response.data.users;
                  _this.pager.total=response.data.total;
                  // console.log(_this.users);
                  // console.log(_this.pager.total);


          })
          .catch(function (error) {
              console.log(error);
          });

      },


      getGroups() {
         var _this = this;
          _this.$http.post('/api/auth/list')
              .then(function (response) {
                  _this.data2  = response.data.group;
          })
          .catch(function (error) {
              console.log(error);
          });
      },

      //删除
      handleDel: function (index, row) {
        console.log(index);
        this.$confirm('确认删除该用户吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { id: row.id };
          this.$http({
            url:'/api/user/deleteGUser',
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
           
            this.getUsers1();
          });
        }).catch(() => {

        });
      },
      //显示编辑界面

      handleRole:function(index,row){
     
         
        this.userRole.assigned = row.roles;
        this.userRole.uid=row.id;
  
        this.roleVisble=true;
       
      },
      handleRoleBatch:function(){
         this.userRole={
             assigned:[],
             uid:''
          }
     
         this.roleVisble=true;
       
      },
        handleAuth(index,row)
      {

         this.ids.uid=row.id;
         this.permissionVisible=true;
         let temp =  [];

         row.auths.forEach(x=>temp.push(x.authId));
         console.log(temp);
         this.authTree=temp;
      },
     handleAuthBatch()
      {

         this.permissionVisible=true;
         this.authTree=[];
       
      },
      modelAuth(index,row)
      {
        console.log(row.modelAuth);
         this.ids.uid=row.id;
         this.modelVisible = true;

            let temp =  [];

         row.directoryAuth.forEach(x=>temp.push(x.directoryId));
         console.log(temp);
         this.modelTree=temp;

      },
      modelAuthBatch()
      {
      
         this.modelVisible = true;
         this.modelTree=[];

      },
      //显示新增界面
      handleAdd: function () {
        if(this.filters1.directoryId>1)
        {
         this.getUsers();
        this.addFormVisible = true;
        }
        else
        {
            this.$alert('请选择模型目录', '提示', {
          confirmButtonText: '确定',
      
       
        
        });
        }
        this.sels=[];

     
      },
       handleEdit: function (index, row) {
        this.editFormVisible = true;

        this.editForm = Object.assign({}, row);
        this.oldEmail=row.email;
      },
    
     
      //新增
      addSubmit: function () {
   
          
              this.addLoading = true;
              //NProgress.start();
              
            // let a = this.sels;
            
            let b = [];

            for(var a of this.sels)
            {
               let c = {uid:'',directoryId:'',mode:''};
               c.uid= a.id;
               c.directoryId= this.filters1.directoryId;
               c.mode = a.tempMode;
               b.push(c);
            }
            console.log(b);
            let para = b;


         
            
              this.$http({method:'post',
                url:'api/DirectoryAuth/add',
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
            
                this.addFormVisible = false;
                this.getUsers1();
              });        
         
     
      },
        editSubmit(index, row)
      {
         let para = {uid:'',directoryId:'',mode:''};
         para.uid = row.id;
         let node = this.$refs.tree1.getCheckedNodes();
         para.directoryId= this.filters1.directoryId;
         para.mode=row.tempMode;
           this.$http({
            url:'/api/DirectoryAuth/update',
            method:'post',
            data:para
          }).then((res) => {

            this.listLoading = false;
            //NProgress.done();
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
           
            // this.getUsers();
            row.tempBool=!row.tempBool;
          });
     
      },

  
      selsChange: function (sels) {
        this.sels = sels;
        console.log(sels);
      },
       selsChange1: function (sels) {
        this.sels1 = sels;
          console.log(sels);
      },
      //批量删除
      batchRemove: function () {


            let b = [];

            for(var a of this.sels1)
            {
               let c = {uid:'',directoryId:'',mode:''};
               c.uid= a.id;
               c.directoryId= this.filters1.directoryId;
               b.push(c);
            }
            console.log(b);
            let para = b;



      

        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          this.$http({url:'/api/DirectoryAuth/delete',method:'post',data:para})
          .then((res) => {
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
           
            this.getUsers1();
          });
        }).catch(() => {

        });
      },
       getDeparts()
      {
          
          var _this = this;
          _this.$http.post('api/depart/list')
              .then(function (response) {

                _this.departs = response.data.depart;
                _this.options = response.data.depart;
                _this.data4 = response.data.depart;
            

          })
          .catch(function (error) {
              console.log(error);
          });
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

    



    },
    mounted() {
      // this.getUsers1();
      // this.getUsers();
      // this.getRoles();
      // this.getGroups();
      this.getDeparts();
      this.getDirectoryTree();
   
    }
  }



</script>


<style scoped>
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
  .tableWrapper{
    width: 100%;
/*     height: calc(100%-100px)!important;
     height: -moz-calc(100% - 100px)!important;
    height :-webkit-calc(100% - 100px)!important; */


   }
</style>
