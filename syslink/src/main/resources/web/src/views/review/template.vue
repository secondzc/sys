<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="模板名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTemplates">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="templates" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="50">
			</el-table-column>
			<el-table-column prop="templateName" label="模板名" width="120" >
			</el-table-column>
			<el-table-column prop="alreadyConfig" label="是否配置" width="120" :formatter="format">
			</el-table-column>
			<el-table-column prop="description" label="模板描述" width="120" >
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="120" >
			</el-table-column>
			<el-table-column prop="lastUpdateTime" label="最后修改时间" min-width="180" >
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index,scope.row)">配置</el-button>
					<el-button type="danger" size="small" @click="batchRemove(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="total,sizes,prev, pager, next" @current-change="handleCurrentChange"
			@size-change="handleSizeChange"  :total="total" :page-sizes ="pageSizes" :page-size="pageSize" style="float:right;">
			</el-pagination>
		</el-col>

	

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addTemplate" label-width="80px" >
				<el-form-item label="模板名" prop="templateName">
					<el-input v-model="addTemplate.templateName" ></el-input>
				</el-form-item>
				<el-form-item label="模板描述" prop="description">
				     <el-input v-model="addTemplate.description"></el-input>
				</el-form-item>
				<el-form-item label="是否为默认模板">
					<el-radio-group v-model="addTemplate.defaultTemplate">
						<el-radio class="radio" :label="true">是</el-radio>
						<el-radio class="radio" :label="false">否</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>

	</section>
</template>

<script>
	export default{
		data() {
			return{
				/*	pageSize 实际的每页显示数
				pageSizes  可选的每页显示数
				page  当前页数
				total  返回的总记录数
				pages  返回的总页数*/
				filters: {
					name: [],
				},
				addFormVisible: false,
				page: 1,
				pageSize: 10,
				pageSizes: [10,20,30],
				listLoading: false,
				templates: [],
				pages: 0,
				total: 0,
				addTemplate: {
					templateName: '',
					description: '',
					defaultTemplate: false,
				},
				sels: [],
				addLoading: false,
				
			}
		},
		methods: {
			format: function(row,column){
				return row.alreadyConfig == false ? '否' : '是';
			},
			getTemplates() {
				let param = {
					page: this.page,
					rows: this.pageSize,
					templateName: this.filters.name,
				};
				this.listLoading = true;
				let url = '/api/reviewFlowTemplate/queryReviewFlowTemplateByName';
				this.func.ajaxPost(url,param,res =>{
					this.templates = res.data.records;
					this.pages = res.data.pages;
					this.total = res.data.total;
					this.listLoading = false;
				});
			},
			handleAdd() {
				this.addFormVisible = true;
				this.addTemplate = {
					templateName: '',
					description: '',
					defaultTemplate: false,
				}
			},
			selsChange(sels) {
				this.sels = sels;
			},
			handleEdit: function(index,row){
				//let templateId = row.templateId;
				//sessionStorage.setItem('templateId',this.sels[0].templateId);
				sessionStorage.setItem('templateId',row.templateId);
				this.$router.push({path:'/setCheckors'});
			},
			
			//批量删除
			batchRemove: function() {
				let ids = this.sels.map(item =>item.templateId).join(",").toString();
			    console.log(ids);
				this.$confirm('确认删除选中记录吗？','提示',{
					type:'warning'
				}).then(()=>{
					this.listLoading = true;
					let params = {templateIds: ids};
					let url = '/api/reviewFlowTemplate/deleteByTemplateId';
					this.listLoading = false;
					this.func.ajaxPost(url,params,res =>{
						if(res.data.flag == true){
							this.$message({
							message: '删除成功',
							type: 'success'
						    });
						    this.listLoading = false;
						    this.getTemplates();
						}
					})
				})
			},
			handleCurrentChange(val){
				this.page = val;
				this.getTemplates();
			},
			handleSizeChange(val) {
				this.pageSize = val;
				this.getTemplates();
			},
			addSubmit: function(){
				this.$confirm('确认提交吗？','提示',{}).then(()=>{
					this.addLoading = true;
					let param = Object.assign({},this.addTemplate);
					let url = '/api/reviewFlowTemplate/addReviewFlowTemplate';
					this.func.ajaxPost(url,param,res =>{
						if(res.data.flag == true){
							this.addLoading = false;
							this.addFormVisible = false;
							this.$message({
							message: '提交成功！',
							type: 'success',
						    })
						    this.getTemplates();
						}
					})
				})
			},
		},
		mounted() {
			this.getTemplates();
		}
	}
</script>

<style scoped>

</style>