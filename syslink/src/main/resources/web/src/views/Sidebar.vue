<template>
    <el-menu mode="vertical"  class="naviMenu" :default-active="$route.path" :collapse="false">
      <sidebar-item :routes='routers'></sidebar-item>
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
      'sidebar'
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
    /*margin-top: 50px;*/
   /* margin-left: 20px;
    margin-right: 10px;*/
    width: 200px;
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

</style>



