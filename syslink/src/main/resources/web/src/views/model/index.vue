<template >
    <div class="modelContent"  >
      <div class="left">
          <!--模型分类树-->
          <div style="border: 2px solid #9E9E9E;margin-top: 20px;padding: 10px">
          <kz-tree :data="tree" @node-click="hanldeNodeClick" style="min-height: 530px"></kz-tree>
          </div>
      </div>
      <div class="right" style="margin-left: 10px;margin-top: 20px">
          <div style="border: 2px solid #9E9E9E;min-height: 550px">
              <div style="display: inline-block;float: left; margin-left: 30px;;margin-top: 20px;">
                  <breadcrumb></breadcrumb>
              </div>
              <div style="display: inline-block;margin-left: 500px;;margin-top: 20px; ">
                  <!--<p>aaaa</p>-->
                  <div>
                      <!--工具条-->
                      <!--<el-col :span="24" class="toolbar" >-->
                      <el-form :inline="true" :model="filters">
                          <el-form-item>
                              <el-input v-model="filters.name" placeholder="模型名称"></el-input>
                          </el-form-item>
                          <el-form-item>
                              <el-button type="primary" v-on:click="getModel">查询</el-button>
                          </el-form-item>
                      </el-form>
                      <!--</el-col>-->
                  </div>
              </div>
              <div style="display: inline-block;float: right;margin-top: 20px;margin-right: 20px;" >
                  <upload-file ></upload-file>
              </div>

              <hr>
              <div style="margin-top: 20px">
                  <div style="display: inline-block;"><p>排序：</p></div>
                  <div id="appp" style="display: inline-block;">

                      <sortable-list
                              :titles="sorttitles"
                              :list="repositories"
                              :sorting-index='0'
                              sorting-type="desc"
                              @sort-finish="onSortFinsh"
                      >
                      </sortable-list>
              </div>
              </div>
              <div style="height: 30px;">
                  <i class="iconfont" id="listIcon" name="listIcon" @click="switchList" style="float: right;">&#xe60b;</i>
                  <i class="iconfont" id="menuIcon" name="menuIcon" @click="switchMenu" style="float: right">&#xe60a;</i>
                  <!--<i class="iconfont" id="information" name="information" @click="viewInfo" style="float: right;margin-right: 20px">&#xe6a4;</i>-->
              </div>
              <hr>
              <div style="margin-top:20px;margin-left: 10px;margin-right: 10px;">
              <!--模型列表-->

              <div>
              <div id="modelList" style="display: block;">
              <!--<model-list ></model-list>-->
                  <template>
                      <section>
                  <div>
                      <!--<div>-->
                          <!--&lt;!&ndash;工具条&ndash;&gt;-->
                          <!--&lt;!&ndash;<el-col :span="24" class="toolbar" >&ndash;&gt;-->
                          <!--<el-form :inline="true" :model="filters">-->
                              <!--<el-form-item>-->
                                  <!--<el-input v-model="filters.name" placeholder="模型名称"></el-input>-->
                              <!--</el-form-item>-->
                              <!--<el-form-item>-->
                                  <!--<el-button type="primary" v-on:click="getModel">查询</el-button>-->
                              <!--</el-form-item>-->
                          <!--</el-form>-->
                          <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
                      <!--</div>-->
                      <p>{{getRepos}}</p>
                      <waterfall
                              :align="align"
                              :line-gap="300"
                              :min-line-gap="100"
                              :max-line-gap="300"
                              :single-max-width="260"
                              :watch="repositories"
                              @reflowed="reflowed"
                              ref="waterfall"
                      >
                          <!-- each component is wrapped by a waterfall slot -->
                          <waterfall-slot
                                  v-for="(item, index) in repositories"
                                  :width="250"
                                  :height="300"
                                  :order="index"
                                  :key="item.index"
                                  move-class="item-move"
                          >
                              <!-- :style="item.style" -->
                              <div class="RepCard" id="modelId"  :index="item.index" v-on:dblclick="modelVar(item.index)">
                                  <div class="center-aligned">
                                      <!--<img v-bind:src="item.imageUrl" class="image">-->
                                      <img src="./../../assets/test1.png" class="image">
                                  </div>
                                  <div class="content">
                                      <a href="javascript:void(0)">
                                          <h4 >模型名称：{{item.name}}</h4>
                                      </a>
                                      <h4>上传者：{{item.userName}}</h4>
                                      <h4>上传日期：{{item.createTime}}</h4>
                                      <small>{{item.discription}}</small>
                                  </div>
                                  <div>
                                      <!--<a href="javascript:void(0)" @click="modelVar(item.index)" title="模型详细信息">详细信息</a>-->
                                      <!--<a href="javascript:void(0)" @click="Model(item.index)" title="模型组件信息">组件信息</a>-->
                                      <!--<a href="javascript:void(0)" @click="Variable" title="模型组件参数信息">参数信息</a>-->
                                  </div>
                                  <div  class="cardButton">
                                      <a class="user username" href="javascript:void(0)">
                                          <!--//TODO:用户名-->
                                          {{item.type}}
                                      </a>
                                      <div class="right-buttons">
                                      </div>
                                  </div>
                              </div>

                          </waterfall-slot>
                      </waterfall>
                  </div>
                      </section>
                  </template>
              </div>
              <div id="packageList" style="display: none">
              <packageList></packageList>
              </div>
              </div>
              </div>

          </div>
          <!--<div style="margin-top:20px;margin-left: 10px;margin-right: 10px;">-->
          <!--&lt;!&ndash;模型列表&ndash;&gt;-->

          <!--<div>-->
              <!--<div id="modelList" style="display: block;">-->
                  <!--<model-list ></model-list>-->
              <!--</div>-->
              <!--<div id="packageList" style="display: none">-->
                  <!--<packageList></packageList>-->
              <!--</div>-->
          <!--</div>-->
          <!--</div>-->

      </div>
    </div>
