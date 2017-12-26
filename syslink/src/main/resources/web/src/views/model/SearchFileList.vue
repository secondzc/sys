<template>
    <section>
        <el-container  class="main-container" style="min-height: 400px">

            <!--<el-aside class="left-aside" style="overflow: hidden">-->
                <!--<div style="height: 100%">-->
                    <!--<span>文件目录</span>-->
                    <!--{{getModelDet}}-->
                    <!--<modelCatalog @catalog_id="getDetailList"  ref="setTreeNode" style="height: 100%;overflow-y: auto"></modelCatalog>-->
                <!--</div>-->
            <!--</el-aside>-->
            <el-main style="height: inherit;overflow-y: hidden">
                <!--<el-container style="height: 100%;">-->
                    <!--<el-header style="height: 40px;">-->
                        <!--<div style="display: inline-block;">-->
                            <!--<el-button type="primary" plain   @click="upFloor">上一层</el-button>-->
                        <!--</div>-->
                        <!--<div style="display: inline-block;">-->
                            <!--&lt;!&ndash;工具条&ndash;&gt;-->
                            <!--&lt;!&ndash;<el-col :span="24" class="toolbar" >&ndash;&gt;-->
                            <!--<el-form :inline="true" :model="filters">-->

                                <!--<el-form-item style="margin-left: 10px;margin-top: -8px">-->

                                    <!--<el-input placeholder="文件名称" v-model="filters.name" class="input-with-select">-->

                                        <!--<el-button slot="append" icon="el-icon-search"  v-on:click="getFile"></el-button>-->
                                    <!--</el-input>-->
                                <!--</el-form-item>-->
                            <!--</el-form>-->
                            <!--&lt;!&ndash;</el-col>&ndash;&gt;-->
                        <!--</div>-->
                    <!--</el-header>-->
                    <!--<el-main style="height: 100%;">-->
                <div style="height: 100%; overflow-y: hidden;display: flex;flex-direction: column;flex-wrap: nowrap;">
                    <el-table
                            ref="singleTable"
                            :data="repositories"
                            height="100%"

                            @current-change="handleCurrentChange"
                            :default-sort = "{prop: 'createTime',prop:'name', order: 'descending'}"
                            style="width: 100%">
                        <el-table-column
                                label=""
                                width="80" >
                            <template scope="scope">

                                <img v-bind:src="scope.row.imageUrl" style="width: 60px;height: 40px;"/>



                            </template>
                        </el-table-column>

                        <el-table-column  label="名称"
                                          prop="name"
                                          min-width= 100 sortable>

                        </el-table-column>
                        <el-table-column
                                label="创建日期"
                                prop="createTime"
                                min-width=100 sortable>


                        </el-table-column>


                        <el-table-column
                                label="类型"
                                prop="ext"
                                min-width=100>

                        </el-table-column>

                        <el-table-column
                                label="文件大小"
                                prop="size"
                                min-width=100>
                        </el-table-column>

                        <el-table-column min-width=150 label="操作">
                            <template scope="scope">

                                <el-button-group>
                                    <!--<el-tooltip class="item" effect="dark" content="查看" placement="top-start">-->
                                        <!--<el-button type="primary"-->
                                                   <!--size="small"-->
                                                   <!--icon="el-icon-search"   @click="handleEdit(scope.$index, scope.row)" ></el-button>-->
                                    <!--</el-tooltip>-->
                                    <el-tooltip class="item" effect="dark" content="下载" placement="top-start">
                                        <el-button type="info"
                                                   size="small"
                                                   icon="el-icon-download"   @click="handleDownload(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>
                                    <el-tooltip class="item" effect="dark" content="转至模型" placement="top-start">
                                        <el-button type="info"
                                                   size="small"
                                                   icon="el-icon-back"   @click="returnToModel(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>

                                    <!--<el-tooltip class="item" effect="dark" content="删除" placement="top-start">-->
                                        <!--<el-button   size="small" type="danger" icon="el-icon-delete"  @click="handleDeleted(scope.$index, scope.row)" v-if="func.authJudge('management_model_delete')"></el-button>-->
                                    <!--</el-tooltip>-->
                                </el-button-group>
                            </template>
                        </el-table-column>
                    </el-table>



                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrent"
                            :current-page="pager.pageIndex"
                            :page-sizes="[10,30,50,100]"
                            :page-size="pager.pageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="pager.total"  style="max-height: 40px;min-height: 30px;">
                    </el-pagination>
                </div>
                    </el-main>
                </el-container>
            <!--</el-main>-->
        <!--</el-container>-->
    </section>
