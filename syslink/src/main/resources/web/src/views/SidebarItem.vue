<template>
  <div >
    <template v-for="item in routes">

      <router-link v-if="!item.hidden&&item.leaf&&item.children.length>0" :to="item.children[0].path">
        <el-menu-item class="naviItem" :index="item.children[0].path" >
            <i :class="item.iconCls"></i><span slot="title">{{item.children[0].name}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu :index="item.name" v-if="!item.leaf&&!item.hidden&&item.children">
        <template slot="title">
         
          <i :class="item.iconCls"></i><span slot="title" >{{item.name}}</span>
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
   a:-webkit-any-link {
    color: -webkit-link;
    cursor: auto;
    text-decoration: none;
    text-decoration-line: none;
    text-decoration-style: initial;
    text-decoration-color: initial;
}


</style>

