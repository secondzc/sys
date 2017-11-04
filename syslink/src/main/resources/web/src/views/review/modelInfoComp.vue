<template>
	<section> 
    <h3>模型信息：</h3> 
    <!-- <el-table :data="reviewModel" border style="width:100%"> 
            <el-table-column prop="name" label="模型名字" width="180"> 
            </el-table-column> 
            <el-table-column prop="type" label="模型类型" width="180"> 
            </el-table-column> 
            <el-table-column prop="description" label="模型描述" width="180"> 
            </el-table-column> 
            <el-table-column prop="createTime" label="创建时间" width="180"> 
            </el-table-column> 
      </el-table> --> 
  </section> 
</template>

<script>
	export default{
		data(){
			return {
				reviewModel: {
					name:'',
					type:'',
					description:'',
					createTime:'',
				}
			}
		},
	}
</script>

<style>
	
</style>