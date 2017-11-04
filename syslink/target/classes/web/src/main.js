import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'



//import router  from './asyncRoutes'



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
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(Vuetify);

//NProgress.configure({ showSpinner: false });


/**

router.beforeEach((to, from, next) => {


  let uid = JSON.parse(localStorage.getItem('uid'))
  console.log(uid)

     
     //验证本地是否存有用户id
     if(uid)
     {

         if (to.path == '/login') 
         {
         
             next({path:'/repository/index'})
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

              
              console.log(uid.uid)
                       store.dispatch('GetUserInfoFirst',uid).then(res =>{

              const auths = res.data.userInfo.auths
              console.log(auths)
          //  store.dispatch('SET_PERMISSION',auths)
             
              store.dispatch('GenerateRoutes',auths).then(()=>{
             
              router.addRoutes(store.getters.addRouters)      
           
                next({ ...to })   
                      })
                  }).catch(() => {
            localStorage.clear()
               next({ path: '/login' })
          
        })
            }
         }
     }
     else
     {
        if(to.path == '/login') 
        {
          next()
        }
        else
        {
          next('/login')
        }
     }
     
})

**/



const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})
/****/

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

