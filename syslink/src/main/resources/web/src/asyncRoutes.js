
import NotFound from './views/404.vue'

import Main from './views/Main.vue'

import Model from './views/model/index.vue'
import myModel from './views/model/MyIndex.vue'
import packageDiagram from './views/model/packageDiagram.vue'
import myPackageDiagram from  './views/model/myPackageDiagram.vue'
import packageDetail from './views/model/packageDetail.vue'
import packageDiagramVariable from './views/model/packageDiagramVariable.vue'
import modelComponent from  './views/model/ModelComponent.vue'
import modelUpload from './views/nav3/UploadFile.vue'
import upload from './views/model/Upload.vue'
import GUser from './views/nav4/GUser.vue'

import log from   './views/nav4/log.vue'
import permission from './views/nav4/permission.vue'
import role from './views/nav4/role.vue'

import team from './views/nav4/team.vue'
import OrgMange from './views/nav4/OrgMange.vue'
import directoryAuth from './views/nav4/directoryAuth.vue'

import error401 from './views/401.vue'
import error404 from './views/404.vue'


import depart from './views/nav4/depart.vue'
import Sidebar from './views/Sidebar.vue'


import myRepo from './views/nav4/myRepo.vue'
import cRepo from  './views/nav4/cRepo.vue'
import changePassWd from './views/nav4/changePassWd.vue'
import personalInfo from './views/nav4/personalInfo.vue'


import LogOut from './views/LogOut.vue'
import NewHeader from './views/NewHeader.vue'
import NewLogin from './views/NewLogin.vue'
import NewHeader1 from './views/NewHeader1.vue'
import NewHeader2 from './views/NewHeader2.vue'
import blankPage from './views/blankPage.vue'


import Vue from 'vue'
import Router from 'vue-router'

import template from './views/review/template.vue' 
import setCheckors from './views/review/setCheckors.vue' 
import mySubmitDetail from './views/review1/mySubmitDetail.vue' 
import checkor from './views/review/checkor.vue' 
import brief from './views/review1/brief.vue' 
import mySubmitAll from './views/review1/mySubmitAll.vue' 
import checkorAll from './views/review1/checkorAll.vue' 
import checkorDetail from './views/review1/checkorDetail.vue' 


Vue.use(Router)



