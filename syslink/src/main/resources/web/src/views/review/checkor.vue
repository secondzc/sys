<template>
	<section>
		<!--列表-->
		<el-table :data="detail" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 55%">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60" >
			</el-table-column>
			<el-table-column prop="model.name" label="模型名" width="120" >
			</el-table-column>
			<el-table-column prop="template.templateName" label="模板名" width="100" >
			</el-table-column>
			<el-table-column prop="instanceName" label="审签实例名" width="100" >
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleAgree(scope.$index, scope.row)">同意</el-button>
					<el-button type="danger" size="small" @click="handleDisagree(scope.$index, scope.row)">拒绝</el-button>
				</template>
			</el-table-column>
		</el-table>
	</section>
</template>

<script>
	export default{
		data(){
			return {
				detail:[],
				listLoading:false,
				page: 1,
				rows: 10,
				sels: [],
			}
		},
		methods: {
			getDetail(){
				this.listLoading = true;
				let url = "api/checkor/queryByReviewer";
				let params = {
					page:this.page,
					rows: this.rows,
				}
				this.func.ajaxPost(url,params,res=>{
					this.detail = res.data.rows;
					this.listLoading = false;
				})
			},
			selsChange(sels){
				this.sels = sels;
			},
			handleAgree(index,row){
				let url="/api/checkor/agree";
				this.func.ajaxPost(url,{id:row.id},res=>{
					if(res.data.flag==true){
						this.$message({
							message:'操作成功！'
						})
					}
				})
			},
			handleDisagree(index,row){
				let url="/api/checkor/disagree";
				this.func.ajaxPost(url,{id:row.id},res=>{
					if(res.data.flag==true){
						this.$message({
							message:'操作成功！'
						})
					}
				})
			},

		},
		mounted() {
			this.getDetail();
		}
	}
</script>

<style scoped>
	
</style>

