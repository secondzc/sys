<template>
	<section>
	     <!--这是注释-->
		<!--工具条-->
		<el-col :span="24"  style="padding-bottom: 0px;margin-top: 10px;">
			<el-form :inline="true" :model="filters" @submit.native.prevent>
				<el-form-item>
					<el-input v-model="filters.name" placeholder="模板名" @keyup.enter.native="getTemplates(false)"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" size="small"  v-on:click="getTemplates(false)" >查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" size="small"  @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<hr/>

		<!--列表-->
		<el-table :data="templates" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="50">
			</el-table-column>
			<el-table-column prop="templateName" label="模板名" width="120" >
				<template slot-scope="scope">               
                    <span style="min-width:120px" v-bind:title="scope.row.templateName"  class="spanEllipsis">{{ scope.row.templateName }}</span>
                </template>
			</el-table-column>
			<el-table-column prop="alreadyConfig" label="是否配置" width="120" :formatter="format">
			</el-table-column>
			<el-table-column prop="description" label="模板描述" width="120" >
				<template slot-scope="scope">               
                    <span style="min-width:120px" v-bind:title="scope.row.description"  class="spanEllipsis">{{ scope.row.description }}</span>
                 </template>
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="120" >
			</el-table-column>
			<el-table-column prop="defaultTemplate" label="是否为默认模板" width="120" :formatter="formatDefault">
			</el-table-column>
			<el-table-column prop="lastUpdateTime" label="最后修改时间" min-width="120" >
			</el-table-column>
			<el-table-column label="操作" width="300">
				<template scope="scope">
				    <el-button size="small" @click="setDefault(scope.$index,scope.row)">设为默认</el-button>
					<el-button size="small" @click="handleEdit(scope.row)">配置</el-button>
					<el-button type="danger" size="small" @click="remove(scope.$index,scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" style="margin-top: 10px;">
			<el-button type="danger" @click="batchRemove" size="small"  :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination layout="total,sizes,prev, pager, next" @current-change="handleCurrentChange"
			@size-change="handleSizeChange"  :total="total" :page-sizes ="pageSizes" :page-size="pageSize" style="float:right;">
			</el-pagination>
		</el-col>

	

		<!--新增界面-->
		<el-dialog title="新增" v-model="addFormVisible"  :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addTemplate" label-width="120px" :rules="addFormRules" ref="addForm">
				<el-form-item label="模板名" prop="templateName" >
					<el-input v-model="addTemplate.templateName"></el-input>
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
			
		var validateName = (rule, value, callback) => {
        let re = new RegExp("^[ a-zA-Z0-9_\u4e00-\u9fa5]+$");
        console.log(value);

        if(!value)
        {
            callback(new Error('请输入模板名'));
        }
        else
        {
          if (re.test(value))
          {
          	
          	if(value.length>20)
          	{
          		 callback(new Error('模板名不得超过20个字符'));
          	}
          	else
          	{
          		
          		if(value.trim())
          		{
         
                   callback();
             
          		}
          		else
          		{
          			callback(new Error('不能全为空格'));
          		}         	  
          	}                 
          } 
          else
          {
              callback(new Error('只允许输入中文、英文、数字、下划线及空格'));
          }
        }
      };
			return{
				path:'/views/review/template',
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
				addFormRules: {
                templateName:[
                 { required:true,validator:validateName,trigger:'blur'}
                ],
            
                  },
				
				
				
				
				
				
			}
		},
		methods: {
			format: function(row,column){
				return row.alreadyConfig == false ? '否' : '是';
			},
			formatDefault: function(row,column){
				return row.defaultTemplate == false ? '否' : '是';
			},
			//needToSetPerson表示刷新后是否需要跳转到人员编辑页面
			getTemplates(needToSetPerson) {
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
					if(needToSetPerson){
						let item = this.templates[this.templates.length-1];
						this.handleEdit(item);
					}
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
			handleEdit: function(row){
				this.$store.commit('setCurPage',{'path':this.path,'page':this.page});
				
				//let templateId = row.templateId;
				//sessionStorage.setItem('templateId',this.sels[0].templateId);
				sessionStorage.setItem('templateId',row.templateId);
				this.$router.push({path:'setCheckors'});
			},
			
			//批量删除
			batchRemove: function() {
				let ids = this.sels.map(item =>item.templateId).join(",").toString();
				this.$confirm('确认删除选中记录吗？','提示',{
					type:'warning',
					 closeOnClickModal:false
				}).then(()=>{
					this.listLoading = true;
					let params = {templateIds: ids};
					let url = '/api/reviewFlowTemplate/deleteByTemplateId';
					this.func.ajaxPost(url,params,res =>{
						if(res.data.flag == true){
							this.$message({
							message: '删除成功',
							type: 'success'
						    });
						    this.listLoading = false;
						    this.getTemplates(false);
						}
					})
				})
			},
			handleCurrentChange(val){
				this.page = val;
				this.getTemplates(false);
			},
			handleSizeChange(val) {
				this.pageSize = val;
				this.getTemplates(false);
			},
			addSubmit: function(){
				
				this.$refs.addForm.validate((valid) => {
				if(value)
					{
						let param = Object.assign({},this.addTemplate);
			       if(this.isRepeatedTemplate(param)){
					this.$message({
						message:'模板名不能重复',
					})
				  }else{
					this.addLoading = true;
			        let url = '/api/reviewFlowTemplate/addReviewFlowTemplate';
			        param.assure = "no";
			        this.func.ajaxPost(url,param,res =>{
			        	if(res.data.code ==200){
			        		if(!res.data.changeDefault){
			        			this.addLoading = false;
			        			this.addFormVisible = false;
			        			this.$message({
			        				message: '提交成功！',
			        				type: 'success',
			        			})
			        			if(this.addTemplate.defaultTemplate){
			        				this.getTemplates(true);
			        			}else{
			        				this.getTemplates(false);
			        			}
			        	
			        		}else{
			        			this.$confirm('已经存在默认模板，要替换吗？','提示',{
			        				 type:'info',
			        				 closeOnClickModal:false
			        			}).then(()=>{
			        				param.assure = "yes";
			        				this.func.ajaxPost(url,param,res =>{
			        					if(res.data.code === 200 && !res.data.changeDefault){
			        						this.addLoading = false;
			        						this.addFormVisible = false;
			        						this.$message({
			        							message: '提交成功！',
			        							type: 'success',
			        						})
                                if(this.addTemplate.defaultTemplate){
			        				this.getTemplates(true);
			        			}else{
			        				this.getTemplates(false);
			        			}			        									        					}
			        				})
			        			})
			        			.catch(()=>{
			        				this.addLoading = false;
			        			})
			        		}

			        	}
			        })
				}
					}
				
				   });
			},
			isRepeatedTemplate(param){
				var flag=false;
				for(var name of this.templates){
					if(name.templateName === param.templateName){
						flag=true;
						break;
					}
				}
				return flag;
			},
			remove: function(index,row){
				this.$confirm('确认删除选中记录吗？','提示',{
					type:'warning',
					 closeOnClickModal:false
				}).then(()=>{
					this.listLoading = true;
					let params = {templateIds: row.templateId};
					let url = '/api/reviewFlowTemplate/deleteByTemplateId';
					this.func.ajaxPost(url,params,res =>{
						if(res.data.flag == true){
							this.$message({
							message: '删除成功',
							type: 'success'
						    });
						    this.listLoading = false;
						    this.getTemplates(false);
						}
					})
				});
			},
			setDefault: function(index,row){
				if(!row.alreadyConfig){
					this.$message({
						message:'未配置人员，不能设为默认模板',
					})
				}else{
				let param = {templateId: row.templateId};
				let url = '/api/reviewFlowTemplate/setDefault';
				param.assure = "no";
				this.func.ajaxPost(url,param,res =>{
					if(res.data.code ===200){
						if(!res.data.reminder){
							this.$message({
							message: '设置成功',
							type: 'success'
						    });
						    this.listLoading = false;
						    this.getTemplates(false);
						}else{
							this.$confirm('已经存在默认模板，要替换吗？','提示',{
								type:'info',
					            closeOnClickModal:false
							}).then(()=>{
								param.assure = "yes";
								this.func.ajaxPost(url,param,res =>{
									if(res.data.code ===200){
										this.$message({
											message: '设置成功',
											type: 'success'
										});
										this.listLoading = false;
										this.getTemplates(false);
									}
								})
							})
						}
					}
				})}
			},
		},
		mounted() {
			this.page = this.func.freshPage(this.path,this.page);
			this.getTemplates(false);
		}
	}
</script>

<style scoped>

</style>