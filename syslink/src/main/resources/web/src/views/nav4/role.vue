<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" >
        <el-form-item>
          <el-button v-if="abc.a" type="primary" @click="handleAdd"  >新建</el-button>
          <el-button  @click="handleEdit">编辑</el-button>
          <el-button type="danger"  @click="handleDel">删除</el-button>
        </el-form-item>
      </el-form>
       
    </el-col>

    <!--列表-->
    <el-table :data="roles" highlight-current-row  @selection-change="selsChange"     style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>

      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="name" label="名称" min-width="120" sortable>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" min-width="160" sortable  >
       </el-table-column>
      <el-table-column prop="description" label="描述" min-width="160" sortable  >
       </el-table-column>
      <el-table-column label="操作" width="200">
        <template scope="scope">
         <el-button size="small" type="primary"  @click="handlePermission(scope.$index, scope.row)">分配权限</el-button>
        </template>
      </el-table-column>
    </el-table>

   

    <!--编辑角色界面-->
    <el-dialog title="编辑角色" v-model="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" ref="editForm"    >
        <el-form-item label="名称" prop="name"  :rules="[{required:true,message:'请输入角色名称',trigger:'blur'}]"  >
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
    <el-dialog title="新建角色" v-model="addFormVisible" :close-on-click-modal="false"  >
      <el-form :model="addForm" label-width="80px"  ref="addForm"    >
        <el-form-item label="名称" prop="name"  :rules="[{required:true,message:'请输入角色名称',trigger:'blur'}]"  >
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




     <!--分配权限界面-->
    <el-dialog title="分配权限" v-model="permissionVisible"  :close-on-click-modal="false"  :before-close="toggleSelection"  >
   <el-form :model="permission" label-width="80px"  ref="permission"    >





   

 <el-table :data="groups" highlight-current-row  @selection-change="permissionChange" ref="singleTable"  style="width: 100%;">
    
      <el-table-column type="selection" width="55"     >
     <!--
        <template scope="scope">
       <el-checkbox-group v-model="checkList">
     <el-checkbox label="scope.row"></el-checkbox>
        </el-checkbox-group>
            </template> 
            -->
      </el-table-column>
      


      <el-table-column  width="55">
          
      </el-table-column>


    </el-table-column>
  
      <el-table-column prop="authName" label="权限组" min-width="120" sortable>
      </el-table-column>
     
      <el-table-column label="功能权限" min-width="400">
        <template scope="scope">
      
     <el-checkbox-group v-model="ids.permissionId"   @change="handleCheckedCitiesChange">
     <el-checkbox v-for="city in scope.row.children" :label="city.authId" :key="city.authCode">{{city.authName}}</el-checkbox>
     </el-checkbox-group>



        </template>
      </el-table-column>
    </el-table>

<!--

<template  v-for = "(a,index) in groups" >


 <el-checkbox :indeterminate="isIndeterminate" v-model="cities[index]" @change="handleCheckAllChange">全选</el-checkbox>




 


  <div style="margin: 15px 0;"></div>
  <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
    <el-checkbox  v-for="city in a.children" :label="city.authId" :key="city.authId">{{city.authName}}</el-checkbox>
  </el-checkbox-group>
</template>
      

