<template>
	<div>
		<review-detail></review-detail>
		<comment-comp></comment-comp>
	</div>
</template>
<script >
    import reviewDetail from '../review/reviewDetailComp';
    import commmentComp from '../review/commmentComp'
	export default{

		data(){
			return {

			}
		},
		components:{
			reviewDetail,
			commmentComp,
		}
	}
</script>

<style></style>