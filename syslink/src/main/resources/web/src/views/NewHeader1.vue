<template>
  
<el-container style="height: 100%;overflow:hidden;width: 100%;">
 <el-header style="padding: 0px;height: 50px;background-color: #fafafa;border-bottom: solid 2px #e6e6e6;padding-right: 20px;display: inline-flex;width: inherit;" v-bind:style="styleObject">





   <!-- <div style="min-width: 250px;margin-left: 100px;">
     <img :src="SysLogo"  style="height: 40px;margin-top: 5px;" />
   <img :src="SylTitle"  style="height: 40px;" />
   </div> -->


   <div style="min-width: 350px;display: flex;">
     <div style="line-height: 50px;width: 150px;">
      
    </div>
    <div  @click="toBrief" class="hoverCursor">
   <img :src="SysLogo"  style="height: 40px;margin-top: 5px;" />
   <img :src="SylTitle"  style="height: 40px;margin-top: 5px;" />
    </div>
 
   </div>
    
    
  
 
  
  <el-menu default-active="3" mode="horizontal"  style="width: inherit;margin-left: 10%;background-color: inherit;min-width: 500px;display: flex;"  v-show="this.func.isLogin()"  >
   <div style="width: 20px;">
     
   </div>
   
  
  <el-menu-item index="1"  @click="toBrief" v-bind:style="styleObject1">工作台</el-menu-item>
  <el-menu-item index="2" @click="toCorporate" v-bind:style="styleObject1">协同</el-menu-item>
  <el-menu-item index="3"  @click="toModel" v-bind:style="styleObject1">模型</el-menu-item>
 <!--  <el-menu-item index="4" @click="toCorporate" v-bind:style="styleObject1">仿真</el-menu-item> -->

  </el-menu>
  
  
  <div style="width: 100px;display: flex;align-items: center;">
  		<span style="margin-right: 10px;font-family: Microsoft Yahei;">
     {{this.$store.state.userInfo.profile.name}} 
      </span>
  	  <el-dropdown style="min-width: 100px;height: 50px;" trigger="click">
   
   
     <div style="display: flex;align-items: center;height: inherit;" >
     
     <div class="icon-focus" style="display: flex;align-items: center;">
     	 <i class="iconfont icon-gerenxinxi" style="font-size: 32px;"></i>
       <i class="iconfont icon-xiala" style="font-size: 16px;"></i>
     </div>
   
   </div>
  
  <el-dropdown-menu slot="dropdown">
     <!--  <div style="text-align: center;border-bottom: solid 1px #e6ebf5;height: 30px;line-height: 30px;font-weight: 700;font-size: 12px;">
        <span>当前用户{{this.$store.state.userInfo.profile.name}} </span>
      </div > -->
     <el-dropdown-item @click.native="personalInfo"><i class="iconfont icon-yonghuziliao-xianxing" style="font-size: 20px;"></i> 个人信息</el-dropdown-item>
     <el-dropdown-item divided @click.native="logout"><i class="iconfont icon-dengluzhuangtaituichu" style="font-size: 20px;"></i> 退出</el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>
  </div>

  







 
  






 </el-header>


        <router-view style="height: cacl(100%-10px);"></router-view>




</el-container>


 

</template>

<script>
import Sidebar from './Sidebar'

import Cookies from 'js-cookie'
import global_ from './global.vue'
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
               type:'warning',
					 closeOnClickModal:false
                }).then(() => {
  

            
                  this.$router.push('/logout');
              

                }).catch(() => {

                });
               

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
                let gogsDomain = global_.gogsHostPath;
                let gogsPort = global_.gogsPort;
                let domain = global_.HostPath;

                 if(gogsPort.length>0)
                {
                  window.location.href="http://"+domain+gogsPort;
                }
                else
                {
                  window.location.href="http://"+domain+'/'+gogsDomain;
                }

                // window.location.href="http://"+url;

              // _this.$router.push({path: '/repository/index'});
            },
            toLogin()
            {
                var _this = this;
              _this.$router.push('/login');
            },
            toBrief()
            {
                 var _this = this;
              _this.$router.push('/brief');
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

            // console.log(window.location.href);
            // console.log(window.location.protocol);
            // console.log(window.location.host);
            // console.log(window.location.port);
            // console.log(window.location.pathname);
            // console.log(window.location.hash);

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


    .icon-focus:focus{
      background-color: #e6e6e6;
    }
    .icon-focus:hover{
      background-color: #e6e6e6;
    }




  
  
 /* .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }*/
</style>