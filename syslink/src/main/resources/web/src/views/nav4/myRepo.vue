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
          <el-button type="primary" v-on:click="queryRepo" size="small" >查询</el-button>
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
    <el-table :data="repos" highlight-current-row   stripe  ref="multipleTable"  style="width: 100%;min-height: 250px;"  @selection-change="selsChange" >
      <!-- <el-table-column type="selection" min-width="100">
      </el-table-column> -->
      <el-table-column type="index" min-width="120">
      </el-table-column>
      <el-table-column prop="name" label="仓库名" min-width="120" >
      </el-table-column>
        <el-table-column prop="Collaboration" label="是否协同" min-width="120" >
            <template  slot-scope="props">
         <i v-if=props.row.Collaboration class="el-icon-check"></i>
         <i v-else class="el-icon-close"></i>
      </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="120" >
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
    
   
   <!--  <hr/>


      <el-table :data="cRepos" highlight-current-row   stripe  ref="multipleTable1"  style="width: 100%;min-height: 250px;"  @selection-change="selsChange" >
      <el-table-column type="selection" min-width="100">
      </el-table-column> -->
<!--       <el-table-column type="index" min-width="120">
      </el-table-column>
      <el-table-column prop="name" label="仓库名" min-width="120" >
      </el-table-column>
      <el-table-column prop="Collaboration" label="是否协同" min-width="120" >
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="120" >
       </el-table-column>
      <el-table-column prop="createdUnix" label="创建时间" min-width="160" >
       </el-table-column>
      <el-table-column prop="updatedUnix" label="更新时间" min-width="160" >
      </el-table-column>

       <el-table-column label="操作" width="300">
        <template slot-scope="scope">
         <el-button  type="primary" size="small" @click="toRepo(scope.$index, scope.row)">查看</el-button>
        </template>
      </el-table-column> -->
     
   <!--  </el-table> -->


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
      path(){
           this.$router.push({path:'/api/druid/'})
      },
     
      formatDate:function(row,column){
        if(row.createDate!=null)
        {
          return util.formatDate.format(new Date(row.createDate), 'yyyy-MM-dd hh:mm')
        }
        

      },
      handleCurrentChange(val) {
        // this.pager.pageIndex = val;
        // this.getLogs();
         console.log(`当前页: ${val}`);
          var filterModel = this.repositories.filter(
              (u,index)=>{
                  if(index < val * para.pageSize && index >= para.pageSize * (val - 1)){
                      return true
                  }
              }
          )
          this.repos = filterModel;
      },
      handleSizeChange(val){
        // this.pager.pageSize = val;
        // this.getLogs();

           console.log(`每页 ${val} 条`);
          this.pager.pageSize = val;
          var filterModel = this.repositories.filter(
              (u,index)=>{
                  if(index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)){
                      return true
                  }
              }
          )
          this.repos = filterModel;
      },
      reset(){
           this.filters=[];

           this.getRepos();
      },
      queryRepo()
      {
        if(this.filters.name!=''&&this.filters!=null)
        {
          let queryRepo=[];
        console.log(this.filters.name);
           console.log(this.repos);
        for(let a of this.repos)
        {
          console.log(a);
          if (a.name ==this.filters.name) 
          {
             queryRepo.push(a);
          }
             
        }
         console.log(queryRepo);
           this.repos=queryRepo;
            console.log(this.repos);
       
        }
        else
        {
          this.getRepos();
        }
        
      },
      //获取日志列表
    
      getRepos()
      {
           let para = {
              name: this.filters.name,
              pageSize : this.pager.pageSize,
              pageIndex : this.pager.pageIndex
          };
           this.$http.post('/api/repository/list').then((response)=>{


            var searchModel = response.data.allRepos.filter(model =>{
                      if(para.name && model.name.indexOf(para.name) == -1 ){
                              return false
                          }
                          else{
                              return true
                          }
                  })
                  var filterModel = searchModel.filter(
                      (u,index)=>{
                          if(index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)){
                              return true
                          }
                      }
                  )
           // this.repos =  response.data.repos;
           // this.cRepos = response.data.cRepos;
           // this.repos = response.data.allRepos;
            this.pager.total = searchModel.length;
                  this.repos = filterModel;


           })
           .catch(function (error) {
              console.log(error);
          });
      },
      toRepo(index,row)
      {
         location.href="http://gogs.modelica-china.com:3000/"+row.userName+"/"+row.name;
      },
     
       batchRemove: function () {
        var ids = this.sels.map(item => item.id);

        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { ids: ids };
          this.$http({url:'/api/operationlog/deleteLogs',method:'post',data:para})
          .then((res) => {
            this.listLoading = false;
            //NProgress.done();
           
           
            this.getLogs();
          });
        }).catch(() => {

        });
      }

 
    },
    mounted() {

      this.getRepos();
    }
  }

</script>

<style scoped>

</style>