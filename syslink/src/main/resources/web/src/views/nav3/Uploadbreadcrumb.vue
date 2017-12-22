<template>
    <section >
        <p>{{getTreeItem}}</p>
    <div >
       <!--  <el-col :span="24" class="breadcrumb-container"> -->
        <el-breadcrumb style="line-height: 30px;font-size: 20px;display: inline-block">
            <el-breadcrumb-item>
            <span style="font-size: 20px"> 上传到：</span>
            </el-breadcrumb-item>
        </el-breadcrumb>
        <el-breadcrumb separator="/" style="line-height: 30px;font-size: 20px;display: inline-block">

        <el-breadcrumb-item  v-for="item in treeItem" :key="item.index" >
            <span >  {{ item.name }}</span>
      
        </el-breadcrumb-item>
        </el-breadcrumb>
    <!--     </el-col> -->
    </div>
    </section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex'
    export default {
        data() {
            return {
                treeItem: [],
            };
        },
        computed: {
            ...mapState({
                a:state =>state.a
            }),
            ...mapGetters(['amsg']),
            getTreeItem(){
                console.log(this.amsg);
                var _this = this;
                if(_this.amsg != null && _this.amsg != ""){
                    var url = '/api/directory/getTreeItem?parent_id='+ _this.amsg
                }

                _this.$http.post(url)
                    .then(function (response) {
                        _this.treeItem = response.data.treeItem;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        },
        methods: {

        }
    }

</script>

