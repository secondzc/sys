<template>
	<section> 
    <h3>模型信息</h3> 
    <div style="display:flex">
    	<div style="margin-left:20px;margin-top:10px;">
    	<el-card style="width:660px;">
    		<div style="display:flex">
    			<div style="width:100px;"><p>模型名:</p></div>
    		    <div >{{this.reviewModel.name}}</div>
    		</div>
    		
    		<div style="display:flex">
    			<div style="width:100px;"><p>模型类型:</p></div>
    		    <div >{{this.reviewModel.type}}</div>
    		</div>

            <div style="display:flex">
    			 <div style="width:100px;"><p>模型描述:</p></div>
    		     <div >{{this.reviewModel.discription}}</div>
    		 </div>
    		
    		<div style="display:flex">
    			  <div style="width:100px;"><p>创建时间:</p></div>
    		      <div >{{this.reviewModel.createTime}}</div>
    		  </div>
    	</el-card>
    </div>
    <!-- <div style="width:300px;">
    	<h5>模型浏览器</h5>
        <model-tree></model-tree>
    </div> -->
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