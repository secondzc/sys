<template>
	<section> 
    <sapn style="font-size: 16px;text-decoration: underline;">模型信息</sapn> 
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
    	    <div style="margin-top: 20px;">
    	    	<sapn style="font-size: 16px;text-decoration: underline;">查看/下载</sapn>
    	    </div>
    	    </el-popover>
            <el-button type="primary" @click="toTree" style="margin-left:20px;margin-top: 20px;" :disabled="!isModelica">查看</el-button>
            <el-button type="primary" @click="download" :loading="downloading">下载</el-button> 
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
				downloading:false,
				isModelica: false,
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
			download(){
				let url="/api/model/download";
				this.downloading=true;
				this.func.ajaxPost(url,{modelId:this.reviewModel.id},res=>{
					if(res.data.flag){
						this.$message({
							message:'下载成功！',
						});
						this.downloading=flase;
					}
				})
			},
			getReviewModel(){
				this.listLoading=true;
				console.log("instanceId="+this.instanceId);
				let url ="/api/checkor/showModelDetails";
				this.func.ajaxPost(url,{instanceId:this.instanceId},res=>{
					if(res.data.flag==true){
						this.reviewModel = res.data.reviewModel;
						this.isModelica = (res.data.reviewModel.type==='Modelica'?true:false);
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