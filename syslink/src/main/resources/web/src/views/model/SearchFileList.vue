<template>
    <section>
        <el-container  class="main-container" style="min-height: 500px">
            <el-main style="height: inherit;overflow-y: hidden">
                <a id="download" style="display: none;" :href = fileUrl download="file" >下载</a>
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

                                <img v-bind:src="scope.row.fileIconUrl" style="width: 60px;height: 40px;"/>



                            </template>
                        </el-table-column>

                        <el-table-column  label="名称"
                                          prop="name"
                                          min-width= 100 sortable>

                        </el-table-column>
                        <el-table-column  label="模型"
                                          prop="modelName"
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
                                prop="fileSize"
                                min-width=100>
                        </el-table-column>

                        <el-table-column min-width=150 label="操作">
                            <template scope="scope">

                                <el-button-group>
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
                allrepository : [],
                searchrepository : [],
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
                fileUrl : '',
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
            getFileList(data,scope){
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var _this = this;
                var url = '/api/model/getAllFiles?scope=' +scope;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.allrepository = response.data.data;
//                        _this.pager.total = response.data.data.length;
                        var searchFils = response.data.data.filter(model => {
                            if (data && model.name.indexOf(data) == -1) {
                                return false
                            }
                            else {
                                return true
                            }
                        })
                        _this.searchrepository = searchFils;
                        _this.pager.total = searchFils.length;
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
                var filterModel = this.searchrepository.filter(
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
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                var filterModel = this.searchrepository.filter(
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
                var url = '/api/model/downloadAttach?attachmentId=' + row.id;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.fileUrl = response.data.data
                        document.getElementById('download').setAttribute('href',_this.fileUrl);
                        document.getElementById('download').click(function(){
                        })
                    }).catch(function (error) {
                    console.log(error);
                });
            },
            returnToModel(index, row){
                this.$emit("showModel",row)
                console.log(row);
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

</style>