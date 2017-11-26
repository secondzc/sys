<template>
	<section>
		<el-tree
	:data="data2"
	show-checkbox
	default-expand-all
	node-key="label"
	ref="tree"
	highlight-current
	:props="defaultProps">
</el-tree>
    <el-button type="primary" @click="affirm" >确认</el-button>
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
		};
	},
	methods: {
		affirm(){
			//每次点击确认后，清空数组
			this.names=[];
			this.checkedNodes = this.$refs.tree.getCheckedNodes();
			for(var i=0;i<this.checkedNodes.length;i++){
				if(!this.checkedNodes[i].disabled){
					this.names.push(this.checkedNodes[i].label);
				}
			};
			if(this.names.length===1){
				//console.log(this.names[0]);
               this.$emit('affirmName',this.names[0]);
			}else{
				this.$message({
					message:'选择人员数目应该唯一',
				});
			}
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
	mounted(){
		this.getData2();
	}
}
</script>

<style>
</style>