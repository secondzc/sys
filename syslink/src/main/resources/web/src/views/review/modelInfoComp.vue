<template>
	<section> 
    <h5>模型信息：</h5> 
    <div style="margin-left:20px;margin-top:10px" >
    	<div>
    		<div class="modelInfo">
    			<div style="display:inline-block"><p>模型名字:</p></div>
    		    <div style="display:inline-block">{{this.reviewModel.name}}</div>
    		</div>
    		<div class="modelInfo">
    			<div style="display:inline-block"><p>模型类型:</p></div>
    		    <div style="display:inline-block">{{this.reviewModel.type}}</div>
    		</div>

    	</div>
    	<div>
    		<div class="modelInfo">
    			 <div style="display:inline-block"><p>模型描述:</p></div>
    		     <div style="display:inline-block">{{this.reviewModel.discription}}</div>
    		</div>
    		<div class="modelInfo">
    			  <div style="display:inline-block"><p>创建时间:</p></div>
    		      <div style="display:inline-block">{{this.reviewModel.createTime}}</div>
    		</div>
    	</div>
    </div>
  </section> 
</template>

<script>
	export default{
		data(){
			return {
				reviewModel:{
					name:'',
					type:'',
					discription:'',
					createTime:'',
				},
				listLoading: false,
			}
		},
		// props: {
		// 	instanceId:{
		// 		type:String,
		// 		required: false,
		// 	}
		// },
		//props: ['instanceId'],
		methods: {
			getReviewModel(){
				this.listLoading=true;
				console.log("instanceId="+this.instanceId);
				let url ="/api/checkor/showModelDetails";
				this.func.ajaxPost(url,{instanceId:this.instanceId},res=>{
					if(res.data.flag==true){
						this.reviewModel = res.data.reviewModel;
						this.listLoading = false;
					}
				})
				console.log(this.reviewModel);
			},
		},
		mounted(){
			this.instanceId = sessionStorage.getItem('instanceId');
			this.getReviewModel();
		},
	}
</script>

<style>
	.modelInfo{
		width:75%;
		border: 2px solid #9E9E9E;
	}
</style>