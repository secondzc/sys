<template>
    <div class="modelContent">
        <div style="border: 2px solid #9E9E9E;height: 70px;width: 100%;">
            <span style="font-size: 20px;position: relative;top: 20px;">模型：{{form.name}}</span>
        </div>
        <!--<div class="left">-->
            <!--<div style="border: 2px solid #9E9E9E;margin-top: 20px;padding: 10px">-->
                <!--<package-diagram-model-child style="height: 544px;" ></package-diagram-model-child>-->
            <!--</div>-->
        <!--</div>-->
        <!--<div class="right"  style="margin-left: 10px;margin-top: 20px">-->
            <!--<div style="border: 2px solid #9E9E9E;min-height: 550px">-->
                <template>
                        <section>
                            <div id="tabs" class="tabs-bottom">
                                <ul>
                                    <li><a href="#tabs-1">基本信息</a></li>
                                    <li><a href="#tabs-2">视图</a></li>
                                    <li><a href="#tabs-3">图片</a></li>
                                    <li><a href="#tabs-4">说明</a></li>
                                    <li><a href="#tabs-5">组件</a></li>
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
            </div>
        <!--</div>-->
    <!--</div>-->
</template>
<script>
    import variabletree from './Variabletree.vue'
    import { mapState,mapGetters} from 'vuex'
    import PackageDiagramModelChild from "./packageDiagramModelChild.vue";
    export default {
        components: {
            PackageDiagramModelChild,
        },
        data() {
            return {
//                form: {
//                    name: '',
//                    region: '',
//                    date1: '',
//                    date2: '',
//                    delivery: false,
//                    type: [],
//                    resource: '',
//                    desc: ''
//                }
                form:[],
                tableData: [
                ],
                svgUrl:'',
                iconUrl:'',
                infoUrl:'',
                currentRow: null,
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
            setCurrent(row) {
                //   this.$refs.singleTable.setCurrentRow(row);
//                this.$store.dispatch('sendModelId',modelId.currentTarget.attributes[2].value);
                var _this=this;
                this.$store.dispatch('sendA',_this.form.directoryParentId);
                this.$router.push({path: '/model/index'});
            },
            handleCurrentChange(val) {
                this.currentRow = val;
            },
            Tabs(){
                $( "#tabs" ).tabs();
                // 修改 class
                $( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
                    .removeClass( "ui-corner-all ui-corner-top" )
                    .addClass( "ui-corner-bottom" );

                // 移动导航到底部
                $( ".tabs-bottom .ui-tabs-nav" ).appendTo( ".tabs-bottom" );
            }
//            svgUrl : function () {
//                var svg = this.$refs.form.model.diagramSvgPath;
//                window.open(svg,"_blank");
//            },
//            iconUrl : function () {
//                var svg = this.$refs.form.model.iconSvgPath;
//                window.open(svg,"_blank");
//            },
//            infoUrl : function () {
//                var svg = this.$refs.form.model.infoTextPath;
//                window.open(svg,"_blank");
//            }
        },mounted(){
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

    .image{
        display: block;
        width: 100%;
        /*需要对高度做限制*/
        max-height: 300px;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        transition: all 1s ease 0s;
    }
</style>
