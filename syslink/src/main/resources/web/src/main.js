import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'



import router  from './asyncRoutes'
import ZkTable from 'Vue-table-with-tree-grid'



import store from './vuex/store'
import Vuex from 'vuex'
import Vuetify from 'vuetify';
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
import routes from './routes'
import Mock from './mock'
import axios from 'axios'
//Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
import  func from './common/js/func'
Vue.prototype.$http = axios;
Vue.prototype.func = func;
Vue.config.productionTip = false;
axios.defaults.withCredentials = true;
//开启debug模式
Vue.config.debug = true;

Vue.use(ElementUI)
//Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(Vuetify);
Vue.use(ZkTable);

//NProgress.configure({ showSpinner: false });


 //用户不主动登出，永久免登陆，不适用session机制，
router.beforeEach((to, from, next) => {


  let uid = JSON.parse(localStorage.getItem('uid'))
  let logined  = JSON.parse(sessionStorage.getItem('logined'))
  let rememberMe = JSON.parse(localStorage.getItem('rememberMe'))
  console.log(rememberMe)
  console.log(logined)
  console.log(uid)
  //console.log(JSON.parse(sessionStorage.getItem('userInfo')))

  //是否登录作用域在单标签页
  if(logined)
  {
      if(to.path =='/login')
      {
            next('/Myspace')
      }
      else
      {
          //  验证用户信息是否失效
            if(store.getters.userInfo)
            {
              if(store.getters.isLoaded)
              {
                next()
              }
              else
              {
           
              
              const auths = store.getters.userInfo.auths
              store.dispatch('GenerateRoutes',auths).then(()=>{
              router.addRoutes(store.getters.addRouters)      
              next({ ...to }) 
                    }) 
              }
              
            }
            else
            {  

              store.dispatch('GetUserInfoFirst',uid).then(res =>{
              const auths = res.data.userInfo.auths
              store.dispatch('GenerateRoutes',auths).then(()=>{
              router.addRoutes(store.getters.addRouters)      
                next({ ...to })   
                      })
                  }).catch(() => {
               localStorage.clear()
               sessionStorage.clear()
               next({ path: '/login' })
          
          })
            
         }
      }
  }
  else
  {
     //是否启用记住我，作用域在整个浏览器
     if(rememberMe)
     {
        let a = {logined:''};
         a.logined=true;
         sessionStorage.setItem('logined',JSON.stringify(a));
         
        

         if (to.path == '/login'||to.path=='/index') 
         {
             next('/Myspace')
         }
         else
         {     
            
            //验证用户信息是否失效
            if(store.getters.userInfo)
            {

              if(store.getters.isLoaded)
              {  
                next()
              }
              else
              {
           
              
              const auths = store.getters.userInfo.auths
              console.log(auths)
                    store.dispatch('GenerateRoutes',auths).then(()=>{
                    router.addRoutes(store.getters.addRouters)      
                    next({ ...to }) 
                    }) 
              }
              
            }
            else
            { 
              store.dispatch('GetUserInfoFirst',uid).then(res =>{
              const auths = res.data.userInfo.auths
              store.dispatch('GenerateRoutes',auths).then(()=>{
             
              router.addRoutes(store.getters.addRouters)      
           
                next({ ...to })   
                      })
                  }).catch(() => {
               localStorage.clear()
               sessionStorage.clear()
               next({ path: '/login' })
          
          })
            }
         }
     }
     else
     {
      console.log('1')
        if(to.path == '/login'||to.path=='/index') 
        {
           console.log('2')
          next()
        }
        else
        {
         // next('/index')
         next('/login')
        }
     }
  }



     
})


// const router = new VueRouter({
//   routes
// })

// router.beforeEach((to, from, next) => {
//   //NProgress.start();
//   if (to.path == '/login') {
//     sessionStorage.removeItem('user');
//   }
//   let user = JSON.parse(sessionStorage.getItem('user'));
//   if (!user && to.path != '/login') {
//     next({ path: '/login' })
//   } else {
//     next()
//   }
// })


//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

