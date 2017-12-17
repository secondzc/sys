<template>

	<section>
        
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            
			<el-form :inline="true" :model="filters">
<!--                 <el-radio-group v-model="tab" style="margin-bottom: 30px;">
        <el-radio-button label="cancel">撤销流程...</el-radio-button>
        </el-radio-group> -->

				<el-form-item>
					<el-input v-model="filters.name" placeholder="模型名"></el-input>
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
			<el-table-column prop="createTime" label="创建时间" width="180" >
			</el-table-column>
			<el-table-column prop="lastUpdateTime" label="最后修改时间" width="180" >
			</el-table-column>
			<el-table-column label="操作" width="300" >
				<template scope="scope">
					<el-button size="small" @click="detail(scope.$index,scope.row)" :disabled="scope.row.status==4">查看详情</el-button>
                    <el-button size="small" @click="cancel(scope.$index,scope.row)" :disabled="scope.row.status==4">撤销流程</el-button>
					<el-button type="danger" size="small" @click="remove(scope.row)">删除记录</el-button>
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

    			instances: [],
    			listLoading: false,
    			filters: {
    				name: '',
    			},
    			sels: [],
    			page: 1,
    			rows: 10,
                tab: 'crate',
    		}
    	},
    	methods: {
            cancel(index,row){
                var url='/api/reviewFlowInstance/cancel';
                this.listLoading = true;
                this.func.ajaxPost(url,{instanceId:row.instanceId},res=>{
                    if(res.data.flag===true){
                        this.listLoading = false;
                        this.$message({
                            type:'success',
                            message:'撤销成功',
                        });
                        this.getInstance();
                    };
                });
            },
    		getInstance(){
                //查询的除了审签中的之外所有状态的视图
                //这时flowInstanceStatus为空，查询所有的
    			this.listLoading = true;
    			let params = {
    				flowInstanceName: this.filters.name,
    				page: this.page,
    				rows: this.pageSize,
                    flowInstanceStatus: '2,3,4',
    			}
    			let url="/api/reviewFlowInstance/queryByNameAndStatus";
    			this.func.ajaxPost(url,params,res=>{
                    this.pages = res.data.pages;
                    this.total = res.data.total;
    				this.instances = res.data.records;
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
    		format: function(row,column){
    			let msg = "";
    			if(row.status == 1){
    				msg = '审签中';
    			}else if(row.status == 2){
    				msg = '被拒绝';
    			}else if(row.status == 3){
    				msg = '完成审签';
    			}else if(row.status == 4){
                    msg = '已撤销';
                }
    			return msg;
    		},
    		detail(index,row){
				sessionStorage.setItem('instanceId',row.instanceId);
				this.$router.push({path:'/mySubmitDetail'});
    		},
    		selsChange(sels){
    			this.sels = sels;
    		},
            //批量删除
            batchRemove: function() {
                let ids = this.sels.map(item =>item.instanceId).join(",").toString();
                this.$confirm('确认删除选中记录吗？','提示',{
                    type:'warning'
                }).then(()=>{
                    this.listLoading = true;
                    let params = {instanceIds: ids};
                    let url = 'api/reviewFlowInstance/deleteByInstanceId';
                    this.func.ajaxPost(url,params,res =>{
                        if(res.data.flag == true){
                            this.$message({
                            message: '删除成功',
                            type: 'success'
                            });
                            this.listLoading = false;
                            this.getInstance();
                        }
                    })
                })
            },
            remove: function(row){
                this.$confirm('确认删除选中记录吗？','提示',{}).then(()=>{
                    this.listLoading = true;
                    let params = {instanceIds: row.instanceId};
                    let url = 'api/reviewFlowInstance/deleteByInstanceId';
                    this.func.ajaxPost(url,params,res =>{
                        if(res.data.flag == true){
                            this.$message({
                            message: '删除成功',
                            type: 'success'
                            });
                            this.listLoading = false;
                            this.getInstance();
                        }
                    })
                });
            },
    	},
    	mounted() {
    		this.getInstance();
    	}
    }
</script>

<style scoped>
	
</style>