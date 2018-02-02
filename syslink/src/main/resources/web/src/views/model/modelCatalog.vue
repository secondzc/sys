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
            refreshModelCatalog(){
                var _this = this;
                if(_this.modelId == null || _this.modelId == ''){
                    return;
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
            },
            filterNode(value, data) {
//                if (!value) return true;
//                return data.name.indexOf(value) !== -1;
                var re =new RegExp("^.*"+ value +".*$", 'i');   //i表示不区分大小写
                return re.test(data.name);
            },
            treeNodeClick(arg,node,tree){
                if(arg.parentId != -1){
                    this.$emit('catalog_id', arg.id,node.parent.data);
                }
                if(arg.parentId == -1){
                    this.$emit('getModelDet',arg.id)
                }
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
            	
            	let level ;
            	if(node.level>10)
            	{
            		level=10;
            	}
            	else
            	{
            		level = node.level;
            	}

                if(node.childNodes.length > 0 || data.floder ){
                    return (
                        
                        <span style={{width:298-18*level+'px'}} title={node.label} class="spanEllipsis">
                        <svg class="icon " aria-hidden="true">
                        <use xlinkHref="#icon-file-b-"></use>
                        `</svg>

                {node.label}
                </span>
              
                );
                }else{
                    return (
                        <span title={node.label}>
                      
                       {node.label}
                    
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
<style scoped>
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