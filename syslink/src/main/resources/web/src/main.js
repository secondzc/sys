import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

//import './assets/theme/theme-green/index.css'
import VueRouter from 'vue-router'



import router  from './asyncRoutes'
import ZkTable from 'Vue-table-with-tree-grid'
import Cookies from 'js-cookie'


import store from './vuex/store'
import Vuex from 'vuex'
import Vuetify from 'vuetify';
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'

import Mock from './mock'
import axios from 'axios'


//Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
import './assets/iconfont/iconfont.css'
// @import url at.alicdn.com/t/font_472907_116i00seniuanhfr.css
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



router.beforeEach((to, from, next) => {


  console.log(from.path)
  console.log(to.path)


 



    let sessionId = Cookies.get('JSESSIONID') 
     let SID = {SID:sessionId};
     store.dispatch('GetSession',SID).then(()=>{
       console.log(store.getters.session)
       if(store.getters.session)
     {
    
     if(to.path=='/login'||to.path=='/')
     {
      console.log(2)
       next('/brief')
     }
     else
     {
        //  验证用户信息是否失效
            if(store.getters.userInfo)
            {
               console.log(1)
              if(store.getters.isLoaded)
              {
                 console.log(5)
                next()
              }
              else
              {
                 console.log(6)
              let token = {auths:'',roles:''}
              token.auths = store.getters.userInfo.auths
              token.roles = store.getters.userInfo.roles
        
              store.dispatch('GenerateRoutes',token).then(()=>{
              router.addRoutes(store.getters.addRouters)      
              next({ ...to }) 
                    }) 
              }
              
            }
            else
            {  

               console.log(4)


              store.dispatch('GetUserInfo').then(res =>{
              // const auths = res.data.userInfo.auths
              // const roles = res.data.userInfo.roles
              let token = {auths:'',roles:''}
              token.auths = res.data.userInfo.auths
              token.roles = res.data.userInfo.roles
              store.dispatch('GenerateRoutes',token).then(()=>{
              router.addRoutes(store.getters.addRouters)      
                next({ ...to })   
                      })

                  }).catch(() => {
             store.dispatch('LogOut').then(()=>{
                 next({ path: '/login' })
                    }) 
               
          
          })
              store.dispatch('GetPublicDirId')
              store.dispatch('GetPrivateDirId')
            
         }
     }
  }
  else
  {
     let rememberMe = Cookies.get('syslink')
      console.log(rememberMe)
      if(rememberMe)
      {
          let userName = {userName:rememberMe};
         store.dispatch('AutoPass',userName).then(()=>{



            if(to.path=='/login'||to.path=='/')
            {
           
             next('/brief')
            }
           else
           {
            console.log(3);
        //  验证用户信息是否失效
            if(store.getters.userInfo)
            {
               console.log(11)
              if(store.getters.isLoaded)
              {
  
                 console.log(9)
                next()
              }
              else
              {
                console.log(11)
           
             let token = {auths:'',roles:''}
              token.auths = res.data.userInfo.auths
              token.roles = res.data.userInfo.roles
              store.dispatch('GenerateRoutes',token).then(()=>{
              router.addRoutes(store.getters.addRouters)      
              next({ ...to }) 
                    }) 
              }
              
            }
            else
            {  
               console.log(10)

            

              store.dispatch('GetUserInfoFirst',userName).then(res =>{
              let token = {auths:'',roles:''}
              token.auths = res.data.userInfo.auths
              token.roles = res.data.userInfo.roles
              store.dispatch('GenerateRoutes',token).then(()=>{
              router.addRoutes(store.getters.addRouters)      
                next({ ...to })   
                      })
                  }).catch(() => {
            store.dispatch('LogOut').then(()=>{
                 next({ path: '/login' })
                    }) 
          
          })
            store.dispatch('GetPublicDirId')
              store.dispatch('GetPrivateDirId')
         }
        }  
         })
         .catch(() => {
               store.dispatch('LogOut').then(()=>{
                 next({ path: '/login' })
                    }) 
          
          })

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
  }


  }).catch(() => {
            store.dispatch('LogOut').then(()=>{
                 next({ path: '/login' })
                    }) 
          
          })

 })





 

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

