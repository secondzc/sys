<template>
    <div class="modelContent">
        <div class="left">
            <div><p>模型基本信息：</p></div>
            <!--<modelbasecont></modelbasecont>-->
            <modelCont></modelCont>
        </div>

        <div class="right">
        </div>

    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import modeltree from './modelTree.vue'
    import component from './Component.vue'
    import variabletree from './Variabletree.vue'
    import modelCont from './modelCont.vue'

    export default {
        name: 'packageDiagram',
        components: {
            modelCont,
        },
        data () {
            return {
                data :{
                },
            }
        },
        methods: {
            hanldeNodeClick (data) {
                //该目录下的模型列表
                this.treeItem = data;
//        this.$emit("node-click",data)
                this.$store.dispatch('sendA',data.parent_id);
                if(data.parent_id <= 0){
                    this.$store.dispatch('sendB',-1);
                }
                else{
                    this.$store.dispatch('sendB',data.parent_id);
                }
            }
//      getRepos (data) {
//          //该目录下的模型列表
//          this.treeItem = data;
//      }
        }
    }
</script>
<style scoped>

    .left {
        min-width: 300px;
        max-width: 750px;
        float: left;
    }
    .right{
        min-width: 300px;
        float: right;
    }


</style>