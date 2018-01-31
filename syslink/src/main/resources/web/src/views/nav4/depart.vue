<template >
   <section style="overflow-y: hidden;">
   

     <!--工具条-->
   <!--  <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" style="height: 45px;" >
        <el-form-item>
          <el-button type="primary" @click="handleAdd" size="small" >新建<i class="el-icon-plus el-icon--right"></i></el-button>
        </el-form-item>
      </el-form>
      <hr />
       
    <!-- </el-col> -->
    <zk-table
      ref="table"
      sum-text="sum"
      index-text="#"
      :data="departs"
      :columns="columns"
      :stripe="false"
      :border="false"
      :show-header="true"
      :show-summary="false"
      :show-row-hover="true"
      :show-index="false"
      :tree-type="true"
      :is-fold="false"
      :expand-type="false"
      :selection-type="false"
      >
       <template slot="operation" slot-scope="scope">
     <el-button size="small"   @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
     <el-button size="small" type="danger"  @click="handleDel(scope.$index, scope.row)">删除</el-button>
       </template>
    </zk-table>



    <!--编辑部门界面-->
    <el-dialog title="编辑部门" :visible.sync="editFormVisible" v-if="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="80px" ref="editForm"   :rules="editFormRules"  >
        <el-form-item label="名称" prop="name"    >
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>

         <el-form-item label="父级部门" prop="parentId"   >
         <el-cascader    :options="options"  :props="props"  @change="handleChange" v-model="editForm.parentId"   change-on-select :clearable="true"  :show-all-levels="false"  expand-trigger="hover">
        </el-cascader>
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
  

    <!--新增部门界面-->
    <el-dialog title="新建部门" :visible.sync="addFormVisible" v-if="addFormVisible" :close-on-click-modal="false"  >
      <el-form :model="addForm" label-width="80px"  ref="addForm"  :rules="addFormRules"   >
        <el-form-item label="名称" prop="name"   >
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
            
         <el-form-item label="父级部门" prop="parentId"   >
         <el-cascader    :options="options"  :props="props"  @change="handleChange" v-model="addForm.parentId"   change-on-select   :clearable="true" :show-all-levels="false" expand-trigger="hover">
        </el-cascader>
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


</section>
</template>