</template>

<script>
import kzTree from './directory.vue';
import modelList from './model.vue' ;
import packageList from './packageList.vue'
import uploadFile from  '../nav3/Page6.vue'
import breadcrumb from '../nav3/breadcrumb.vue'
import {mapActions} from 'vuex'

import { getReposList} from '../../api/api'
//waterfall瀑布流组件
import Waterfall from 'vue-waterfall/lib/waterfall'
import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'
import { mapState,mapGetters} from 'vuex'
import sortableList from './sortable-list'


export default {
  name: 'ModelContent',
  components: {
    kzTree,
    modelList,
    packageList,
      uploadFile,
      breadcrumb,
      Waterfall,
      WaterfallSlot,
      sortableList
  },
  data () {
    return {
        sorttitles:[{
            key:'name',
            name:'名称'
        },{
            key:'uploadTime',
            name:'上传时间'
        },{
            key:'userName',
            name:'作者'
        },],
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
        filters:{
            name : ""
        },
        loading: false,
        align:'left',
        repositories:[
        ],
        isBusy: false,
    }
  },
    computed:{
        ...mapState({
            a:state =>state.a
        }),
        ...mapGetters(['amsg']),
        getRepos(){
            var _this = this;
            if(_this.amsg != null && _this.amsg != ""  ){
                var url = '/api/model/list?parent_id='+ _this.amsg
            }else{
                _this.amsg = 0;
                var url = '/api/model/list?parent_id='+ _this.amsg
            }
            _this.$http.post(url)
                .then(function (response) {
                    _this.repositories = response.data.repositories;
                })
                .catch(function (error) {
                    console.log(error);
                });
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
      },
      //获取用户列表
      getModel() {
          let para = {
              name: this.filters.name
          };
          var _this = this;
          if(_this.amsg != null && _this.amsg != ""){
              var url = '/api/model/list?parent_id='+ _this.amsg
          }else{
              _this.amsg = null;
              var url = '/api/model/list?parent_id='+ _this.amsg
          }
          _this.$http.post(url)
              .then(function (response) {
                  let searchModel = response.data.repositories.filter(model =>{
                      for(var i =0 ;i <response.data.repositories.length; i++){
                          if(para.name && response.data.repositories[i].name.indexOf(para.name) == -1 ){
                              return false
                          }
                          else{
                              return true
                          }
                      }
                  })
                  _this.repositories = searchModel;
                  //   _this.repositories = response.data.repositories;
              })
              .catch(function (error) {
                  console.log(error);
              });
      },
      reflowed: function () {
          this.isBusy = false
      },
      modelVar : function (modelId) {
          this.$store.dispatch('sendModelId',modelId);
          this.$store.dispatch('sendTreeModelId',modelId);
          this.$router.push({path: '/model/packageDiagram'});
      },
      onSortFinsh(repositories){
          console.log('onSortFinsh');
          console.log(JSON.stringify(repositories));
          this.list=repositories
      }
  }
}
</script>
<style scoped>

