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
					<el-button type="primary" v-on:click="getModel">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="repositories" highlight-current-row v-loading="loading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="name" label="姓名" width="120" sortable>
				</el-table-column>
				<el-table-column prop="userName" label="上传者" width="120" sortable>
				</el-table-column>
				<el-table-column prop="createTime" label="上传日期" width="120" sortable>
				</el-table-column>
				<el-table-column prop="discription" label="模型描述" width="120">
				</el-table-column>
			</el-table>
		</template>

	</section>
</template>
<script>
	import { getUserList } from '../../api/api';
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
					_this.amsg = null;
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
            getModel: function () {
                let para = {
                    name: this.filters.name
                };
                var _this = this;
                if(_this.amsg != null && _this.amsg != ""){
                    var url = '/api/model/list?parent_id='+ _this.amsg
                }else{
                    _this.amsg = null;
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
		mounted() {
			this.getModel();
		}
	};

</script>

<style scoped>

</style>