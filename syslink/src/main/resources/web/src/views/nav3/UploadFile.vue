<template>
    <uploader :options="options" :file-status-text="statusText" class="uploader-example" ref="uploader"
              @file-complete="fileComplete" @complete="complete"  @file-added="fileAdded" @upload-start="startUpload"></uploader>
    <!--@files-submitted="filesAdd"-->

</template>

<script>
    import uploader from 'vue-simple-uploader'
    import util from '../../common/js/util'
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
                    chunkSize : 400*1024*1024,
                },
                attrs: {
                    accept: 'image/*',
                    ignore : false,
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
            },
            fileAdded(file){
                console.log("11111");
                if (file.size > 400*1024*1024) {
                    this.$message({
                        message: '存在单文件400M以上文件,从文件列表中已删除，请重新选择文件！',
                        type: 'error',
                        duration: 2000
                    });
                    file.ignored = true;
                }
                //对模型根目录验证，相同根目录无法提交到文件列表
                var m ;
                for(m in this.fileLists){
                    if(this.fileLists[m].isFolder){
                        if(this.fileLists[m].path.split("/")[0] == file.relativePath.split("/")[0]){
                            file.ignored = true;
                        }
                    }
                    else{
                        if(this.fileLists[m].relativePath.split("/")[0] == file.relativePath.split("/")[0]){
                            file.ignored = true;
                        }
                    }
                }
                file.uniqueIdentifier = util.UUID.uuid(8,16);
            },
            startUpload(){
                console.log("开始上传！");
                this.$emit("stopUpload");
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