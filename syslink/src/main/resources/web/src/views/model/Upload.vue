<template>
    <section>
        <el-container>
                <el-aside :span="16">
                    <el-form ref="form"  :model="form" label-width="80px" class="demo-form-inline">


                        <el-form-item label="模型名">
                            <el-col >
                                <el-input  v-model="form.name"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="模型类别">
                            <el-col >
                                <el-select style="width: 100%" v-model="form.region" placeholder="模型类别">
                                    <el-option
                                            v-for="item in modelType" :key="item.id" :label="item.name" :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="模型描述">
                            <el-col >
                                <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}"  placeholder="请添加相关描述" v-model="form.desc"></el-input>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="模型图标">
                            <!--<el-col style="height: 120px;">-->
                                <!--<div style="height: 100%">-->
                                    <!--<img src="../../assets/test1.png" style="width: 100%;height: 100%;"/>-->
                                <!--</div>-->
                            <!--</el-col>-->
                            <uploadPicture></uploadPicture>

                        </el-form-item>
                        <el-form-item>
                            <!--<el-button type="primary" @click="onSubmit">立即创建</el-button>-->
                            <!--<el-button>取消</el-button>-->
                        </el-form-item>
                    </el-form>

                </el-aside>
            <el-container>
                <el-main>
                    <uploadFile></uploadFile>
                </el-main>
            <el-footer>
                <div style="float: right;height: 30px;width: 100%" >
                    <el-button style="float: right;margin-left: 10px">取消</el-button>
                    <el-button style="float: right" type="primary" @click.native="onSubmit">提交</el-button>


                </div>
            </el-footer>
            </el-container>
        </el-container>


    </section>
</template>
<script>
    import upload_breadcrumb from '../nav3/Uploadbreadcrumb.vue'
    import uploadPicture from '../nav3/UploadPicuter.vue'
    import uploadFile from '../nav3/UploadFile.vue'
    export default {
        components: {
            upload_breadcrumb,
            uploadPicture,
            uploadFile
        },
        data() {
            return {
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: ''
                },
                modelType:[],
            }
        },
        methods: {
            onSubmit() {
                console.log('submit!');
            },
            getRoles() {
                var _this = this;
                _this.$http.post('/api/modeltype/list')
                    .then(function (response) {
                        let a = response.data.role;
                        _this.roles1 = response.data.role;
                        for(let i = 0;i<a.length;i++)
                        {

                            _this.roles.push({
                                key:a[i].id,
                                label:a[i].name
                            })
                            // _this.roles = response.data.role;
                        }

                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
        }
    }

</script>
<style>

</style>