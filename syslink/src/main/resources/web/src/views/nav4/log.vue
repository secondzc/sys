<template>
  <section>
    <!--工具条-->
   <!--  <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.realName" placeholder="真实姓名/用户名"  min-width="120" ></el-input>
        </el-form-item>
         <el-form-item>
          <el-input v-model="filters.content" placeholder="内容"  min-width="120" ></el-input>
        </el-form-item>
       
         <el-form-item>
        
           <el-date-picker
      v-model="filters.time"
      type="datetimerange"
      :picker-options="pickerOptions2"
      placeholder="选择时间范围"
      align="right">
    </el-date-picker>
          
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getLogs" size="small" >查询</el-button>
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
    <el-table :data="logs" highlight-current-row   stripe  ref="multipleTable"  style="width: 100%;"  @selection-change="selsChange" >
      <el-table-column type="selection" min-width="100">
      </el-table-column>
      <el-table-column type="index" min-width="120">
      </el-table-column>
       <el-table-column type="expand">
      <template slot-scope="props">

        详细信息：{{props.row.content}}
        
      </template>
    </el-table-column>
      <el-table-column prop="userName" label="用户名" min-width="120" >
      </el-table-column>
      <el-table-column prop="realName" label="全名" min-width="120" >
       </el-table-column>
      <el-table-column prop="userIp" label="用户IP" min-width="160" >
       </el-table-column>
         <el-table-column prop="title" label="标题" min-width="150" >
       </el-table-column>
    <!--   <el-table-column prop="content" label="内容" min-width="150" >
       </el-table-column> -->
      <el-table-column prop="createDate" label="操作时间" min-width="150" :formatter="formatDate" sortable>
      </el-table-column>
    </el-table>


    <div style="display: flex;margin-top: 10px;text-align: justify;justify-content: space-between;">
      <el-button type="danger"  size="small" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
     <el-pagination  
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageIndex"
      :page-sizes="[30, 50, 100, 300]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </el-pagination>

    </div>
    
     
    
    


  </section>
</template>

<script>
  import util from '../../common/js/util'



  export default {

    data() {
      return {
          pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        filters: {
          realName: '',
          content:'',
          time:''
        },
        pager:{
          total:1,
          pageSize:30,
          pageIndex:1,
          currentPage:''

        },
        logs: [],
    //    total: 0,
 //       page: 1,
        listLoading: false,
        sels:[]
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
        this.pager.pageIndex = val;
        this.getLogs();
      },
      handleSizeChange(val){
        this.pager.pageSize = val;
        this.getLogs();
      },
      reset(){
           this.filters=[];
           this.getLogs();
      },
      //获取日志列表
      getLogs() {
       
          var _this = this;
            let para = Object.assign({},this.filters,this.pager);
           _this.$http({
            url:'/api/log/list',
            method:'POST',
            data:para

           })
              .then((response)=> {
                  _this.logs = response.data.logs;
                  _this.pager.total=response.data.total;
                  
          })
          .catch(function (error) {
              console.log(error);
          });
      },
       batchRemove: function () {
        var ids = this.sels.map(item => item.id);

        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //NProgress.start();
          let para = { ids: ids };
          this.$http({url:'/api/log/deleteLogs',method:'post',data:para})
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
      this.getLogs();
    }
  }

</script>

<style scoped>

</style>