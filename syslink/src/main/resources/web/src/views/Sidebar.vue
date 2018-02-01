<template>
    <el-menu mode="vertical"  class="el-menu-vertical-demo"  :default-active="$route.path" :collapse="!opened" :unique-opened=true>
     <div >
    <template v-for="item in routers">

     
          
          <router-link v-if="!item.hidden&&item.leaf&&item.children.length>0" :to="item.children[0].path">
        <el-menu-item class="naviItem" :index="item.children[0].path" >
            <i :class="item.iconCls"></i><span slot="title" v-show="opened">{{item.children[0].name}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu :index="item.name" v-if="!item.leaf&&!item.hidden&&item.children.length>0">
        <template slot="title">
         
          <i :class="item.iconCls"></i><span slot="title" v-show="opened">{{item.name}}</span>
        </template>
        <template v-for="child in item.children" v-if='!child.hidden'>

          <sidebar-item  v-if='child.children&&child.children.length>0' :routes='[child]'> </sidebar-item>
          <router-link v-else :to="child.path">
            <el-menu-item class="naviItem"  :index="child.path">
               <i :class="child.iconCls"></i>
              <span >{{child.name}}</span>
            </el-menu-item>
          </router-link>

        </template>

      </el-submenu>

    </template>
  </div>
    </el-menu>
</template>


<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'routers',
      'opened'
    ]),
    isCollapse() {
      console.log(this.routers);
   //   return !this.sidebar.opened
   return false
    }
  },
  mounted(){
    console.log(this.routers);
  }
}
</script>

<style type="text/css" scoped>
  
  .naviMenu
  {
   /* position: relative;*/
  /*  border-left: solid 1px #e6e6e6;
    border-right: solid 1px #e6e6e6;
    border-top: solid 1px #e6e6e6;*/
  /*  border-bottom: solid 1px  #e6e6e6; */
    height: inherit;
    width: 200px;
    overflow-x:visible;

    
    /*margin-top: 50px;*/
   /* margin-left: 20px;
    margin-right: 10px;*/
   
   /* overflow-y: auto;*/

  }
  a:-webkit-any-link {
    color: -webkit-link;
    cursor: auto;
    text-decoration: none;
    text-decoration-line: none;
    text-decoration-style: initial;
    text-decoration-color: initial;
}
 .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }

  .el-menu--collapse {
    min-width: 64px;
    position: fixed;
    z-index: 1000;
    overflow-x: visible;
}
  /*span:hover{
  	visibility: hidden;
  	
  }*/
  span{
  	transition-property: visibility;
transition-duration: 0.02s;
-moz-transition-property: visibility; 
-moz-transition-duration: 0.02s; 
-webkit-transition-property: visibility;
-webkit-transition-duration: 0.02s; 
-o-transition-property: visibility; 
-o-transition-duration: 0.02s; 
  }

</style>



