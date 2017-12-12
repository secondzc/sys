<template>
	<section>
		<el-tabs v-model="activeTab">
			<el-tab-pane label="待审核的流程" name="checkor">
				<checkor></checkor>
			</el-tab-pane>
			<el-tab-pane label="审签中的流程" name="mySubmit">
				<my-submit></my-submit>
			</el-tab-pane>
		</el-tabs>
	</section>
</template>

<script>
	import checkor from "../review/checkor";
	import mySubmit from "../review/mySubmit";
	export default{
		components:{
			checkor,
			mySubmit,
		},
		data(){
			return{
				activeTab:"checkor",
			};
		},
	}
</script>

<style>
	
</style>