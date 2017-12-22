<template>
    <div class="modelContent">
      <div class="left">
          <div ><p>模型库：</p>
          <modeltree></modeltree>
          </div>
          <!--组件树-->

          <div style="margin-top: 50px;"><p>模型变量视图：</p></div>
          <variabletree  ></variabletree>
      </div>

      <div class="right">
          <!--组件图-->
          <div><p>组件图：</p></div>
          <component></component>


      </div>

    </div>
</template>

<script>
import {mapActions} from 'vuex'
import modelbasecont from './modelBaseContent.vue'
import modeltree from './modelTree.vue'
import variabletree from './Variabletree.vue'

export default {
  name: 'packageDiagramModel',
  components: {
      modeltree,
      variabletree
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
    max-width: 600px;
  float: left;
}
.right{
    max-width: 300px;
    float: right;
}


</style>