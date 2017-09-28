<template>
  <section>




    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" >
        <el-form-item>
          <el-button v-if="abc.a" type="primary" @click="handleAdd"  >新建组</el-button>
          <el-button  @click="handleEdit">编辑组</el-button>
          <el-button type="danger"  @click="handleDel">删除组</el-button>
        </el-form-item>
      </el-form>
       
    </el-col>

    <!--列表-->
    <el-table :data="group" highlight-current-row  @selection-change="selsChange"     style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
        <el-table-column type="expand">
           <template scope="scope" >
     
        <el-table :data="scope.row.children" >
      <el-table-column type="selection" width="55">
      </el-table-column>
       <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="authName" label="名称" min-width="120" sortable>
      </el-table-column>
       <el-table-column prop="parentId" label="父权限" min-width="120" sortable>
      </el-table-column>
       <el-table-column prop="url" label="请求地址" min-width="120" sortable>
      </el-table-column>
        <el-table-column prop="authCode" label="代码" min-width="120" sortable>
      </el-table-column>
        <el-table-column prop="createDate" label="创建时间" min-width="160" sortable  >
       </el-table-column>
      <el-table-column prop="description" label="描述" min-width="160" sortable  >
       </el-table-column>
        <el-table-column label="操作" width="200">
        <template scope="scope">
         
          <el-button size="small" @click="handleEditItem(scope.$index, scope.row)">编辑项</el-button>
          <el-button type="danger" size="small" @click="handleItemDel(scope.$index, scope.row)">删除项</el-button>
        </template>
      </el-table-column>
        </el-table>
   

   

      </template>
    </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="authName" label="名称" min-width="120" sortable>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" min-width="160" sortable  >
       </el-table-column>
      <el-table-column prop="description" label="描述" min-width="160" sortable  >
       </el-table-column>
      <el-table-column label="操作" width="200">
        <template scope="scope">
         <el-button  type="primary" size="small" @click="handleAddItem(scope.$index, scope.row)">新建项</el-button>
        </template>
      </el-table-column>
    </el-table>

   

    <!--编辑权限组界面-->
    <el-dialog title="编辑权限组" v-model="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" ref="editForm"    >
        <el-form-item label="名称" prop="authName"  :rules="[{required:true,message:'请输入权限组名称',trigger:'blur'}]"  >
          <el-input v-model="editForm.authName" auto-complete="off"></el-input>
        </el-form-item>

          <el-form-item label="请求地址" prop="url"  :rules="[{required:true,message:'请输入请求地址',trigger:'blur'}]"  >
          <el-input v-model="editForm.url" auto-complete="off"></el-input>
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
  
  <!--编辑权限项界面-->
    <el-dialog title="编辑权限项" v-model="editItemVisible"         :close-on-click-modal="false"  >
      <el-form :model="editItem" label-width="80px"  ref="editItem"    >
        <el-form-item label="名称" prop="authName"  :rules="[{required:true,message:'请输入权限项名称',trigger:'blur'}]"  >
          <el-input v-model="editItem.authName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="代码" prop="authCode" :rules="[{required:true,message:'请输入权限项代码',trigger:'blur'}]"  >
          <el-input v-model="editItem.authCode" ></el-input>
        </el-form-item>
         <el-form-item label="请求地址" prop="url" :rules="[{required:true,message:'请输入请求地址',trigger:'blur'}]"  >
          <el-input v-model="editItem.url" ></el-input>
        </el-form-item>

         <el-form-item label="描述" prop="description" >
          <el-input v-model="editItem.description" type="textarea"  :autosize="{ minRows: 4, maxRows: 10}"  placeholder="请添加相关描述"  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editItemVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editItemSubmit" :loading="editItemLoading">提交</el-button>
      </div>
    </el-dialog>


    <!--新建权限组界面-->
    <el-dialog title="新建权限组" v-model="addFormVisible" :close-on-click-modal="false"  >
      <el-form :model="addForm" label-width="80px"  ref="addForm"    >
        <el-form-item label="名称" prop="authName"  :rules="[{required:true,message:'请输入权限组名称',trigger:'blur'}]"  >
          <el-input v-model="addForm.authName" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="代码" prop="authCode"  :rules="[{required:true,message:'请输入权限组名称',trigger:'blur'}]"  >
          <el-input v-model="addForm.authCode" auto-complete="off"></el-input>
        </el-form-item>
      
        <el-form-item label="请求地址" prop="url"  :rules="[{required:true,message:'请输入请求地址',trigger:'blur'}]"  >
          <el-input v-model="addForm.url" auto-complete="off"></el-input>
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
     <!--新增权限项界面-->
    <el-dialog title="新增权限项" v-model="addItemVisible" :close-on-click-modal="false"  >
      <el-form :model="addItem" label-width="80px"  ref="addItem"    >
        <el-form-item label="名称" prop="authName"  :rules="[{required:true,message:'请输入权限项名称',trigger:'blur'}]"  >
          <el-input v-model="addItem.authName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="代码" prop="authCode" :rules="[{required:true,message:'请输入权限项代码',trigger:'blur'}]"  >
          <el-input v-model="addItem.authCode" ></el-input>
        </el-form-item>
         <el-form-item label="请求地址" prop="url" :rules="[{required:true,message:'请输入请求地址',trigger:'blur'}]"  >
          <el-input v-model="addItem.url" ></el-input>
        </el-form-item>
         <el-form-item label="描述" prop="description" >
          <el-input v-model="addItem.description" type="textarea"  :autosize="{ minRows: 4, maxRows: 10}"  placeholder="请添加相关描述"  auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addItemVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addItemSubmit" :loading="addItemLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  //import NProgress from 'nprogress'

  export default {
    data() {
       
      return {
        addItem:{
          authName:'',
          authCode:'',
          url:'',
          description:''
        },
        editItem:{
          authName:'',
          authCode:'',
          url:'',
          description:''
        },
        
        abc:{
          a:true,
          b:true,
        },
        group: [],

        listLoading: false,
        sels: [],//列表选中列

        editFormVisible: false,//编辑界面是否显示
        editLoading: false,
        addItemLoading:false,
        addItemVisible:false,

        dialogVisible:false,



        editItemLoading:false,
        editItemVisible:false,
       
        //编辑界面数据
        editForm: {
     //     id: 0,
          authName: '',
          ahtuCode:'',
          description: '',
          
        },

        addFormVisible: false,//新增界面是否显示
        addLoading: false,
       
        //新增界面数据
        addForm: {

         authName:'',
         authCode:'',
         description:''

        }
        ,   
      }
    },
    methods: {
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

      //获取权限组列表
      getGroups() {
         var _this = this;
          _this.$http.post('/api/auth/list')
              .then(function (response) {
                  _this.group = response.data.group;

          })
          .catch(function (error) {
              console.log(error);
          });
      },
      //删除
      handleDel: function () {
        this.$confirm('确认删除该权限组吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let  a = this.sels;
          let b = a.length;
          console.log(b);
          let para = { id: a[b-1].authId};
          this.$http({
            url:'/api/auth/delete',
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
            this.getGroups();
          });
        }).catch(() => {

        });
      },
       handleItemDel: function (index, row) {
        this.$confirm('确认删除该权限项吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { id: row.authId };
          this.$http({
            url:'/api/auth/delete',
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
           
            this.getGroups();
          });
        }).catch(() => {

        });
      },
      //显示编辑界面
      handleEdit: function () {
        this.editFormVisible = true;
        let a = this.sels;
        let b = a.length;

        this.editForm = Object.assign({}, this.sels[this.sels.length-1]);
        console.log(this.sels);
       // console.log(this.editForm);
      
      },
      handleAddItem:function(index,row){
        this.addItemVisible = true;
        this.addItem = {};
        this.addItem.parentId = row.authId;
        let a = row.authId;
        console.log(a);

      },
        handleEditItem: function(index,row){
          console.log(this.editItem);
          console.log(this.editItemVisible);
        this.editItemVisible = true;
         this.editItem = Object.assign({}, row);
      },

      //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm = {
         name:'',
         description:''
          
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
          
              this.$http({
                url:'/api/auth/editAuth',
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
                this.editFormVisible = false;
                this.getGroups();
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
              
              this.$http({method:'post',
                //url:'api/permission/addGroup',
                url:'api/auth/addAuth',
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
                this.getGroups();
              });
            });
          }
        });
      },
         addItemSubmit: function () {
        this.$refs.addItem.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.addItemLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.addItem);
              
              this.$http({method:'post',
                url:'api/auth/addAuth',
                data:para}).then((res)=>{
                this.addItemLoading = false;
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
                this.$refs['addItem'].resetFields();
                this.addItemVisible = false;
                this.getGroups();
              });
            });
          }
        });
      },
      editItemSubmit: function () {
        this.$refs.editItem.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.editItemLoading = true;
              //NProgress.start();

              let para = Object.assign({}, this.editItem);
          
              this.$http({
                url:'/api/auth/editAuth',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editItemLoading = false;
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
                
                this.$refs['editItem'].resetFields();
                this.editItemVisible = false;
                this.getGroups();
              });
            });
          }
        });
      },
      selsChange: function (sels) {
        this.sels = sels;
        console.log(sels);
      }
    },
    mounted() {
      this.getGroups();
  
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
