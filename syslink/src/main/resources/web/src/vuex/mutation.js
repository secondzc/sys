import * as types from './mutation_type'
export default{
    [types.SEND_A](state,aData){
        state.amsg=aData;
    },
    [types.SEND_B](state,bData){
        state.bmsg=bData;
    },
    [types.Send_ModelId](state,mdoelIdData){
        state.modelId = mdoelIdData;
    },
    [types.Send_TreeModelId](state,treeMdoelIdData){
        state.treeModelId= treeMdoelIdData;
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
    SET_ISLOADED: (state) => {
      state.isLoaded = !state.isLoaded
    },
    RESET_ROUTERS :(state,routers) => {
        state.routers = routers
    }

}