.left {
  max-width: 400px;
  float: left;
}

.right{
    /*min-width: 750px;*/
    width: 75%;
    float : left;
    height: 100%
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
.modelContent{
    height: 100%;
}




.RepCard {
    position: absolute;
    top: 10px;
    left: 10px;
    right: 10px;
    bottom: 10px;
    background-color: #FFF;
    border-radius: 5px;
    box-shadow: 0 2px 3px 0 #d4d4d5, 0 0 0 1px #ededed;
    transition: all 1s ease 0s;
    overflow: hidden;
}
.image{
    display: block;
    width: 100%;
    /*需要对高度做限制*/
    max-height: 160px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    transition: all 1s ease 0s;
}
.RepCard .content{
    padding: 6px 15px 6px 15px;
    height: 70px;
    overflow: hidden;
}
.RepCard .content h4{
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: keep-all;
    white-space: nowrap;
    display: block;
    font-weight: bold;
    line-height: 18px;
    margin-top: 7px;
    margin-bottom: 5px;
}
.RepCard .content a{
    color: #60BDE8;
    text-decoration: none;
}
.RepCard .content small {
    color: #838383;
    padding-bottom: 10px;
    font-size: 85%;
}
.RepCard .cardButton {
    margin-top: .5rem;
    padding: .5rem;
    text-align: left;
    background-color: #f8f8f8;
}
.username {
    text-transform: capitalize;
    color: #888;
}
.avatar.round {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    border: 1px solid rgba(139,191,229,.5);
}
.item-move {
    transition: all .5s cubic-bezier(.55,0,.1,1);
    -webkit-transition: all .5s cubic-bezier(.55,0,.1,1);
}

    /*排序*/
#appp {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /*margin-top: 60px;*/
    max-width: 400px;
    min-width: 300px;
    margin: auto;
}

.item{
    cursor: pointer;
    position: relative;
    height: 11.625rem;/* 465px */
    border-bottom: 1px solid #ddd;
    /*background: #eaeaea;*/
}
.item>img{
    position: absolute;
    left: .7rem;/* 28px */
    top: .7rem;/* 28px */
    width: 7.75rem;/* 310px */
    height: 10.45rem;/* 418px */
    border-bottom: 1px solid #ddd;
    background: #eaeaea;
}

.text-container{
    position: absolute;
    left: 9.65rem;/* 386px */
    text-align: left;
    color: #999
}

.text-container >div{
    margin-bottom: 1.25rem;/* 50px */
}
.text-container .title{
    font-weight: bold;
    margin-top: 2rem;/* 80px */
    color: black
}

.item-complete-move{
    transition: transform 1s;
    -moz-transition: transform 1s;
    -webkit-transition: transform 1s;
    -o-transition: transform 1s;
}
/*排序*/

</style>