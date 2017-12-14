<template>
    <section >

        <div >
            <el-upload
                    class="upload-demo"
                    ref="vueFileUploader"
                    multiple
                    :action = "uploadUrl()"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-success="uploadSuccess"
                    :file-list="fileList"
                    :before-upload="beforeUploadFile"
                    :auto-upload="true"
                    :show-file-list = "true"
            >
                <el-button slot="trigger" size="small" type="primary" style="font-size: 12px;" >上传文件</el-button>
                <!--<p>{{directoryContent}}</p>-->
                <!--{{bmsg}}-->
                <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
                <!--<div slot="tip" class="el-upload__tip">只能上传zip文件，且不超过500M</div>-->
            </el-upload>
        </div>

        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
         >
            <span>是否覆盖原有的模型？</span>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="abortUpload">取 消</el-button>
                    <el-button type="primary" @click="coverModel">确 定</el-button>
                 </span>
        </el-dialog>
    </section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex'
    import global_ from '../global.vue'
    export default {
        data() {
            return {
                isCover : false,
                dialogVisible: false,
                fileList: [
                ],
//                name : JSON.parse(sessionStorage.getItem('user')).name,
                name : this.$store.state.userInfo.profile.name,
            };
        },
        computed: {
            ...mapState({
                b:state =>state.b
            }),
            ...mapGetters(['bmsg']),
        },
        methods: {
            //上传文件
            uploadUrl :function(){
                var scope = false;
                return "http://"+global_.HostPath +"/api/directory/uploadDirectory?name="+this.$data.name+"&directoryId="+this.bmsg + "&scope=" + scope
                //  return "https://jsonplaceholder.typicode.com/posts/"
            },
            submitUpload() {
                //     this.$refs.vueFileUploader.submit;
                //     this.$refs.vueFileUploader.autoUpload = true;
                this.$refs.vueFileUploader.submit();
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            },
            beforeUploadFile(file){
//                    //检验gogs仓库是否存在，不存在则创建一个仓库
//                    var modelUrl = '/api/repository/add?name=' + this.$data.name + '&fileName=' + file.name.split("\.")[0]
//                    this.$http.post(modelUrl)
//                        .then(function (response) {
//                        })
//                        .catch(function (error) {
//                            console.log(error)
//                        })
                    if (this.bmsg < 0) {
                        this.$message({
                            message: '请选择一个模型目录！',
                            type: 'warning',
                            duration: 2000
                        });
                        this.$refs.vueFileUploader.abort(file);
                        return false;
                    }
//                    else {
//                        //检验这个模型分类下是否存在这个模型
//                        var checkModelUrl = '/api/directory/checkModel?fileName=' + file.name.split("\.")[0] + "&directoryId=" + this.bmsg
//                        var _this = this;
//                        _this.$http.post(checkModelUrl)
//                            .then(function (response) {
//                                if (response.data.status == 0) {
//                                    _this.dialogVisible = true;
//                                    return false;
//                                }
//                            })
//                            .catch(function (error) {
//                                console.log(error)
//                            })
//                    }

                    let fileMAx = 1024 * 1024 * 500;
                    if (fileMAx < file.size) {
                        this.$message({
                            message: '文件过大，只能上传500M以内！',
                            type: 'warning',
                            duration: 2000
                        });
                        this.$refs.vueFileUploader.abort(file);
                        return false;
                    }
                    if (!(file.name).endsWith(".zip")) {
                        this.$message({
                            message: '请上传压缩文件！',
                            type: 'warning',
                            duration: 2000
                        });
                        this.$refs.vueFileUploader.abort(file);
                        return false;
                    }
                    if (this.$refs.vueFileUploader.uploadFiles.length > 1) {
                        this.$message({
                            message: '请上传一个文件！',
                            type: 'warning',
                            duration: 2000
                        });
                        this.$refs.vueFileUploader.uploadFiles = [];
                        this.$refs.vueFileUploader.abort(file);
                        return false;
                    }
                    if(this.bmsg >= 0 && !this.isCover){
                    return new Promise((resolve, reject) => {
                        var checkModelUrl = '/api/directory/checkModel?fileName=' + file.name.split("\.")[0] + "&directoryId=" + this.bmsg
                        var _this = this;
                        _this.$http.post(checkModelUrl)
                            .then(function (response) {
//                                var realUrl =   "http://gogs.modelica-china.com:8080/api/directory/uploadDirectory?name="+_this.$data.name+"&directoryId="+_this.bmsg;
//                                _this.$refs.vueFileUploader.uploadFiles[0].url = realUrl;
                                if (response.data.status == 0) {
                                    if(!_this.isCover){
                                        _this.dialogVisible = true;
                                    }else{
                                        resolve(true);
                                    }
//                                    reject(false)
                                }else {
                                    resolve(true);
                                }
                            })
                            .catch(function (error) {
                                console.log(error)
                                reject(false)
                            })
                    });
                    }
//                    return true;

            },
            uploadSuccess(response,file,fileList){
                this.$message({
                    message: '上传成功！',
                    type: 'success',
                    duration: 3000
                });
                this.isCover = false;
                this.$refs.vueFileUploader.clearFiles();
                this.$emit("refreshMyModel");
                this.$emit("allowToReview");
 
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            coverModel(){
                var scope = false;
                var realUrl =   "http://"+global_.HostPath +"/api/directory/uploadDirectory?name="+this.$data.name+"&directoryId="+this.bmsg + "&scope=" + scope;
                this.$refs.vueFileUploader.uploadFiles[0].url = realUrl;
                this.isCover = true;
                this.dialogVisible = false;
                this.$refs.vueFileUploader.submit();

            },
            abortUpload(){
//                this.$refs.vueFileUploader.abort(file);
                this.dialogVisible = false;
            }
        }
    }
</script>