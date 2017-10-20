<template>
    <div class="modelContent">
      <div class="left">
          <!--模型分类树-->
          <kz-tree :data="tree" @node-click="hanldeNodeClick"></kz-tree>
      </div>
      <div class="right">
          <!--模型列表-->
          <div class="dir">
              <!--<img style="height: 40px;width: 40px;;" src="../../assets/fileList.png"></img>-->
              <i class="iconfont" id="listIcon" name="listIcon" @click="switchList" style="float: right;">&#xe60b;</i>
              <i class="iconfont" id="menuIcon" name="menuIcon" @click="switchMenu" style="float: right">&#xe60a;</i>
              <i class="iconfont" id="information" name="information" @click="viewInfo" style="float: right;margin-right: 20px">&#xe6a4;</i>

          </div>
          <div id="modelList" style="display: block;">
              <model-list ></model-list>
          </div>
          <div id="packageList" style="display: none">
              <packageList></packageList>
          </div>

      </div>
    </div>
</template>

<script>
import kzTree from './directory.vue';
import modelList from './model.vue' ;
import packageList from './packageList.vue'
import {mapActions} from 'vuex'


export default {
  name: 'ModelContent',
  components: {
    kzTree,
    modelList,
    packageList
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
      switchList(){
          document.getElementById("modelList").style.display="block";
          document.getElementById("packageList").style.display="none";
      },
      switchMenu(){
          document.getElementById("packageList").style.display="block";
          document.getElementById("modelList").style.display="none";
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
  max-width: 300px;
  float: left;
}

.right{
    min-width: 750px;
    max-width: 750px;
    float : left;
}
.dir{
    color: #666;
    height: 28px;
    line-height: 28px;
    font-size: 12px;
    position: relative;
    padding-top: 16px;
    padding-right: 28px;
    float: right;
}

.iconfont{
    font-family:"iconfont";
    font-size:16px;
    font-style:normal;
}
@font-face {
    font-family: 'iconfont';  /* project id 445633 */
    src: url('//at.alicdn.com/t/font_445633_vk2n1ctxz8iftj4i.eot');
    src: url('//at.alicdn.com/t/font_445633_vk2n1ctxz8iftj4i.eot?#iefix') format('embedded-opentype'),
    url('//at.alicdn.com/t/font_445633_vk2n1ctxz8iftj4i.woff') format('woff'),
    url('//at.alicdn.com/t/font_445633_vk2n1ctxz8iftj4i.ttf') format('truetype'),
    url('//at.alicdn.com/t/font_445633_vk2n1ctxz8iftj4i.svg#iconfont') format('svg');
}


</style>