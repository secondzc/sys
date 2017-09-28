<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true">
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitAll">完成</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="cancelAll">取消配置</el-button>
				</el-form-item>
			</el-form>
		</el-col>

        <!--列表-->
		<el-table :data="items" highlight-current-row  @selection-change="selsChange" style="width: 80%;">
			<el-table-column type="selection" width="50">
			</el-table-column>
			<el-table-column type="index" label="节点次序" width="100">
			</el-table-column>
			<el-table-column prop="reviewNodeName" label="节点名字" width="120" >
			</el-table-column>
			<el-table-column prop="description" label="节点描述" width="120" >
			</el-table-column>
			<el-table-column prop="userName" label="审核者名字" width="120" >
			</el-table-column>
			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
					<el-button type="danger" size="small" @click="remove(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--新增界面-->
		<el-dialog title="配置人员" v-model="addItemsDialogVisible" :close-on-click-modal="false">
			<el-form :model="addItemsDialog" label-width="80px" >
				<el-form-item label="节点名字" prop="reviewNodeName">
					<el-input v-model="addItemsDialog.reviewNodeName" ></el-input>
				</el-form-item>
				<el-form-item label="节点描述" prop="description">
				     <el-input v-model="addItemsDialog.description"></el-input>
				</el-form-item>
				<el-form-item label="审核者名字" prop="userName">
				     <el-input v-model="addItemsDialog.userName" :disabled="true"></el-input>
				     <el-button type="primary" @click.native="chooseName">选择审核者</el-button>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addItemsDialogVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitOne">确定</el-button>
			</div>
		</el-dialog>

		<!--修改界面-->
		<el-dialog title="配置人员" v-model="editItemsDialogVisible" :close-on-click-modal="false">
			<el-form :model="editItemsDialog" label-width="80px" >
				<el-form-item label="节点名字" prop="reviewNodeName">
					<el-input v-model="editItemsDialog.reviewNodeName" ></el-input>
				</el-form-item>
				<el-form-item label="节点描述" prop="description">
				     <el-input v-model="editItemsDialog.description"></el-input>
				</el-form-item>
				<el-form-item label="审核者名字" prop="userName">
				     <el-input v-model="editItemsDialog.userName" :disabled="true"></el-input>
				     <el-button type="primary" @click.native="chooseName">选择审核者</el-button>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editItemsDialogVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitEdit">确定</el-button>
			</div>
		</el-dialog>

		<!--选择人员-->
		<el-dialog title="选择人员" v-model="chooseNameVisible" :close-on-click-modal="false">
			<template>
			  <el-transfer
			    filterable
			    filter-placeholder="请输入审核者名字"
			    v-model="value2"
			    :data="data2">
			  </el-transfer>
			  <el-button @click.native="submitName">确认</el-button>
			</template>
		</el-dialog>
	</section>
</template>

<script>
	export default {
		data(){
			return {
			  tempateId: 0,
			  //items的形式为 "{reviewNodeName: ,description: ,userName: },{}" 		  
			  items:[],
			  addItemsDialog: {
			  	reviewNodeName:'',
			  	description: '',
			  	userName: '',
			  },
			  editItemsDialog: {
			  	reviewNodeName:'',
			  	description: '',
			  	userName: '',
			  },
			  chooseCheckorDialog: {
			  	userName: '',
			  },
			  sels: [],
			  addItemsDialogVisible: false,
			  editItemsDialogVisible: false,
			  sequence: 0,
			  chooseNameVisible: false,
			  userNames: ['zhang','hhhh','dihd'],
			  value2: [],
			  data2: [],
			  userName: '',
			}
		},
		created(){
			this.templateId = sessionStorage.getItem('templateId');
		},
		methods: {
			submitOne(){
				let str=",";
				let index1=this.addItemsDialog.reviewNodeName.indexOf(str);
				let index2=str.indexOf(this.addItemsDialog.description);
				let index3 = str.indexOf(this.addItemsDialog.userName);
				if(index1>0 ||index2>0 ||index3>0){
					this.$message({
						message:'配置中不能含有英文逗号，请重新输入',
					})
				}else{
					this.items.push(this.addItemsDialog);
					this.addItemsDialogVisible = false;		
				}

			},
			submitAll(){
				this.$confirm('确认提交吗？','提示',{}).then(()=>{
					let url = '/api/reviewNode/batchAdd';
					let nodeName = [];
					let des = [];
					let usern = [];
					for(var i=0;i<this.items.length;i++){
						nodeName.push(this.items[i].reviewNodeName);
						des.push(this.items[i].description);
						usern.push(this.items[i].userName);
					}
					this.func.ajaxPost(url,{reviewNodeName: nodeName.join(','),
						description: des.join(','),
						userName: usern.join(','),
						templateId:this.templateId},res =>{
						if(res.data.flag == true){
							this.$message({
							message: '提交成功！',
							type: 'success',
						    })
						    this.redirect();
						}
					})
				})
			},
			redirect() {
				sessionStorage.removeItem('templateId');
				this.$router.push({path:'/template'});
			},
			cancelAll() {
				this.redirect();
			},
			selsChange(sels){
				this.sels = sels;
			},
			handleEdit(row){
				this.editItemsDialogVisible = true;
				this.sequence = this.items.indexOf(row);
				this.editItemsDialog = Object.assign({},row);
			},
			handleAdd() {
				this.addItemsDialogVisible = true;
				this.addItemsDialog = {
				  	reviewNodeName:'',
				  	description: '',
				  	userName: '',
			    }
			},
			//修改时将scope取得的index记录于sequence中，提交修改时删除原来的，并加上新的
			submitEdit() {
				this.items.splice(this.sequence,1,this.editItemsDialog);
				this.editItemsDialogVisible = false;
			},
			chooseName() {
				this.chooseNameVisible = true;
			},
			getUserNames() {

			},
			getData2() {
				this.userNames.forEach((userName,index)=>{
					this.data2.push({
						label: userName,
						key: index,
					})
				})
			},
			submitName() {
				if(this.value2.length!=1){
					this.$message({
						message:'选择的人员数应该是一个',
					})
				}else{
					//还需要验证选择的人数为一
					let index = this.value2[0];
					this.addItemsDialog.userName = this.data2[index].label;
					this.editItemsDialog.userName = this.data2[index].label;
					this.chooseNameVisible = false;
				}
			},
			remove(row){
				let sequence = this.items.indexOf(row);
				this.items.splice(sequence,1);
			}
		},
		mounted() {
			this.getUserNames();
			this.getData2();
		}
	}
</script>

<style scoped>
	
</style>