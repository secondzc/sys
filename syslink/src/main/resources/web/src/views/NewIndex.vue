<template>
	
<el-container class="main-container">
	<el-header  class="top-header">
		<span>标题栏</span>
	</el-header>
	<el-header class="middle-header">
		<span>#############</span>
	</el-header>
    <el-header  class="bottom-header" >
    	<div style="padding: 10px;min-width: 200px;">
    	
     <el-button type="primary" size="small"  style="float: left;">添加分类</el-button>
      <el-button type="primary" size="small"  style="float: left;">上传</el-button>

  

    <div  style="float: left;height: inherit;margin-left: 100px;">
      
          <el-breadcrumb separator="/"  style="line-height: 30px;">
     <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
     <el-breadcrumb-item>活动管理</el-breadcrumb-item>
     <el-breadcrumb-item>活动列表</el-breadcrumb-item>
     <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>
    </div>

 

        
   
        <el-button-group  style="float: right;">
        <el-button  icon="el-icon-tickets" size="small"  @click="list=true"></el-button>
        <el-button  icon="el-icon-menu"  size="small" @click="list=false"></el-button>  
        </el-button-group>
       <el-button icon="el-icon-info"  style="float: right;margin-right:1%;"  size ="small"
       @click="info=!info"></el-button>
    	</div>
    	
    </el-header>
    <el-container style="border-top:solid 1px #e7e7e7 ;height: 100%;overflow-y: hidden;">
    	<el-aside class="left-aside">

         <el-tree
         :props="props"
         :load="loadNode"
         lazy
         show-checkbox
         @check-change="handleCheckChange"  style="margin-top: 20px;overflow: inherit;">
        </el-tree>

    	</el-aside> 
    	<el-main class="list-main" v-show="list">

    		<div style="height: 100%;" ref="mainTalbe">
    			<el-table
    :data="tableData3"
      :highlight-current-row="true" height="100%" style="overflow-x: hidden;">
     <el-table-column
      label=""
      width="200" align="center">
      <template slot-scope="scope">
        <img :src="errGif" style="height: 100px;padding-right:2px" /> 
       
      </template>
    </el-table-column>
    <el-table-column
      prop="date"
      label="日期"
      >
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名"
      >
    </el-table-column>
    <el-table-column
      prop="province"
      label="省份"
    >
    </el-table-column>
    <el-table-column
      prop="city"
      label="市区"
      >
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址"
    >
    </el-table-column>
    <el-table-column
      prop="zip"
      label="邮编"
     >
    </el-table-column>
  </el-table>
    		</div>



     
    	</el-main>






    	<el-main class="card-main" v-show="!list">
    		<div style="overflow-y: hidden;border-bottom: solid 1px  #e7e7e7;height: 30px;">
    			<span>排序</span>
    		</div>
        <div  style="display: flex;flex-direction: column;height: inherit;width: auto;">

      <div  style=" ;overflow-x: hidden;overflow-y: auto;justify-content: center;
     display: flex;flex-wrap: wrap;height:calc(100% - 30px);">
        <el-card style="height: 400px;width: 300px; margin: 12px;" v-for="o in 11">
          <div slot="header" class="clearfix">
           <span @click="toDetails">卡片名称</span>
           <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
         </div>
         <div v-for="o in 4" :key="o" class="text item">

           {{'列表内容 ' + o }}
         </div>
           <img :src="errGif" />
        </el-card>


      </div>

   


  
    			

    		</div>

     <!--       	<div style="border-bottom: solid 1px  #e7e7e7;height: 40px;">

    		<span>#########################################</span>
    		</div>
            <div  style="overflow: auto;height: 90%;">
            	<el-form :inline="true">
    		   <el-form-item  v-for="o in 10" >
    		   <el-card style="height: 400px;width: 300px; margin: 12px;align-self: center;" >
               <div slot="header" class="clearfix">
               <span @click="toDetails">卡片名称</span>
               <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
               </div>
                <div v-for="o in 4" :key="o" class="text item">

               {{'列表内容 ' + o }}
                </div>
              	<img :src="errGif" />
                </el-card>
    			</el-form-item>
    		</el-form>
            </div> -->

    		
    	</el-main>
    	<el-aside class="right-aside" v-show="info">

        <el-card style="height: inherit;overflow: inherit;">
  <div slot="header" class="clearfix">
    <span>卡片名称</span>
    
    <i class="el-icon-close" style="float: right;padding: 3px 0;" @click="info=!info"> </i>
  </div>
  <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}

  </div>
  	<img :src="errGif" />
