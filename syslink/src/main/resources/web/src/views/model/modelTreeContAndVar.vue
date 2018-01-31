<template>
    <section>
        {{getModelVar}}
        <el-tree
                class="filter-tree"
                :data="data2"
                :props="defaultProps"
                highlight-current
                accordion
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
                   var url = '/api/variable/contAndVariableTree?modelId=' + _this.treeModelId;
                    _this.$http.post(url)
                        .then(function (response) {
                           _this.data2 = response.data.rootData;
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
                return data.label.indexOf(value) !== -1;
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
</style>