<template>
    <div class="modelContent">
      <div class="left">
          <!--模型组件视图-->
          <div >
              <p>模型组件视图：</p>
              <modelComponent></modelComponent>
          </div>

          <!--变量树-->
          <div >
              <p>模型变量视图：</p>
              <variabletree ></variabletree>
          </div>
      </div>

    </div>
</template>

<script>
import {mapActions} from 'vuex'
import modelbasecont from './modelBaseContent.vue'
import modeltree from './modelTree.vue'
import variabletree from './Variabletree.vue'
import modelComponent from './ModelComponent.vue'

export default {
  name: 'packageDiagramVariable',
  components: {
      modeltree,
      variabletree,
      modelComponent,
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