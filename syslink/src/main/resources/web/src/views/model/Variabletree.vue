<template>
    <div>
        {{getVarTree}}
        <tree
            :data="data"
            :props="defaultProps"
            node-key="id"
            default-expand-all
            :expand-on-click-node="false"
            :columns="columns"
            ref="tree1"
        >
        </tree>
    </div>
</template>

<script>
    import tree from './../../components/index'
    import { mapState,mapGetters} from 'vuex'
    console.log(tree)
    export default {
        computed: {
            ...mapState({
                modelId: state => state.modelId,
            }),
            ...mapGetters(['modelId']),
            getVarTree(){
                var _this = this;
                if(_this.modelId == null || _this.modelId == ''){
                    this.$router.push({path: '/model/index'});
                }else {
                    var url = '/api/variable/variableTree?modelId=' + _this.modelId;
//                    this.$store.dispatch('sendTreeModelId',_this.modelId);
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.data = response.data.data;
                        })
                        .catch(function (error) {
                            console.log(error)
                        })


                }
            }
        },
        data () {
            return {
                columns: [{
                    minWidth: 100,
                    label: '名称',
                    name: 'name'
                }, {
                    minWidth: 100,
                    label: '变量类型',
                    name: 'type'
                }, {
                    minWidth:100,
                    label: '默认值',
                    name: 'defaultValue'
                }, {
                    minWidth: 100,
                    label: '变量单位',
                    name: 'units'
                }, {
                    minWidth: 100,
                    label: '变量下界',
                    name: 'lowerBound'
                },{
                    minWidth: 100,
                    label: '变量上界',
                    name: 'upperBound'
                },{
                    minWidth: 100,
                    label: '是否参数变量',
                    name: 'isParam'
                },{
                    minWidth: 100,
                    label: '是否输入变量',
                    name: 'isInput'
                }
//              {
//                    width: 140,
//                    minWidth: 140,
//                    label: '操作',
//                    renderContent: this.renderContent
//                }
                ],
                data: [
//                    {
//                    id: 1,
//                    name: '一级 1',
//                    type: '123',
//                    defaultValue: '123',
//                    units: '123',
//                    children: [{
//                        id: 1,
//                        name: '二级 1-1',
//                        type: '123',
//                        defaultValue: '123',
//                        units: '123',
//                        children: [{
//                            id: 9,
//                            name: '三级 1-1-1',
//                            type: '123',
//                            defaultValue: '123',
//                            units: '123',
//                        }, {
//                            id: 10,
//                            name: '三级 1-1-2',
//                            type: '123',
//                            defaultValue: '123',
//                            units: '123',
//                        }]
//                    }]
//                }, {
//                    id: 2,
//                    name: '一级 2',
//                    type: '123',
//                    defaultValue: '123',
//                    units: '123',
//                    children: [{
//                        id: 5,
//                        name: '二级 2-1',
//                        type: '123',
//                        defaultValue: '123',
//                        units: '123',
//                    }, {
//                        id: 6,
//                        name: '二级 2-2',
//                        type: '123',
//                        defaultValue: '123',
//                        units: '123',
//                    }]
//                }, {
//                    id: 3,
//                    name: '一级 3',
//                    type: '123',
//                    defaultValue: '123',
//                    units: '123',
//                    children: [{
//                        id: 7,
//                        name: '二级 3-1',
//                        type: '123',
//                        defaultValue: '123',
//                        units: '123',
//                    }, {
//                        id: 8,
//                        name: '二级 3-2',
//                        type: '123',
//                        defaultValue: '123',
//                        units: '123',
//                    }]
//                }
                ],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                }
            }
        },
        components: {
            tree
        }
    }
</script>

<style>

</style>