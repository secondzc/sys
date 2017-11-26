<template>
	<div>
		<review-detail></review-detail>
		<comment-comp :isCheckor="isCheckor"></comment-comp>
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

<style></style>