</template>


<script >
    import errGif from '@/assets/401_images/401.gif'
    import global_ from '../global.vue'
    import { mapState,mapGetters} from 'vuex'
    import {mapActions} from 'vuex'
    export default {
        components: {
        },
        data() {
            return {
                repositories: [],
                parentAttach : '',
                catalog : 0,
                name : this.$store.state.userInfo.profile.name,
                filters: {
                    name: ""
                },
                pager: {
                    total: 0,
                    pageSize: 10,
                    pageIndex: 1,
                },
            };
        },
    computed: {
    ...mapState({
            modelId: state => state.modelId,
            a:state =>state.a
        }),
    ...mapGetters(['modelId','amsg','treeModelId']),
        },
        methods: {
            getFileList(data){
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getAllFiles';
                _this.$http.post(url)
                    .then(function (response) {
//                    _this.repositories = response.data.data;
                        _this.pager.total = response.data.data.length;
                        var searchFils = response.data.data.filter(model => {
                            if (data && model.name.indexOf(data) == -1) {
                                return false
                            }
                            else {
                                return true
                            }
                        })
                        var filterModel = searchFils.filter(
                            (u, index) => {
                                if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
                                    return true
                                }
                            }
                        )
                        _this.repositories = filterModel;
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            handleSizeChange(val){
                console.log(`每页 ${val} 条`);
                this.pager.pageSize = val;
                var filterModel = this.repositories.filter(
                    (u, index) => {
                        if (index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)) {
                            return true
                        }
                    }
                )
                this.repositories = filterModel;
            },
            handleCurrent(val) {
                console.log(`当前页: ${val}`);
                var filterModel = this.repositories.filter(
                    (u, index) => {
                        if (index < val * para.pageSize && index >= para.pageSize * (val - 1)) {
                            return true
                        }
                    }
                )
                this.repositories = filterModel;
            },
            handleCurrentChange(val) {
                if(val == null){
                    return
                }
                console.log(val);
            },
            handleDownload(index, row){
                console.log(index, row);
                var _this = this;
                var url = '/api/model/download?modelId=' + row.index;
                _this.$http.post(url)
                    .then(function (response) {
                        location.href = response.data.data;
                    }).catch(function (error) {
                    console.log(error);
                });
            },
            returnToModel(index, row){
                this.$emit("showModel",row)
                console.log(row);
            },

//            getDetailList(data){
//                var _this = this;
//                var url = '/api/model/getModelDetail?catalogId=' + data;
//                _this.catalog = data;
//                _this.$http.post(url)
//                    .then(function (response) {
//                        _this.repositories = response.data.data;
//                    })
//                    .catch(function (error) {
//                        console.log(error)
//                    })
//            },
//            getFile(){
//                let para = {
//                    name: this.filters.name,
//                    pageSize: this.pager.pageSize,
//                    pageIndex: this.pager.pageIndex
//                };
//                var _this = this;
//                var url = '/api/model/getModelDetail?modelId=' + _this.treeModelId;
//                _this.$http.post(url)
//                    .then(function (response) {
//                        var searchModel = response.data.data.filter(model => {
//                            if (para.name && model.name.indexOf(para.name) == -1) {
//                                return false
//                            }
//                            else {
//                                return true
//                            }
//                        })
//                        var filterModel = searchModel.filter(
//                            (u, index) => {
//                                if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
//                                    return true
//                                }
//                            }
//                        )
//                        _this.pager.total = searchModel.length;
//                        _this.repositories = filterModel;
//                    })
//                    .catch(function (error) {
//                        console.log(error);
//                    });
//
//            },
//            upFloor(){
//                if(this.catalog == 0){
//                    this.$message({
//                        message: '已经是顶层目录了！',
//                        type: 'warning',
//                        duration: 2000
//                    });
//                }else{
//                    let para = {
//                        pageSize: this.pager.pageSize,
//                        pageIndex: this.pager.pageIndex
//                    };
//                    var _this = this;
//                    var url = '/api/model/getParentFiles?catalogId=' + _this.catalog;
//                    _this.$http.post(url)
//                        .then(function (response) {
//                            _this.parentAttach = response.data.parentAttach;
//                            _this.pager.total = response.data.data.length;
//                            var filterModel = response.data.data.filter(
//                                (u, index) => {
//                                    if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
//                                        return true
//                                    }
//                                }
//                            )
//                            _this.repositories = filterModel;
//                            _this.$refs.setTreeNode.updateNode(_this.parentAttach);
//                        })
//                        .catch(function (error) {
//                            console.log(error)
//                        })
//                }
//            }
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