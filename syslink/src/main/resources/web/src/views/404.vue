<template>
	<div style="text-align: center;margin-top: 10%;">
		  <img :src="notFound" style="height: 224px;width: 512px;" />
	</div>
  
</template>


<script >
	
	 import notFound from '@/assets/notFound.png'

	 export default {
  data() {
    return {
      notFound: notFound ,
     
    }
  },
  methods: {
   
  }
}
</script>

<style  scoped>
  
</style>