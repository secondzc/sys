<template>
    <section >

    <div >
    <el-upload
            class="upload-demo"
            ref="vueFileUploader"
            :action = "uploadUrl()"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :on-success="uploadSuccess"
            :file-list="fileList"
            :before-upload="beforeUploadFile"
            :auto-upload="true"
    >
        <el-button slot="trigger" size="small" type="primary"  >上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
        <!--<p>{{directoryContent}}</p>-->
        <!--{{bmsg}}-->
        <!--<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
        <!--<div slot="tip" class="el-upload__tip">只能上传zip文件，且不超过500M</div>-->
    </el-upload>
    </div>
    </section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex'

    var fileNub = 0;
    export default {
        data() {
            return {
                fileList: [
                ],
                name : this.$store.state.userInfo.profile.name,
                // JSON.parse(sessionStorage.getItem('user')).name,
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
                return "http://gogs.modelica-china.com:8080/api/directory/uploadDirectory?name="+this.$data.name+"&directoryId="+this.bmsg
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
                var modelUrl = '/api/repository/add?name=' + this.$data.name +'&fileName=' +file.name.split("\.")[0]
                this.$http.post(modelUrl)
                    .then(function (response) {
                    })
                    .catch(function (error) {
                        console.log(error)
                    })

                let fileMAx = 1024 * 1024 *500;
                if(fileMAx < file.size){
                    this.$message({
                        message: '文件过大，只能上传500M以内！',
                        type: 'warning',
                        duration: 2000
                    });
                    abort(file);
                }
                if(!(file.name).endsWith(".zip")){
                    this.$message({
                        message: '请上传压缩文件！',
                        type: 'warning',
                        duration: 2000
                    });
                    abort(file);
                }
                fileNub +=1;
                if(fileNub >1){
                    this.$message({
                        message: '请上传一个文件！',
                        type: 'warning',
                        duration: 2000
                    });
                    fileNub =0;
                    abort(file);
                }
                if(this.bmsg < 0) {
                    this.$message({
                        message: '请选择一个模型目录！',
                        type: 'warning',
                        duration: 2000
                    });
                    abort(file);
                }
            },
            uploadSuccess(response,file,fileList){
                this.$message({
                    message: '上传成功！',
                    type: 'success',
                    duration: 1000
                });
                this.$refs.vueFileUploader.clearFiles();
            }
        }
    }
</script>