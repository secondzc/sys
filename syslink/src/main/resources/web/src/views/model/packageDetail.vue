<template>
        <el-container  class="main-container" style="min-height: 500px">

            <el-aside class="left-aside" style="overflow: hidden">
                <div style="height: 100%">
                    <span>文件目录</span>
                    <!--{{getModelDet}}-->
                    <modelCatalog @catalog_id="getDetailList" @getModelDet="getModelDet"  ref="setTreeNode" style="height: 100%;overflow-y: auto"></modelCatalog>
                </div>
            </el-aside>
            <el-main style="overflow-y: hidden">
                <el-container style="height: 100%;">
                    <el-header style="height: 40px;">
                        <div style="display: inline-block;">
                            <el-button type="primary" plain   @click="upFloor">上一层</el-button>
                            <a id="download" style="display: none;" :href = fileUrl download="file" >下载</a>
                        </div>
                        <div style="display: inline-block;">
                            <!--工具条-->
                            <!--<el-col :span="24" class="toolbar" >-->
                            <el-form :inline="true" :model="filters">

                                <el-form-item style="margin-left: 10px;margin-top: -8px">

                                    <el-input placeholder="文件名称" v-model="filters.name" class="input-with-select" @keydown.enter.native="getFile">

                                        <el-button slot="append" icon="el-icon-search"  v-on:click="getFile"></el-button>
                                    </el-input>
                                </el-form-item>
                            </el-form>
                            <!--</el-col>-->
                        </div>
                    </el-header>
                    <el-main style="height: 100%;">
                <div style="height: 100%; overflow-y: hidden;display: flex;flex-direction: column;flex-wrap: nowrap;">
                    <el-table
                            ref="singleTable"
                            :data="repositories"
                            height="100%"
                            @current-change="handleCurrentChange"
                            highlight-current-row
                            :row-class-name="tableRowClassName"
                            :default-sort = "{prop: 'createTime',prop:'name', order: 'descending'}"
                            style="width: 100%"
                            @row-dblclick="checkFloder"
                    >
                        <el-table-column
                                label=""
                                width="42" >
                            <template scope="scope">
                                <div v-if="scope.row.floder == false">
                                    <img  v-bind:src="scope.row.fileIconUrl" style="width: 40px;height: 25px;"/>
                                </div >
                                <div v-else="scope.row.floder == true">
                                <img  src="../../../src/assets/file.jpg" style="width: 40px;height: 25px;"/>
                                </div>

                            </template>
                        </el-table-column>

                        <el-table-column  label="名称"
                                          prop="name"
                                          min-width=100 sortable>
                                          
                               <template slot-scope="scope">
                                <span style="min-width:100px" v-bind:title="scope.row.name"  class="spanEllipsis">{{ scope.row.name }}</span>
                               </template>

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
                                prop="fileSize"
                                min-width=100>
                        </el-table-column>


                        <el-table-column min-width=150 label="操作">
                            <template scope="scope">
                                <el-tooltip class="item" effect="dark" content="查看" placement="top-start" >
                                    <el-button type="primary"
                                               size="small"
                                               @click="handleWatch(scope.$index, scope.row)"
                                               :disabled="scope.row.floder == false"
                                    >
                                        <i class="iconfont icon-chakan" style="font-size: 12px;"></i>
                                    </el-button>
                                </el-tooltip>
                                <el-button-group>
                                    <el-tooltip class="item" effect="dark" content="下载" placement="top-start">
                                        <el-button type="primary"
                                                   size="small"
                                                   icon="el-icon-download"   @click="handleDownload(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>
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
            </el-main>
        </el-container>

</template>


