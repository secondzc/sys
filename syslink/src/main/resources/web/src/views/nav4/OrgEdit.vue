<template>
  <section>

  <el-breadcrumb separator="/">
  <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>活动管理</el-breadcrumb-item>
  <el-breadcrumb-item>活动列表</el-breadcrumb-item>
  <el-breadcrumb-item>活动详情</el-breadcrumb-item>
</el-breadcrumb>
    
  <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="handleAdd"  >新建</el-button>
        </el-form-item>
      </el-form>
       
    </el-col>

    <!--列表-->
    <el-table :data="orgs" highlight-current-row   stripe  ref="multipleTable"  style="width: 100%;">
      <el-table-column type="selection" min-width="100">
      </el-table-column>
      <el-table-column type="index" min-width="120">
      </el-table-column>
      <el-table-column prop="name" label="组织名称" min-width="120" >
      </el-table-column>
      <el-table-column prop="numTeams" label="团队数量" min-width="60" >
       </el-table-column>
      <el-table-column prop="numMembers" label="成员数量" min-width="60" >
       </el-table-column>
      </el-table-column>
      <el-table-column prop="numRepos" label="仓库数量" min-width="60" >
       </el-table-column>
      <el-table-column prop="createdUnix" label="创建时间" min-width="150" >
       </el-table-column>


         <el-table-column label="操作" width="300">
        <template slot-scope="scope">
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
      :page-sizes="[30, 50, 100, 300]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </el-pagination>
    </el-col>



      <!--新增界面-->
    <el-dialog title="创建新的组织" v-model="addFormVisible" :close-on-click-modal="false"   >
      <el-form :model="addForm" label-width="80px"   :label-position="left"  ref="addForm"   :inline="true"  >
        <el-form-item label="组织名称" prop="name"  :rules="[{required:true,message:'请输入组织名称',trigger:'blur'}]"  >
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>


    <!--编辑界面-->
    <el-dialog title="基本设置" v-model="editFormVisible" :close-on-click-modal="false"  >
        <el-form :model="editForm" label-width="100px" :rules="addFormRules" ref="editForm"    >
        <el-form-item label="组织名称" prop="name"   >
           <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
        
        <el-form-item label="组织全名" prop="fullName"   >
            <el-input v-model="editForm.fullName" auto-complete="off"></el-input>
        </el-form-item>
          <el-form-item label="组织描述" prop="description"  >
          <el-input v-model="editForm.description" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="官方网站" prop="website"   >
            <el-input v-model="editForm.website" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="所在地区" prop="location"   >
            <el-input v-model="editForm.location" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label-width="150px" label="允许创建最大库数量" prop="maxRepoCreation">
             <el-input-number v-model="editForm.maxRepoCreation"  :min="-1" ></el-input-number>
             <label>  （设置为 -1 表示使用全局默认值）</label>
        </el-form-item>
       
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>


  

  
  </section>
</template>

<script>
  import util from '../../common/js/util'

  export default {
    data() {
      return {
         
        filters: {
          realName: '',
          content:'',
          time:''
        },
        pager:{
          total:1,
          pageSize:30,
          pageIndex:1,
          currentPage:''

        },
        orgs:[],
    //    total: 0,
 //       page: 1,

        addloading: false,
        addFormVisible: false,


        addForm:{
          name:''
        },
        editLoading:false,
        editFormVisible:false,
        editForm:{
          name:'',
          fullName:'',
          description:'',
          website:'',
          location:'',
          maxRepoCreation:''
        }
      }
    },
    methods: {
        //显示新增界面
      handleAdd: function () {
        this.addFormVisible = true;
        this.addForm = {
          name:''
        };
      },
       handleEdit: function (index, row) {
        this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
        console.log(this.editForm.isActive);
      },

      handleCurrentChange(val) {
        this.pager.pageIndex = val;
        this.getLogs();
      },
      handleSizeChange(val){
        this.pager.pageSize = val;
        this.getLogs();
      },
      reset(){
           this.filters=[];
           this.query();
      },
      getOrgs()
      {

        let para = 1;
        this.$http({
            url:'/api/org/query',
            method:'POST',
            data:para

           })
              .then((response)=> {
                  this.orgs = response.data.orgs;
               //    _this.pager.total=response.data.total;
                 // _this.$router.push({path: '/druid2'});

          })
          .catch(function (error) {
              console.log(error);
          });
      },
       addSubmit: function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
          
              this.addLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.addForm);
           

      
              this.$http({method:'post',
                url:'api/org/add',
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
                this.getOrgs();
              });
          
          }
        });
      },
         editSubmit: function () {
        this.$refs.editForm.validate((valid) => {
          if (valid) {
           
              this.editLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.editForm);
           
              this.$http({
                url:'/api/org/update',
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
                this.getOrgs();
              });
           
          }
        });
      },
        handleDel: function (index, row) {
        console.log(index);
        this.$confirm('确认删除该组织吗?', '提示', {
          type: 'warning',
          closeOnClickModal:false
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { id: row.id };
          this.$http({
            url:'/api/org/delete',
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
           
            this.getOrgs();
          });
        }).catch(() => {

        });
      },
 
    },
    mounted() {
   //   this.getOrgs();
    }
  }

</script>

<style scoped>

</style>