<template>
    <div class="modelContent">
      <div class="left">
          <!--模型分类树-->
          <kz-tree :data="tree" @node-click="hanldeNodeClick"></kz-tree>
      </div>
      <div class="right">
          <!--模型列表-->
          <model-list ></model-list>
      </div>
    </div>
</template>

<script>
import kzTree from './directory.vue';
import modelList from './model.vue' ;
import {mapActions} from 'vuex'


export default {
  name: 'ModelContent',
  components: {
    kzTree,
    modelList,
  },
  data () {
    return {
      tree: {
        url: {
//          C: '/directory/add',
//          U: '/directory/update',
//          R: '/directory/list',
//          D: '/directory/delete'
            C: '/api/directory/add',
            U: '/api/directory/update',
            R: 'api/directory/list',
            D: '/api/directory/delete'
        }
      },
      data :{
          treeItem : "",
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
  float: left;
}


</style>