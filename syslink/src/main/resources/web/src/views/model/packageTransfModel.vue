<template>
    <div class="modelContent">
        <div class="left">
            <!--<div><p>模型基本信息：</p></div>-->
            <!--<modelbasecont></modelbasecont>-->
        </div>

        <div class="right">
        </div>

    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    import modelbasecont from './modelBaseContent.vue'
    import modeltree from './modelTree.vue'
    import component from './Component.vue'
    import variabletree from './Variabletree.vue'

    export default {
        name: 'packageDiagram',
        components: {
            modelbasecont,
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
            },
            dispatchModelId(){
                if(this.$route.query.modelId != null &&  this.$route.query.modelId != ""){
                    this.$store.dispatch('sendModelId',this.$route.query.modelId);
                    this.$router.push({path: '/model/modelCont'});
                }
            }
//      getRepos (data) {
//          //该目录下的模型列表
//          this.treeItem = data;
//      }
        }, mounted(){
            this.dispatchModelId();
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