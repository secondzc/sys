<template>
  <section>
    <!--列表-->
    <el-table :data="msgs" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
      <el-table-column type="selection" width="50">
      </el-table-column>
      <el-table-column prop="fromUserName" label="发件人" width="120" >
      </el-table-column>
      <el-table-column prop="subject" label="主题" width="120" >
      </el-table-column>
      <el-table-column prop="content" label="内容" width="120" >
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template scope="scope">
          <el-button type="danger" size="small" @click="remove(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </section>
</template>

<script>
  export default{
    data(){
       return {
         msgs:[],
         sels:[],
         listLoading: false,
       }
    },
    methods: {
      getMsg(){
        this.listLoading = true;
        let url="/api/msg/getMsgs";
        this.func.ajaxPost(url,{},res =>{
           if(res.data.flag == true){
             this.msgs = res.data.msgs;
             this.listLoading = false;
           }
		    });
      }, 
      selsChange(sels){
          this.sels = sels;
      },
      remove(row){
         let url="/api/msg/removeMsgs";
         this.listLoading = true;
         this.func.ajaxPost(url,{ids:row.id},res=>{
            if(res.data.flag ==true){
               this.listLoading = false;
               this.getMsg();
            }
         })
      },
    },
    mounted() {
      this.getMsg();
    },
  }
</script>

<style>
</style>