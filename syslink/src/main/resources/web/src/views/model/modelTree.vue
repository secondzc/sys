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
            }),
            ...mapGetters(['modelId']),
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
//                    {
//                    id: 1,
//                    label: '一级 1',
//                        iconClass:"iconClassRoot",
//                    children: [{
//                        id: 4,
//                        label: '二级 1-1',
//                        iconClass:"iconClassRoot",
//                        children: [{
//                            id: 9,
//                            label: '三级 1-1-1',
//                            iconClass:"iconClassRoot",
//                        }, {
//                            id: 10,
//                            label: '三级 1-1-2',
//                            iconClass:"iconClassRoot",
//                        }]
//                    }]
//                }, {
//                    id: 2,
//                    label: '一级 2',
//                        iconClass:"iconClassRoot",
//                    children: [{
//                        id: 5,
//                        label: '二级 2-1',
//                        iconClass:"iconClassRoot",
//                    }, {
//                        id: 6,
//                        label: '二级 2-2',
//                        iconClass:"iconClassRoot",
//                    }]
//                }, {
//                    id: 3,
//                    label: '一级 3',
//                        iconClass:"iconClassRoot",
//                    children: [{
//                        id: 7,
//                        label: '二级 3-1',
//                        iconClass:"iconClassRoot",
//                    }, {
//                        id: 8,
//                        label: '二级 3-2',
//                        iconClass:"iconClassRoot",
//                        children:[]
//                    }]
//                }
                ],
                defaultProps: {
                    children: 'children',
                    label: 'label',
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
</style>