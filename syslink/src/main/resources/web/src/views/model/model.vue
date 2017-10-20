<template>
    <section>
        <!--<div >-->
        <!--<model-content  @node-click="getRepos"></model-content>-->
        <!--</div>-->
        <!--<div><p>{{treeItem}}</p></div>-->
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
                  <!--<a class="user username" href="/circuitdigest">-->
                  <a class="user username" href="javascript:void(0)">
                      <!--<div v-html="'<img class=\'avatar round\' src=\'file:\///C:\/qq.png\'>'" ></div>-->
                      <!--<img  class='avatar round'   src='item.imageUrl' >-->
                      <!--<a href="C:/package2.Test2.A.diagram.svg"></a>-->
                      <!--<img class="avatar round" src="../../assets/test1.png">-->
                      <!--//TODO:用户名-->
                      {{item.type}}
                  </a>
                  <div class="right-buttons">
                  </div>
              </div>
          </div>

        </waterfall-slot>
      </waterfall>
    </section>

</template>

<script>
  import { getReposList} from '../../api/api'
  //waterfall瀑布流组件
  import Waterfall from 'vue-waterfall/lib/waterfall'
  import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'
  import kzTree from  './directory.vue'
  import ModelContent from "./index";
  import { mapState,mapGetters} from 'vuex'

  export default {
      name : 'model-list',
    data(){
      return {
        filters:{
            name : ""
        },
          loading: false,
        align:'left',
        repositories:[],
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
              if(_this.amsg != null && _this.amsg != ""){
                  var url = '/api/model/list?parent_id='+ _this.amsg
              }else{
                  _this.amsg = null;
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
    methods:{
        //获取用户列表
        getModel: function () {
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
//            let searchModel = this.repositories.filter(model => {
//                if (name && user.name.indexOf(name) == -1) return false;
//                return true;
//            });
//            this.repositories = searchModel.searchModel;
        },
//      getRepos : function(arg){
////        let para ={name: this.filters.name};
////        getReposList(para).then((res)=>{
////          this.repositories = res.data.repositories;
////          //window.alert(this.repositories[0].id+this.repositories[0].name);
////        });
//          var _this = this;
////          var param = _this.$attrs.data.parent_id;
////          if(param != "" || param != null){
////              var url = '/api/model/list?parent_id='+param
////          }else{
////              var url = '/api/model/list'
////          }
//          var param = arg;
//          _this.$http.post('/api/model/list')
//              .then(function (response) {
//                  _this.repositories = response.data.repositories;
//          })
//          .catch(function (error) {
//              console.log(error);
//          });
//      },
      reflowed: function () {
        this.isBusy = false
      },
        modelVar : function (modelId) {
            this.$store.dispatch('sendModelId',modelId);
            this.$store.dispatch('sendTreeModelId',modelId);
            this.$router.push({path: '/model/packageDiagram'});
        },
//        Model : function (modelId) {
//            this.$store.dispatch('sendModelId',modelId);
//            this.$store.dispatch('sendTreeModelId',modelId);
//            this.$router.push({path: '/model/packageDiagramModel'});
//        },
//        Variable : function (modelId) {
//            this.$store.dispatch('sendModelId',modelId);
//            this.$router.push({path: '/model/packageDiagramVariable'});
//        }
    },


//    mounted(){
//      this.getRepos();
//    },
    components:{
        ModelContent,
        Waterfall,
      WaterfallSlot,
        kzTree,
    },
}

</script>

<style scoped>
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
</style>