<script>
  export default {

    data() {
      //新建部门时名称验证，只允许中文，英文，以及数字且名称唯一
       var validateName = (rule, value, callback) => {
        let re = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
        console.log(value);

        if(!value)
        {
            callback(new Error('请输入部门名称'));
        }
        else
        {
          if (re.test(value))
          {
          	
          	if(value.length>15)
          	{
          		 callback(new Error('部门名称不得超过15个字符'));
          	}
          	else
          	{
          		 let para = {name:''};
              para.name=value;
            
              this.$http({
                url:'/api/depart/nameExist',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
                //NProgress.done();
                if(res.data.flag)
                {
                  callback(new Error('部门名称重复'));
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
              callback(new Error('只允许输入中文、字母、数字'));
          }
        }
      };
      //编辑部门时名称验证，同上
        var validateName1 = (rule, value, callback) => {
        let re = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
        console.log(value);

        if(!value)
        {
            callback(new Error('请输入部门名称'));
        }
        else
        {
          if (re.test(value))
          {

            if(value==this.departName)
            {
              callback();
            }
            else
            {
            	
            	if(value.length>15)
          	  {
          		 callback(new Error('部门名称不得超过15个字符'));
          	  }
          	   else
          	  {
          		 let para = {name:''};
              para.name=value;
            
              this.$http({
                url:'/api/depart/nameExist',
                method:'post',
                data:para
              })
               .then((res) => {
                this.editLoading = false;
                //NProgress.done();
                if(res.data.flag)
                {
                  callback(new Error('部门名称重复'));
                }
                else
                {
                   callback();
                }
                
              });
          
          	  }
              
            }
                                      
          } 
          else
          {
              callback(new Error('只允许输入中文、字母、数字'));
          }
        }
      };
        var validateParentId = (rule, value, callback) => {
          console.log(value);
          let parentId = value[value.length-1];
          console.log(parentId);
          console.log(this.selectedDepart);
          if(!value||value.length==0)
          {
            callback();
          }
          else
          {

             if(this.selectedDepart.childrenIds.length==0&&this.selectedDepart.id!=parentId)
             {
              console.log(1);
              callback();
             }
             else
             {
               if(this.selectedDepart.childrenIds.indexOf(parentId)>=0||this.selectedDepart.id==parentId)
                {
                  callback(new Error('父级部门不可选择该部门或该部门的子部门'));
                }
               else
               {
                 console.log(2);
                 callback();
               }
             }
            
          }
         
        
       
      };
      return {

        departName:'',
        addFormRules:{
          name:[
            {required:true, validator:validateName,trigger:'blur'}
          ]
        },
         editFormRules:{
          name:[
            {required:true,validator:validateName1,trigger:'blur'}
          ],
          parentId:[
            {validator:validateParentId,trigger:'blur'}
          ]
        },
     //   data:[],
        props:{value:'id',label:'name',children:'children'},
        departs:[],
        options:[],
        selectedOptions:[],
        selectedDepart:{},
        childrenIds:[],
        addFormVisible: false,//新增界面是否显示
        addLoading: false,
        addForm:{

        },
        editFormVisible:false,
        editLoading:false,
        editForm:{

        },

        columns: [
          {
            label: '名称',
            prop: 'name',
           minWidth: '300px',
         
          },
          {
            label: '父部门',
            prop: 'parentName',
            minWidth: '100px',
          },
          {
            label: '描述',
            prop: 'description',
             minWidth: '200px'
          },
          {
            label: '创建时间',
            prop: 'createDate',
            minWidth: '150px'
          },
          {
            label:'操作',
            prop:'operation',
            minWidth:'200px',
            type:'template',
            template:'operation',
          }
        ]
      }
    },
   
    methods: {

        handleChange(value) {
        // console.log(value);
      },
        //显示编辑界面
      handleEdit(index,row) {
        this.editFormVisible = true;
        this.selectedDepart=row;
        this.departName=row.name;
        this.editForm = Object.assign({}, row);
 

        // let a = this.$refs.table.getCheckedProp('id');
        // console.log(a);
     //   console.log(this.sels);
       // console.log(this.editForm);
      
      },
     
    
      //显示新增界面
      handleAdd: function () {
        this.addForm = {};
        this.addFormVisible = true;
        
      },
      addSubmit: function () {
        this.$refs.addForm.validate((valid) => {
          if (valid) {
  
              this.addLoading = true;
              //NProgress.start();
            
              let para = Object.assign({}, this.addForm);
              console.log(this.addForm.parentId);
              if(this.addForm.parentId&&this.addForm.parentId.length>0)
              {
                  para.parentId=para.parentId[para.parentId.length-1];
              }
              else
              {
                 para.parentId=0;
              }
            
              this.$http({method:'post',
                url:'api/depart/add',
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
                this.getDeparts();
              
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
              if(this.editForm.parentId&&this.editForm.parentId.length>0)
              {
                 para.parentId = para.parentId[para.parentId.length-1];
              }
             else
             {
                para.parentId = 0 ;
             }
              this.$http({
                url:'/api/depart/update',
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
                this.getDeparts();
              });
          
          }
        });
      },
      handleDel(index,row){

            this.$confirm('确认删除该组织吗?', '提示', {
          type: 'warning'
        }).then(() => {
            let para = {id :''};
            para.id = row.id;
            this.$http({method:'post',url:'api/depart/delete',data:para})
            .then((res)=>{
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
                  message: res.data.msg,
                  type: 'error'
                });
                }
                this.getDeparts();
            })
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


          })
          .catch(function (error) {
              console.log(error);
          });
      },
       

     

    },
      mounted() {
      this.getDeparts(); 
    }
  }
</script>

<style  >


.zk-table{
  height: -moz-calc(100% - 40px)!important;
   height :-webkit-calc(100% - 40px)!important; 
   height : calc(100% - 40px)!important;
   overflow-x: auto!important;
 
}
.zk-table__body-wrapper {
    overflow: auto!important;
    height: calc(100%-60px)!important;
     height: -moz-calc(100% - 60px)!important;
    height :-webkit-calc(100% - 60px)!important; 
}

</style>