<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.realName" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="reset">重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd"  >新增</el-button>
        </el-form-item>
      </el-form>
       <div class="sub-title">激活即列出输入建议</div>
    <el-autocomplete
      class="inline-input"
      v-model="filters.realName"
      :fetch-suggestions="querySearch"
      @select="searchSelect"
      :props="search"
      placeholder="请输入内容"
    ></el-autocomplete>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row  @selection-change="selsChange"     style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
        <el-table-column type="expand">
      <template slot-scope="props">
      
        <el-form label-position="left" inline class="demo-table-expand">

          <el-form-item label="用户名">
            <span>{{ props.row.userName }}</span>
          </el-form-item>
          <el-form-item label="姓名">
            <span>{{ props.row.realName }}</span>
          </el-form-item>
          <el-form-item label="性别"  >
            <span>{{ props.row.gender }}</span>
          </el-form-item>
          <el-form-item label="创建人">
            <span>{{ props.row.createUser }}</span>
          </el-form-item>
          <el-form-item label="所属部门">
            <span>{{ props.row.departmentName }}</span>
          </el-form-item>
          <el-form-item label="创建时间">
            <span>{{ props.row.createDate }}</span>
          </el-form-item>
          <el-form-item label="最后修改时间">
            <span>{{ props.row.updateDate }}</span>
          </el-form-item>
          <el-form-item label="状态">
            <span>{{ props.row.status }}</span>
          </el-form-item>
           <el-form-item label="手机">
            <span>{{ props.row.mobile }}</span>
          </el-form-item>
           <el-form-item label="邮箱">
            <span>{{ props.row.email }}</span>
          </el-form-item>
        </el-form>
      
      </template>
    </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="userName" label="用户名" min-width="120" sortable>
      </el-table-column>
      <el-table-column prop="realName" label="姓名" min-width="120" sortable>
       </el-table-column>
      <el-table-column prop="createUser" label="创建人" min-width="120" sortable>
       </el-table-column>
       <el-table-column prop="departmentName" label="所属部门" min-width="120" sortable>
       </el-table-column>
       <!--
      <el-table-column prop="createDate" label="创建时间" min-width="160" sortable  >
       </el-table-column>
      <el-table-column prop="updateDate" label="最后修改时间" min-width="160" sortable  >
       </el-table-column>
       -->
      <el-table-column prop="status" label="状态" width="120" sortable>
      </el-table-column>


     
      <el-table-column prop="gender" label="性别" width="100"  sortable >
      </el-table-column>
      <!--
      <el-table-column prop="age" label="年龄" width="100" sortable>
      </el-table-column>
      <el-table-column prop="createDate" label="生日" width="120"  sortable>
      </el-table-column>
      <el-table-column prop="addr" label="地址" min-width="180" sortable>
      </el-table-column>
       -->


      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
         <el-button  type="primary" size="small" @click="handleRole(scope.$index, scope.row)">分配角色</el-button>
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
     
      <el-pagination  
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageIndex"
      :page-sizes="[10, 30, 50, 100]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </el-pagination>
     <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" :rules="addFormRules" ref="editForm"   :inline="true"  >
        <el-form-item label="用户名" prop="userName"  :rules="[{required:true,message:'请输入用户名',trigger:'blur'}]"  >
          <el-input v-model="editForm.userName" auto-complete="off"></el-input>
        </el-form-item>
         <el-form-item label="姓名" prop="realName"  :rules="[{required:true,message:'请输入姓名',trigger:'blur'}]" >
          <el-input v-model="editForm.realName" auto-complete="off"></el-input>
        </el-form-item>
        <el-cascader :options="options2"   :show-all-levels="false" change-on-select v-model="editForm.departmentId" :props="props"></el-cascader>
        <el-form-item label="性别">
          <el-radio-group v-model="editForm.gender">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
       
      
        <el-form-item label="联系电话" prop="mobile"   >
          <el-input v-model="editForm.mobile" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email"    :rules="[
      { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }

    ]"  >

          <el-input v-model="editForm.email" auto-complete="off" ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false"   >
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm"   :inline="true"  >
        <el-form-item label="用户名" prop="userName"  :rules="[{required:true,message:'请输入用户名',trigger:'blur'}]"  >
          <el-input v-model="addForm.userName" auto-complete="off"></el-input>
        </el-form-item>
         <el-form-item label="姓名" prop="realName"  :rules="[{required:true,message:'请输入姓名',trigger:'blur'}]" >
          <el-input v-model="addForm.realName" auto-complete="off"></el-input>
        </el-form-item>
<!--
         <el-form-item label="部门">
        <el-cascader :options="options2"   :show-all-levels="false" change-on-select v-model="addForm.departmentId" :props="props"></el-cascader>
     </el-form-item>
