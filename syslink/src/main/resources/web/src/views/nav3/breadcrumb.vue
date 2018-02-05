<template>
    <section style="height: inherit;display: flex;align-items: center;">
        <p style="display: none;">{{getTreeItem}}</p>
   <!-- <div >-->
       <!--  <el-col :span="24" class="breadcrumb-container"> -->
       <!--	<span style="font-weight: bold;">当前分类:</span>-->
        <el-breadcrumb separator="/" >
        <el-breadcrumb-item v-for="item in treeItem" :key="item.index" :title='item.name' >
           <span style="display:inline-block;height:inherit;max-width: 100px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">  {{ item.name }}</span>
         
        </el-breadcrumb-item>
        </el-breadcrumb>
    <!--     </el-col> -->
  <!--  </div>-->
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
                        _this.$emit("uplaodTitle",_this.treeItem)
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

<style scoped>
	.el-breadcrumb
	{
	/*	line-height: 20px;*/

	/*display: flex;
	align-items: center;*/
	}
</style>