</el-card>


    	</el-aside>
    </el-container>
    <el-footer class="main-footer">
    	<span >
    		################################################################
    	</span>
    </el-footer>
</el-container>

</template>



<script >
	import errGif from '@/assets/401_images/401.gif'

	 export default {
    data() {
      return {
      	 errGif: errGif + '?' + +new Date(),
        props: {
          label: 'name',
          children: 'zones'
        },
        count: 1,
        info:false,
        list:true,
        

         tableData3: [{
          date: '2016-05-03',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-02',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-04',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-01',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-08',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-06',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-07',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        },
        {
          date: '2016-05-08',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-06',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }, {
          date: '2016-05-07',
          name: '王小虎',
          province: '上海',
          city: '普陀区',
          address: '上海市普陀区金沙江路 1518 弄',
          zip: 200333
        }]
      
      };
    },


    methods: {
    toDetails()
   	{
        this.$router.push('/NewDetails');
   	},
      handleCheckChange(data, checked, indeterminate) {
        console.log(data, checked, indeterminate);
      },
      handleNodeClick(data) {
        console.log(data);
      },
      loadNode(node, resolve) {
        if (node.level === 0) {
          return resolve([{ name: 'region1' }, { name: 'region2' }]);
        }
        if (node.level > 3) return resolve([]);

        var hasChild;
        if (node.data.name === 'region1') {
          hasChild = true;
        } else if (node.data.name === 'region2') {
          hasChild = false;
        } else {
          hasChild = Math.random() > 0.5;
        }

        setTimeout(() => {
          var data;
          if (hasChild) {
            data = [{
              name: 'zone' + this.count++
            }, {
              name: 'zone' + this.count++
            }];
          } else {
            data = [];
          }

          resolve(data);
        }, 500);
      }
    }
    ,
    mounted() {
      console.log(this.$refs.mainTalbe.style.height);
    }
  };
	
</script>

<style scoped lang="scss">
	@import '~scss_vars';
	
.main-container{
	height: 100%;
	overflow: hidden;
}
.top-header{
	max-height: 40px;
	background-color: #f8f8f8;
}
.middle-header{
	max-height: 50px;
	border-top: solid 1px #e7e7e7;
}
.bottom-header{
	max-height:  50px;
	border-top: solid 1px #cfdbe5;
}
.main-footer{
	max-height: 30px;
	text-align: center;
	border-top: solid 1px #e7e7e7;

}
.left-aside{
	border-right: solid 1px #e7e7e7;
	flex: 0 0 200px;
	overflow-x:hidden; 
	overflow-y:auto;
}
.list-main{
	height: 100%;
	padding: 0px;
	overflow: hidden;
}
.card-main{
	height: inherit;
	padding: 0px;
	width: inherit;
	overflow: hidden;
}
.right-aside{
	height: 100%;
	overflow-x: hidden;
	overflow-y:auto;
}

.el-table
{
  height: inherit;
}
.el-table__body-wrapper
{
  height: inherit;
  overflow-x: hidden;
}
.is-scroll-left
{
  height: inherit;
  overflow-x: hidden;
}
.is-scroll-right
{
	height: inherit;
	overflow-x: hidden;
}

.el-table--fit
{
   height: inherit;
   overflow-x: hidden;
} 
.el-table--enable-row-hover
{
   height: inherit;
   overflow-x: hidden;
} 
.el-table--enable-row-transition
{
   height: inherit;
   overflow-x: hidden;
}

.el-table__body
{
  overflow-x: hidden;

}


</style>