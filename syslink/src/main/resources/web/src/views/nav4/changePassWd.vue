<template >
  <section>
   
  
  <el-form :model="editForm" label-width="100px" :rules="addFormRules" ref="editForm"  style=" width: 50%;margin-top: 5%;margin-left: 10%;" >
       
        
     <!--    <el-form-item label="自定义名称" prop="fullName"   >
            <el-input v-model="editForm.fullName" auto-complete="off"></el-input>
        </el-form-item>
          <el-form-item label="邮箱" prop="email"  :rules="[ { required:'true',type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' } ]"  >
          <el-input v-model="editForm.email" auto-complete="off" ></el-input>
        </el-form-item> -->
         <el-form-item  label="原始密码" prop="fullName"   >
         <el-tooltip content="将值留空使其保持不变。" placement="top" effect="light">
            <el-input v-model="editForm.oldPassWd" auto-complete="off"></el-input>
            </el-tooltip>
        </el-form-item>
          <el-form-item label="新密码" prop="fullName"   >
         <el-tooltip content="将值留空使其保持不变。" placement="top" effect="light">
            <el-input v-model="editForm.newPassWd" auto-complete="off"></el-input>
            </el-tooltip>
        </el-form-item>
         <el-form-item label="确认密码" prop="fullName"   >
         <el-tooltip content="将值留空使其保持不变。" placement="top" effect="light">
            <el-input v-model="editForm.newCheckPassWd" auto-complete="off"></el-input>
            </el-tooltip>
        </el-form-item>
        <div style="float: right;">
         <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
        </div>
      </el-form>

</section>
</template>

<script>
  export default {
    data() {
      return {
        editForm:{
          oldPassWd:'',
          newPassWd:'',
          newCheckPassWd:''

        }
       
      
    }
   },
    methods: {

   

     

     

    },
      mounted() {
      
    }
  }
</script>

<style scoped >
 
</style>