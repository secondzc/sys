<template>
	<section>
		<p>{{getModel}}</p>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="模型名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="searchModel" >查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<!--<template>-->
			<!--<el-table :data="repositories" highlight-current-row v-loading="loading" style="width: 100%;">-->
				<!--<el-table-column type="index" width="60">-->
				<!--</el-table-column>-->
				<!--<el-table-column prop="name" label="姓名" width="120" sortable>-->
				<!--</el-table-column>-->
				<!--<el-table-column prop="userName" label="上传者" width="120" sortable>-->
				<!--</el-table-column>-->
				<!--<el-table-column prop="createTime" label="上传日期" width="120" sortable>-->
				<!--</el-table-column>-->
				<!--<el-table-column prop="discription" label="模型描述" width="120">-->
				<!--</el-table-column>-->
			<!--</el-table>-->
		<!--</template>-->

		<el-table
				:data="repositories"
				border
				:default-sort = "{prop: 'createTime',prop:'name', order: 'descending'}"
				style="width: 100%">
			<el-table-column
					label="姓名"
					prop="name"
					width="180" sortable>
				<template scope="scope">
					<div style="display: inline-block">
						<img src="./../../assets/test1.png" style="width: 60px;height: 60px;"/>
					</div>
					<div style="display: inline-block">
						<el-popover trigger="hover" placement="top">
							<p>姓名: {{ scope.row.name }}</p>
							<p>住址: {{ scope.row.address }}</p>
							<div slot="reference" class="name-wrapper">
								<el-tag>{{ scope.row.name }}</el-tag>
							</div>
						</el-popover>
					</div>
				</template>
			</el-table-column>
			<el-table-column
					label="日期"
					prop="createTime"
					width="180" sortable>
				<template scope="scope">
					<el-icon name="time"></el-icon>
					<span style="margin-left: 10px">{{ scope.row.createTime }}</span>
				</template>
			</el-table-column>

			<!--<el-table-column-->
					<!--prop="tag"-->
					<!--label="标签"-->
					<!--width="100"-->
					<!--:filters="[{ text: '家', value: '家' }, { text: '公司', value: '公司' }]"-->
					<!--:filter-method="filterTag"-->
					<!--filter-placement="bottom-end">-->
				<!--<template scope="scope">-->
					<!--<el-tag-->
							<!--:type="scope.row.tag === '家' ? 'primary' : 'success'"-->
							<!--close-transition>{{scope.row.tag}}</el-tag>-->
				<!--</template>-->
			<!--</el-table-column>-->

			<el-table-column label="操作">
				<template scope="scope">
					<el-button
							size="small"
							@click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button
							size="small"
							type="danger"
							@click="handleDelete(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

	</section>
</template>
<script>
    import { mapState,mapGetters} from 'vuex';
	//import NProgress from 'nprogress'
	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				loading: false,
                repositories: [
				]
			}
		},
        computed:{
            ...mapState({
                a:state =>state.a
            }),
            ...mapGetters(['amsg']),
			getModel: function () {
				let para = {
					name: this.filters.name
				};
				var _this = this;
				if(_this.amsg != null && _this.amsg != ""){
					var url = '/api/model/list?parent_id='+ _this.amsg
				}else{
					_this.amsg = 0;
					var url = '/api/model/list?parent_id='+ _this.amsg
				}
				_this.$http.post(url)
					.then(function (response) {
						let searchModel = response.data.repositories.filter(model =>{
							for(var i =0 ;i <response.data.repositories.length; i++){
								if(para.name && response.data.repositories[i].name.indexOf(para.name) == -1 ){
									return false
								}
								else{
									return true
								}
							}
						})
						_this.repositories = searchModel;
						//   _this.repositories = response.data.repositories;
					})
					.catch(function (error) {
						console.log(error);
					});
			}

        },
		methods: {
			//获取用户列表
            searchModel () {
                let para = {
                    name: this.filters.name
                };
                var _this = this;
                if(_this.amsg != null && _this.amsg != ""){
                    var url = '/api/model/list?parent_id='+ _this.amsg
                }else{
                    _this.amsg = 0;
                    var url = '/api/model/list?parent_id='+ _this.amsg
                }
                _this.$http.post(url)
                    .then(function (response) {
                        var searchModel = response.data.repositories.filter(model =>{
                            if(para.name && model.name.indexOf(para.name) == -1 ){
                                return false
                            }
                            else{
                                return true
                            }
                        })
                        _this.repositories = searchModel;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
			},
            handleEdit(index, row) {
                console.log(index, row);
            },
            handleDelete(index, row) {
                console.log(index, row);
            }


		},
		mounted() {
			this.searchModel();
		}
	};

</script>

<style scoped>

</style>