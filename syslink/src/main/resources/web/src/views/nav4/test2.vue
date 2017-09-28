<template>
  <section>
    <!--工具条-->

    <el-tabs   v-model="activeName2" >
    <el-tab-pane label="组织管理" name="first">

    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
     <el-button  type="primary" size="small" @click="handleRole()">新建组织</el-button>
    </el-col>
<!--
  <template >
    <el-card class="box-card">
    <div slot="header" class="clearfix">
    <span style="line-height: 36px;"></span>
    <el-button style="float: right;" type="primary">操作按钮</el-button>
    </div>
    <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}
    </div>
    </el-card>
</template>
-->

    <!--列表-->
    <el-table :data="department"    ref="multipleTable" highlight-current-row stripe @selection-change="selsChange" 
    @current-change="rowChange"      style="width: 100%;">
      
   
     <el-table-column type="expand" min-width="100"   >

      </el-table-column> 
      <el-table-column type="selection" min-width="100">
      </el-table-column>
      
      <el-table-column prop="name" label="名称" min-width="120" >
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="120" >
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" min-width="120" >
      </el-table-column>
       <el-table-column label="操作" width="200">
        <template scope="scope">
         <el-button  type="primary" size="small" @click="handelManage(scope.$index, scope.row)">管理</el-button>
        </template>
      </el-table-column>

    
    </el-table>
    
    </el-tab-pane>
    <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
    <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
    <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
  </el-tabs>
   
   

    
  </section>
</template>

<script>
  import util from '../../common/js/util'
  //import NProgress from 'nprogress'

  export default {
    data() {
       
      return {
        department:[],
        activeName2:'first'
      
      }
    },
    methods: {
        rowChange(now,old)
      {
        this.selection=now;
      },
        selsChange(val) {
        this.selection = val;
      },
      getDept()
      {
        let para = 0;
        this.$http.post('api/department/list').
        then((response)=>{
          this.department = response.data.depts;

        }).
        catch((error)=>{});
      },
      handelManage(row,index)
      {
        // this.$router.push({path:'/team'})
        this.$router.push({ name: '团队管理', params: { parentId: row.id }});
      }

      
    },
    mounted() {
       this.getDept();
  
    }
  }



</script>


<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  .clearfix:after {
      clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
