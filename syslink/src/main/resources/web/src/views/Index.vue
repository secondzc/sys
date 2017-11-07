<template>
	
<el-container style="height: 100%; overflow:hidden;">
  <el-header style="padding: 0px;">
  <el-col :span="24" class="header" >
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        {{collapsed?'':sysName}}
      </el-col>
     <!--  <el-col :span="2"> -->
        <!-- <div class="tools" @click.prevent="collapse"> -->
         <!--  <div class="tools" >
          <i class="fa fa-align-justify"></i>
        </div> -->
      <!-- </el-col> -->
      <el-col :span="8">
         <a class="item" href="http://gogs.modelica-china.com/#/index"  id="home-page">首页</a>
        <a class="item" href="javascript:void(0)"   @click="toLogin">登录</a>
        <!-- <a class="item" href="http://gogs.modelica-china.com:3000/issues">工单管理</a>
        <a class="item" href="http://gogs.modelica-china.com:3000/pulls">合并请求</a>
        <a class="item" href="http://gogs.modelica-china.com:3000/explore/repos">探索</a> -->
        <!--        <el-menu theme="dark" :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                                     <el-menu-item index="1">处理中心</el-menu-item>
                                     <el-menu-item index="2">处理中心</el-menu-item>
                                     <el-menu-item index="3">处理中心</el-menu-item>

                                </el-menu> -->
      </el-col>
    <!--   <el-col :span="4" class="userinfo">
        <el-dropdown v-if="func.isLogin" trigger="hover">
          <span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>我的消息</el-dropdown-item>
            <el-dropdown-item>设置</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col> -->
    </el-col>
    </el-col>
<!-- 
<el-menu :default-active="activeIndex"  mode="horizontal"  class="navigation" @select="handleSelect"> -->

<!--   <el-menu-item index="7" style="float:left;margin-left: 10%;">
  <i class="el-icon-location-outline"></i></el-menu-item>
  <el-menu-item index="1">首页</el-menu-item>
  <el-menu-item index="2">工作台</el-menu-item>
  <el-menu-item index="3">协同</el-menu-item>
  <el-menu-item index="4">模型</el-menu-item>
  <el-menu-item index="5">仿真</el-menu-item>
  <el-menu-item index="6" style="float: right;margin-right: 10%;" @click="toLogin">登录</el-menu-item>
 </el-menu> -->
 </el-header>
  <el-main> <router-view></router-view></el-main>
 </el-container>
</template>

<script>

    export default {

        data() {
            return {
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
        methods: {
            
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
        },
        mounted() {
           
           console.log(sessionStorage.getItem('logined'));
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
    /*background-color: #D3DCE6;*/
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
   /* background-color: #E9EEF3;*/
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
        line-height: 38px;
        position: relative;
        display: inline-block;
        margin-right: 48px;
        text-decoration:none;
      }
      .item:not(:hover) {
        color: #9d9d9d
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