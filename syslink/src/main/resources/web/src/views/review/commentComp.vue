<template>
	<div>
		<div style="width:40%" id="comment">
			<h3>协同</h3>
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
			<el-button type="primary" @click="submit" v-loading="submitLoading">提交</el-button>
			</el-form-item>
		    </el-form>
		</div>
		<div style="width:60%">
			<h3>处理人意见区</h3>
			<div style="border: 1px solid #9E9E9E;margin-left:20px" v-for="(commentPage,index) in commentPages">
				<div style="width:20%;float:left">审核者：{{commentPage.checkorName}}</div>
				<div style="width:40%;float:left">审核结果：{{commentPage.showStatus}}</div>
				<div style="width:40%;float:left">审核时间：{{commentPage.lastUpdateTime}}</div>

				<div>
				处理人意见：{{commentPage.comment}}
			    </div>
			</div>
		</div>
	</div>
</template>

<script >
	export default{
		data(){
			return {
				instanceId:0,
			    commentPages:[],
			    isAgree:"",
			    comment:'',
			    id:0,
			    submitLoading:false,
			}
		},
		methods:{
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
			},
		},
		mounted(){
			this.instanceId = sessionStorage.getItem('instanceId');
			this.id = sessionStorage.getItem('id');
			this.getCommentPages(this.instanceId);
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