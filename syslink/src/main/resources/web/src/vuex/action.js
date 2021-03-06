import * as types from './mutation_type'

import { commonRoutes, limitRoutes } from '../asyncRoutes'
import axios from 'axios'
import Cookies from 'js-cookie'

  import global_ from '../views/global.vue'




/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(auth,role, route) {



    if(!auth)
  {
    auth=[];
  }
  if(!role)
  {
    role=[];
  }
  if(route.meta)
  {
    return role.some(role => route.meta.role.indexOf(role) >= 0)||auth.some(auth => route.meta.auth.indexOf(auth) >= 0)
  }
  else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(limitRoutes, auth,role) {
  const accessedRouters = limitRoutes.filter(route => {
    if (hasPermission(auth,role, route)) {

      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, auth,role)
 
      }
      return true
    }
    return false
  })
  return accessedRouters
}


function getUserInfoFirst(userName){
  return axios.post('api/user/getUserInfoFirst',userName)
}

function getUserInfo(){
  return axios.post('api/user/getUserInfo')
}

function getSession(){
  return axios.post('api/user/sessionJudge')
}

function autoPass(userName){
  return axios.post('api/user/autoPass',userName)
}

function getPrivateDirId(privateDir){
    return axios.post('api/directory/getPrivateDirId',privateDir)
}
function getPublicDirId(publicDir){
    return axios.post('api/directory/getPublicDirId',publicDir)
}

function clearLogin() {
      Cookies.remove('syslink')
      Cookies.remove('gogs_awesome')
    return    axios.post('api/user/destory')
              
              
               
              
    
}
function clearLogin1()
{
   let gogsDomain = global_.gogsHostPath;
   let gogsPort = global_.gogsPort;
   let domain = global_.HostPath;


      if(gogsPort.length>0)
        {
          return   axios.get ("http://"+domain+gogsPort+'/user/logout');
        }
      else
        {
          window.location.href="http://"+domain+'/'+gogsDomain;
          return   axios.get('http://'+domain+'/'+gogsDomain+'/user/logout');
        }
           
}







export default{
    sendA({commit},aData){
        commit(types.SEND_A,aData);
    },
    sendB({commit},bData){
        commit(types.SEND_B,bData);
    },
    sendTreeData({commit},data){
        commit(types.Send_TreeData,data);
    },
    sendModelId({commit},mdoelIdData){
        commit(types.Send_ModelId,mdoelIdData);
    },
    sendTreeModelId({commit},treeModelIdData){
        commit(types.Send_TreeModelId,treeModelIdData);
    },


    GetUserInfo({ commit, state },userInfo){
     
           return new Promise((resolve, reject) => {
        getUserInfo().then(response => {
          const data = response.data
          commit('SET_USERINFO',data.userInfo)  
          resolve(response)
        }).catch(error => {
          reject(error)
        })
         
          
      })
      },
 GetPrivateDirId({ commit, state },privateDirId){
    return new Promise((resolve, reject) => {
        getPrivateDirId().then(response => {
               const data = response.data
               commit('SET_PrivateDirId',data)
               resolve(response)
           }).catch(error => {
               reject(error)
           })
     })
},
 GetPublicDirId({ commit, state },privateDirId){
         return new Promise((resolve, reject) => {
            getPublicDirId().then(response => {
               const data = response.data
               commit('SET_PublicDirId',data)
               resolve(response)
           }).catch(error => {
               reject(error)
           })
 })
 
},


    GetUserInfoFirst({ commit, state },userName){
      
       return new Promise((resolve, reject) => {
        getUserInfoFirst(userName).then(response => {
          const data = response.data
          commit('SET_USERINFO',data.userInfo) 
          resolve(response)
        }).catch(error => {
          reject(error)
        })
         
      })
      },
    GetSession({commit,state},){
      return new Promise((resolve,reject)=>{
        getSession().then(response=>{
          const data = response.data
          console.log(data.session)
          commit('SET_SESSION',data.session)
          // commit('SET_UID',data.uid)
          console.log(state.session)
          resolve(response)
        }).catch(error=>{
          reject(error)
        })
      })
    },
    AutoPass({commit,state},userName){
      return new Promise((resolve,reject)=>{
        autoPass(userName).then(response=>{
          const data = response.data
          commit('SET_SESSION',data.session)
          console.log(data.session)
          // commit('SET_UID',data.uid)
          resolve(response)
        }).catch(error=>{
          reject(error)
        })
      })
    },
    GenerateRoutes({ commit }, token) {
      return new Promise(resolve => {
        const  auth  = token.auths
        const  role  = token.roles
      console.log(token)
       console.log(auth)
       console.log(role)
        let accessedRouters
  

          accessedRouters = filterAsyncRouter(limitRoutes, auth,role)
   
   
        commit('SET_ROUTERS', accessedRouters)
        commit('SET_ISLOADED')
        resolve()
      })
    },
    LogOut({ commit, state }) {

          return new Promise((resolve, reject) => {
          clearLogin()
          clearLogin1()
         commit('RESET_ROUTERS',commonRoutes)
          commit('SET_ISLOADED')
          resolve()
     
          
      })


    
    },
    ResetAuth({ commit, state }){
        return new Promise((resolve, reject) => {
  
          commit('SET_PERMISSION', [])
 
          resolve()

      })
    },
    ResetIsLoad({ commit, state }){
        return new Promise((resolve, reject) => {
  
          commit('SET_ISLOADED')
 
          resolve()

      })
    },
     ToggleSideBar({ commit }) {
      commit('TOGGLE_SIDEBAR')
    },
    ListCard({ commit }) {
      commit('LIST_CARD')
    },
    CardList({ commit }) {
      commit('CARD_LIST')
    }


}