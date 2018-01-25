<!--<template>-->
    <!--<section>-->
        <!--<el-container>-->
                <!--<el-aside :span="16">-->
                    <!--<el-form ref="form"  :model="form" :rules="rules" label-width="80px" class="demo-form-inline">-->


                        <!--<el-form-item label="模型名" prop="name">-->
                            <!--<el-col >-->
                                <!--<el-input  v-model="form.name"></el-input>-->
                            <!--</el-col>-->
                        <!--</el-form-item>-->
                        <!--<el-form-item label="模型类别">-->
                            <!--<el-col >-->
                                <!--<el-select style="width: 100%" v-model="form.region" @change="getPhotoUrl(item)" placeholder="模型类别">-->
                                    <!--<el-option-->
                                            <!--v-for="item in modelType" :key="item.id" :label="item.name" :value="item.name" >-->
                                    <!--</el-option>-->
                                <!--</el-select>-->
                            <!--</el-col>-->
                        <!--</el-form-item>-->
                        <!--<el-form-item label="模型描述">-->
                            <!--<el-col >-->
                                <!--<el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}"  placeholder="请添加相关描述" v-model="form.desc"></el-input>-->
                            <!--</el-col>-->
                        <!--</el-form-item>-->
                        <!--<el-form-item label="模型图标">-->
                            <!--&lt;!&ndash;<uploadPicture ></uploadPicture>&ndash;&gt;-->
                            <!--<template>-->
                                <!--<section>-->
                                    <!--<div v-if="form.showPicture" class="avatar-uploader" style="margin-bottom: 17px">-->
                                        <!--<img v-bind:src="photoUrl" class="avatar"/>-->
                                    <!--</div>-->
                                    <!--<div v-else="!form.showPicture">-->
                                        <!--<el-upload-->
                                                <!--ref="ModelTypePicture"-->
                                                <!--class="avatar-uploader"-->
                                                <!--:action = "photo()"-->
                                                <!--:show-file-list="false"-->
                                                <!--:on-success="handleAvatarSuccess"-->
                                                <!--:before-upload="beforeAvatarUpload">-->
                                            <!--<img v-if="imageUrl" :src="imageUrl" class="avatar">-->
                                            <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
                                        <!--</el-upload>-->
                                    <!--</div>-->
                                    <!--<el-button v-if="form.showPicture" size="small" @click="form.showPicture = !form.showPicture" type="primary" style="margin-left: 45px">点击修改</el-button>-->
                                    <!--<el-button v-else="form.showPicture" size="small" @click="form.showPicture = !form.showPicture" type="primary" style="margin-left: 45px">取消修改</el-button>-->
                                    <!--&lt;!&ndash;<el-button size="small" type="primary">点击上传</el-button>&ndash;&gt;-->

                                <!--</section>-->
                            <!--</template>-->

                        <!--</el-form-item>-->
                        <!--<el-form-item>-->
                            <!--&lt;!&ndash;<el-button type="primary" @click="onSubmit">立即创建</el-button>&ndash;&gt;-->
                            <!--&lt;!&ndash;<el-button>取消</el-button>&ndash;&gt;-->
                        <!--</el-form-item>-->
                    <!--</el-form>-->

                <!--</el-aside>-->
            <!--<el-container>-->
                <!--<el-main>-->
                    <!--<uploadFile @sendFiles="sendFiles" @sendFileLists="sendFileLists"></uploadFile>-->
                <!--</el-main>-->
            <!--<el-footer>-->
                <!--<div style="float: right;height: 30px;width: 100%" >-->
                    <!--<el-button style="float: right;margin-left: 10px" @click="closeDia">取消</el-button>-->
                    <!--<el-button style="float: right" type="primary" @click.native="onSubmit" :loading="submitLoading">提交</el-button>-->
                    <!--<el-button style="float: right" type="primary" @click.native="returnToUploadModel">上传模型</el-button>-->
                <!--</div>-->
            <!--</el-footer>-->
            <!--</el-container>-->
        <!--</el-container>-->


    <!--</section>-->
