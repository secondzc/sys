<template>
  <section>
    <!--工具条-->
   <!--  <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="仓库名"  min-width="120" ></el-input>
        </el-form-item>
       <!--   <el-form-item>
          <el-input v-model="filters.content" placeholder="内容"  min-width="120" ></el-input>
        </el-form-item> -->
       
         <el-form-item>
        
     <!--       <el-date-picker
      v-model="filters.time"
      type="datetimerange"
      :picker-options="pickerOptions2"
      placeholder="选择时间范围"
      align="right">
    </el-date-picker> -->
          
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getRepos" size="small" >查询</el-button>
        </el-form-item>
         <el-form-item>
          <el-button type="primary" v-on:click="reset"  size="small" >重置</el-button>
        </el-form-item>
        <!--
       <el-form-item>
           <el-button @click="toggleSelection()">取消选择</el-button>
        </el-form-item>
        -->
       
      
      </el-form>
      <hr/>
    <!-- </el-col> -->
   
       <!--列表-->
    <el-table :data="cRepos" highlight-current-row   stripe  ref="multipleTable"  style="width: 100%;min-height: 250px;"  @selection-change="selsChange" >
      <!-- <el-table-column type="selection" min-width="100">
      </el-table-column> -->
     <!--  <el-table-column type="index" min-width="120">
      </el-table-column> -->
      <el-table-column prop="name" label="仓库名" min-width="120" >
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" >
       </el-table-column>
      <el-table-column prop="createdUnix" label="创建时间" min-width="160" >
       </el-table-column>
      <el-table-column prop="updatedUnix" label="更新时间" min-width="160" >
      </el-table-column>

       <el-table-column label="操作" width="300">
        <template slot-scope="scope">
         <el-button  type="primary" size="small" @click="toRepo(scope.$index, scope.row)">查看</el-button>
        </template>
      </el-table-column>
     
    </el-table>
    
   



    <!--工具条-->
    <el-col :span="24" class="toolbar">
     
      <el-pagination  
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageIndex"
      :page-sizes="[30, 50, 100, 300]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </el-pagination>
  
    </el-col>


  </section>
</template>

<script>
  import util from '../../common/js/util'

   import global_ from '../global.vue'

  export default {

    data() {
      return {
         
        filters: {
          realName: '',
          content:'',
          time:'',
          name
        },
        pager:{
          total:1,
          pageSize:10,
          pageIndex:1,
          currentPage:''

        },
        logs: [],
        repos:[],
        cRepos:[],
    //    total: 0,
 //       page: 1,
        listLoading: false,
        sels:[]
      }
    },
    computed:{
     abc:function(){
       return 333;
     }
    },
    methods: {
    
       selsChange: function (sels) {
        this.sels = sels;
      },

     
       toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },

     
      formatDate:function(row,column){
        if(row.createDate!=null)
        {
          return util.formatDate.format(new Date(row.createDate), 'yyyy-MM-dd hh:mm')
        }
        

      },
      handleCurrentChange(val) {
         this.pager.pageIndex = val;
         this.getRepos();
   
      },
      handleSizeChange(val){
         this.pager.pageSize = val;
         this.getRepos();

       
      },
      reset(){
           this.filters=[];

           this.getRepos();
      },
    
      //获取日志列表
    
      getRepos()
      {
           let para = {
              name: this.filters.name,
              pageSize : this.pager.pageSize,
              pageIndex : this.pager.pageIndex,
              uid: this.$store.state.userInfo.profile.iD
          };
           this.$http.post('/api/repository/cRepo',para).then((response)=>{


         
            this.cRepos = response.data.cRepos;

             this.pager.total = response.data.total;


           })
           .catch(function (error) {
              console.log(error);
          });
      },
      toRepo(index,row)
      {
       
               let gogsDomain = global_.gogsHostPath;
                let gogsPort = global_.gogsPort;
                let domain = global_.HostPath;

             if(gogsPort.length>0)
                {
                  window.location.href="http://"+domain+gogsPort+"/"+row.userName+"/"+row.name;
                }
                else
                {
                  window.location.href="http://"+domain+'/'+gogsDomain+"/"+row.userName+"/"+row.name;
                }
      },
     
 

 
    },
    mounted() {

      this.getRepos();
    }
  }

</script>

<style scoped>

</style>