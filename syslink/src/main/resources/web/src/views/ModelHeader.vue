<template>
  
<el-container style="height: 100%;overflow:hidden;">
 <el-header style="padding: 0px;">
    <!--  <loginHeader></loginHeader> -->
  <el-col :span="24" class="header" >
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        {{collapsed?'':sysName}}
      </el-col>
  
       <el-col :span="10 " style="flex: 0 0 230;">
        <a  href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toMySpace"  >工作台</a>
         <a  href="http://gogs.modelica-china.com/#/index" v-show="!this.func.isLogin()" id="home-page">首页</a>
        <a  href="http://gogs.modelica-china.com:3000/" v-show="this.func.isLogin()" >协同</a>
        <a href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toModel">模型</a>
        <a href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toCorporate">仿真</a>
        <a  href="javascript:void(0)"  v-show="!this.func.isLogin()"  @click="toLogin">登录</a>
      
      </el-col>
      <el-col :span="4" class="userinfo">
        <el-dropdown v-if="this.func.isLogin()" trigger="hover">
          <span class="el-dropdown-link userinfo-inner">
            <i class="el-icon-bell"></i>
            <!-- <img :src="this.sysUserAvatar" />  -->{{this.$store.state.userInfo.profile.name}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>我的消息</el-dropdown-item>
            <el-dropdown-item @click.native="changePassWd">修改密码</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    </el-col>
 

 </el-header>
  <el-container >
     <!-- <el-aside v-if="this.func.isLogin()">
      <div style="width: 200px;height: 30px;border: solid 1px #e6e6e6; margin-bottom: 1%;">
        <span>导航菜单</span>
      </div>


      <sidebar ></sidebar>
      </el-aside> -->
  
    <el-container>
   <!--    <el-header style="height: 10px;margin-top: 2%; " v-if="this.func.isLogin()">

        <el-breadcrumb separator="/">
     <el-breadcrumb-item :to="{ path: '/Myspace' }"><i class="el-icon-location-outline"></i>
     </el-breadcrumb-item>
       <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
          {{ item.name }}
        </el-breadcrumb-item>
       </el-breadcrumb>

      </el-header> -->
      <el-main> <router-view></router-view></el-main>
      <el-footer style="height: 30px;"></el-footer>
  
    </el-container>
  </el-container>
</el-container>


 

</template>

<script>
import Sidebar from './Sidebar'
import LoginHeader from './LoginHeader'
import Cookies from 'js-cookie'
    export default {
       components: {
          Sidebar,
          LoginHeader
 
  },
        data() {
            return {
                // changePassWdVisible:false,
                // changePassWdLoading:false,
                // changePassWdForm:[],
                sysName:'SYSLINK',
                collapsed:false,
                sysUserName: '',
                sysUserAvatar: '',
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                }
            }
        },
        computed:{
            
        },
        methods: {
          
            //退出登录
            logout: function () {
                var _this = this;
                this.$confirm('确认退出吗?', '提示', {
                    //type: 'warning'
                }).then(() => {
  
               sessionStorage.clear();
               localStorage.clear();
               _this.$store.dispatch('LogOut');
               console.log(sessionStorage.getItem('logined'));
                _this.$http.post('api/user/destory');  
               /**  **/
               Cookies.remove('abc');
                _this.$router.push('/index');
                location.reload();

                }).catch(() => {

                });


            },
            changePassWd()
            {

            },
            
            toModel :function () {
                var _this = this;
                _this.$router.push('/model/index');
            },
            toCorporate :function () {
                var _this = this;
              _this.$router.push({path: '/repository/index'});
            },
            toLogin()
            {
                var _this = this;
              _this.$router.push('/login');
            },
            toMySpace()
            {
                 var _this = this;
              _this.$router.push('/Myspace');
            },
            //折叠导航栏
            collapse:function(){
                this.collapsed=!this.collapsed;
                if (this.collapsed) {

                }
            },
          
        },
        mounted() {
            var user = sessionStorage.getItem('user');
            if (user) {
                user = JSON.parse(user);
                this.sysUserName = user.name || '';
                this.sysUserAvatar = user.avatar || '';
            // let abc = sessionStorage.getItem('logined');
            // console.log(abc);
            }
            // const uid = JSON.parse(localStorage.getItem('uid'));
            // console.log(uid);
            // this.sysUserName = uid.userName;

        }
    }

</script>

<style scoped lang="scss">
  @import '~scss_vars';

     .el-header{
  /*  background-color: #B3C0D1;*/
    color: #333;
    text-align: center;
    /*line-height: 60px;*/
   /* margin-top:  25px;*/

  }
   .el-footer {
 /*   background-color: #B3C0D1;*/
    color: #333;
    text-align: center;
    line-height: 60px;
    margin-top:  0px;

  }
  
  .el-aside {
  /*  background-color: #D3DCE6;*/
    width: 230px;
    flex: 0 0 230px;
    color: #333;
    text-align: left;
    line-height: 200px;
    height: 90%;
   /* width: 250px;*/
    overflow:auto;
    margin-top: 30px;
    margin-left: 20px;
   
    /*border-top: solid 1px #e6e6e6;*/

  }
  .navigation{
    border: solid 1px #e6e6e6;
  }
  .el-main {
/*    background-color: #E9EEF3;*/
    color: #333;
 /*   text-align: center;*/
  /*  line-height: 160px;*/
     /*height: 100%;*/
    min-height: 400px;
    margin: 1%;
    overflow-x: hidden;
    /* margin-bottom: 20px;
     margin-left: 20px;
     margin-right: 20px;*/

     /*margin: auto;*/
 
  }
  
  body > .el-container {

    margin-bottom: 40px;


  }
  ::-webkit-scrollbar{width:0px}

  .header {
      height: 50px;
      line-height: 50px;
      //background: url(http://demo.vueadmintemplate.com/dark/img/nav_bg.jpg);
      background:#222;
      color:#fff;
      .userinfo {
        text-align: right;
        padding-right: 35px;
        float: right;
        .userinfo-inner {
          cursor: pointer;
          color:#fff;
          img {
            width: 40px;
            height: 40px;
            border-radius: 20px;
            margin: 5px 0px 5px 5px;
            float: right;
          }
        }

      }
      .logo {
        //width:230px;
        height:60px;
        font-size: 22px;
        padding-left:80px;
        padding-right:0px;
        border-color: rgba(238,241,146,0.3);
        border-right-width: 1px;
        border-right-style: solid;
        img {
          width: 40px;
          float: left;
          margin: 10px 10px 10px 18px;
        }
        .txt {
          color:#fff;
        }
      }
      .logo-width{
        width:320px;
      }
      .logo-collapse-width{
        width:80px
      }
      .tools{
        padding: 0px 23px;
        width:14px;
        height: 50px;
        line-height: 50px;
        cursor: pointer;
      }
      .item{
        color:#fff;
        font-size: 18px;
        /*line-height: 38px;*/
        position: relative;
        /*display: inline-block;*/
        margin-right: 48px;
        text-decoration:none;

      }
      .item:not(:hover) {
        /*color: #9d9d9dE*/
      }
         a:-webkit-any-link {
   /* color: -webkit-link;*/
       color:#fff;
       font-size: 18px;
       position: relative;
       margin-right: 48px;
       cursor: auto;
       text-decoration: none;
       text-decoration-line: none;
       text-decoration-style: initial;
       text-decoration-color: initial;
}
    }


  
  
 /* .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }*/
</style>