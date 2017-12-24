<template>
    <section>
        <div v-if="showPicture" class="avatar-uploader" style="margin-bottom: 17px">
            <img src="../../assets/test1.png" class="avatar"/>
        </div>
        <div v-else="!showPicture">
            <el-upload
                    class="avatar-uploader"
                    :action = "photo()"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </div>
        <el-button size="small" @click="showPicture = !showPicture" type="primary">点击修改</el-button>
        <el-button size="small" type="primary">点击上传</el-button>

    </section>
</template>

<script>
    export default {
        data() {
            return {
                showPicture : true,
                imageUrl: ''
            };
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            photo(){
                return "http://"+global_.HostPath+ "/api/model/uploadModelIcon"
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/*';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>