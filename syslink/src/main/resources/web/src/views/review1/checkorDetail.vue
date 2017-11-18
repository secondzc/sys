<template>
	<div>
		<review-detail></review-detail>
		<comment-comp></comment-comp>
	</div>
</template>
<script >
    import reviewDetail from '../review/reviewDetailComp';
    import commentComp from '../review/commentComp';
	export default{

		data(){
			return {

			}
		},
		components:{
			reviewDetail,
			commentComp,
		}
	}
</script>

<style></style>