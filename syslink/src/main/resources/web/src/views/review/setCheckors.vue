<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true">
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="submitAll" :loading='submitAllLoading'>完成</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="cancelAll">取消配置</el-button>
				</el-form-item>
			</el-form>
		</el-col>

        <!--列表-->
		<el-table :data="items" highlight-current-row  @selection-change="selsChange" style="width: 100%;" :loading='nodesLoading'>
			<el-table-column type="selection" width="50">
			</el-table-column>
			<el-table-column type="index" label="节点次序" width="120">
			</el-table-column>
			<el-table-column prop="reviewNodeName" label="节点名" min-width="120" >
			</el-table-column>
			<el-table-column prop="description" label="节点描述" min-width="120" >
			</el-table-column>
			<el-table-column prop="userName" label="审核者" min-width="120" >
			</el-table-column>
			<el-table-column label="操作" min-width="200">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
					<el-button type="danger" size="small" @click="remove(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--新增界面-->
		<el-dialog title="配置人员" :visible.sync="addItemsDialogVisible" v-model="addItemsDialogVisible" :close-on-click-modal="false">
			<el-form :model="addItemsDialog" label-width="80px" >
				<el-form-item label="节点名" prop="reviewNodeName">
					<el-input v-model="addItemsDialog.reviewNodeName" ></el-input>
				</el-form-item>
				<el-form-item label="节点描述" prop="description">
				     <el-input v-model="addItemsDialog.description"></el-input>
				</el-form-item>
				<el-form-item label="审核者" prop="userName" :rules="[{required:true,message:'请 选择审核者',trigger:'blur'}]">
					 <div style="display:flex;">
				      <el-input v-model="addItemsDialog.userName" :disabled="true" style="margin-right:10px;"></el-input>
				      <el-button type="primary" @click.native="chooseName">选择审核者</el-button>
				     </div>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addItemsDialogVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitOne">确定</el-button>
			</div>
		</el-dialog>

		<!--修改界面-->
		<el-dialog title="配置人员" :visible.sync="editItemsDialogVisible" v-model="editItemsDialogVisible" :close-on-click-modal="false">
			<el-form :model="editItemsDialog" label-width="80px" >
				<el-form-item label="节点名" prop="reviewNodeName">
					<el-input v-model="editItemsDialog.reviewNodeName" ></el-input>
				</el-form-item>
				<el-form-item label="节点描述" prop="description">
				     <el-input v-model="editItemsDialog.description"></el-input>
				</el-form-item>
				<el-form-item label="审核者" prop="userName">
					<div style="display:flex;">
				     <el-input v-model="editItemsDialog.userName" :disabled="true" style="margin-right:10px;"></el-input>
				     <el-button type="primary" @click.native="chooseName">选择审核者</el-button>
				 </div>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editItemsDialogVisible = false">取消</el-button>
				<el-button type="primary" @click.native="submitEdit">确定</el-button>
			</div>
		</el-dialog>

		<!--选择人员-->
		<el-dialog title="选择人员" :visible.sync="chooseNameVisible" v-model="chooseNameVisible" :close-on-click-modal="false"
		v-loading="userNamesLoading">
			<template>
			  <user-tree @affirmName="affirmName1" :reset="chooseNameVisible"></user-tree>
			</template>
		</el-dialog>
	</section>
</template>

<script>
    import userTree from "../review/userTreeComp";
	export default {
		components:{
			userTree
		},
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
			  userNames: [],
			  value2: [],
			  data2: [],
			  userName: '',
			  submitAllLoading: false,
			  nodesLoading: false,
			  userNamesLoading: false,
			}
		},
		created(){
			this.templateId = sessionStorage.getItem('templateId');
		},
		methods: {
			affirmName1(userName){
				this.chooseNameVisible = false;
				this.addItemsDialog.userName = userName;
				this.editItemsDialog.userName = userName;
			},
			query() {
				let url="/api/user/query";
				this.func.ajaxPost(url,{},res=>{
					this.userNamesLoading = true;
					if(res.data.flag==true){
						let users = res.data.users;
						for(var i=0;i<users.length;i++){
							this.userNames.push(users[i].name);
						}
						this.userNamesLoading = false;
					}
				})
			},
			submitOne(){
				let str=",";
				let index1=this.addItemsDialog.reviewNodeName.indexOf(str);
				let index2=str.indexOf(this.addItemsDialog.description);
				let index3 = str.indexOf(this.addItemsDialog.userName);
				let curSequence = this.items.length+1;
				if(this.func.isNull(this.addItemsDialog.userName)){
					this.$message({
						message:'人员不能为空',
					})
				}else{
				if(index1>0 ||index2>0 ||index3>0){
					this.$message({
						message:'配置中不能含有英文逗号，请重新输入',
					})
				}else{
					if(this.func.isNull(this.addItemsDialog.reviewNodeName)){
						this.addItemsDialog.reviewNodeName = '节点'+curSequence;
					}
					if(this.func.isNull(this.addItemsDialog.description)){
						this.addItemsDialog.description = '这是节点'+curSequence;
					}
					this.items.push(this.addItemsDialog);
					this.addItemsDialogVisible = false;		
				}
			    }
			},
			submitAll(){
				if(this.items.length===0){
					this.$message({
						type:'info',
						message:'未配置不能提交',
					})
				}else{
				  this.$confirm('确认提交吗？','提示',{
				  	  type:'info ',
                    closeOnClickModal:false
				  }).then(()=>{
					this.submitAllLoading = true;
					let url = '/api/reviewNode/batchAdd';
					let nodeName = [];
					let des = [];
					let usern = [];
					for(var i=0;i<this.items.length;i++){
						nodeName.push(this.func.isNull(this.items[i].reviewNodeName)
							?'defaultName':this.items[i].reviewNodeName);
						des.push(this.func.isNull(this.items[i].description)
							?'defaultDescription':this.items[i].description);
						usern.push(this.items[i].userName);
					}
					this.func.ajaxPost(url,{reviewNodeName: nodeName.join(','),
						description: des.join(','),
						userName: usern.join(','),
						templateId:this.templateId},res =>{
						if(res.data.flag == true){
							this.submitAllLoading = false;
							this.$message({
							message: '提交成功！',
							type: 'success',
						    })
						    this.redirect();
						}
					})
				  })					
				}
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
				if(this.func.isNull(this.editItemsDialog.reviewNodeName)){
					this.editItemsDialog.reviewNodeName = '节点'+(this.sequence+1);
				}
				if(this.func.isNull(this.editItemsDialog.description)){
					this.editItemsDialog.description = '这是节点'+(this.sequence+1);
				}
				this.items.splice(this.sequence,1,this.editItemsDialog);
				this.editItemsDialogVisible = false;
			},
			chooseName() {
				this.chooseNameVisible = true;
				//父组件向子组件传递刷新已选人员的信息
				this.reset=false;
				this.reset=true;
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
				this.$confirm('确认要删除吗？','提示',{   
					    type:'warning',
              closeOnClickModal:false}).then(()=>{
				    let sequence = this.items.indexOf(row);
				    this.items.splice(sequence,1);
				});
			},
			getNodes(){
				this.nodesLoading=true;
				let url='api/reviewNode/queryReviewNode';
				this.func.ajaxPost(url,{templateId: this.templateId},res=>{
					if(res.data.flag==true){
						this.items = res.data.nodes;
						this.nodesLoading = false;
					}
				})
			}
		},
		mounted() {
			this.getUserNames();
			this.getData2();
			this.getNodes();
		}
	}
</script>

<style scoped>
	
</style>