<template>
  
</template>

<script>
import Cookies from 'js-cookie'
import axios from 'axios'


export default {

    data() {
      return {
         
      }
    },
    methods: {
    
       logout() {
            
   

             //  this.$store.dispatch('LogOut');
             // location.reload();      
 
                  this.$store.dispatch('LogOut').then(()=>{
                     location.reload();
                    })                

            },
 
    },
    mounted() {
      this.logout();
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  
</style>
