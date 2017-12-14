<template>
    <div class="modelContent">
        <div style="border: 2px solid #9E9E9E;height: 70px;width: 100%;">
         <span style="font-size: 20px;position: relative;top: 20px;">模型库：{{form.name}}</span>
        </div>

        <div class="left">
            <div style="border: 2px solid #9E9E9E;margin-top: 20px;padding: 10px">
            <!--<modelbasecont></modelbasecont>-->
            <!--模型基本信息-->
            <!--<div><p>模型基本信息：</p></div>-->
            <!--<modelbasecont></modelbasecont>-->
            <!--模型树-->
            <!--<div><p>模型树：</p></div>-->
            <modeltree style="height: 544px;"></modeltree>
            <!--变量树-->
            <!--<div style="margin-top: 80px">-->
            <!--<p>变量树：</p>-->
            <!--<variabletree  ></variabletree>-->
            </div>
        </div>




      <div class="right"  style="margin-left: 10px;margin-top: 20px">
          <div style="border: 2px solid #9E9E9E;min-height: 550px">
          <template>
              <section>
                  <div id="tabs" class="tabs-bottom">
                      <ul>
                          <li><a href="#tabs-1">基本信息</a></li>
                          <li><a href="#tabs-2">视图</a></li>
                          <!--<li><a href="#tabs-3">Aenean lacinia</a></li>-->
                      </ul>
                      <div class="tabs-spacer"></div>
                      <div id="tabs-1">
                          <div style="min-height: 452px;">
                              <el-form :inline="true" ref="form" :model="form" label-width="80px" @submit.prevent="onSubmit" style="margin:20px;width:60%;min-width:600px;">
                                  <!--<div-->
                                  <!--&gt;{{modelId}}</div>-->
                                  {{getModelVar}}
                                  <el-form-item label="模型信息:" >
                                      <el-input  disabled="disabled" v-model="form.name"></el-input>
                                  </el-form-item>
                                  <el-form-item label="模型受限类型:" label-width="100px">
                                      <el-input disabled="disabled" v-model="form.classes"></el-input>
                                  </el-form-item>
                                  <el-form-item label="模型类型:" >
                                      <el-input  disabled="disabled" v-model="form.type"></el-input>
                                  </el-form-item>

                                  <el-form-item  label-width="100px" label="导入包名:" >
                                      <el-input  disabled="disabled" v-model="form.import"></el-input>
                                  </el-form-item>
                                  <el-form-item label="基类名:" >
                                      <el-input disabled="disabled" v-model="form.extends"></el-input>
                                  </el-form-item>
                                  <el-form-item label="上传者:"  label-width="100px">
                                      <el-input  disabled="disabled" v-model="form.userName"></el-input>
                                  </el-form-item>
                                  <el-form-item label="模型描述:" >
                                      <el-input disabled="disabled" v-model="form.discription"></el-input>
                                  </el-form-item>
                              </el-form>
                          </div>
                      </div>
                      <div id="tabs-2">
                          <div style="min-height: 452px;">
                              <component></component>
                          </div>
                      </div>
                      <!--<div id="tabs-3">-->
                          <!--<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>-->
                          <!--<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>-->
                      <!--</div>-->
                  </div>
              </section>
          </template>
          </div>
          <!--组件图-->
          <!--<div><p>组件图：</p></div>-->
          <!--<component></component>-->


      </div>
    </div>
</template>

<script>
import {mapActions} from 'vuex'
import modelbasecont from './modelBaseContent.vue'
import modeltree from './modelTree.vue'
import component from './Component.vue'
import variabletree from './Variabletree.vue'
import { mapState,mapGetters} from 'vuex'
import global_ from '../global.vue'

import 'jquery-ui/external/jquery-1.9.1/jquery'
import 'jquery-ui/ui/widgets/tabs'
import 'jquery-ui/themes/base/all.css'
import 'jquery-ui/external/jquery/jquery'

export default {
  name: 'packageDiagram',
  components: {
//      modelbasecont,
      modeltree,
      component,
//      variabletree
  },
  data () {
    return {
        form:[],
      data :{
      },
    }
  },
    computed: {
        ...mapState({
            modelId: state => state.modelId,
            a:state =>state.a
        }),
        ...mapGetters(['modelId','amsg']),
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
                            _this.svgUrl = "http://"+global_.HostPath+"/FileLibrarys/FileLibrary/zanwu.jpg"
                        }else{
                            _this.svgUrl = response.data.form.diagramSvgPath;
                        }
                        if(response.data.form.iconSvgPath == null){
                            _this.iconUrl = "http://"+global_.HostPath+"/FileLibrarys/FileLibrary/zanwu.jpg"
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
//    hanldeNodeClick (data) {
//      //该目录下的模型列表
//        this.treeItem = data;
////        this.$emit("node-click",data)
//        this.$store.dispatch('sendA',data.parent_id);
//        if(data.parent_id <= 0){
//            this.$store.dispatch('sendB',-1);
//        }
//        else{
//            this.$store.dispatch('sendB',data.parent_id);
//        }
//    }
//      getRepos (data) {
//          //该目录下的模型列表
//          this.treeItem = data;
//      }
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