<template>
    <div>
        {{getVarTree}}
        {{treeModelVar}}
        <zk-table
                ref="table"
                sum-text="sum"
                index-text="#"
                :data="data"
                :columns="columns"
                :stripe="props.stripe"
                :border="props.border"
                :show-header="props.showHeader"
                :show-summary="props.showSummary"
                :show-row-hover="props.showRowHover"
                :show-index="props.showIndex"
                :tree-type="props.treeType"
                :is-fold="props.isFold"
                :expand-type="props.expandType"
                :selection-type="props.selectionType">
            <template slot="$expand" scope="scope">
                {{ `My name is ${scope.row.name},
           this rowIndex is ${scope.rowIndex}.`
                }}
            </template>
            <template slot="likes" scope="scope">
                {{ scope.row.likes.join(',') }}
            </template>
        </zk-table>
    </div>
</template>

<script>
    import zkTable from 'vue-table-with-tree-grid'
    import { mapState,mapGetters} from 'vuex'
    export default {
        computed: {
            ...mapState({
                modelId: state => state.modelId,
            }),
            ...mapGetters(['modelId', 'treeModelId']),
            propList() {
                return Object.keys(this.props).map(item => ({
                    name: item,
                }));
            },
            getVarTree(){
                var _this = this;
                if (_this.modelId == null || _this.modelId == '') {
                    this.$router.push({path: '/model/index'});
                } else {
                    var url = '/api/variable/variableTree?modelId=' + _this.modelId;
//                    this.$store.dispatch('sendTreeModelId',_this.modelId);
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.data = response.data.rootData;
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                }
            }, treeModelVar(){
                var _this = this;
                if (_this.modelId == null || _this.modelId == '') {
                    this.$router.push({path: '/model/index'});
                } else {
                    var url = '/api/variable/variableTree?modelId=' + _this.treeModelId;
//                    this.$store.dispatch('sendTreeModelId',_this.modelId);
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.data = response.data.rootData;
                        })
                        .catch(function (error) {
                            console.log(error)
                        })
                }
            }
        },
        data () {
            return {
                props: {
                    stripe: true,
                    border: false,
                    showHeader: true,
                    showSummary: false,
                    showRowHover: true,
                    showIndex: false,
                    treeType: true,
                    isFold: true,
                    expandType: false,
                    selectionType: false,
                },
                columns: [{
                    minWidth: 200,
                    label: '名称',
                    prop: 'name'
                }, {
                    minWidth: 300,
                    label: '变量类型',
                    prop: 'type'
                }, {
                    minWidth:100,
                    label: '默认值',
                    prop: 'defaultValue'
                }, {
                    minWidth: 100,
                    label: '变量单位',
                    prop: 'units'
                }
                // , {
                //     minWidth: 100,
                //     label: '变量下界',
                //     prop: 'lowerBound'
                // },{
                //     minWidth: 100,
                //     label: '变量上界',
                //     prop: 'upperBound'
                // }
                ,{
                    minWidth: 100,
                    label: '是否参数变量',
                    prop: 'isParam'
                },{
                    minWidth: 100,
                    label: '是否输入变量',
                    prop: 'isInput'
                }
                ],
                data: [
                ],
            }
        },
        components: {
            zkTable,
        }
    }
</script>

<style>

</style>