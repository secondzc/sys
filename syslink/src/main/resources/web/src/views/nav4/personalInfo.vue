<template >
  <section>
   
  
  <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm"  style=" width: 50%;margin-top: 5%;margin-left: 10%;" >
       
        
    
         <el-form-item  label="真实姓名" prop="fullName"   >
       
            <el-input v-model="editForm.fullName" auto-complete="off"></el-input>
        
        </el-form-item>
          <el-form-item label="邮箱" prop="email"   >
        
            <el-input v-model="editForm.email" auto-complete="off"></el-input>
            
        </el-form-item>

         <el-form-item  label="原始密码" prop="oldPassWd"   >
 
            <el-input type="password"  v-model="editForm.oldPassWd" auto-complete="off" placeholder="将值留空使其保持不变"></el-input>
         
        </el-form-item>
          <el-form-item label="新密码" prop="newPassWd"   >
        
            <el-input type="password"  v-model="editForm.newPassWd" auto-complete="off"  :disabled="!this.editForm.oldPassWd"></el-input>
        
        </el-form-item>
         <el-form-item label="确认密码" prop="newCheckPassWd"   >
         
            <el-input type="password"  v-model="editForm.newCheckPassWd" auto-complete="off"  :disabled="!this.editForm.oldPassWd"></el-input>
           
        </el-form-item>
       
        <div style="float: right;">
         <el-button @click.native="cancel">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
        </div>
      </el-form>

</section>
</template>

<script>
  export default {
    data() {


     
      var validatePass2 = (rule, value, callback) => {
        if(this.editForm.oldPassWd!='')
        {
          if (value === '') {
          callback(new Error('请输入新密码'));
          } 
          else
          {

            if(value.length<6)
          {
           callback(new Error('密码长度不得小于6位'));
          }
          else
          {
            callback();
          }
          }
        
         // callback();
        }
        else
        { 
          callback();
          
        }
     
      };
       var validatePass3 = (rule, value, callback) => {

        if(this.editForm.newPassWd!='')
        {
           if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.editForm.newPassWd) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
        }
        else
        {
          callback();
        }

       
      };
      return {
        editForm:{
          email:'',
          fullName:'',
          oldPassWd:'',
          newPassWd:'',
          newCheckPassWd:''

        },
        editLoading:false,
         editFormRules: {
      
          newPassWd: [
            {  validator: validatePass2, trigger: 'blur' }
          ],
          newCheckPassWd: [
            { validator: validatePass3, trigger: 'blur' }
          ]
          
        },
       
      
    }
   },
    methods: {

     editSubmit: function () {
      console.log(this.$refs.editForm.validate());
        this.$refs.editForm.validate((valid) => {
          if (valid) {
         
              this.editLoading = true;
              //NProgress.start();
              let para = Object.assign({}, this.editForm);

           
              this.$http({
                url:'/api/user/personalInfo',
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
                  message: res.data.msg,
                  type: 'error'
                });
                }               
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.$store.dispatch('GetUserInfo').then(()=>{
                 

                this.setPersonalInfo();
                })
              
              });
            
          }
        });
      },

      setPersonalInfo()
      {
        this.editForm.fullName=this.$store.state.userInfo.profile.fullName;
        this.editForm.email=this.$store.state.userInfo.profile.email;
      },
      cancel()
      {
        this.$refs['editForm'].resetFields();
        this.setPersonalInfo();
      }

     
     

    },
      mounted() {
      this.setPersonalInfo();
    }
  }
</script>

<style scoped >
 
</style>