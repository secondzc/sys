<template>
  <section>
    <el-badge :value="msgNum" class="item">
      <el-button size="small" @click="detail(scope.$index,scope.row)">通知</el-button>
    </el-badge>
  </section>
</template>

<script>
  export default{
    data(){
       return {
         msgNum: 0,
       }
    },
    methods: {
      loopGetMsg(){
        setInterval(()=>{
          this.getMsg();
        },60*1000)
      },
      getMsg(){
        let url="/api/msg/getMsgNum";
        this.func.ajaxPost(url,{},res =>{
           if(res.data.flag == true){
             this.msgNum = res.data.msgNum;
           }
		});
      },
      detail(index,row){
        this.$router.push({path:'/msgDetail'});
      }
    },
    mounted() {
      //this.loopGetMsg();
    },
  }
</script>

<style>
.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>