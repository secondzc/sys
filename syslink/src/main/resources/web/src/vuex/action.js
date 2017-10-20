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
function getUserInfoFirst(para){
  return axios.post('api/user/getUserInfoFirst',para)
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
     

            commit('SET_USERINFO',userInfo)  
      },

    GetUserInfoFirst({ commit, state },uid){
      
       return new Promise((resolve, reject) => {
        getUserInfoFirst(uid).then(response => {
          const data = response.data
          commit('SET_USERINFO',data.userInfo) 
          resolve(response)
        }).catch(error => {
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
    }


}