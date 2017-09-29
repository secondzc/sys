import * as types from './mutation_type'
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
    }
}