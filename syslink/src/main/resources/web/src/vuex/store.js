import vuex from 'vuex'
import vue from 'vue'
import actions from './action'
import mutations from './mutation'
import { commonRoutes, limitRoutes } from '../asyncRoutes'
import Cookies from 'js-cookie'
vue.use(vuex);
export default new vuex.Store({
    state:{
        amsg: '',
        bmsg: '',
        modelId : '',
        treeModelId : '',
        // treeRootId : '',
        routers:commonRoutes,
        routers1:limitRoutes,
        addRouters:[],
        permission:[],
        isLoaded:false,
        userInfo:'',
        privateDirId : '',
        publicDirId : '',
        opened:!+Cookies.get('sidebarStatus'),
        list:Cookies.get('bbb'),
        session:false
    },
    actions,
    mutations,
    getters:{
        amsg(state){
            console.log(state)
            return state.amsg
        },
        bmsg(state){
            return state.bmsg
        },
        modelId(state){
            return state.modelId
        },
        treeModelId(state){
            return state.treeModelId
        },
        // treeRootId(state){
        //     return state.treeRootId
        // },

         routers(state){

            return state.routers
        },
        routers1(state){

            return state.routers1
        },
        addRouters(state){

            return state.addRouters
        },
        permission(state){
            return state.permission
        },
        isLoaded(state){
            return state.isLoaded
        },
        userInfo(state){
            return state.userInfo
        },

        privateDirId(state){
            return state.privateDirId
        },
        publicDirId(state){
            return state.publicDirId
        },
         opened(state){
            return state.opened
        },
        list(state){
            return state.list
        },
        session(state){
            return state.session
        },
    }
})
