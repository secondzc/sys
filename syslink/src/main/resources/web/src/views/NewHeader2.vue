<template>
  
<el-container style="height: 100%;overflow:hidden;width: 100%;">
 <el-header style="padding: 0px;min-height: 50px;background-color: #fafafa;border-bottom: solid 2px #e6e6e6;padding-right: 20px;display: inline-flex;width: inherit;" v-bind:style="styleObject">





   <!-- <div style="min-width: 250px;margin-left: 100px;">
     <img :src="SysLogo"  style="height: 40px;margin-top: 5px;" />
   <img :src="SylTitle"  style="height: 40px;" />
   </div> -->


   <div style="min-width: 350px;display: flex;">
     <div style="line-height: 50px;width: 150px;">
      
    </div>
    <div>
   <img :src="SysLogo"  style="height: 40px;margin-top: 5px;" />
   <img :src="SylTitle"  style="height: 40px;margin-top: 5px;" />
    </div>
 
   </div>
    
    
  
 
  
  <el-menu  mode="horizontal"  style="width: inherit;margin-left: 10%;background-color: inherit;display: flex;"  v-show="this.func.isLogin()"  >
   <div style="width: 20px;">
     
   </div>
   
  
  <el-menu-item index="1"  @click="toIndex" v-bind:style="styleObject1">首页</el-menu-item>
  <div style="min-width: 60%;"></div>
  <el-menu-item index="2" @click="toLogin" v-bind:style="styleObject1">登录</el-menu-item>


  </el-menu>



  






 </el-header>


        <router-view style="height: inherit;"></router-view>




</el-container>


 

</template>

<script>
import Sidebar from './Sidebar'

import Cookies from 'js-cookie'

import SysLogo from '@/assets/syslogo.png'
import SylTitle from '@/assets/systitle.png'
    export default {
       components: {
          Sidebar
 
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
            toIndex()
            {
                 var _this = this;
              _this.$router.push('/index');
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
  }
  .Main{
    height: inherit;
    margin-left: 240px;

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