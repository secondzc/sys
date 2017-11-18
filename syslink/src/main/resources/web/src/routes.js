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
import FileContent from './views/nav3/index.vue'
import echarts from './views/charts/echarts.vue'
import Repository from './views/repository/index2.vue'
import Model from './views/model/index.vue'
import modelVariable from './views/model/modelVariable.vue' ;
import packageDiagram from './views/model/packageDiagram.vue'
import packageDiagramModel from './views/model/packageDiagramModel.vue'
import packageDiagramVariable from './views/model/packageDiagramVariable.vue'
import modelComponent from  './views/model/ModelComponent.vue'
import modelCont from './views/model/modelCont.vue'
import packageTransfModel from  './views/model/packageTransfModel.vue'

import template from './views/review/template.vue' 
import setCheckors from './views/review/setCheckors.vue' 
import mySubmitDetail from './views/review1/mySubmitDetail.vue' 
import checkor from './views/review/checkor.vue' 
import brief from './views/review1/brief.vue' 
import mySubmitAll from './views/review1/mySubmitAll.vue' 
import checkorAll from './views/review1/checkorAll.vue' 
import checkorDetail from './views/review1/checkorDetail.vue' 

let routes = [
      { 
          path: '/', 
          component: Home, 
          name: '审签管理', 
          iconCls: 'el-icon-setting',//图标样式class 
          children: [ 
              // { path: '/main', component: Main, name: '项目', hidden: true }, 
              { path: '/setCheckors', component: setCheckors, name: '审核人员配置',hidden: true }, 
              { path: '/mySubmitDetail', component: mySubmitDetail, name: '我的提交详情',hidden: true }, 
              {path:'/checkorDetail',component:checkorDetail,name:'审核详情',hidden:true}, 
          ] 
      }, 
      { 
          path: '/', 
          component: Home, 
          name: '我的审签', 
          iconCls: 'el-icon-star-on',//图标样式class 
          children: [ 
              // { path: '/main', component: Main, name: '项目', hidden: true }, 
              {path:'/template',component:template,name:'模板管理'}, 
              {path:'/brief',component:brief,name:'我的流程'}, 
              {path:'/checkorAll',component:checkorAll,name:'我的审核'}, 
              {path:'/mySubmitAll',component:mySubmitAll,name:'我的提交'}, 
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
            { path: '/repository/index2', component: Repository, name: '我的项目' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-document',
        leaf: true,//只有一个节点
        children: [
            { path: '/model/index', component: Model, name: '我的模型' },
            { path: '/model/packageTransfModel', component: packageTransfModel, name: '包转模型'},
            { path: '/model/modelCont', component: modelCont, name: '模型信息'},
            // { path: '/model/modelVariable', component: modelVariable, name: '模型参数' },
            { path: '/model/packageDiagram', component: packageDiagram, name: '包视图'},
            { path: '/model/packageDiagramModel', component: packageDiagramModel, name: '包视图组件信息' },
            { path: '/model/packageDiagramVariable', component: packageDiagramVariable, name: '包视图组件参数信息',
                iconCls: 'el-icon-document',
                leaf: true,//只有一个节点
                children: [
                    { path: '/model/ModelComponent', component: modelComponent, name: '模型组件树信息' },
                    ]
            },
            // { path: '/model/ModelComponent', component: modelComponent, name: '模型组件树信息' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        children: [
            // { path: '/page6', component: Page6, name: '我的仿真' }
            { path: '/nav3/index', component: FileContent, name: '我的仿真' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'el-icon-share',
        leaf: true,//只有一个节点
        children: [
            { path: '/page4', component: Page4, name: '我的分享' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '我的收藏',
        iconCls: 'el-icon-star-on',//图标样式class
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            { path: '/table', component: Table, name: '项目' },
            { path: '/form', component: Form, name: '模型' },
            { path: '/user', component: user, name: '仿真' },
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