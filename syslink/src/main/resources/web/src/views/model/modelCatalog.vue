<template>
    <section>
        {{getModelVar}}
        <el-input style="width:300px;"
                placeholder="输入关键字进行过滤"
                v-model="filterText">
        </el-input>

        <el-tree
                class="filter-tree"
                :data="data2"
                :props="defaultProps"
                node-key="id"
                highlight-current
                accordion
                @node-click="treeNodeClick"
                :filter-node-method="filterNode"
                ref="tree2">
        </el-tree>
    </section>
</template>

<script>
    import { mapState,mapGetters} from 'vuex'
    export default {
        watch: {
            filterText(val) {
                this.$refs.tree2.filter(val);
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
                    var url = '/api/model/treeModelCatalog?modelId=' + _this.treeModelId;
                    _this.$http.post(url)
                        .then(function (response) {
                           _this.data2 = response.data.data;
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                }
            }
        },

        methods: {
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            treeNodeClick(arg){
                this.$emit('catalog_id', arg.id);
            },
            updateNode(data){
//                console.log(data);
                this.$refs.tree2.setCheckedKeys([]);
                this.$refs.tree2.setCheckedNodes([{
                    id: data.id,
                    label: data.name
                }]);
                var currNode;
                currNode = this.$refs.tree2.getCurrentNode();
//                this.$refs.tree2.setCurrentKey[7];
//                currNode = this.$refs.tree2.getCurrentNode();
                console.log(currNode);
            },
            getCatalog(data){
                var _this = this;
                var url = '/api/model/treeModelCatalog?modelId=' + data;
                _this.$http.post(url)
                    .then(function (response) {
                        _this.data2 = response.data.data;
                    })
                    .catch(function (error) {
                        console.log(error)
                    })
            },
        },
            data() {
                return {
                    filterText: '',
                    data2: [],
                    defaultProps: {
                        children: 'children',
                        label: 'name',
                    }
                };
            }

    };
</script>
<style>
    .iconClassRoot {
        width:15px;
        height:15px;
        display: inline-block;
        background: url("../../assets/root.png") no-repeat center/100% auto;
    }
    .el-tree-node__label{
        width: 298px;
    }

    /*.el-tree-node>.el-tree-node__children {*/
        /*overflow-x: scroll;*/
    /*}*/
</style>