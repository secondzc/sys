<!--<template>-->
    <!--<section style="text-align: center;">-->
            <!--<el-upload-->
                    <!--class="avatar-uploader"-->
                    <!--:action = "photo()"-->
                    <!--:show-file-list="false"-->
                    <!--:on-success="handleAvatarSuccess"-->
                    <!--:before-upload="beforeAvatarUpload">-->
                <!--<img v-if="imageUrl" :src="imageUrl" class="avatar">-->
                <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
            <!--</el-upload>-->

    <!--</section>-->
<!--</template>-->

<!--<script>-->
    <!--import global_ from '../global.vue'-->
    <!--import { mapState,mapGetters} from 'vuex'-->
    <!--import {mapActions} from 'vuex'-->
    <!--export default {-->
        <!--components: {-->
            <!--global_,-->
        <!--},-->
        <!--data() {-->
            <!--return {-->
                <!--imageUrl: '',-->
                <!--name : this.$store.state.userInfo.profile.name,-->
            <!--};-->
        <!--},-->
        <!--computed: {-->
            <!--...mapState({-->
                <!--modelId: state => state.modelId,-->
                <!--a: state => state.a-->
            <!--}),-->
            <!--...mapGetters(['modelId', 'bmsg', 'treeModelId']),-->
        <!--},-->
        <!--methods: {-->
            <!--handleAvatarSuccess(res, file) {-->
                <!--this.imageUrl = URL.createObjectURL(file.raw);-->
            <!--},-->
            <!--photo(){-->
                <!--return "http://"+global_.HostPath+ "/api/modeltype/addModelType?name="+this.name-->
            <!--},-->
            <!--beforeAvatarUpload(file) {-->
                <!--const isJPG = file.type === 'image/jpeg';-->
                <!--const isLt2M = file.size / 1024 / 1024 < 2;-->

                <!--if (!isJPG) {-->
                    <!--this.$message.error('上传头像图片只能是 JPG 格式!');-->
                <!--}-->
                <!--if (!isLt2M) {-->
                    <!--this.$message.error('上传头像图片大小不能超过 2MB!');-->
                <!--}-->
                <!--return isJPG && isLt2M;-->
            <!--}-->
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




