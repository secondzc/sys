<template>
	<section>
		
        <!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" >
				<el-form-item>
					<el-input v-model="modelName" placeholder="模型名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getDetail">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--包括同意、不同意、正在审核  这三种情况-->
		<el-table :data="detail" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%">
			<el-table-column type="index" min-width="60" v-if="false">
			</el-table-column>
			<el-table-column prop="model.name" label="模型名" min-width=120 sortable>
			</el-table-column>
			<el-table-column prop="userName" label="用户名" min-width=120 sortable>
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" min-width=180 sortable>
			</el-table-column>

			<el-table-column prop="reviewNodeInstance.status" label="审签状态" min-width="150" :formatter="format" sortable>
			</el-table-column>
			<el-table-column label="操作" min-width=300>
				<template scope="scope">
					<el-button size="small" @click="toDetail(scope.$index, scope.row)" >详情</el-button> 
					<el-button size="small" @click="handleAgree(scope.$index, scope.row)" :disabled="passed(scope.row)">同意</el-button>
					<el-button type="danger" size="small" @click="handleDisagree(scope.$index, scope.row)" :disabled="passed(scope.row)">拒绝</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-pagination layout="total,sizes,prev, pager, next" @current-change="handleCurrentChange"
            @size-change="handleSizeChange"  :total="total" :page-sizes ="pageSizes" :page-size="pageSize" style="float:right;">
            </el-pagination>
        </el-col>

	</section>
</template>

<script>
	export default{

		data(){
			return {
				total: 0,
                pageSize: 10,
                pageSizes: [10,20,30],
                page: 1,
                pages: 0,
                total: 0,

				detail:[],
				listLoading:false,
				sels: [],
				modelName: '',
			}
		},
		methods: {
			toDetail(index,row){ 
                //转到详情页 
                sessionStorage.setItem('instanceId',row.reviewNodeInstance.instanceId); 
                sessionStorage.setItem('id',row.id);
                this.$router.push({path:'/checkorDetail'});
             }, 
             handleAgree(index,row){
				let url="/api/checkor/agree";
				this.func.ajaxPost(url,{id:row.id},res=>{
					if(res.data.flag==true){
						this.$message({
							message:'操作成功！'
						})
						this.getDetail();
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
						this.getDetail();
					}
				})
			},
			passed(row){
				console.log('检查是否审核过了',row);
				if(row.reviewNodeInstance.status===2){
					return false;
				}else{
					return true;
				}
			},
			getDetail(){
				this.listLoading = true;
				let url = "api/checkor/queryAllByReviewer";
				let params = {
					page:this.page,
					rows: this.pageSize,
					modelName: this.modelName,
				}
				this.func.ajaxPost(url,params,res=>{
					this.pages = res.data.pages;
                    this.total = res.data.total;
					this.detail = res.data.records;
					this.listLoading = false;
				})
			},
			handleCurrentChange(val){
                this.page = val;
                this.getInstance();
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getInstance();
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
						this.getDetail();
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
						this.getDetail();
					}
				})
			},
			format: function(row,column){
				let status = row.reviewNodeInstance.status;
				let msg = '';
				//逻辑上来说查出来的数据只有2.3.4的情况
				if(status==1){
				 msg="未进入审核状态";
				}else if(status ==2){
				 msg = "等待审核";
				}else if(status == 3){
				 msg = "已同意";
				}else if(status ==4){
				 msg ="已拒绝";
				}
				return msg;
			},

		},
		mounted() {
			this.getDetail();
		}
	}
</script>

<style scoped>
	
</style>

