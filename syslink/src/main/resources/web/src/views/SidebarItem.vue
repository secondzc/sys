<template>
  <div >
    <template v-for="item in routes">

      <router-link v-if="!item.hidden&&item.leaf&&item.children.length>0" :to="item.children[0].path">
        <el-menu-item class="naviItem" :index="item.children[0].path" >
          <!-- <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg> -->
          <i :class="item.iconCls"></i><span slot="title">{{item.children[0].name}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu :index="item.name" v-if="!item.leaf&&!item.hidden">
        <template slot="title">
          <!-- <icon-svg v-if='item.icon' :icon-class="item.icon"></icon-svg> -->
          <i :class="item.iconCls"></i><span slot="title" >{{item.name}}</span>
        </template>
        <template v-for="child in item.children" v-if='!child.hidden'>

          <sidebar-item  v-if='child.children&&child.children.length>0' :routes='[child]'> </sidebar-item>

          <router-link v-else :to="child.path">
            <el-menu-item class="naviItem"  :index="child.path">
              <!-- <icon-svg v-if='child.icon' :icon-class="child.icon"></icon-svg> --><span >{{child.name}}</span>
            </el-menu-item>
          </router-link>

        </template>

      </el-submenu>

    </template>
  </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    }
  }
}
</script>

<style type="text/css" scoped>
  
  .naviItem{
   /* border-bottom:solid 1px #e6e6e6 ; 
    border-left: solid 1px #e6e6e6;
    border-right: solid 1px #e6e6e6 ;   */
 
  }
  .el-submenu{
   /* border-top:solid 1px  #e6e6e6;
     border-left: solid 1px #e6e6e6;
    border-right: solid 1px #e6e6e6 ;   */
  }
 /* .el-submenu__title{
    border-bottom: solid 1px #e6e6e6; 

  }*/
  .el-submenu.is-active .el-submenu__title {
  /*  border-bottom-color: #e6e6e6;
   background: #e6e6e6;*/
  }
  .abc{
     
  }
</style>