export let  commonRoutes = [
  
      {
        path: '',
        component: NewHeader,
        iconCls: 'iconfont icon-liucheng1',
        name: '我的流程',
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
              //{path:'/template',component:template,name:'模板管理'}, 
              {path:'/brief',component:brief,name:'我的待办',iconCls: 'iconfont icon-daibanshixiang'}, 
              {path:'/checkorAll',component:checkorAll,name:'我的审核',iconCls: 'iconfont icon-page1'}, 
              {path:'/mySubmitAll',component:mySubmitAll,name:'我的提交',iconCls: 'iconfont icon-tijiao'}, 

              { path: '/setCheckors', component: setCheckors, name: '审核人员配置',hidden: true }, 
              { path: '/mySubmitDetail', component: mySubmitDetail, name: '我的提交详情',hidden: true }, 
              {path:'/checkorDetail',component:checkorDetail,name:'审核详情',hidden:true}, 
          ] 
      },
    {
        path: '',
        component: NewLogin,
        name: '',
        hidden: true,
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            {path:'/login',component:NewLogin,name:'登录'}
        ]
    },

    {
        path: '',
        component: LogOut,
        name: '',
        hidden: true,
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            {path:'/logout',component:LogOut,name:'登出'}
        ]
    },
      {
        path: '',
        component: NewHeader,
        name: '个人设置',
        hidden: true,
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            {path:'/changePassWd',component:changePassWd,name:'修改密码'}
        ]
    },
      {
        path: '',
        component: NewHeader,
        name: '个人设置',
        hidden: true,
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            {path:'/personalInfo',component:personalInfo,name:'个人信息'}
        ]
    },

    {
        path: '',
        component: NewHeader1,
        name: '',
        hidden: true,
         children: [
           
            {path:'/404',component:error404,name:'无法找到'}
        
        ]
    },
    {
        path: '',
        component: NewHeader,
        name: '欢迎',
        hidden: true,
        children: [
            // { path: '/main', component: Main, name: '项目', hidden: true },
            {path:'/Myspace',component:error401,name:'我的通知'}
        
        ]
    },
    {
        path: '/Main',
        component: Main,
        name: '',
        hidden: true
    },
 
    {
        path: '',
        component: NewHeader1,
        name: '模型',
        iconCls: 'iconfont icon-moxing',
        leaf: true,//只有一个节点
        children: [
            { path: '/model/MyIndex', component: myModel, name: '我的模型' },
            { path: '/model/upload', component: upload, name: '模型上传' },
            {path: '/model/uploadFile',component: modelUpload, name : "模型上传"},
            { path: '/model/myPackageDiagram', component: myPackageDiagram, name: '包视图'},
            { path: '/model/packageDetail', component: packageDetail, name: '包内容'},
            { path: '/model/packageDiagramVariable', component: packageDiagramVariable, name: '包视图组件参数信息',
                iconCls: 'el-icon-document',
                leaf: true,//只有一个节点
                children: [
                    ]
            },
            // { path: '/model/ModelComponent', component: modelComponent, name: '模型组件树信息' },
        ]
    },
     {
        path: '',
        component: NewHeader1,
        name: '模型',
        iconCls: 'iconfont icon-moxing',
        leaf: true,//只有一个节点
        hidden:true,
        children: [
            { path: '/model/index', component: Model, name: '公共模型' },
            { path: '/model/upload', component: upload, name: '模型上传' },
            {path: '/model/uploadFile',component: modelUpload, name : "模型上传"},
            { path: '/model/packageDiagram', component: packageDiagram, name: '包视图'},
           
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
        path: '',
        component: NewHeader,
        name: '仓库管理',
        iconCls: 'iconfont icon-cangku',
      
   
        children: [
            // { path: '/page6', component: Page6, name: '我的仿真' }
            { path: '/myRepo', component: myRepo, name: '我的仓库',iconCls: 'iconfont icon-renwu' },
             { path: '/cRepo', component: cRepo, name: '协同仓库' ,iconCls: 'iconfont icon-renren'}
        ]
    },
  
    //   {
    //     path: '',
    //     component: corporate,
    //     name: '组织',
    //     iconCls: 'el-icon-delete',
    //     leaf: true,//只有一个节点
    //     children: [
    //         { path: '/Myorg', component: Myorg, name: '我的组织'},
    //         { path: '/Myorg/:orgName', component: OrgMange, name: '组织管理',hidden:true},
    //         { path: '/Myorg/:orgName/:teamName', component: team, name: '团队管理',hidden:true}
    //     ]
    // },
    { path: '/', component: NewHeader2, hidden: true,
      children: [
            { path: '/index', component: error401, name: '首页'}
          
        ] },
    

   
   
]


export let limitRoutes=[
     {
        path: '',
        component: NewHeader,
        name: '系统管理',
        iconCls: 'iconfont icon-shezhi',//图标样式class
        children: [
           
            // { path: '/tree', component: tree, name: '组织管理' ,meta:{role:['management_org_list']}},
            { path: '/depart', component: depart, name: '组织管理' ,iconCls:'iconfont icon-zuzhijiagoujiekou', meta:{auth:['management_org_list'],role:[1]}},
          //  { path: 'TreeGrid', component: TreeGrid, name: '用户管理' ,meta:{role:['management_user_list']}},
            { path: '/GUser', component: GUser, name: '用户管理' ,iconCls:'iconfont icon-hezuoguanxi', meta:{auth:['management_user_list'],role:[1]}},
            { path: '/role', component: role, name: '角色管理' ,iconCls:'iconfont icon-jiaosequnti', meta:{auth:['management_role_list'],role:[1]}},
            // { path: '/permission', component: permission, name: '权限管理' ,iconCls:'iconfont icon-suoding',  meta:{auth:['management_auth_list'],role:[1]} },
             { path: '',component:blankPage,  name: '权限管理' ,iconCls:'iconfont icon-suoding',  meta:{auth:['management_auth_list'],role:[1]}, children: [
            { path: '/permission', component: permission, name: '系统权限',iconCls:'iconfont icon-xitong'},
             { path: '/directoryAuth', component: directoryAuth, name: '目录控制',iconCls:'iconfont icon-guize'}
          
        ] },
         //   { path: 'user', component: user, name: '应用监控' },
            { path: '/log', component: log, name: '日志管理' ,iconCls:'iconfont icon-rili', meta:{auth:['management_log_list'],role:[1]}},
            // { path: '/tree/:orgName', component: OrgMange, name: '组织管理',hidden:true},
            // { path: '/tree/:orgName/:teamName', component: team, name: '团队管理',hidden:true}
            {path:'/template',component:template,name:'模板管理',iconCls: 'iconfont icon-moban',meta:{auth:['management_template_list'],role:[1]}}, 
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
]



 export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: commonRoutes
})

/****/
//export default commonRoutes;