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
                @node-click="treeNodeClick"
                default-expand-all
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
//                    this.$store.dispatch('sendTreeModelId',_this.modelId);
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
//                    children: [{
//                        id: 4,
//                        label: '二级 1-1',
//                        children: [{
//                            id: 9,
//                            label: '三级 1-1-1'
//                        }, {
//                            id: 10,
//                            label: '三级 1-1-2'
//                        }]
//                    }]
//                }, {
//                    id: 2,
//                    label: '一级 2',
//                    children: [{
//                        id: 5,
//                        label: '二级 2-1'
//                    }, {
//                        id: 6,
//                        label: '二级 2-2'
//                    }]
//                }, {
//                    id: 3,
//                    label: '一级 3',
//                    children: [{
//                        id: 7,
//                        label: '二级 3-1'
//                    }, {
//                        id: 8,
//                        label: '二级 3-2',
//                        children:[]
//                    }]
//                }
                ],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                }
            };
        }
    };
</script>