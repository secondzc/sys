<template>
	<section>
		<!--工具条-->
		<el-col :span="24" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters" @submit.native.prevent>
				<el-form-item>
					<el-input v-model="filters.name" placeholder="模型名" @keyup.enter.native="getInstance"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" size="small" v-on:click="getInstance">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>
        <hr/>

		<!--列表-->
		<el-table :data="instances" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="50">
			</el-table-column>

            <el-table-column prop="instanceName" label="模型名" min-width="120" sortable>
            	<template slot-scope="scope">
                    <span style="min-width:120px" v-bind:title="scope.row.instanceName"  class="spanEllipsis">{{ scope.row.instanceName }}</span>
                 </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" min-width="120" sortable>
            </el-table-column>
			<el-table-column prop="status" label="当前状态" min-width="120" :formatter="format" sortable>
			</el-table-column>

			<el-table-column label="操作" width="300" >
                <template scope="scope">
                    <el-button size="small" @click="detail(scope.$index,scope.row)" >查看详情</el-button>
                    <el-button size="small" @click="cancel(scope.$index,scope.row)" :disabled="scope.row.status!=1">撤销流程</el-button>
                    <el-button type="danger" size="small" @click="remove(scope.row)">删除记录</el-button>
                </template>
            </el-table-column>
		</el-table>

        <!--工具条-->
        <el-col :span="24" style="margin-top: 10px;">
            <el-button type="danger" @click="batchRemove" size="small" :disabled="this.sels.length===0">批量删除</el-button>
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
                path:'/views/review/mySubmit',
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
                packageId:-1,
                redirectFlag:false,
    		}
    	},
        watch: {
            redirectFlag() {
                if(this.redirectFlag){
                    this.$store.dispatch('sendModelId', this.packageId);
                    console.log('已经获得packageid'+this.packageId);
                    this.$router.push({path:'/mySubmitDetail'});   
                }

            },
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
                //这里是brief的组件，只查询提交的审签中的流程，即status为1 
    			this.listLoading = true;
    			let params = {
    				flowInstanceName: this.filters.name,
    				page: this.page,
    				rows: this.pageSize,
                    flowInstanceStatus: 1, 
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
                this.$store.commit('setCurPage',{'path':this.path,'page':this.page});

                sessionStorage.setItem('instanceId',row.instanceId);
                //由instanceId得到modelId，存入vuex供modelTree使用
                var url = 'api/reviewFlowInstance/getModelIdByInstanceId';
                this.func.ajaxPost(url,{instanceId:row.instanceId},res=>{
                    if(res.data.flag===true){
                        this.packageId=res.data.modelId;
                        console.log('提交成功...');
                        this.redirectFlag = true;
                    }
                });
                //当异步获取的结果返回之后才能存入packageId            
    		},
    		selsChange(sels){
    			this.sels = sels;
    		},
            //批量删除
            batchRemove: function() {
                let ids = this.sels.map(item =>item.instanceId).join(",").toString();
                this.$confirm('确认删除选中记录吗？','提示',{
                    type:'warning',
                    closeOnClickModal:false
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
                this.$confirm('确认删除选中记录吗？','提示',{
                	  closeOnClickModal:false
                }).then(()=>{
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
            this.page = this.func.freshPage(this.path,this.page);
    		this.getInstance();
    	}
    }
</script>

<style scoped>
	
</style>