-->
        <el-form-item label="性别">
          <el-radio-group v-model="addForm.gender">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
         <el-form-item label="密码" prop="passWord">
      <el-input type="password" v-model="addForm.passWord" auto-complete="off"></el-input>
     </el-form-item>
     <el-form-item label="确认密码" prop="checkPass">
     <el-input type="password" v-model="addForm.checkPass" auto-complete="off"></el-input>
     </el-form-item>
       
        <el-form-item label="联系电话" prop="mobile">
          <el-input v-model="addForm.mobile" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email"    :rules="[
      { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }

    ]"  >

          <el-input v-model="addForm.email" auto-complete="off" ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
    <el-dialog title="分配角色" v-model="roleVisble" :close-on-click-modal="false">


     <el-transfer :data="roles"  v-model="userRole.assigned"
      :button-texts="['移除', '分配']" :titles="['未分配', '已分配']"
     ></el-transfer>
     <div slot="footer" class="dialog-footer">
        <el-button @click.native="roleVisble = false">取消</el-button>
        <el-button type="primary" @click.native="roleSubmit" :loading="roleLoading">提交</el-button>
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
        var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
       var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.addForm.checkPass !== '') {
            this.$refs.addForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.addForm.passWord) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
         options2: {
          id:'',
          name: '',
          children: []
        },
        props: {
          label: 'name',
          children: 'children',
          value:'id'

        },

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
        ids:[],
        filters: {
          realName: ''
        },
        state1:'',
       userSearch:[],
        abc:{
          a:true,
          b:true,
        },
        users: [],

        listLoading: false,
        sels: [],//列表选中列
       

        editFormVisible: false,//编辑界面是否显示
        editLoading: false,
        editFormRules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ]
        },
        //编辑界面数据
        editForm: {
     //     id: 0,
          userName: '',
          gender: '',
          
        },

        addFormVisible: false,//新增界面是否显示
        addLoading: false,
      
        //新增界面数据
        addForm: {
          userName: '',
          realName: '',
          gender: '',
          age: '',
          birth: '',
          addr: '',
          pass:'',
          checkPass: '',
          depts:''

        },
        roleVisble:false,
        roleLoading:false,
        roles:[],


        userRole:{
           assigned:[],
           userId:''
        }
        ,
        addFormRules: {
           name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
           ,
             age: [
            { validator: checkAge, trigger: 'blur' }
          ]
        }

      }
    },
    methods: {

      getUserRoles(para){

        this.$http({
          url:'/api/user/queryUserRoles',
          method:'post',
          data:para
        }).then((res)=>{
          if(res.data.flag)
          {
           this.userRole.assigned = res.data.userRoles[0].roles;
          }
        })
        .catch(error=>{
          console.log(error);
        })
      },
     // 获取角色穿梭框数据
      getRoles() {
         var _this = this;
          _this.$http.post('/api/role/list')
              .then(function (response) {
                let a = response.data.role;

                for(let i = 0;i<a.length;i++)
                {

                  _this.roles.push({
                    key:a[i].id,
                    label:a[i].name
                  })
                  // _this.roles = response.data.role;
                }

          })
          .catch(function (error) {
              console.log(error);
          });
      },
      reset(){
           this.filters=[];
           this.getUsers();
      },
       handleCurrentChange(val) {
        console.log(val);
        this.pager.pageIndex = val;
        this.getUsers();
      },
      handleSizeChange(val){
          console.log(val);
        this.pager.pageSize = val;
        this.getUsers();
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
    
      getOptinos(){
          this.$http.post('api/department/root')
              .then((response)=> {
                  this.options2 = response.data.data;

          })
          .catch(function (error) {
              console.log(error);
          });
      },

      permissonJudge(){
            var _this = this;
        _this.$http.post('/api/user/if')
        .then(function(response){
          _this.abc.a=response.data.flag;
        }) 
        .catch(function (error) {
              console.log(error);
          });
      },
  
      //获取用户列表
      getUsers() {
         var _this = this;
         let para = Object.assign({},_this.filters,_this.pager);
          _this.$http({method:'post',
            url:'/api/user/list',
            data:para})
              .then(function (response) {
                  _this.users = response.data.user;
                  _this.pager.total=response.data.total;
                  _this.userSearch=response.data.userName;

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
            url:'/api/user/delete',
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
           
            this.getUsers();
          });
        }).catch(() => {

        });
      },
      //显示编辑界面
      handleEdit: function (index, row) {
        this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
        let sex = this.editForm.gender;
        if(sex=="男")
          {
            this.editForm.gender=1;
          }
          if(sex=="女")
          {
            this.editForm.gender=0;
          }
      },
      handleRole:function(index,row){
         this.userRole={
             assigned:[],
             userId:''
          }
         
      
        this.userRole.userId=row.id;
        let para = Object.assign({},this.userRole);
        this.getUserRoles(para);
        console.log(this.userRole.assigned);
        this.roleVisble=true;
       
      },
      //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm = {
          userName: '',
          realName: '',
          age: '',
          birth: '',
          addr: '',
          pass:'',
          checkPass: ''
        };
      },
      //编辑
      editSubmit: function () {
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.editLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.editForm);
              let a = para.departmentId;
              console.log(a);
              let b = a.length;
              para.departmentId=a[b-1];
              this.$http({
                url:'/api/user/edit',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
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
                this.$refs['editForm'].resetFields();
                this.roleVisble = false;
                this.getUsers();
              });
            });
          }
        });
      },
      //新增
      addSubmit: function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.addForm);
              para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
              

         //     let a = para.departmentId;
           //   console.log(a);

          //    let b = a.length;
           //   console.log(b);
            

          //    para.departmentId=a[b-1];
           //     para.depts=para.departmentId;
              
              this.$http({method:'post',
                url:'api/user/add',
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
                this.getUsers();
              });
            });
          }
        });
      },
      roleSubmit:function(){
           
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.roleLoading = true;
              //NProgress.start();
          //    let userId = thsi.sels.id;
          
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
                this.getUsers();
              });
            });
          
      },

      selsChange: function (sels) {
        this.sels = sels;
      },
      //批量删除
      batchRemove: function () {
        var ids = this.sels.map(item => item.id);

        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { ids: ids };
          this.$http({url:'/api/user/deleteUsers',method:'post',data:para})
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
           
            this.getUsers();
          });
        }).catch(() => {

        });
      }

    },
    mounted() {
      this.getUsers();
      this.getOptinos();
      this.getRoles();
  //    this.permissonJudge();
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
