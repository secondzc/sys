import * as types from './mutation_type'
import Cookies from 'js-cookie'
export default{
    [types.SEND_A](state,aData){
        state.amsg=aData;
    },
    [types.SEND_B](state,bData){
        state.bmsg=bData;
    },
    [types.Send_TreeData](state,data){
        state.treeDatas=data;
    },
    [types.Send_ModelId](state,mdoelIdData){
        state.modelId = mdoelIdData;
    },
    [types.Send_TreeModelId](state,treeMdoelIdData){
        state.treeModelId= treeMdoelIdData;
    },
    [types.Send_TreeRootId](state,treeRootIdData){
        state.treeRootId= treeRootIdData;
    },
     SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = state.routers.concat(routers)
        console.log(state.addRouters)
        console.log(state.routers)
    },
    SET_PERMISSION: (state, permission) => {
      state.permission = permission
    },
     SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
    },

    SET_PrivateDirId: (state, privateDirId) => {
        state.privateDirId = privateDirId
    },
    SET_PublicDirId: (state, publicDirId) => {
        state.publicDirId = publicDirId
    },

    SET_ISLOADED: (state) => {
      state.isLoaded = !state.isLoaded
    },
    RESET_ROUTERS :(state,routers) => {
        state.routers = routers
    },
     TOGGLE_SIDEBAR: state => {
      if (state.opened) {
        Cookies.set('sidebarStatus', 1)
      } else {
        Cookies.set('sidebarStatus', 0)
      }
      state.opened = !state.opened
    },
     LIST_CARD: state => {
    
       
        Cookies.set('bbb', true)
      
      state.list = Cookies.get('bbb')
      console.log(state.list)
      },
      CARD_LIST:state => {
    
        Cookies.set('bbb', false)
          state.list = Cookies.get('bbb')
           console.log(state.list)
      
      },
    SET_SESSION:(state,session)=>{
      state.session = session
    }

}