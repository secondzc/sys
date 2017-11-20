import * as types from './mutation_type'

import { commonRoutes, limitRoutes } from '../asyncRoutes'
import axios from 'axios'






/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.role) {
    return roles.some(role => route.meta.role.indexOf(role) >= 0)
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(limitRoutes, roles) {
  const accessedRouters = limitRoutes.filter(route => {
    if (hasPermission(roles, route)) {

      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, roles)
 
      }
      return true
    }
    return false
  })
  return accessedRouters
}


/**
**通过localStorage 中的uid获取用户信息
**/
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


export default{
    sendA({commit},aData){
        commit(types.SEND_A,aData);
    },
    sendB({commit},bData){
        commit(types.SEND_B,bData);
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
    GetSession({commit,state}){
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
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const  roles  = data
       //  console.log(roles)
       console.log('7')
        let accessedRouters
  

          accessedRouters = filterAsyncRouter(limitRoutes, roles)
   
   
        commit('SET_ROUTERS', accessedRouters)
        commit('SET_ISLOADED')
        resolve()
      })
    },
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
   //     logout(state.token).then(() => {
   //       console.log(state.permission)
          commit('RESET_ROUTERS',commonRoutes)
          commit('SET_ISLOADED')
      //    removeToken()
          resolve()
 //       }).catch(error => {
 //         reject(error)
 //       })
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