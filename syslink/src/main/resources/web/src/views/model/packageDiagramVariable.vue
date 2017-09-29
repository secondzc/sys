<template>
    <div class="modelContent">
      <div class="left">
          <!--变量树-->
          <div >
              <p>变量树：</p>
              <variabletree  ></variabletree>
          </div>
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
  name: 'packageDiagramVariable',
  components: {
      modeltree,
      component,
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
    max-width: 750px;
  float: left;
}
.right{
    min-width: 300px;
    float: right;
}


</style>