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
                @current-change = "treeNodeChange"
                :filter-node-method="filterNode"
                :render-content="renderContent"
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
                    return;
//                    this.$router.push({path: '/model/index'});
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
            treeNodeClick(arg,node,tree){
                this.$emit('catalog_id', arg.id,node.parent.data);
            },
            updateNode(data){
                this.$refs.tree2.setCurrentKey(data.id);
                this.$refs.tree2.currentNode =this.$refs.tree2.getCurrentNode();
                console.log(this.$refs.tree2.getCurrentNode());
                this.$emit('catalog_id', data.id,this.$refs.tree2.$parent.$data.data2[0]);
            },
            getFloderContent(data){
                this.$refs.tree2.setCurrentKey(data.parentId);
                var parentNode = this.$refs.tree2.getCurrentNode();
                console.log(this.$refs.tree2.getCurrentNode());
                this.$refs.tree2.setCurrentKey(data.id);
                this.$refs.tree2.currentNode = this.$refs.tree2.getCurrentNode();
                this.$emit('catalog_id', data.id,parentNode);
            },

            treeNodeChange(arg1,arg2){
                console.log(arg1);
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
            renderContent(h, { node, data, store }) {

                if(node.childNodes.length == 0){
                    return (
                        <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
                        <span>
                        <span>{node.label}</span>
                        </span>
                    </span>
                );
                }else{
                    return (
                    <span style="flex: 1; display: flex; align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
                    <span>
                                 <svg class="icon " aria-hidden="true">
                                <use xlinkHref="#icon-file-b-"></use>
                                `</svg>

                    <span>{node.label}</span>
                </span>
                </span>
                );
                }

            }
        },
            data() {
                return {
                    filterText: '',
                    data2: [],
                    defaultProps: {
                        children: 'children',
                        label : 'parentId',
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
    .icon {
        width: 1em;
        height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }

    /*.el-tree-node>.el-tree-node__children {*/
        /*overflow-x: scroll;*/
    /*}*/
</style>