<template>
	
</template>

<script >
	export default{
		data(){
			instanceId: 0,
			
		},
		methods:{

		},
		mounted(){
			this.instanceId = sessionStorage.getItem('instanceId');
		},
	}
</script>
<style >
	
</style>