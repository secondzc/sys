<template>
    <el-container>
        <el-header style="height: 50px;background-color: rgb(238, 241, 246);border: 1px solid #B0C4DE">
            <span style="font-size: 20px;position: relative;top: 20px;">模型：{{form.name}}</span>
        </el-header>
        <el-container>
        <el-aside width="300px" style="background-color: rgb(238, 241, 246);border: 1px solid #B0C4DE;min-height: -webkit-fill-available">
            <modeltree ></modeltree>
        </el-aside>
        <el-main style="background-color: rgb(238, 241, 246);border: 1px solid #B0C4DE;margin-left: 20px">
            <template>
                <section>
                    <div id="tabs" class="tabs-bottom">
                        {{getModelVar}}{{treeModelVar}}
                        <ul>
                            <li><a href="#tabs-3">文本</a></li>
                            <li><a href="#tabs-2">图标</a></li>
                            <li><a href="#tabs-1">组件</a></li>
                            <li><a href="#tabs-4">说明</a></li>
                            <li><a href="#tabs-5">变量</a></li>
                        </ul>
                        <div class="tabs-spacer"></div>
                        <div id="tabs-1">
                            <div style="min-height: 452px;">
                                <modelComponent></modelComponent>
                            </div>
                        </div>
                        <div id="tabs-2">
                            <div style="min-height: 452px;">
                                <img :src=svgUrl class="image"/>
                            </div>
                        </div>
                        <div id="tabs-3">
                            <div style="min-height: 452px;">
                                <img :src=iconUrl class="image"/>
                            </div>
                        </div>
                        <div id="tabs-4">
                            <div style="min-height: 452px;">
                                <iframe :src=infoUrl  style="width: 100%;height: 464px;overflow: auto;" ></iframe>
                            </div>
                        </div>
                        <div id="tabs-5">
                            <div style="min-height: 452px;">
                                <package-diagram-model-child ></package-diagram-model-child>
                            </div>
                        </div>
                    </div>
                </section>
            </template>
        </el-main>
        </el-container>
    </el-container>
</template>

<script>
import {mapActions} from 'vuex'
import modeltree from './modelTree.vue'
import component from './Component.vue'
import modelComponent from './ModelComponent.vue'
import variabletree from './Variabletree.vue'
import PackageDiagramModelChild from "./packageDiagramModelChild.vue";
import { mapState,mapGetters} from 'vuex'

import 'jquery-ui/external/jquery-1.9.1/jquery'
import 'jquery-ui/ui/widgets/tabs'
import 'jquery-ui/themes/base/all.css'
import 'jquery-ui/external/jquery/jquery'
import ElContainer from "../../../node_modules/element-ui/packages/container/src/main";

export default {
  name: 'packageDiagram',
  components: {
      ElContainer,
      modeltree,
      component,
      modelComponent,
      PackageDiagramModelChild,
//      variabletree
  },
  data () {
    return {
        form:[],
      data :{
          svgUrl:'',
          iconUrl:'',
          infoUrl:'',
      },
    }
  },
    computed: {
        ...mapState({
            modelId: state => state.modelId,
            a:state =>state.a
        }),
        ...mapGetters(['modelId','amsg','treeModelId']),
        getModelVar(){
            var _this = this;
            if(_this.modelId == null || _this.modelId == ''){
                this.$router.push({path: '/model/index'});
            }else {
                var url = '/api/model/modelVariable?modelId=' + _this.modelId;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.form = response.data.form;
                        if(response.data.form.diagramSvgPath == null){
                            _this.svgUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                        }else{
                            _this.svgUrl = response.data.form.diagramSvgPath;
                        }
                        if(response.data.form.iconSvgPath == null){
                            _this.iconUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                        }else{
                            _this.iconUrl = response.data.form.iconSvgPath;
                        }
                        if(response.data.form.infoTextPath == null){
                            _this.infoUrl = ""
                        }else{
                            _this.infoUrl = response.data.form.infoTextPath;
                        }

                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            }
        },treeModelVar(){
            var _this = this;
            if(_this.modelId == null || _this.modelId == ''){
                this.$router.push({path: '/model/index'});
            }else {
                var url = '/api/model/modelVariable?modelId=' + _this.treeModelId;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.form = response.data.form;
                        if(response.data.form.diagramSvgPath == null){
                            _this.svgUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                        }else{
                            _this.svgUrl = response.data.form.diagramSvgPath;
                        }
                        if(response.data.form.iconSvgPath == null){
                            _this.iconUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                        }else{
                            _this.iconUrl = response.data.form.iconSvgPath;
                        }
                        if(response.data.form.infoTextPath == null){
                            _this.infoUrl = ""
                        }else{
                            _this.infoUrl = response.data.form.infoTextPath;
                        }

                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            }
        }

    },
  methods: {
      Tabs(){
          $( "#tabs" ).tabs();
          // 修改 class
          $( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
              .removeClass( "ui-corner-all ui-corner-top" )
              .addClass( "ui-corner-bottom" );

          // 移动导航到底部
          $( ".tabs-bottom .ui-tabs-nav" ).appendTo( ".tabs-bottom" );
      }
  },
    mounted(){
        this.Tabs();
    }
}
</script>
<style scoped>

.left {
  max-width: 320px;
  float: left;
}
.right{
    min-width: 300px;
    float: right;
}


</style>