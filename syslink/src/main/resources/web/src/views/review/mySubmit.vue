<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="模板名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.status" placeholder="状态"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getInstance">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="instances" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="50">
			</el-table-column>
			<el-table-column prop="instanceName" label="流程名" width="120" >
			</el-table-column>
			<el-table-column prop="description" label="流程描述" width="120" >
			</el-table-column>
			<el-table-column prop="status" label="当前状态" width="120" :formatter="format">
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" width="120" >
			</el-table-column>
			<el-table-column prop="lastUpdateTime" label="最后修改时间" min-width="180" >
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small" @click="detail(scope.$index,scope.row)">查看详情</el-button>
					<el-button type="danger" size="small" @click="remove(scope.$index,scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
	</section>
</template>

<script>
    export default{
    	data(){
    		return {
    			instances: [],
    			listLoading: false,
    			filters: {
    				name: '',
    				status: '',
    			},
    			sels: [],
    			page: 1,
    			rows: 10,
    		}
    	},
    	methods: {
    		getInstance(){
    			this.listLoading = true;
    			let params = {
    				flowInstanceName: this.filters.name,
    				flowInstanceStatus: this.filters.status,
    				page: this.page,
    				rows: this.rows,
    			}
    			let url="/api/reviewFlowInstance/queryByNameAndStatus";
    			this.func.ajaxPost(url,params,res=>{
    				this.instances = res.data.rows;
    				this.listLoading = false;
    			})
    		},
    		format: function(row,column){
    			let msg = "";
    			if(row.status == 1){
    				msg = '审签中';
    			}else if(row.status == 2){
    				msg = '被拒绝';
    			}else if(row.status == 3){
    				msg = '完成审签';
    			}
    			return msg;
    		},
    		detail(index,row){
				sessionStorage.setItem('instanceId',row.instanceId);
				this.$router.push({path:'/mySubmitDetail'});
    		},
    		remove(row){

    		},
    		selsChange(sels){
    			this.sels = sels;
    		}
    	},
    	mounted() {
    		this.getInstance();
    	}
    }
</script>

<style scoped>
	
</style>