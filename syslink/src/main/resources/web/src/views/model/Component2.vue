<template>
    <section>
        <el-form :inline="true" ref="form" :model="form" label-width="80px" @submit.prevent="onSubmit" style="margin:20px;width:60%;min-width:600px;">
            <!--<div-->
            <!--&gt;{{modelId}}</div>-->
            {{getModelVar}}
            <el-form-item >
                <!--<el-input  disabled="disabled" v-model="form.name"></el-input>-->
                <div class="center-aligned" style="margin-bottom: 170px">
                <img :src=svgUrl class="image">
                </div>
            </el-form-item>

        </el-form>
    </section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex'
    import global_ from '../global.vue'
    export default {
        data() {
            return {
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
                                _this.svgUrl = "http://"+global_.HostPath+"/FileLibrarys/FileLibrary/zanwu.jpg"
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
                var _this=this;
                this.$store.dispatch('sendA',_this.form.directoryParentId);
                this.$router.push({path: '/model/index'});
            },
            handleCurrentChange(val) {
                this.currentRow = val;
            }
        }
    }

</script>



<style scoped>
    .image{
        display: block;
        width: 70%;
        /*需要对高度做限制*/
        max-height: 160px;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        transition: all 1s ease 0s;
    }
</style>