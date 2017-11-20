<template>
	
<el-container   style="height: 100%;">
	<el-header class="top-header">
	</el-header>
	<el-header class="middle-header">
	</el-header>

    <el-container  class="main-container" >

    	<el-aside class="left-aside">
    		 <el-tree
         :props="props"
         :load="loadNode"
         lazy
         show-checkbox
         @check-change="handleCheckChange"  style="margin-top: 20px;overflow: inherit;">
        </el-tree>
    	</el-aside>
    	<el-main style="height: inherit;overflow-y: hidden;">
   <el-tabs   tab-position="bottom">
  <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
    11111111
    <img :src="errGif"  v-for="o in 10"  />
 


  </el-tab-pane>
  <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
    222222222
  </el-tab-pane>
   <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
    333333333
  </el-tab-pane>
   <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
    444444444
  </el-tab-pane>
   <el-tab-pane>
    <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
    5555555555
  </el-tab-pane>
</el-tabs>
    	</el-main>
    </el-container>
    

	<el-footer class="main-footer">
		<span>
			############################################
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
  };
	
</script>



<style scoped lang="scss">
	@import '~scss_vars';
	
.main-container{
	height: inherit;;
	overflow-y:hidden;
}
.top-header{
	max-height: 40px;
	background-color: #f8f8f8;
}
.middle-header{
	max-height: 50px;
	border-top: solid 1px #e7e7e7;
}
.main-footer{
	max-height: 30px;
	text-align: center;
	border-top: solid 1px #e7e7e7;

}

.left-aside
{
	border-right: solid 1px #e7e7e7;
}
/*.el-tabs.el-tabs--bottom{
	height: inherit;
}
.el-tab-pane
{
	height: 100px;
	overflow-y: auto;
}
.el-tabs__content{
    overflow: hidden;
    position: relative;
    height: 200px;
}
.el-tabs.el-tabs--bottom .el-tabs__header
{
	height: 400px;
}
*/

.el-tabs.el-tabs--bottom
{
	height: inherit;
	overflow-y: hidden;


	

}
.el-tabs__content {
    overflow-y: auto;
    position: relative;
}
.el-tab-pane
{
	max-height: 200px;
	overflow-y: auto;
}

</style>