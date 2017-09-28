import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Gogs from './views/Gogs.vue'
import Main from './views/Main.vue'
import Table from './views/nav1/Table.vue'
import Form from './views/nav1/Form.vue'
import user from './views/nav1/user.vue'
import Page4 from './views/nav2/Page4.vue'
import Page5 from './views/nav2/Page5.vue'
import Page6 from './views/nav3/Page6.vue'
import echarts from './views/charts/echarts.vue'
import Repository from './views/repository/index.vue'
import Model from './views/model/index.vue'
import template from './views/review/template.vue'
import setCheckors from './views/review/setCheckors.vue'
import mySubmit from './views/review/mySubmit.vue'
import mySubmitDetail from './views/review/mySubmitDetail.vue'
import checkor from './views/review/checkor.vue'
import Myorg from './views/nav4/Myorg.vue'
import OrgEdit from './views/nav4/OrgEdit.vue'
import GUser from './views/nav4/GUser.vue'
import TreeGrid from './views/nav4/TreeGrid.vue'
import test from './views/nav4/model.vue'
import tree from './views/nav4/tree.vue'
import log from   './views/nav4/log.vue'
import permission from './views/nav4/permission.vue'
import role from './views/nav4/role.vue'
import test2 from './views/nav4/test2.vue'
import team from './views/nav4/team.vue'

let routes = [
    {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'el-icon-setting',//图标样式class
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            { path: '/setCheckors', component: setCheckors, name: '审核人员配置',hidden: true },
            { path: '/mySubmitDetail', component: mySubmitDetail, name: '我的提交详情',hidden: true },
        ]
    },
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-menu',
        leaf: true,//只有一个节点
        children: [
            { path: '/repository/index', component: Repository, name: '我的项目' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '我的项目',
        iconCls: 'el-icon-star-on',//图标样式class
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            { path: '/table', component: Table, name: '项目' },
            { path: '/form', component: Form, name: '模型' },
            { path: '/user', component: user, name: '仿真' },
            {path:'/template',component:template,name:'我的模板'},
            {path:'/checkor',component:checkor,name:'我的待办'},
            {path:'/mySubmit',component:mySubmit,name:'我的提交'},
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-document',
        leaf: true,//只有一个节点
        children: [
            { path: '/model/index', component: Model, name: '我的模型' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '我的仿真' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-share',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '我的分享' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-delete',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '回收站' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-delete',
        leaf: true,//只有一个节点
        children: [
            { path: '/Myorg', component: Myorg, name: '我的组织'},
            { path: '/Myorg/:name', component: team, name: '团队管理',hidden:true}
        ]
    },
     {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'el-icon-setting',//图标样式class
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            { path: '/test2', component: test2, name: '项目管理'
   //         ,children:[{    path:'/team',component: team, name:'团队管理'    }]
        },
            { path: '/tree', component: tree, name: '组织管理' },
            { path: '/TreeGrid', component: TreeGrid, name: '用户管理' },
             { path: '/GUser', component: GUser, name: 'Gos用户管理' },
            { path: '/role', component: role, name: '角色管理' },
            { path: '/permission', component: permission, name: '权限管理' },
            { path: '/user', component: user, name: '应用监控' },
            { path: '/log', component: log, name: '日志管理' },
            { path:'/team',component: team, name:'团队管理'  ,hidden:true  }

        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    },
    //gogs
    {
        path: '/',
        component: Gogs,
        name: '',
        iconCls: 'el-icon-menu',
        leaf: true,//只有一个节点
        children: [
            { path: '/repository/index', component: Repository, name: '我的项目' }
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '',
        iconCls: 'el-icon-document',
        leaf: true,//只有一个节点
        children: [
            { path: '/model/index', component: Model, name: '我的模型' }
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '我的仿真' }
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '',
        iconCls: 'el-icon-share',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '我的分享' }
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '我的收藏',
        iconCls: 'el-icon-star-on',//图标样式class
        children: [
            { path: '/main', component: Main, name: '展示' },
            { path: '/table', component: Table, name: '项目' },
            { path: '/form', component: Form, name: '模型' },
            { path: '/user', component: user, name: '仿真' },
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '',
        iconCls: 'el-icon-delete',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '回收站' }
        ]
    },
    {
        path: '/',
        component: Gogs,
        name: '系统管理',
        iconCls: 'el-icon-setting',//图标样式class
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            { path: '/table', component: Table, name: '项目管理' },
            { path: '/form', component: Form, name: '组织管理' },
            { path: '/user', component: user, name: '用户管理' },
            { path: '/user', component: user, name: '角色管理' },
            { path: '/user', component: user, name: '权限管理' },
            { path: '/user', component: user, name: '应用监控' },
            { path: '/user', component: user, name: '日志管理' },
        ]
    },
];

export default routes;