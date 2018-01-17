<template>
	<section>
		<el-tabs v-model="activeTab" @tab-click="handleClick">
			<el-tab-pane label="待审核的流程" name="checkor">
				<checkor></checkor>
			</el-tab-pane>
			<el-tab-pane label="审签中的流程" name="mySubmit">
				<my-submit></my-submit>
			</el-tab-pane>
		</el-tabs>
	</section>
</template>

<script>
	import checkor from "../review/checkor";
	import mySubmit from "../review/mySubmit";
	export default{
		components:{
			checkor,
			mySubmit,
		},
		data(){
			return{
				tabs:['checkor','mySubmit'],
				activeTab:'',
				activeTabIndex: 0,
			};
		},
		methods:{
			handleClick(tab,events){
				//将tab的index存入vuex
				this.$store.commit('setActiveTabIndex',tab.index);
			}
		},
		created(){
			 this.activeTabIndex = this.$store.getters.activeTabIndex;
			 this.activeTab = this.tabs[this.activeTabIndex];
			 //console.log('activeTab',this.activeTab);
		}
	}
</script>

<style>
	
</style>