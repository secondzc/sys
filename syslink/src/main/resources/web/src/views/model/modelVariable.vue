<template>
    <section>
    <el-form :inline="true" ref="form" :model="form" label-width="80px" @submit.prevent="onSubmit" style="margin:20px;width:60%;min-width:600px;">
        <!--<div-->
            <!--&gt;{{modelId}}</div>-->
        {{getModelVar}}
        <el-form-item label="模型信息:" >
            <el-input  disabled="disabled" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="模型受限类型:" label-width="150px">
                <el-input disabled="disabled" v-model="form.classes"></el-input>
        </el-form-item>
        <el-form-item label="模型类型:" >
            <el-input  disabled="disabled" v-model="form.type"></el-input>
        </el-form-item>

        <el-form-item  label-width="150px" label="导入包名:" >
            <el-input  disabled="disabled" v-model="form.import"></el-input>
        </el-form-item>
        <el-form-item label="基类名:" >
            <el-input disabled="disabled" v-model="form.extends"></el-input>
        </el-form-item>
        <el-form-item label="上传者:"  label-width="150px">
            <el-input  disabled="disabled" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item label="模型描述:" >
            <el-input disabled="disabled" v-model="form.discription"></el-input>
        </el-form-item>
        <el-form-item label="视图:" label-width="150px">
            <a  href="javascript:void(0)"   @click="svgUrl" >视图</a>
        </el-form-item>
        <el-form-item label="图片:" >
            <a  href="javascript:void(0)"   @click="iconUrl" >图片</a>
        </el-form-item>
        <el-form-item label="Info:" >
            <a  href="javascript:void(0)"   @click="infoUrl" >info信息</a>
        </el-form-item>
    </el-form>

    <el-table
            ref="singleTable"
            :data="tableData"
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
        <el-table-column
                label="序号"
                type="index"
                width="100">
        </el-table-column>
        <el-table-column
                property="name"
                label="类型名"
                width="120">
        </el-table-column>
        <el-table-column
                property="type"
                label="变量类型"
                width="120">
        </el-table-column>
        <el-table-column
                property="defaultValue"
                label="默认值"
                width="240">
        </el-table-column>
        <el-table-column
                property="units"
                label="变量单位"
                width="120">
        </el-table-column>
        <el-table-column
                property="lowerBound"
                label="变量下界"
                width="120">
        </el-table-column>
        <el-table-column
                property="upperBound"
                label="变量上界"
                width="120">
        </el-table-column>
        <el-table-column
                property="IsParam"
                label="是否参数"
                width="120">
        </el-table-column>
        <el-table-column
                property="IsInput"
                label="输入变量"
                width="120">
        </el-table-column>
    </el-table>
    <div style="margin-top: 20px">
        <el-button @click="setCurrent()">返回模型</el-button>
    </div>
    </section>
</template>

<script>
    import { mapState,mapGetters} from 'vuex'
    export default {
        data() {
            return {
                form:[],
                tableData: [
                ],
                currentRow: null,
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
                    var url = '/api/model/modelVariable?modelId=' + _this.modelId;
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.tableData = response.data.tableData;
                            _this.form = response.data.form;
                        })
                        .catch(function (error) {
                            console.log(error)
                        })


                }
            }
        },
        methods: {
            setCurrent(row) {
                var _this=this;
                this.$store.dispatch('sendA',_this.form.directoryParentId);
                this.$router.push({path: '/model/index'});
            },
            handleCurrentChange(val) {
                this.currentRow = val;
            },
            svgUrl : function () {
                var svg = this.$refs.form.model.diagramSvgPath;
                window.open(svg,"_blank");
            },
            iconUrl : function () {
                var svg = this.$refs.form.model.iconSvgPath;
                window.open(svg,"_blank");
            },
            infoUrl : function () {
                var svg = this.$refs.form.model.infoTextPath;
                window.open(svg,"_blank");
            }
        }
    }

</script>