<!--</template>-->
<!--<script>-->
    <!--import upload_breadcrumb from '../nav3/Uploadbreadcrumb.vue'-->
    <!--import uploadPicture from '../nav3/UploadPicuter.vue'-->
    <!--import uploadFile from '../nav3/UploadFile.vue'-->
    <!--import global_ from '../global.vue'-->
    <!--import { mapState,mapGetters} from 'vuex'-->
    <!--import {mapActions} from 'vuex'-->
    <!--export default {-->
        <!--components: {-->
            <!--upload_breadcrumb,-->
            <!--uploadPicture,-->
            <!--uploadFile,-->
            <!--global_-->
        <!--},-->
        <!--data() {-->
            <!--var validateName = (rule, value, callback) => {-->
                <!--let re = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5]+$");-->
                <!--console.log(value);-->

                <!--if(!value)-->
                <!--{-->
                    <!--callback(new Error('请输入模型名称'));-->
                <!--}-->
                <!--else-->
                <!--{-->
                    <!--if (re.test(value))-->
                    <!--{-->
                        <!--callback();-->
                    <!--}-->
                    <!--else-->
                    <!--{-->
                        <!--callback(new Error('只允许输入中文、字母、数字'));-->
                    <!--}-->
                <!--}-->
            <!--};-->
            <!--return {-->
                <!--form: {-->
                    <!--name: '',-->
                    <!--region: '',-->
                    <!--desc: '',-->
                    <!--photoName : '',-->
                    <!--files : [],-->
                    <!--fileLists : [],-->
                    <!--showPicture: true,-->
                <!--},-->
                <!--rules: {-->
                    <!--name: [{-->
                        <!--required: true,-->
                        <!--validator : validateName,-->
                        <!--trigger: 'blur'-->
                    <!--}],-->
                <!--},-->
                <!--submitLoading :false,-->
                <!--photoUrl: '',-->
                <!--modelType: [],-->
                <!--imageUrl: '',-->
                <!--name : this.$store.state.userInfo.profile.name,-->

            <!--}-->
        <!--},-->
        <!--computed: {-->
            <!--...mapState({-->
                <!--modelId: state => state.modelId,-->
                <!--a: state => state.a-->
            <!--}),-->
            <!--...mapGetters(['modelId', 'bmsg', 'treeModelId']),-->
        <!--},-->
        <!--methods: {-->
            <!--sendFiles(data){-->
               <!--this.form.files = data;-->
            <!--},-->
            <!--sendFileLists(data){-->
                <!--this.form.fileLists = data;-->
            <!--},-->
            <!--submit(){-->
                <!--if(this.form.fileLists.length >0){-->
                    <!--this.submitLoading = true;-->
                    <!--console.log('submit!');-->
                    <!--if(!this.form.showPicture){-->
                        <!--var fileListEndPosition = this.$refs.ModelTypePicture.uploadFiles.length -1;-->
                        <!--this.form.photoName = this.$refs.ModelTypePicture.uploadFiles[fileListEndPosition].name;-->
                    <!--}-->
                    <!--var _this = this;-->
                    <!--let para = Object.assign({}, _this.form);-->
                    <!--_this.$http({method:'post',-->
                        <!--url:'api/model/uploadFloder?name='+this.name +"&directoryId="+this.bmsg + "&scope=" + true,-->
                        <!--data:para})-->
                        <!--.then(function (response) {-->
                            <!--if(response.data.msg == "ok"){-->
                                <!--_this.submitLoading = false;-->
                                <!--_this.$message({-->
                                    <!--message: '上传成功！',-->
                                    <!--type: 'success',-->
                                    <!--duration: 2000-->
                                <!--});-->
                                <!--_this.closeDia();-->
                                <!--_this.$router.push('/mySubmitAll');-->
                            <!--}-->
                        <!--})-->
                        <!--.catch(function (error) {-->
                            <!--console.log(error);-->
                            <!--_this.submitLoading = false;-->
                            <!--_this.$message({-->
                                <!--message: '上传失败！',-->
                                <!--type: 'error',-->
                                <!--duration: 2000-->
                            <!--});-->
                        <!--});-->
                <!--}-->
                <!--else{-->
                    <!--this.$message({-->
                        <!--message: '请上传文件！',-->
                        <!--type: 'error',-->
                        <!--duration: 2000-->
                    <!--});-->
                <!--}-->
            <!--},-->
            <!--onSubmit() {-->
                <!--this.$refs.form.validate((valid) => {-->
                    <!--if (valid) { // 验证通过-->
                        <!--var checkModelName = '/api/model/checkName?checkModelName=' + this.form.name +"&directoryId="+this.bmsg-->
                        <!--var _this = this;-->
                        <!--_this.$http.post(checkModelName)-->
                            <!--.then(function (response) {-->
                                <!--if (response.data.msg == "ok") {-->
                                    <!--_this.submit();-->
                                <!--}else{-->
<!--//                                    _this.$message({-->
<!--//                                        message: '请重新输入模型分类名称！',-->
<!--//                                        type: 'warning',-->
<!--//                                        duration: 2000-->
<!--//                                    });-->
                                    <!--_this.$emit("openInnerVisible",_this.form.name)-->
                                <!--}-->
                            <!--}).catch(function (error) {-->
                            <!--console.log(error)-->
                            <!--_this.submitLoading = false;-->
                            <!--_this.$message({-->
                                <!--message: '提交失败！',-->
                                <!--type: 'error',-->
                                <!--duration: 2000-->
                            <!--});-->
                        <!--})-->
                    <!--}-->
                <!--})-->

            <!--},-->
            <!--getModelTypeList() {-->
                <!--var _this = this;-->
                <!--_this.$http.post('/api/modeltype/getModelTypeList')-->
                    <!--.then(function (response) {-->
                        <!--let a = response.data.modelTypeList;-->
                        <!--_this.modelType = response.data.modelTypeList;-->
                        <!--if(_this.modelType.length != 0){-->
                            <!--_this.form.region = _this.modelType[0].name;-->
                            <!--_this.photoUrl = _this.modelType[0].filePath-->
                        <!--}-->
                    <!--})-->
                    <!--.catch(function (error) {-->
                        <!--console.log(error);-->
                    <!--});-->
            <!--},-->
            <!--getPhotoUrl(item){-->
                <!--if (this.modelType.length != 0 && this.form.region != "") {-->
                    <!--for (var i = 0; i < this.modelType.length; i++) {-->
                        <!--if (this.modelType[i].name == this.form.region) {-->
                            <!--this.photoUrl =this.modelType[i].filePath-->
                        <!--}-->
                    <!--}-->
                <!--}-->
            <!--},-->
            <!--closeDia(){-->
                <!--this.$emit("closeDialog")-->
            <!--},-->
            <!--returnToUploadModel(){-->
                <!--this.$emit("returnToModel")-->
            <!--},-->
            <!--refreshTable(){-->
                <!--this.$refs['form'].resetFields();-->
                <!--this.getModelTypeList();-->
                <!--this.getPhotoUrl();-->
            <!--},-->
            <!--coverModel(data){-->
                <!--this.deleteModel(data);-->
            <!--},-->
            <!--deleteModel(data){-->
                <!--var _this = this;-->
                <!--_this.$http.post('/api/model/deleteModel?modelName='+data +"&directoryId="+this.bmsg)-->
                    <!--.then(function (response) {-->
                        <!--_this.submitLoading = true;-->
                        <!--if (response.data.msg == "ok") {-->
                            <!--_this.submit();-->
                        <!--}-->
                    <!--})-->
                    <!--.catch(function (error) {-->
                        <!--console.log(error);-->
                        <!--_this.submitLoading = false;-->
                        <!--_this.$message({-->
                            <!--message: '上传失败！',-->
                            <!--type: 'error',-->
                            <!--duration: 2000-->
                        <!--});-->
                    <!--});-->
            <!--},-->
            <!--//-&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;上传图片-->
            <!--handleAvatarSuccess(res, file) {-->
                <!--this.imageUrl = URL.createObjectURL(file.raw);-->
            <!--},-->
            <!--photo(){-->
                <!--return "http://"+global_.HostPath+ "/api/model/uploadModelIcon?name="+this.name +"&directoryId="+this.bmsg + "&scope=" + true-->
            <!--},-->
            <!--beforeAvatarUpload(file) {-->
                <!--const isJPG = file.type === 'image/jpeg'|| file.type === 'image/png' || file.type === 'image/webp';-->
                <!--const isLt2M = file.size / 1024 / 1024 < 2;-->

                <!--if (!isJPG) {-->
                    <!--this.$message.error('上传图片只能是 JPG,PNG,WEBP 格式!');-->
                <!--}-->
                <!--if (!isLt2M) {-->
                    <!--this.$message.error('上传图片大小不能超过 2MB!');-->
                <!--}-->
                <!--return isJPG && isLt2M;-->
            <!--}-->
            <!--//-&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;&#45;-->
        <!--},-->

        <!--mounted(){-->
            <!--this.getModelTypeList();-->
        <!--}-->
    <!--}-->

<!--</script>-->
<!--<style>-->
    <!--.avatar-uploader .el-upload {-->
        <!--border: 1px dashed #d9d9d9;-->
        <!--border-radius: 6px;-->
        <!--cursor: pointer;-->
        <!--position: relative;-->
        <!--overflow: hidden;-->
    <!--}-->
    <!--.avatar-uploader .el-upload:hover {-->
        <!--border-color: #409EFF;-->
    <!--}-->
    <!--.avatar-uploader-icon {-->
        <!--font-size: 28px;-->
        <!--color: #8c939d;-->
        <!--width: 178px;-->
        <!--height: 178px;-->
        <!--line-height: 178px;-->
        <!--text-align: center;-->
    <!--}-->
    <!--.avatar {-->
        <!--width: 178px;-->
        <!--height: 178px;-->
        <!--display: block;-->
    <!--}-->

<!--</style>-->