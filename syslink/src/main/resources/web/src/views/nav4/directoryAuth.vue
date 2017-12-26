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
     

  
  

     
      
   


      <el-table-column label="操作" width="500" >
        <template slot-scope="scope">

       
   <el-radio-group v-model="scope.row.ttt" :disabled="!scope.row.fff" >
    <el-radio :label="1" >可读</el-radio>
    <el-radio :label="2" >可写</el-radio>
    <el-radio :label="3" >完全</el-radio>
  </el-radio-group>

    <el-button-group style="margin-left: 10px;">
  <el-button type="primary" size="mini" icon="el-icon-edit" @click="scope.row.fff=!scope.row.fff" :disabled="scope.row.fff"></el-button>
  <el-button type="primary" size="mini" icon="el-icon-share" :disabled="!scope.row.fff" @click="editSubmit(scope.$index, scope.row)" ></el-button>
</el-button-group>


   
         
        </template>
      </el-table-column>
    </el-table>

  </div>

     
 <div>
  
  <div style="display: flex;height: 40px;margin-top: 10px;margin-bottom: 10px;width: 100%;">
    <div style="min-width: 700px;">
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
     
    <div style="max-width: 100px;">
      <el-button type="danger" size="small" @click="batchRemove" :disabled="this.sels1.length===0">批量删除</el-button>
    <!--     <el-dropdown>
  <el-button type="primary" size="small">
    批量操作<i class="el-icon-caret-bottom el-icon--right"></i>
  </el-button>
  <el-dropdown-menu slot="dropdown">



   
        <el-dropdown-item style="width: inherit;" :disabled="this.sels.length===0" > 


      <div @click="batchRemove" style="font-size: 12px;text-align: center;color: red">
         <el-button  type="text"  
      style="width: inherit;" size="small" :disabled="this.sels.length===0">删除</el-button>
      </div>
       </el-dropdown-item>
        <el-dropdown-item style="width: inherit;" :disabled="this.sels.length===0"> 
  
     
      <div @click="handleRoleBatch"  style="font-size: 12px;text-align: center;color: red">
         <el-button  type="text"  
      style="width: inherit;" size="small" :disabled="this.sels.length===0">分配角色</el-button>
      </div>
       </el-dropdown-item>
        <el-dropdown-item style="width: inherit;" :disabled="this.sels.length===0"> 
  
     
      <div @click="handleAuthBatch" style="font-size: 12px;text-align: center;color: red">
         <el-button  type="text"  
      style="width: inherit;" size="small" :disabled="this.sels.length===0">分配权限</el-button>
      </div>
       </el-dropdown-item>
        <el-dropdown-item style="width: inherit;" :disabled="this.sels.length===0"> 
  
     
      <div @click="modelAuthBatch" size="small" style="font-size: 12px;text-align: center;color: red">
         <el-button  type="text"  
      style="width: inherit;" :disabled="this.sels.length===0">模型数据权限</el-button>
      </div>
       </el-dropdown-item>

 
  </el-dropdown-menu>
  </el-dropdown> -->
    </div>
     
  </div>
     
   

 </div>

  
    

    <!--编辑界面-->
    <el-dialog title="编辑用户信息" :visible.sync="editFormVisible" v-if="editFormVisible" :close-on-click-modal="false"  ref="editDialog" >
   
        <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm"    >
        <el-form-item label="用户名" prop="name"   >
          {{editForm.name}}
        </el-form-item>

         <el-form-item label="部门" prop="departId"   >
         <el-cascader    :options="options"  :props="props"  @change="handleChange" v-model="editForm.departId"   change-on-select   :show-all-levels="false" expand-trigger="hover">
        </el-cascader>
        </el-form-item>
        
        <el-form-item label="真实姓名" prop="fullName"   >
            <el-input v-model="editForm.fullName" auto-complete="off"></el-input>
        </el-form-item>
          <el-form-item label="邮箱" prop="email"   >
          <el-input v-model="editForm.email" auto-complete="off" ></el-input>
        </el-form-item>
         <el-form-item label="密码" prop="passwd"   >
            <el-input v-model="editForm.passwd" auto-complete="off" placeholder="将值留空使其保持不变"></el-input>
        </el-form-item>
      
        
        </el-form-item> -->
         <el-form-item  label-width="20px"  prop="prohibitLogin" style="margin-left: 20px;">
          <el-checkbox v-model="editForm.prohibitLogin">该账户被禁止登录</el-checkbox>
        </el-form-item>
 
         

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

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

      <el-table-column label="权限级别" width="500" >
        <template slot-scope="scope">

       
   <el-radio-group v-model="scope.row.ttt"  >
    <el-radio :label="1" >可读</el-radio>
    <el-radio :label="2" >可写</el-radio>
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
       
        //编辑界面数据
        editForm: {
         fullName:'',
         email:'',
         website:'',
         location:'',
         passwd:'',
         maxRepoCreation:'',
         isActive:'',
         isAdmin:'',
         prohibitLogin:'',
         allowGitHook:''
          
        },

        addFormVisible: false,//新增界面是否显示
        addLoading: false,
      
        //新增界面数据
        addForm: {
          name: '',
          email: '',
          passwd:'',
          checkPass: '',
          departId:''
         

        },
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
        querySearch(queryString, cb) {
        var userSearch = this.userSearch;
        var results = queryString ? userSearch.filter(this.createFilter(queryString)) : userSearch;
        console.log(results);
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (user) => {
          return (user.realName.indexOf(queryString.toLowerCase()) === 0);
        };
      },
    
  

      query() {
         var _this = this;
         let para = Object.assign({},_this.filters,_this.pager);
         console.log(this.filters);
          _this.$http({method:'post',
            url:'/api/user/query',
            data:para})
              .then(function (response) {
                  _this.users = response.data.users;
                  _this.pager.total=response.data.total;
          })
          .catch(function (error) {
              console.log(error);
          });
      },


    getUsers1(node)
      {

         var _this = this;
         if(node)
         {
            _this.filters1.directoryId=node.id;
         }
         else
         {
           _this.filters1.departId=1;
         }
       
          let para = Object.assign({},_this.filters1,_this.pager1);
          _this.$http({method:'post',
            url:'/api/user/list1',
            data:para})
              .then(function (response) {
                  _this.users1 = response.data.users;
                  _this.pager1.total=response.data.total;

                  for(var a of _this.users1)
                  {
                    let b = a.directoryAuth;
                    for(var c of b)
                    {
                      if (c.directoryId==node.id)
                      {
                        a.ttt=c.mode;
                      }
                    }
                    a.fff=false;
                  }
                   console.log(_this.users1);
                  // console.log(_this.pager.total);

                
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
               c.mode = a.ttt;
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
         para.mode=row.ttt;
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
            row.fff=!row.fff;
          });
     
      },

      roleSubmit:function(){
           
           
              this.roleLoading = true;
              if(this.sels.length>0)
              {
                let a =  [];

                    this.sels.forEach(x=>a.push(x.id));
                    this.userRole.uids=a;
                    
              }
              let para = Object.assign({},this.userRole);

              
              this.$http({method:'post',
                url:'api/user/assignRole',
                data:para}).then((res)=>{
                this.roleLoading = false;
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
           //     this.$refs['addForm'].resetFields();
                this.roleVisble = false;
                this.getUsers1();
              });
           
          
      },
    

      permissionSubmit: function () {


        this.$refs.permission.validate((valid) => {
          if (valid) {
        
              this.permissionLoading = true;

               if(this.sels.length>0)
              {
                let a =  [];

                this.sels.forEach(x=>a.push(x.id));
                this.ids.uids=a;
                    
              }
              let  b = this.getCheckedNodes();
              let c = [];
              b.forEach(x=>c.push(x.authId));
              this.ids.authIds = c;

              // this.ids.authIds=this.getCheckedNodes();
              let para = Object.assign({}, this.ids);
   
              this.$http({
                url:'/api/user/assign',
                method:'post',
                data:para
              })
               .then((res) => {
                this.permissionLoading = false;
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
                this.$refs['permission'].resetFields();
                this.permissionVisible = false;
                this.getGroups();
                this.getUsers1();
              });
           
          }
        });
      },
       modelAuthSubmit: function () {


        this.$refs.directoryForm.validate((valid) => {
          if (valid) {
        
              this.modelVisible = true;
                 if(this.sels.length>0)
              {
                let a =  [];

                this.sels.forEach(x=>a.push(x.id));
                this.ids.uids=a;
                    
              }
              let  b = this.$refs.tree1.getCheckedNodes();
              let c = [];
              b.forEach(x=>c.push(x.id));
              this.ids.directoryIds = c;
  
            


              let para = Object.assign({}, this.ids);
   
              this.$http({
                url:'/api/user/modelAuth',
                method:'post',
                data:para
              })
               .then((res) => {
                this.modelLoading = false;
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
                this.$refs['directoryForm'].resetFields();
                this.modelVisible = false;
                this.getUsers1();
              });
           
          }
        });
      },

      selsChange: function (sels) {
        this.sels = sels;
        console.log(sels);
      },
       selsChange1: function (sels) {
        this.sels1 = sels;
          console.log(sels1);
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
      handleDepart(row, rowIndex, $event)
      {
        console.log(row);
      //  console.log(rowIndex);
       console.log(rowIndex);
        console.log(event);
        console.log(event.target);
    

     //   this.getDeparts();
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
