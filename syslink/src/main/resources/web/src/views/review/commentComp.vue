<template>
	<div>
		
		<sapn style="font-size: 16px;text-decoration: underline;" v-if="isCheckor && isActive">意见</sapn>

		<el-card style="width:660px;margin-left:20px;margin-top: 20px;"  v-if="isCheckor && isActive">
			<el-form>
			<el-form-item label="是否通过:">
				<el-radio-group v-model="isAgree">
					<el-radio label="true">同意</el-radio>
					<el-radio label="false">不同意</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="意见:">
				<el-input type="textarea" v-model="comment" style="width:75%;margin-left:20px"></el-input>
			</el-form-item>
			<el-form-item>
			<el-button type="primary" @click="submit" v-loading="submitLoading" style="margin-left:400px;">提交</el-button>
			</el-form-item>
		    </el-form>
		</el-card>

		<div style="margin-top: 20px;margin-bottom: 20px;">
			<sapn style="font-size: 16px;text-decoration: underline;">处理人意见区</sapn>
		</div>
					
		<!-- <div style="width:520px">

			<div style="border: 1px solid #9E9E9E;margin-left:20px" v-for="(commentPage,index) in commentPages">
				<div style="width:20%;float:left">审核者：{{commentPage.checkorName}}</div>
				<div style="width:40%;float:left">审核结果：{{commentPage.showStatus}}</div>
				<div style="width:40%;float:left">审核时间：{{commentPage.lastUpdateTime}}</div>

				<div>
				处理人意见：{{commentPage.comment}}
			    </div>
			</div>
		</div> -->
		<el-card style="width:660px;margin-left:20px;">
			<div v-for="(commentPage,index) in commentPages">
    	        <div style="display:flex">
    	        	<div style="width:100px;"><p>审核者:</p></div>
    	            <span :title="commentPage.checkorName" class="spanEllipsis">{{commentPage.checkorName}}</span>
    	        </div>
    	        
    	        <div style="display:flex">
    	        	<div style="width:100px;"><p>审核结果:</p></div>
    	            <div >{{commentPage.showStatus}}</div>
    	        </div>

                 <div style="display:flex">
    	        	 <div style="width:100px;"><p>审核时间:</p></div>
    	             <div >{{commentPage.lastUpdateTime}}</div>
    	         </div>
    	        
    	        <div style="display:flex">
    	        	  <div style="width:100px;"><p>处理人意见:</p></div>
    	              <div >{{commentPage.comment}}</div>
    	        </div>
    	        <hr v-if="index!=commentPages.length-1">
    	     </div>
    	</el-card>
	</div>
</template>

<script >
	export default{
		data(){
			return {
				isActive: false,
				instanceId:0,
			    commentPages:[],
			    isAgree:"",
			    comment:'',
			    id:0,
			    submitLoading:false,
			}
		},
		props:['isCheckor'],
		methods:{
			judgeActive(){
				let url = "/api/nodeInstance";
				this.func.ajaxPost(url,{id:this.id},res=>{
					if(res.data.flag === true){
						if(res.data.msg.status === 2){
							this.isActive = true;
						}
					}
				})
			},
			getCommentPages(id){
				let url="/api/comment/queryCommentPages";
			    this.func.ajaxPost(url,{instanceId:id},res=>{
			    	if(res.data.flag==true){
			    		this.commentPages = res.data.commentPages;
			    	}
			    })
			},
			submit(){
				let url='';
				if(this.isAgree=="true"){
					url="api/checkor/agreeWithComment";
				}
				if(this.isAgree=="false"){
					url="api/checkor/disagreeWithComment";
				}
				let param = {
					id:this.id,
					comment:this.comment,
				}
				this.submitLoading=true;
				this.func.ajaxPost(url,param,res=>{
					if(res.data.flag==true){
						this.$message({
							message:'提交成功',
							type:'success',
						})
						this.submitLoading = false
					}
				})
				//提交完之后转到我的代办页面
				this.$router.push({path:'/brief'});
			},
		},
		mounted(){
			this.instanceId = sessionStorage.getItem('instanceId');
			this.id = sessionStorage.getItem('id');
			this.getCommentPages(this.instanceId);
			this.judgeActive();
		},
	}
</script>
<style >
	#comment{
		border: 1px solid #9E9E9E;
		margin-left: 20px;
		margin-top: 10px;
	}
</style>