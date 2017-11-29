<template>

    <el-container   style="height: 100%;">
        <el-header class="top-header">
            <span style="font-size: 20px;position: relative;top: 8px;">模型：{{form.name}}</span>
            <div style="display: inline-block;margin-left: 95px">

                <a class="ui basic button" @click="addWatch(form)">
                    <span>关注</span>
                    <i v-if="form.alreadyWatch == true" class="iconfont icon-guanzhu"  ></i>
                    <i v-else="form.alreadyWatch == false" class="iconfont icon-quxiaoguanzhu01"  ></i>
                    {{form.numberWatch}}
                </a>
            </div>
            <div style="display:inline-block;margin-top: 18px">

                <a class="ui basic button" @click="addStar(form)">
                    <span>点赞</span>
                    <i v-if="form.alreadyStar == true" class="iconfont icon-guanzhu3" ></i>
                    <i v-else="form.alreadyStar == false" class="iconfont icon-guanzhu4"  ></i>
                    {{form.numberStar}}
                </a>
            </div>
            <div style="display:inline-block;margin-top: 8px;position: absolute;right: 60px;">
                <el-button type="warning" plain   @click="goReposity">转到仓库</el-button>
            </div>
        </el-header>
        <el-container  class="main-container" >

            <el-aside class="left-aside" style="overflow: hidden">
                <!--<el-tree-->
                        <!--:props="props"-->
                        <!--:load="loadNode"-->
                        <!--lazy-->
                        <!--show-checkbox-->
                        <!--@check-change="handleCheckChange"  style="margin-top: 20px;overflow: inherit;">-->
                <!--</el-tree>-->
                <div style="height: 50%">
                    <span>模型浏览器：</span>
                    <modeltree style="height: 100%;overflow-y: auto"></modeltree>
                </div>
                <div style="margin-top: 30px;height: 50%;overflow: auto">
                    <span>组件浏览器：</span>
                    <modeltreeContAndVar style="overflow-y: auto"> </modeltreeContAndVar>
                </div>
            </el-aside>
            <el-main style="height: inherit;overflow-y: hidden;">
                <el-tabs   tab-position="bottom" style="height: 100%">
                    {{getModelVar}}{{treeModelVar}}
                    <el-tab-pane class="el-tabs--bottom" style="height: 100%">
                        <span slot="label"><i class="el-icon-document"></i> 文本</span>
                        <!--11111111-->
                        <!--<img :src="errGif"  v-for="o in 10"  />-->
                        <div style="height: 100%;">
                            <div v-html="textInfo" class="font">
                                <div >
                                    {{this.textInfo}}
                                </div>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane style="height: 100%">
                        <span slot="label"><i class="el-icon-picture"></i> 图标</span>
                        <div style="height: 100%;text-align: center;">
                            <img :src=iconUrl class="images" style="height: 70%"/>
                        </div>
                    </el-tab-pane >
                    <el-tab-pane style="height: 100%">
                        <span slot="label"><i class="el-icon-picture-outline"></i> 组件</span>
                        <div style="height: 100%;text-align: center;">
                            <img :src=svgUrl class="images" style="height: 70%"/>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane style="height: 100%">
                        <span slot="label"><i class="el-icon-document"></i> 说明</span>
                        <div style="height: 100%;">
                            <iframe :src=infoUrl  style="width: 100%;height: 100%;overflow: auto;" ></iframe>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane style="height: 100%">
                        <span slot="label"><i class="el-icon-tickets"></i> 变量</span>
                        <div style="">
                            <package-diagram-model-child ></package-diagram-model-child>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </el-main>
        </el-container>


        <!--<el-footer class="main-footer" style="">-->
		<!--<span>-->
			<!--############################################-->
		<!--</span>-->

        <!--</el-footer>-->
    </el-container>



</template>