<script >
    import errGif from '@/assets/401_images/401.gif'
    import global_ from '../global.vue'
    import modelCatalog from './modelCatalog.vue'
    import { mapState,mapGetters} from 'vuex'
    import {mapActions} from 'vuex'
    export default {
        components: {
            modelCatalog
        },
        data() {
            return {
                repositories: [],
                details : [],
                parentAttach : '',
                catalog : 0,
                treeParentNode : '',
                name : this.$store.state.userInfo.profile.name,
                filters: {
                    name: ""
                },
                pager: {
                    total: 0,
                    pageSize: 10,
                    pageIndex: 1,
                },
                fileUrl : '',
            };
        },
    computed: {
    ...mapState({
        }),
    ...mapGetters(['treeModelId']),
//        getModelDet(){
//            let para = {
//                pageSize: this.pager.pageSize,
//                pageIndex: this.pager.pageIndex
//            };
//            var _this = this;
//            var url = '/api/model/getModelDetail?modelId=' + _this.treeModelId;
//            _this.$http.post(url)
//                .then(function (response) {
////                    _this.repositories = response.data.data;
//                    _this.details = response.data.data;
//                    _this.pager.total = response.data.data.length;
//                    var filterModel = response.data.data.filter(
//                        (u, index) => {
//                            if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
//                                return true
//                            }
//                        }
//                    )
//                    _this.repositories = filterModel;
//                })
//                .catch(function (error) {
//                    console.log(error)
//                })
//        }

        },
        methods: {
            checkFloder(row, event, column){
                if(row.floder){
                    this.handleWatch(column,row);
                }
            },
            handleSizeChange(val){
                console.log(`每页 ${val} 条`);
                this.pager.pageSize = val;
                var filterModel = this.details.filter(
                    (u, index) => {
                        if (index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)) {
                            return true
                        }
                    }
                )
                this.repositories = filterModel;
            },
            handleCurrent(val) {
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                this.pager.pageIndex =val;
                console.log(`当前页: ${val}`);
                var filterModel = this.details.filter(
                    (u, index) => {
                        if (index < val * para.pageSize && index >= para.pageSize * (val - 1)) {
                            return true
                        }
                    }
                )
                console.log("333333")
                this.repositories = filterModel;
            },
            handleCurrentChange(val) {
                if(val == null){
                    return
                }
                this.$refs.singleTable.setCurrentRow(val);
            },
            handleDownload(index, row){
                console.log(index, row);
                var _this = this;
                if(row.floder){
                    var url = '/api/model/downloadAttachFloder?attachmentId=' + row.id;
                }else{
                    var url = '/api/model/downloadAttach?attachmentId=' + row.id;
                }
                _this.$http.post(url)
                    .then(function (response) {
//                        location.href = response.data.data;
                        _this.fileUrl = response.data.data
                        document.getElementById('download').setAttribute('href',_this.fileUrl);
                        document.getElementById('download').click(function(){
                        })
                    }).catch(function (error) {
                    console.log(error);
                });
            },
            getDetailList(data,parentNode){
                this.treeParentNode = parentNode;
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getModelDetail?catalogId=' + data;
                _this.catalog = data;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.details = response.data.data;
                        _this.pager.total = response.data.data.length;
                        para.pageIndex = 1;
                        var filterModel = response.data.data.filter(
                            (u, index) => {
                                if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
                                    return true
                                }
                            }
                        )
                        console.log(filterModel);
                        _this.repositories = filterModel;
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            getFile(){
                let para = {
                    name: this.filters.name,
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getModelFiles?modelId=' + _this.treeModelId;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.details = response.data.data;
                        var searchModel = response.data.data.filter(model => {
//                            if (para.name && model.name.indexOf(para.name) == -1) {
//                                return false
//                            }
//                            else {
//                                return true
//                            }
                            var re =new RegExp("^.*"+ para.name +".*$", 'i');   //i表示不区分大小写
                            return re.test(model.name);
                        })
                        var filterModel = searchModel.filter(
                            (u, index) => {
                                if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
                                    return true
                                }
                            }
                        )
                        _this.pager.total = searchModel.length;
                        _this.repositories = filterModel;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

            },
            upFloor(){
                if(this.catalog == 0){
                    this.$message({
                        message: '已经是顶层目录了！',
                        type: 'warning',
                        duration: 2000
                    });
                }else{
                    let para = {
                        pageSize: this.pager.pageSize,
                        pageIndex: this.pager.pageIndex
                    };
                    var _this = this;
                    var url = '/api/model/getParentFiles?catalogId=' + _this.catalog;
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.parentAttach = response.data.parentAttach;
                            if(_this.parentAttach == null){
//                               return ;
                                _this.refreshModel();
                                _this.$refs.setTreeNode.refreshModelCatalog();
                                _this.catalog = 0;
                            }

                            _this.details = response.data.data;
                            _this.pager.total = response.data.data.length;
                            var filterModel = response.data.data.filter(
                                (u, index) => {
                                    if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
                                        return true
                                    }
                                }
                            )
                            _this.repositories = filterModel;
                            _this.$refs.setTreeNode.getFloderContent(_this.treeParentNode);
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                }
            },
            showFile(data){
                console.log(data);
                this.$refs.setTreeNode.getCatalog(data.modelId);
                this.selectCurrentFile(data);
            },
            selectCurrentFile(data){
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var currentRow ;
                var _this = this;
                var url = '/api/model/getModelDetail?modelId=' + _this.treeModelId;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.details = response.data.data;
                        _this.repositories = response.data.data;
                        for(var i= 0;i < _this.repositories.length;i++){
                            if(data.filePath == _this.repositories[i].filePath){
                                _this.pager.pageIndex = parseInt((i+1) / _this.pager.pageSize)+1;
                                currentRow = _this.repositories[i];
                            }
                        }
                        _this.pager.total = response.data.data.length;
                        var filterModel = response.data.data.filter(
                            (u, index) => {
                                if (index < _this.pager.pageIndex * _this.pager.pageSize && index >= _this.pager.pageSize * (_this.pager.pageIndex - 1)) {
                                    return true
                                }
                            }
                        )
                        _this.repositories = filterModel;
                        _this.handleCurrentChange(currentRow);
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
            tableRowClassName({row, rowIndex}) {
                if (rowIndex === 0) {
                    return 'warning-row';
                }
                return '';
            },
            handleWatch(index, row){
                this.$refs.setTreeNode.getFloderContent(row);
            },
            getModelDet(data){
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getModelDetail?modelId=' + data;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.details = response.data.data;
                        _this.pager.total = response.data.data.length;
                        var filterModel = response.data.data.filter(
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
            refreshModel(){
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getModelDetail?modelId=' + _this.treeModelId;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.details = response.data.data;
                        _this.pager.total = response.data.data.length;
                        var filterModel = response.data.data.filter(
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
    .el-table.warning-row {
        background: #f0f9eb!important;
    }

    .el-table.success-row {
        background: #f0f9eb;
    }

</style>