<template>
    <uploader :options="options" :file-status-text="statusText" class="uploader-example" ref="uploader"
              @file-complete="fileComplete" @complete="complete"></uploader>
</template>

<script>
    import uploader from 'vue-simple-uploader'
    import global_ from '../global.vue'
    export default {
        components: {
            global_
        },
        data () {
            return {
                options: {
                    target: "http://" + global_.HostPath + "/api/model/uploadModelFloder?name=" + this.$store.state.userInfo.profile.name + "&directoryId=" + this.$store.state.bmsg + "&scope=" + true,
// '//jsonplaceholder.typicode.com/posts/',
                    testChunks: false,
                    relativePath :true,
                },
                attrs: {
                    accept: 'image/*',
                },
                statusText: {
                    success: '成功了',
                    error: '出错了',
                    uploading: '上传中',
                    paused: '暂停中',
                    waiting: '等待中'
                },
                name: this.$store.state.userInfo.profile.name,
                files : [],
                fileLists :[],
            }
        },
        methods: {
            complete () {
                console.log('complete', arguments)
                this.$emit("sendFiles", arguments);
            },
            fileComplete () {
                console.log('file complete', arguments)
                this.analysis(arguments);
                this.$emit("sendFileLists", this.fileLists);
            },
            //解析文件arguments
            analysis(data){
                if(data[0].isFolder == false && data[0].fileList.length == 0){
                    this.fileLists.push(data[0]);
                }
                if(data[0].isFolder == true){
                    this.fileLists.push(data[0]);
                    this.analysisList(data[0].fileList)
                }

            },
            analysisList(data){
                if(data.length != 0){
                    for(var i= 0;i<data.length ; i++){
                        if(data[i].isFolder == false){
                            this.fileLists.push(data[i])
                        }
                        if(data[i].isFolder == true){
                            this.fileLists.push(data[i]);
                            this.analysisList(data[i].fileList)
                        }
                    }
                }
            }
        },
        mounted () {
            this.$nextTick(() => {
                window.uploader = this.$refs.uploader.uploader
            })
        }
    }
</script>

<style>
    .uploader-example {
        /*width: 880px;*/
        padding: 15px;
        margin: -18px auto 0;
        font-size: 14px;
        box-shadow: 0 0 10px rgba(0, 0, 0, .4);
    }

    .uploader-example .uploader-btn {
        margin-right: 4px;
    }

    .uploader-example .uploader-list {
        max-height: 440px;
        overflow: auto;
        overflow-x: hidden;
        overflow-y: auto;
    }
</style>