<script >
    import errGif from '@/assets/401_images/401.gif'
    import modeltree from './modelTree.vue'
    import modeltreeContAndVar from './modelTreeContAndVar.vue'
    import PackageDiagramModelChild from "./packageDiagramModelChild.vue";
    import { mapState,mapGetters} from 'vuex'
    import {mapActions} from 'vuex'
    import '../../assets/jqueryUI/tabs.css'
    export default {
        components: {
            modeltree,
            modeltreeContAndVar,
            PackageDiagramModelChild,
        },
        data() {
            return {
//                errGif: errGif + '?' + +new Date(),
//                props: {
//                    label: 'name',
//                    children: 'zones'
//                },
//                count: 1,
//                info:false,
//                list:true,
                form:[],
                textInfo : '',
                data :{
                    svgUrl:'',
                    iconUrl:'',
                    infoUrl:'',
                },
                name : this.$store.state.userInfo.profile.name,

            };
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
                        _this.textInfo= response.data.form.textInfo;
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
//                        if(response.data.form.textInfo != null){
//                            var htmlTextInfo = "";
//                            htmlTextInfo = response.data.form.textInfo
//                        }

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
                        _this.textInfo= response.data.form.textInfo
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
            toDetails()
            {
                this.$router.push('/NewDetails');
            },
            handleCheckChange(data, checked, indeterminate) {
                console.log(data, checked, indeterminate);
            },
            handleNodeClick(data) {
                console.log(data);
            },
            loadNode(node, resolve) {
                if (node.level === 0) {
                    return resolve([{ name: 'region1' }, { name: 'region2' }]);
                }
                if (node.level > 3) return resolve([]);

                var hasChild;
                if (node.data.name === 'region1') {
                    hasChild = true;
                } else if (node.data.name === 'region2') {
                    hasChild = false;
                } else {
                    hasChild = Math.random() > 0.5;
                }

                setTimeout(() => {
                    var data;
                    if (hasChild) {
                        data = [{
                            name: 'zone' + this.count++
                        }, {
                            name: 'zone' + this.count++
                        }];
                    } else {
                        data = [];
                    }

                    resolve(data);
                }, 500);
            },
            addStar(item){
                if(item.alreadyStar == false){
                    var _this = this;
                    var url = '/api/star/add?userId='+ item.userId +"&repoName=" +item.repositoryName
                    _this.$http.post(url)
                        .then(function (response) {
                            if(response.data.msg == "ok"){
                                _this.treeModelRefresh();
                            }
                        }).catch(function (error) {
                        console.log(error);
                    });
                }
                if(item.alreadyStar == true){
                    var _this = this;
                    var url = '/api/star/delete?userId='+ item.userId +"&repoName=" +item.repositoryName
                    _this.$http.post(url)
                        .then(function (response) {
                            if(response.data.msg == "ok"){
                                _this.treeModelRefresh();
                            }
                        }).catch(function (error) {
                        console.log(error);
                    });
                }

            },
            addWatch(item){
                if(item.alreadyWatch == false){
                    var _this = this;
                    var url = '/api/watch/add?userId='+ item.userId +"&repoName=" +item.repositoryName
                    _this.$http.post(url)
                        .then(function (response) {
                            if(response.data.msg == "ok"){
                                _this.treeModelRefresh();
                            }
                        }).catch(function (error) {
                        console.log(error);
                    });
                }
                if(item.alreadyWatch == true){
                    var _this = this;
                    var url = '/api/watch/delete?userId='+ item.userId +"&repoName=" +item.repositoryName
                    _this.$http.post(url)
                        .then(function (response) {
                            if(response.data.msg == "ok"){
                                _this.treeModelRefresh();
                            }
                        }).catch(function (error) {
                        console.log(error);
                    });
                }
            },
            treeModelRefresh(){
                var _this = this;
                if(_this.modelId == null || _this.modelId == ''){
                    this.$router.push({path: '/model/index'});
                }else {
                    var url = '/api/model/modelVariable?modelId=' + _this.treeModelId;
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.form = response.data.form;
                            _this.textInfo= response.data.form.textInfo
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
            },
            goReposity(){
                location.href = "http://gogs.modelica-china.com:3000" + "/"+ "admin" +"/"+ this.form.name.split(".",1)[0]
            }
        }
    };

</script>



<style scoped lang="scss">

    .main-container{
        height: inherit;;
        overflow-y:hidden;
    }
    .top-header{
        max-height: 53px;
        background-color: #f8f8f8;
    }
    .middle-header{
        max-height: 50px;
        border-top: solid 1px #e7e7e7;
    }
    .main-footer{
        max-height: 10px;
        text-align: center;
        border-top: solid 1px #e7e7e7;
    }

    .left-aside
    {
        border-right: solid 1px #e7e7e7;
    }
    /*.el-tabs.el-tabs--bottom{
        height: inherit;
    }
    .el-tab-pane
    {
        height: 100px;
        overflow-y: auto;
    }
    .el-tabs__content{
        overflow: hidden;
        position: relative;
        height: 200px;
    }
    .el-tabs.el-tabs--bottom .el-tabs__header
    {
        height: 400px;
    }
    */

    .el-tabs.el-tabs--bottom
    {
        height: inherit;
        overflow-y: hidden;

    }
    .el-tab-pane
    {
        overflow-y: auto;
    }
    @font-face {
        font-family: 'iconfont';  /* project id 445633 */
        src: url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.eot');
        src: url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.eot?#iefix') format('embedded-opentype'),
        url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.woff') format('woff'),
        url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.ttf') format('truetype'),
        url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.svg#iconfont') format('svg');
    }

</style>