<template>
	 <el-col :span="24" class="header" >
      <el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
        SYSLINK
      </el-col>
  
      <el-col :span="10">
        <a class="item" href="javascript:void(0)" v-if="this.func.isLogin()"  @click="toMySpace"  >工作台</a>
         <a class="item" href="http://gogs.modelica-china.com/#/index" v-else id="home-page">首页</a>
        <a class="item" href="http://gogs.modelica-china.com:3000/" v-if="this.func.isLogin()">协同</a>
        <a class="item" href="javascript:void(0)"  v-show="this.func.isLogin()" @click="toModel">模型</a>
        <a class="item" href="javascript:void(0)"  v-if="this.func.isLogin()" @click="toCorporate">仿真</a>
      <a class="item" href="javascript:void(0)"  v-if="!this.func.isLogin()"  @click="toLogin">登录</a> 
      
      </el-col>
      <el-col :span="4" class="userinfo">
        <el-dropdown  trigger="hover" v-if="this.func.isLogin()">
          <span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>我的消息</el-dropdown-item>
            <el-dropdown-item @click.native="changePassWd">修改密码</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    </el-col>
</template>


<script>

export default {

        data() {
            return {
            	judgeLogin:'',
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
        watch:
        {
        	judgeLogin:function()
        	{
        		console.log(this.func.isLogin());
        		return this.func.isLogin();
        	}
        },
        methods: {
            onSubmit() {
                console.log('submit!');
            },
            handleopen() {
            },
            handleclose() {
                //console.log('handleclose');
            },
            handleselect: function (a, b) {
            },
            //退出登录
            logout: function () {
                var _this = this;
                this.$confirm('确认退出吗?', '提示', {
                    //type: 'warning'
                }).then(() => {
               // sessionStorage.removeItem('user');

             
               // sessionStorage.removeItem('userInfo');
               sessionStorage.clear();
               localStorage.clear();
               _this.$store.dispatch('LogOut');
               console.log(sessionStorage.getItem('logined'));
               // _this.$http.post('api/user/destory');  
               /**  **/
                _this.$router.push('/index');
              //  location.reload();

                }).catch(() => {

                });


            },
            changePassWd()
            {

            },
            //跳转gogs
            gogs :function(){
                var _this = this;
                _this.$router.push('/main');
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
            showMenu(i,status){
                this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
            }
        },
        mounted() {
            var user = sessionStorage.getItem('user');
            if (user) {
                user = JSON.parse(user);
                this.sysUserName = user.name || '';
                this.sysUserAvatar = user.avatar || '';
            let abc = JSON.parse(sessionStorage.getItem('logined'));
            console.log(abc);
            if(this.func.isLogin)
            {
               console.log('abc');
            }
            else
            {
               console.log('bcd');
            }
            this.judgeLogin=this.$store.state.isLoaded;
           }

        }
  
}
</script>


<style scoped lang="scss">
	@import '~scss_vars';
	
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



</style>