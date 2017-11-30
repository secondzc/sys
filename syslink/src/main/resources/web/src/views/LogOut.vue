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
            
               this.$store.dispatch('LogOut');
               this.$http.post('api/user/destory').then(()=>{
               Cookies.remove('abc');
               Cookies.remove('syslink');
               Cookies.remove('gogs_awesome');
               // Cookies.remove('i_like_gogits');
               console.log(Cookies.get());




                let host = window.location.host;               
                let a = host;
                let b = a.split(':');
                let c = b[0];
                let url = c+":3000";
                // window.location.href="http://"+url



               axios.get('http://'+url+'/user/logout');
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
