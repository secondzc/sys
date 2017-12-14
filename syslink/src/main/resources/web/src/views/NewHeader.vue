<template>
	
<el-container style="height: 100%;overflow:hidden;width: 100%;">
 <el-header style="padding: 0px;height: 50px;background-color: #fafafa;border-bottom: solid 2px #e6e6e6;padding-right: 20px;display: inline-flex;width: inherit;" v-bind:style="styleObject">





   <div style="min-width: 350px;display: flex;">
     <div style="line-height: 50px;width: 150px;">
       <el-tooltip class="item" effect="dark" content="收缩菜单" placement="bottom">
        <i class="iconfont icon-weibiaoti261" @click="collapse" v-show="this.func.isLogin()&&this.func.isOpened()" style="font-size: 20px;"></i>
      </el-tooltip>
       <el-tooltip class="item" effect="dark" content="展开菜单" placement="bottom">
        <i class="iconfont icon-weibiaoti26" @click="collapse" v-show="this.func.isLogin()&&!this.func.isOpened()" style="font-size: 20px;"></i>
      </el-tooltip>
    </div>
    <div @click="toMySpace">
         <img :src="SysLogo"  style="height: 40px;margin-top: 5px;" />
   <img :src="SylTitle"  style="height: 40px;margin-top: 5px;" />
    </div>
 
   </div>
    
    
  
 
  
  <el-menu default-active="1" mode="horizontal"  style="width: inherit;margin-left: 10%;background-color: inherit;min-width: 500px;display: flex;"  v-show="this.func.isLogin()"  >
   
    <div style="width: 20px;"></div>
   
  
  <el-menu-item index="1"  @click="toMySpace" v-bind:style="styleObject1">工作台</el-menu-item>
  <el-menu-item index="2" @click="toCorporate" v-bind:style="styleObject1">协同</el-menu-item>
  <el-menu-item index="3"  @click="toModel" v-bind:style="styleObject1">模型</el-menu-item>
  <!-- <el-menu-item index="4" @click="toCorporate" v-bind:style="styleObject1">仿真</el-menu-item> -->
 <!--  <el-color-picker v-model="bgColor"     @active-change="colorChange" show-alpha></el-color-picker>

   <el-color-picker v-model="fontColor"     @active-change="fontChange" show-alpha></el-color-picker> -->
  </el-menu>



  <el-dropdown style="min-width: 50px;" trigger="click">
   
   
   <i class="iconfont icon-gerenxinxi" style="font-size: 32px;line-height: 50px;"></i>
  
  <el-dropdown-menu slot="dropdown">
      <div style="text-align: center;border-bottom: solid 1px #e6ebf5;height: 30px;line-height: 30px;font-weight: 700;font-size: 12px;">
        <span>当前用户{{this.$store.state.userInfo.profile.name}} </span>
      </div >
     <el-dropdown-item @click.native="personalInfo"><i class="iconfont icon-yonghuziliao-xianxing" style="font-size: 20px;"></i> 个人信息</el-dropdown-item>
     <el-dropdown-item divided @click.native="logout"><i class="iconfont icon-dengluzhuangtaituichu" style="font-size: 20px;"></i> 退出</el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>





 
  


  


   
