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
                highlight-current = "true"
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
            ...mapGetters(['modelId','amsg']),
            getModelVar(){
                var _this = this;
                if(_this.modelId == null || _this.modelId == ''){
                    this.$router.push({path: '/model/index'});
                }else {
                    var url = '/api/model/treeModel?modelId=' + _this.modelId;
//                   this.$store.dispatch('sendTreeModelId',_this.modelId);
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
                this.$store.dispatch('sendTreeModelId',arg.id);
            }
        },

        data() {
            return {
                filterText: '',
                data2: [
                ],
                defaultProps: {
                    children: 'children',
                    label: 'name',
                    iconClass:'iconClass'
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