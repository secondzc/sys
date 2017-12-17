<template>
	<section>
		<el-tree
	:data="data2"
	show-checkbox
	default-expand-all
	node-key="label"
	ref="tree"
	highlight-current
	:props="defaultProps"
	@check-change="check">
</el-tree>
    <el-button type="primary" @click="affirm" :disabled="this.namesNum!==1">确定</el-button>
	</section>
	

</template>

<script >
export default{
	data() {
		return {
			data2: [],
			defaultProps: {
				children: 'children',
				label: 'label'
			},
			treeLoading:false,
			checkedNodes:[],
			names:[],
			namesNum:0,
		};
	},
	props:['reset'],
	methods: {
		//参数表示节点本身，节点是否被选中，节点的子树种是否有被选中的节点
		check(var1,var2,var3){
			console.log(this.namesNum);
			console.log(var1);
			if(var2 && var1.leaf){
				this.namesNum++;
				console.log(this.namesNum);
			}else if((!var2) && var1.leaf){
				this.namesNum--;
				console.log(this.namesNum);
			}
		},
		affirm(){
			//每次点击确认后，清空数组
			this.names=[];
			this.checkedNodes = this.$refs.tree.getCheckedNodes();
			for(var i=0;i<this.checkedNodes.length;i++){
				if(!this.checkedNodes[i].disabled){
					this.names.push(this.checkedNodes[i].label);
				}
			};
            this.$emit('affirmName',this.names[0]);
            this.reset=false;
		},
		getData2(){
			var url='/api/userTree/query';
			this.func.ajaxPost(url,{},res=>{
				if(res.data.flag===true){
					this.data2 = res.data.users;
				}
			});
		},
	},
	watch:{
		reset(val,oldval){
			console.log(val+'  '+oldval);
			if((!oldval) && val){
				this.$refs.tree.setCheckedKeys([]);
			}
		}
	},
	mounted(){
		this.getData2();
		console.log('开始加载人员树');
	},
}
</script>

<style>
</style>