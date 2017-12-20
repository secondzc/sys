<template>
    <!--<div class="center-aligned" style="margin-bottom: 170px">-->
        <!--<img src="http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/package2.Test2.A.diagram.svg" class="image">-->
    <!--</div>-->
    <section>
        <el-form :inline="true" ref="form" :model="form" label-width="80px" @submit.prevent="onSubmit" style="margin:20px;width:100%;min-height:400px;">
            <!--<div-->
            <!--&gt;{{modelId}}</div>-->
            {{getModelVar}}
            <el-form-item  style="min-height: 440px">
                <!--<el-input  disabled="disabled" v-model="form.name"></el-input>-->
                <div class="center-aligned" >
                <img :src=svgUrl style="width: 100%;height: 400px;">
                    <router-link :to="{path:'/model/packageTransfModel',query: {modelId: form.index}}" class="username" target="_blank" title="模型详细信息" >详细信息</router-link>
                <!--<a href="javascript:void(0)" @click="modelVar" title="模型详细信息">详细信息</a>-->
                <!--<a href="javascript:void(0)" @click="Model" title="模型组件树">组件树</a>-->
                </div>
            </el-form-item>

        </el-form>
    </section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex'
    export default {
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
                currentRow: null,
            }
        },
        computed: {
            ...mapState({
                modelId: state => state.modelId,
                a:state =>state.a
            }),
            ...mapGetters(['treeModelId','amsg']),
            getModelVar(){
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
            modelVar : function (modelId) {
                this.$store.dispatch('sendModelId',this.form.index);
//               this.$router.push({path: '/model/packageDiagram'});
            },
//            Model : function (modelId) {
//                this.$store.dispatch('sendModelId',this.form.index);
//                this.$store.dispatch('sendTreeModelId',this.form.index);
//                this.$router.push({path: '/model/ModelComponent'});
//            },
//            Variable : function (modelId) {
//                this.$store.dispatch('sendTreeModelId',this.form.index);
//                this.$store.dispatch('sendModelId',this.form.index);
//                this.$router.push({path: '/model/packageDiagramVariable'});
//            }
        }
    }

</script>



<style scoped>
    .image{
        display: block;
        width: 100%;
        /*需要对高度做限制*/
        /*max-height: 160px;*/
        height: 100%;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        transition: all 1s ease 0s;
    }

    .username {
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
</style>