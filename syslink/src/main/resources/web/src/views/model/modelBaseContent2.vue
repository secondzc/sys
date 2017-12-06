<template>
    <section>
        <el-tabs type="border-card" style="overflow: auto;width: 1000px">
        <el-tab-pane label="包基本信息">
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
            <!--<el-form-item label="视图:" label-width="100px">-->
                <!--&lt;!&ndash;<a href="http://syslink.com:8080/FileLibrary/xieyx/2017-09-07_09-09-722/syslink/src/test/ModelDomLibrary/package2.Test2.A.diagram.svg" target="_blank">svg</a>&ndash;&gt;-->
                <!--<a  href="javascript:void(0)"   @click="svgUrl" >视图</a>-->
                <!--&lt;!&ndash;form.diagramSvgPath&ndash;&gt;-->
            <!--</el-form-item>-->
            <!--<el-form-item label="图片:" >-->
                <!--&lt;!&ndash;<a href="http://syslink.com:8080/FileLibrary/xieyx/2017-09-07_09-09-722/syslink/src/test/ModelDomLibrary/package2.Test2.A.diagram.svg" target="_blank">svg</a>&ndash;&gt;-->
                <!--<a  href="javascript:void(0)"   @click="iconUrl" >图片</a>-->
                <!--&lt;!&ndash;form.diagramSvgPath&ndash;&gt;-->
            <!--</el-form-item>-->
            <!--<el-form-item label="Info:" >-->
                <!--&lt;!&ndash;<a href="http://syslink.com:8080/FileLibrary/xieyx/2017-09-07_09-09-722/syslink/src/test/ModelDomLibrary/package2.Test2.A.diagram.svg" target="_blank">svg</a>&ndash;&gt;-->
                <!--<a  href="javascript:void(0)"   @click="infoUrl" >info信息</a>-->
                <!--&lt;!&ndash;form.diagramSvgPath&ndash;&gt;-->
            <!--</el-form-item>-->
        </el-form>
        </el-tab-pane>
        <el-tab-pane label="包视图">
            <div >
                <img :src=svgUrl class="image"/>
            </div>
        </el-tab-pane>
        <el-tab-pane label="包图片">
            <div>
                <img :src=iconUrl class="image"/>
            </div>
        </el-tab-pane>
        <el-tab-pane label="包信息">
            <iframe :src=infoUrl  style="width: 100%;height: 464px;overflow: auto;" ></iframe>
            <!--<img :src=infoUrl class="image"/>-->
        </el-tab-pane>
            <el-tab-pane label="组件信息">
                <div style="overflow: auto">
                <package-diagram-model  ></package-diagram-model>
                </div>
            </el-tab-pane>
        </el-tabs>
    </section>
</template>
<script>
    import variabletree from './Variabletree.vue'
    import { mapState,mapGetters} from 'vuex'
    import PackageDiagramModel from "./packageDiagramModel";
    export default {
        components: {
            PackageDiagramModel,
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
                                _this.svgUrl = "http://syslink.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                            }else{
                                _this.svgUrl = response.data.form.diagramSvgPath;
                            }
                            if(response.data.form.iconSvgPath == null){
                                _this.iconUrl = "http://syslink.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
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
        }
    }

</script>
<style scoped>
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
