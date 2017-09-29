import vuex from 'vuex'
import vue from 'vue'
import actions from './action'
import mutations from './mutation'
vue.use(vuex);
export default new vuex.Store({
    state:{
        amsg: '',
        bmsg: '',
        modelId : '',
        treeModelId : '',
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
        }
    }
})
