<template>
	<section> 
    <h3>模型信息</h3> 
    <div style="display:flex">
    	<div style="margin-left:20px;margin-top:10px;">
    	<div style="width:660px;">
    		<div class="modelInfo">
    			<div style="display:inline-block"><p>模型名字:</p></div>
    		    <div style="display:inline-block">{{this.reviewModel.name}}</div>
    		</div>
    		<div class="modelInfo">
    			<div style="display:inline-block"><p>模型类型:</p></div>
    		    <div style="display:inline-block">{{this.reviewModel.type}}</div>
    		</div>

    	
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
    <!-- <div style="width:300px;">
    	<h5>模型浏览器</h5>
        <model-tree></model-tree>
    </div> -->
    </div>
    <h3>详情查看</h3>
    <el-button @click="toTree">转到模型树</el-button>
  </section> 
</template>

<script>
	export default{
		data(){
			return {
				reviewModel:{
					id:0,
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
			toTree(){
				console.log('12.10'+this.reviewModel.id);
				this.$store.dispatch('sendModelId',this.reviewModel.id);
				this.$store.dispatch('sendTreeModelId',this.reviewModel.id);
				this.$router.push({path:'/model/packageDiagram'});
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