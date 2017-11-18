<template>
	<section>
		<h3>待审核流程</h3>
		<checkor></checkor>
		<h3>审签中的流程进度</h3>
	    <my-submit></my-submit>
	</section>
</template>

<script>
	import checkor from "../review/checkor";
	import mySubmit from "../review/mySubmit";
	export default{
		components:{
			checkor,
			mySubmit,
		}
	}
</script>

<style>
	
</style>