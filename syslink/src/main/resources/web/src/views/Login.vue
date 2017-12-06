<template>




  <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px" class="login-container" style="height: 400px;">
  <!--   <h3 class="title">系统登录</h3> -->
    <img :src="loginLogo"  style="max-height: 100px;width: 200px;" />
    <el-form-item prop="userName">
      <el-input type="text" v-model="ruleForm2.userName" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="ruleForm2.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox v-model="ruleForm2.rememberMe" checked class="remember">记住密码</el-checkbox>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">登录</el-button>
     
    </el-form-item>
  </el-form>


</template>

<script>
  import { requestLogin } from '../api/api';
  import Cookies from 'js-cookie'
  import loginLogo from '@/assets/loginLogo.png'
  //import NProgress from 'nprogress'
  export default {
    data() {
        var validateUsername = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入用户名'));
            }
            callback();
        };
        var validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            }
            callback();
        };
      return {
        loginLogo: loginLogo + '?' + +new Date(),
        logining: false,
        ruleForm2: {
          userName: 'admin',
          password: '111111'
        },
        rules2: {
          userName: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { validator: validateUsername }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { validator: validatePassword }
          ]
        },
          rememberMe: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm2.resetFields();
      },
      //错误封装方法
      errormsg(msgerror) {
          this.$message.error(msgerror);
      },
      handleSubmit2(ev) {
          var _this = this;
          _this.$http.get('/api/loginUser'
              ,{
                  params: {
                      userName: _this.ruleForm2.userName,
                      password: _this.ruleForm2.password,
                      rememberMe: _this.ruleForm2.rememberMe,
                  },

              },)
              .then(function (response) {
                  let result = response.data.result;
                  if( result =="1"){
                      let user =  {
                          id: 1,
                          username: _this.ruleForm2.userName,
                          avatar: 'https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
                      }
                      sessionStorage.setItem('user', JSON.stringify(user));

                      

                      if(_this.ruleForm2.rememberMe)
                      {
                        // let rememberMe = {rememberMe:true};
                        // localStorage.setItem('rememberMe',JSON.stringify(rememberMe));
                        Cookies.set('syslink', _this.ruleForm2.userName,{ expires: 30, path: '' });
                        

                      }

                       Cookies.set('gogs_awesome', _this.ruleForm2.userName,{ expires: 30, path: '' });


                     
                      // console.log( _this.ruleForm2.username);
                      // let uname = {userName:  _this.ruleForm2.userName};
                      // console.log(uname);
                      // _this.$store.dispatch('AutoPass',uname);

                      //存储用户信息
                      // let logined={logined:''};
                      // logined.logined = true;
                      // sessionStorage.setItem('logined',JSON.stringify(logined));
                      // console.log(sessionStorage.getItem('logined'));

                     
                      // Cookies.set('sidebarStatus',0);
                     
                       
                     
                      // let uid ={uid:'',name:''}; 
                      // uid.uid = response.data.userInfo.profile.iD;
                      // uid.userName = response.data.userInfo.profile.name;
                      // //存储用户id
                      // localStorage.setItem('uid',JSON.stringify(uid));
                      
                     
                    
                      _this.$router.push({path: '/Myspace'});
                 }else{
                     //如果返回500，就在页面上方弹出错误信息
                     _this.errormsg(response.data.errormsg);
                 }
              })
              .catch(function (error) {
                  console.log(error);
              });

/*        var _this = this;
        this.$refs.ruleForm2.validate((valid) => {
          if (valid) {
            //_this.$router.replace('/table');
            this.logining = true;
            //NProgress.start();
            var loginParams = { username: this.ruleForm2.userName, password: this.ruleForm2.password };
            requestLogin(loginParams).then(data => {
              this.logining = false;
              //NProgress.done();
              let { msg, code, user } = data;
              if (code !== 200) {
                this.$message({
                  message: msg,
                  type: 'error'
                });
              } else {
                sessionStorage.setItem('user', JSON.stringify(user));
                this.$router.push({ path: '/table' });
              }
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });*/
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    max-height: 350px;
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
   /* margin: 180px auto;*/
   /* margin-left: 50%;
    margin-right: 50%;
    margin-top: 10%; */  
    margin:5% auto; 
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    .title {
      margin: 0px auto 40px auto;
      text-align: center;
      color: #505458;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
  }
</style>