<template>
	<section>
		
        <!--工具条-->
		<el-col :span="24" style="padding-bottom: 0px;margin-top: 10px;">
			<el-form :inline="true"  @submit.native.prevent>
				<el-form-item>
					<el-input v-model="modelName" placeholder="模型名" @keyup.enter.native="getDetail"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" size="small" v-on:click="getDetail">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<hr/>

		<!--包括同意、不同意、正在审核  这三种情况-->
		<el-table :data="detail" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%">
			<el-table-column type="index" min-width="60" v-if="false">
			</el-table-column>
			<el-table-column prop="model.name" label="模型名" min-width=120 sortable>
				<template slot-scope="scope">
                    <span style="min-width:120px" v-bind:title="scope.row.model.name"  class="spanEllipsis">{{ scope.row.model.name }}</span>
                </template>
			</el-table-column>
			<el-table-column prop="userName" label="用户名" min-width=120 sortable>
				<template slot-scope="scope">
                    <span style="min-width:120px" v-bind:title="scope.row.userName"  class="spanEllipsis">{{ scope.row.userName }}</span>
                </template>
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
        <el-col :span="24" style="margin-top: 10px;">
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
				path: '/views/review1/checkorAll',
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
			    this.$store.commit('setCurPage',{'path':this.path,'page':this.page});
			    console.log('curPage',this.$store.getters.curPage);
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
				//console.log('检查是否审核过了',row);
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
                this.getDetail();
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getDetail();
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
			this.page = this.func.freshPage(this.path,this.page);
			console.log('page',this.page);
			this.getDetail();
		}
	}
</script>

<style scoped>
	
</style>