-->


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="toggleSelection">取消</el-button>
        <el-button type="primary" @click.native="permissionSubmit" :loading="permissionLoading">提交</el-button>
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
       
      return {


        checkAll: true,
        checkedCities: [],
        cities: [1,2,3,4],

        
        ids:{
          permissionId:[],
          roleId:''
        },
        
        isIndeterminate: true,
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
        
        abc:{
          a:true,
          b:true,
        },
       
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

        }
        ,   
      }
    },
    methods: {
      
      
       toggleSelection(rows) {
      //  if (rows) {
      //    console.log(rows);
      //    rows.forEach(row => {
      //      this.$refs.singleTable.toggleRowSelection(row);
       //   });
      //  } else {
          this.$refs.singleTable.clearSelection();
          this.permissionVisible=false;
      //  }
      },
        setRow(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.singleTable.toggleRowSelection(row);
          });
     //     console.log(rows);
      //    this.permissionChange(rows);
        } 
       // else {
        //  this.$refs.singleTable.clearSelection();
         // this.permissionVisible=false;
       // }
      },
      
      

        handleCheckAllChange(event) {
     //     console.log(event);
     //     console.log(event.target);
        this.checkedCities = event.target.checked ? cityOptions : [];




        this.isIndeterminate = false;
      },
      handleCheckedCitiesChange(value) {
      //  console.log(value);
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.cities.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;

        /*
        let g = new Set();
        //   this.ids.permissionId.forEach(x => s.add(x));
        value.forEach(x => g.add(x)); 
        let h=[];
        for(let a =0; a<this.groups.length;a++)
        {
              let f = true;
              for(let b=0;b<this.groups[a].children.length;b++)
              {
              //  let e = true;
                 let e = g.has(this.groups[a].children[b].authId);
        //         console.log(e);

                 f=f&&e;
                 
              }
             

              if(f)
              {
                //this.setRow([this.groups[a]]);
                h.push(this.groups[a]);
                console.log(h);
              }
            
        }
        console.log(h)
        this.setRow(h);
        

**/

      }
      ,
      /**
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

      **/
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



          })
          .catch(function (error) {
              console.log(error);
          });
      },

      /**
       getCheckBox() {
         var _this = this;
          _this.$http.post('/api/permission/list')
              .then(function (response) {
                _this.groups = response.data.group;
                  _this.cities = response.data.group[1].children;
                 _this.checkedCities = response.data.group[1].children;
               console.log(_this.groups);
                  console.log(_this.checkedCities);
                  console.log(_this.cities);

          })
          .catch(function (error) {
              console.log(error);
          });
      },

      **/
      //删除
      handleDel: function () {
        this.$confirm('确认删除该角色吗?', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let  a = this.sels;
          let b = a.length;
      //    console.log(b);
          let para = { id: a[b-1].id};
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
        }).catch(() => {

        });
      },
     
      //显示编辑界面
      handleEdit: function () {
        this.editFormVisible = true;
        let a = this.sels;
        let b = a.length;

        this.editForm = Object.assign({}, this.sels[this.sels.length-1]);
     //   console.log(this.sels);
       // console.log(this.editForm);
      
      },
    
      //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm = {
         name:'',
         description:''
          
        };
      },

      handlePermission:function(index,row){

           
          this.checkedCities=[];
          this.cities=[];
     ///    this.ids={
   ///       permissionId:[],
    //      roleId:''
   //     };
     //    this.$refs.singleTable.clearSelection();
         //this.$refs.permissionTable.clearSelection();
         this.permissionVisible=true;
     //     this.setRow([this.groups[0]]);

       //  sels=null;
        /**
          if(this.ids.permissionId=="[]")
          {
            this.ids.permissionId=[];
          }
          else
          {
            this.ids.permissionId = row.permissionJsonData;
          }
          

          this.ids.roleId = row.id;

           this.permissionVisible = true;

          console.log(this.ids.permissionId);
  **/
        if((row.permissions!="[]")&&(row.permissions!=null)&&(row.permissions!=""))
        {
           this.ids.permissionId = row.permissions;
       //    this.checkedCities = row.permissions;
        }

           let g = new Set();
        //   this.ids.permissionId.forEach(x => s.add(x));
        this.ids.permissionId.forEach(x => g.add(x)); 
        for(let a =0; a<this.groups.length;a++)
        {
              let f = true;
              for(let b=0;b<this.groups[a].children.length;b++)
              {
              //  let e = true;
                 let e = g.has(this.groups[a].children[b].authId);
             //    console.log(e);

                 f=f&&e;
                 
              }
              //console.log(f);

              if(f)
              {
                this.setRow([this.groups[a]]);
              }
            
        }

     //   this.cities=this.groups;
          
     //     this.ids.permissionId = this.roles.permissions;
     //     console.log(this.ids.permissionId);
     //     console.log(this.checkedCities);
        this.ids.roleId=row.id;

    
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
                url:'/api/role/edit',
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
                this.getRoles();
              });
            });
          }
        });
      },
       permissionSubmit: function () {
        this.$refs.permission.validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.permissionLoading = true;
              //NProgress.start();
              // this.ids.permissionId = this.cities;
           //   console.log(this.ids);
          //    this.ids.permissionId = this.checkedCities;
        
              let para = Object.assign({}, this.ids);
   
              this.$http({
                url:'/api/role/assign',
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
                this.getRoles();
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
            });
          }
        });
      },
    
      selsChange: function (sels) {
        this.sels = sels;

      //   console.log(sels);
      },
      permissionChange: function (sels) {

         let gg = this.ids.permissionId;
   //      this.ids.permissionId=[];
       this.perCheck = sels;
  //     console.log(this.perCheck);
        let b = 0;
        for(let i =0;i<this.perCheck.length;i++)
        {
          b+=this.perCheck[i].children.length;
        }
         let s = new Set();
    //   this.ids.permissionId.forEach(x => s.add(x));
          
       let c =[];
   //    this.ids.permissionId.forEach(x =>(s.add(x)));
    //    console.log(this.ids.permissionId);
        
      
       for(let a = 0;a<sels.length;a++)
       {
         for(let d= 0;d<sels[a].children.length;d++)
         {
          s.add(sels[a].children[d].authId);
      
         }
       }
        //gg.forEach(x=>(s.add(x)));

         console.log(this.ids.permissionId);
    //       console.log(s);
        s.forEach(x=>(c.push(x)));
          console.log(c);
        this.ids.permissionId=c;
    




       
      }
    },
    mounted() {
      this.getRoles();
   //   this.getCheckBox();
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
