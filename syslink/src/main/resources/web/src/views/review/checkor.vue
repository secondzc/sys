<template>
	<section>

        <!--工具条-->
		<el-col :span="24"  style="padding-bottom: 0px;">
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

		<!--列表-->
		<el-table :data="detail" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width:100%;">
			<el-table-column type="index" v-if="false" >
			</el-table-column>
			<el-table-column prop="model.name" label="模型名" min-width=120 sortable >
				<template slot-scope="scope">
                    <span style="min-width:120px" v-bind:title="scope.row.model.name"  class="spanEllipsis">{{ scope.row.model.name }}</span>
                </template>
			</el-table-column>
			<el-table-column prop="userName" label="用户名" min-width=120 sortable>
				<template slot-scope="scope">
                     <span style="min-width:120px" v-bind:title="scope.row.userName"  class="spanEllipsis">{{ scope.row.userName }}</span>
               </template>
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" min-width=120 sortable>
			</el-table-column>
			<el-table-column label="操作" min-width=300>
				<template scope="scope">
					<el-button size="small" @click="toDetail(scope.$index, scope.row)">详情</el-button> 
					<el-button size="small" @click="handleAgree(scope.$index, scope.row)">同意</el-button>
					<el-button type="danger" size="small" @click="handleDisagree(scope.$index, scope.row)">拒绝</el-button>
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
				path: '/views/review/checkor',
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

				console.log("AAAAAAA");
                //转到详情页 
                sessionStorage.setItem('instanceId',row.reviewNodeInstance.instanceId); 
                sessionStorage.setItem('id',row.id);
                this.$router.push({path:'/checkorDetail'});
                //将包id放入vuex中
                //this.$store.dispatch('sendModelId', row.model.id);
             }, 
			// details(index,row){ 
   //            //转到详情页 
   //            sessionStorage.setItem('instanceId',row.reviewNodeInstance.instanceId);
   //            sessionStorage.setItem('id',row.id); 
   //            this.$router.push({path:'/checkorDetail'}); 
   //          }, 
			getDetail(){
				this.listLoading = true;
				let url = "api/checkor/queryByReviewer";
				let params = {
					page:this.page,
					rows: this.pageSize,
					modelName: this.modelName,
				}
				this.func.ajaxPost(url,params,res=>{
					this.pages = res.data.pages;
                    this.total = res.data.total;
					this.detail = res.data.records;
					console.log('返回的结果');
					console.log(this.detail);
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

		},
		mounted() {
			this.page = this.func.freshPage(this.path,this.page);
			this.getDetail();
		}
	}
</script>

<style scoped>
	
</style>

