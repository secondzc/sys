<template>
    <el-menu mode="vertical"  class="el-menu-vertical-demo"  :default-active="$route.path" :collapse="!opened">
     <div >
    <template v-for="item in routers">

      <router-link v-if="!item.hidden&&item.leaf&&item.children.length>0" :to="item.children[0].path">
        <el-menu-item class="naviItem" :index="item.children[0].path" >
          <!-- <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg> -->
          <i :class="item.iconCls"></i><span slot="title" v-show="opened">{{item.children[0].name}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu :index="item.name" v-if="!item.leaf&&!item.hidden">
        <template slot="title">
          <!-- <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg> -->
          <i :class="item.iconCls"></i><span slot="title" v-show="opened">{{item.name}}</span>
        </template>
        <template v-for="child in item.children" v-if='!child.hidden'>

          <sidebar-item  v-if='child.children&&child.children.length>0' :routes='[child]'> </sidebar-item>

          <router-link v-else :to="child.path">
            <el-menu-item class="naviItem"  :index="child.path">
              <i v-if='child.iconCls' :class="child.iconCls"></i><span >{{child.name}}</span>
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
  }
}
</script>

<style type="text/css">
  
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

</style>