<!--   <el-col :span="24" class="header" >
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        {{collapsed?'':sysName}}
      </el-col>
  
      <el-col :span="10 " style="flex: 0 0 230;">
  
        <i class="iconfont icon-weibiaoti261" @click="collapse" v-show="this.func.isLogin()&&this.func.isOpened()"></i>
       
         <i class="iconfont icon-weibiaoti26" @click="collapse" v-show="this.func.isLogin()&&!this.func.isOpened()"></i>

        <a  href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toMySpace"  >工作台</a>
        <a  href="http://gogs.modelica-china.com:8080/login.html#/index" v-show="!this.func.isLogin()" id="home-page">首页</a>
        <a  href="http://gogs.modelica-china.com:3000/" v-show="this.func.isLogin()" >协同</a>
        <a  href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toModel">模型</a>
        <a  href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toCorporate">仿真</a>
        <a  href="javascript:void(0)"  v-show="!this.func.isLogin()"  @click="toLogin">登录</a>

        

      
      </el-col>
      <el-col :span="4" class="userinfo">
        <el-dropdown v-if="this.func.isLogin()" trigger="hover">
          <span class="el-dropdown-link userinfo-inner">
            <i class="el-icon-bell"></i>
         {{this.$store.state.userInfo.profile.name}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="personalInfo">个人信息</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    </el-col>
  -->





 </el-header>
  <el-container style="height: 100%;">
     <el-aside v-if="this.func.isLogin()" :class="{hideAside:!sidebar,MainAside:sidebar}">


     <!--  <div style="width: 200px;height: 30px;border: solid 1px #e6e6e6; margin-bottom: 1%;">
        <span>导航菜单</span>
      </div> -->


      <sidebar ></sidebar>
       
      </el-aside>
  
   <!--  <el-container  :class="{hideMain:!sidebar,Main:sidebar}"> -->
    <!--   <el-header style="height: 10px;margin-top: 2%; " v-if="this.func.isLogin()">

        <el-breadcrumb separator="/">
     <el-breadcrumb-item :to="{ path: '/Myspace' }"><i class="el-icon-location-outline"></i>
     </el-breadcrumb-item>
       <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
          {{ item.name }}
        </el-breadcrumb-item>
       </el-breadcrumb>

      </el-header > -->
      


      <el-main  :class="{hideMain:!sidebar,Main:sidebar}"  >
        <div style="height: 20px;" v-if="this.func.isLogin()">
         
          <el-breadcrumb separator="/">
     <el-breadcrumb-item :to="{ path: '/Myspace' }"><i class="el-icon-location-outline"></i>
     </el-breadcrumb-item>
       <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
          {{ item.name }}
        </el-breadcrumb-item>
       </el-breadcrumb>
              
        </div>

      	<router-view style="height: inherit;overflow-x: hidden;"></router-view>



      </el-main>
     <!--  <el-footer style="height: 30px;"></el-footer> -->
  
    <!-- </el-container> -->
  </el-container>
</el-container>


 

</template>

<script>
import Sidebar from './Sidebar'
import LoginHeader from './LoginHeader'
import Cookies from 'js-cookie'

import SysLogo from '@/assets/syslogo.png'
import SylTitle from '@/assets/systitle.png'
    export default {
    	 components: {
          Sidebar,
          LoginHeader
 
  },
        data() {
            return {
               SysLogo: SysLogo + '?' + +new Date(),
               SylTitle:SylTitle + '?' + +new Date(),
               styleObject:{backgroundColor:'rgb(250, 250, 250)'},
               styleObject1:{color:'black',fontSize:'18px'},
               bgColor:'',
               fontColor:'',
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

          sidebar() {
      return this.$store.state.opened
    }
            
        },
        methods: {


          colorChange(value)
          {
        
           this.styleObject.backgroundColor=value;

          },
          fontChange(value)
          {
         
           this.styleObject1.color=value;

          },
          
            //退出登录
            logout: function () {
               //  var _this = this;
                this.$confirm('确认退出吗?', '提示', {
                    //type: 'warning'
                }).then(() => {
  

            
                  this.$router.push('/logout');
              

                }).catch(() => {

                });
               

            },
            changePassWd()
            {
               this.$router.push('/changePassWd');
            },
            personalInfo()
            {
               this.$router.push('/personalInfo');
            },


            
            toModel :function () {
                var _this = this;
                _this.$router.push('/model/index');
            },
              toCorporate :function () {
                var _this = this;
                let host = window.location.host;               
                let a = host;
                let b = a.split(':');
                let c = b[0];
                let url = c+":3000";
                window.location.href="http://"+url
                console.log(b);

                // window.location.href="http://"+url;

              // _this.$router.push({path: '/repository/index'});
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
                // this.collapsed=!this.collapsed;
                // if (this.collapsed) {

                // }
                this.$store.dispatch('ToggleSideBar');
                console.log(this.$store.state.opened);
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

            console.log(window.location.href);
            console.log(window.location.protocol);
            console.log(window.location.host);
            console.log(window.location.port);
            console.log(window.location.pathname);
            console.log(window.location.hash);

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
  


  .hideAside{
    margin-left: 10px;
    position: fixed;
    height: inherit;
    overflow-x: visible;
    z-index: 1000;
    max-width: 50px;
  }
  .MainAside{
    margin-left: 10px;
    position: fixed;
 
    overflow-x: visible;
    z-index: 1000;
    max-width: 210px;
   height: inherit;
  }

  .navigation{
    border: solid 1px #e6e6e6;
  }

  body > .el-container {

    margin-bottom: 40px;


  }
  .hideMain{
    height: inherit;
    margin-left: 100px;
    overflow-y: hidden;
  }
  .Main{
    height: inherit;
    margin-left: 240px;
    overflow-y: hidden;
    display: flex;
    flex-direction: column;
    padding: 20px 0px 0px 0px;

  }

  .el-menu-item
  {
    height: 50px;
    line-height: 27px;
    width: 100px;
    padding-left:  16px;
    padding-right: 16px;
    padding-top: 12px;
    padding-bottom: 12px;
    color: #1B1C1D;
  }

  .el-menu-item:hover
  {
  
    background: #fafafa;
    border-bottom: solid 1px;
  }
  .el-menu-item:focus
  {
  
    background: #fafafa;
  }

 .is-active
  {
    height: 52px;

  }

  .el-menu--horizontal>.el-menu-item.is-active
  {
        border-bottom: 2px solid #1B1C1D;
        font-weight: 700;



  }

/*  .el-menu--horizontal .el-menu-item:focus
  {
    color: #e6e6e6;
  }*/


  .el-menu--horizontal
  {
    border-right: none;
    border-bottom: none;
  }


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
         text-decoration:none;
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