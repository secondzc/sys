<template>
	<div class="component">
		<sapn style="font-size: 16px;text-decoration: underline;">审核流程</sapn>
		<review-detail style="margin-top: 20px;"></review-detail>
		<comment-comp :isCheckor="isCheckor" style="margin-top: 20px;"></comment-comp>
	</div>
</template>
<script >
    import reviewDetail from '../review/reviewDetailComp';
    import commentComp from '../review/commentComp';
	export default{

		data(){
			return {
				isCheckor:true,
			}
		},
		components:{
			reviewDetail,
			commentComp,
		}
	}
</script>

<style scoped>
	.component
	{
		margin-top: 10px;
          height: calc(100%-60px)!important;
     height: -moz-calc(100% - 60px)!important;
    height :-webkit-calc(100% - 60px)!important; 
	}

</style>