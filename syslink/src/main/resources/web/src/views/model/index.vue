<template >
<<<<<<< HEAD
    <el-container style="height: 100%;">
=======
    <el-container>
>>>>>>> a894fdacd57791befddc286140ce4711aad8d62d
        <el-header style="height: 30px">
        </el-header>
    <el-container style="border: 0.1px solid #eee">
        <el-aside width="300px" style="border: 0.1px solid #000000">
          <!--模型分类树-->
            <el-container>
                <el-main>
                    <kz-tree :data="tree" @node-click="hanldeNodeClick" ></kz-tree>
                </el-main>
            </el-container>
          <!--</div>-->
        </el-aside>
        <el-main style="border: 0.1px solid #000000;margin-left: 20px;padding: 10px">
            <el-container>
                <p>{{getRepos}}</p>
                <el-header style="height: 40px;">
                    <el-row type="flex" justify="end">
                    <el-col :span="6">
                      <div style="display: inline-block;float: left;margin-top: 5px ">
                          <breadcrumb></breadcrumb>
                      </div>
                    </el-col>
                    <el-col :span="8" :offset="7">
                      <div id="searchMoudle">
                          <!--<p>aaaa</p>-->
                          <div style="display: inline-block;">
                              <!--工具条-->
                              <!--<el-col :span="24" class="toolbar" >-->
                              <el-form :inline="true" :model="filters">
                                  <el-form-item >
                                      <el-input v-model="filters.name" placeholder="模型名称"></el-input>
                                  </el-form-item>
                                  <el-form-item >
                                      <el-button type="primary" v-on:click="getModel">查询</el-button>
                                  </el-form-item>
                              </el-form>
                              <!--</el-col>-->
                          </div>
                      </div>
                    </el-col>
                    <el-col :span="3">
                      <div style="display: inline-block;float: right;margin-right: 10px;" >
                          <upload-file ></upload-file>
                      </div>
                    </el-col>
                    </el-row>
                </el-header>
                <hr style="width: 100%"/>
               <el-main style="padding: 5px">
                   <el-row type="flex" justify="end">
                   <el-col :span="12" >
                      <div style="display: inline-block;"  id="sortMoudle">
                          <div style="display: inline-block;"><p>排序：</p></div>
                          <div id="appp" style="display: inline-block;">

                              <sortable-list
                                      :titles="sorttitles"
                                      :list="repositories"
                                      :sorting-index='0'
                                      sorting-type="desc"
                                      @sort-finish="onSortFinsh"
                              >
                              </sortable-list>
                            </div>
                      </div>
                       <div style="display: none;"  id="modelNumb">
                           <div style="font-size:14px;" v-if="{amsg} == 0">
                               总共有个{{modelTotal}}模型库
                           </div>
                           <div v-else style="font-size:14px;">
                               该类下有{{modelTotal}}个模型库
                           </div>
                       </div>
                   </el-col>
                   <el-col :span="1" :offset="7">
                       <!--id="show-document-details"  svg-icon-grid-view  svg-icon-list-view -->
                       <!--<i class="iconfont" id="information" name="information" @click="viewInfo" style="border: inset;">&#xe6a4;</i>-->
                       <button  id="information" @click="viewInfo" type="button" class="btn btn-default active" ng-class="{'active': showDocumentDetails}" data-original-title="Document details" data-placement="bottom">
                           <svg class="os-icon-large os-document-details-toggle os-svg-icon" icon="document-details" bt-no-focus="" alt="Details">
                               <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#svg-icon-document-details">
                                   <svg viewBox="0 0 30 30" id="svg-icon-document-details" width="100%" height="100%">
                                   <path fill="#333" d="M15 6c-4.971 0-9 4.029-9 9s4.029 9 9 9 9-4.029 9-9-4.029-9-9-9zm0 16c-4.039 0-7-2.961-7-7s2.961-7 7-7 7 2.961 7 7-2.961 7-7 7zm-1-12h2v2h-2v-2zm3 10h-4v-1h1v-5h-1v-1h3v6h1v1z">
                                   </path></svg>
                               </use></svg>
                       </button>
                   </el-col>
              <!--<div style="height: 30px;display: inline-block;float: right;margin-right: 20px">-->
                   <el-col :span="3" :offset="1">
                       <!--<i class="iconfont" id="menuIcon" name="menuIcon" @click="switchMenu" style="border: inset;">&#xe60a;</i>-->
                       <div class="btn-group os-button-group">
                           <label @click="switchMenu"  data-original-title="List view" class="btn btn-default ng-untouched ng-valid ng-not-empty active ng-dirty ng-valid-parse" uib-btn-radio="viewModes.LIST" >
                               <svg class="os-svg-icon" icon="list-view"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#svg-icon-list-view">
                                   <svg viewBox="0 0 20 20" id="svg-icon-list-view"  width="100%" height="100%"><g fill="none" fill-rule="evenodd"><path d="M0 0h20v20H0z"></path><path d="M18 3.25v1.5a.23.23 0 0 1-.081.175.276.276 0 0 1-.192.075H6.273a.273.273 0 0 1-.192-.075A.23.23 0 0 1 6 4.75v-1.5a.23.23 0 0 1 .081-.175A.272.272 0 0 1 6.273 3h11.454c.073 0 .137.024.192.074A.23.23 0 0 1 18 3.25m0 4v1.5a.23.23 0 0 1-.081.176.276.276 0 0 1-.192.074H6.273a.272.272 0 0 1-.192-.074A.23.23 0 0 1 6 8.75v-1.5a.23.23 0 0 1 .081-.175A.273.273 0 0 1 6.273 7h11.454c.073 0 .137.025.192.075A.23.23 0 0 1 18 7.25m0 4v1.5a.231.231 0 0 1-.081.175.276.276 0 0 1-.192.074H6.273a.272.272 0 0 1-.192-.074A.231.231 0 0 1 6 12.75v-1.5c0-.068.027-.126.081-.175A.273.273 0 0 1 6.273 11h11.454c.073 0 .137.025.192.075.054.05.081.107.081.176M4 3.25v1.5a.24.24 0 0 1-.074.175A.24.24 0 0 1 3.75 5h-1.5a.242.242 0 0 1-.176-.075A.24.24 0 0 1 2 4.75v-1.5a.24.24 0 0 1 .074-.175A.241.241 0 0 1 2.25 3h1.5c.068 0 .127.024.176.074A.24.24 0 0 1 4 3.25m14 12v1.5a.23.23 0 0 1-.081.175.276.276 0 0 1-.192.075H6.273a.273.273 0 0 1-.192-.075A.23.23 0 0 1 6 16.75v-1.5a.23.23 0 0 1 .081-.175.273.273 0 0 1 .192-.075h11.454c.073 0 .137.025.192.075a.23.23 0 0 1 .081.175m-14-8v1.5a.24.24 0 0 1-.074.176A.238.238 0 0 1 3.75 9h-1.5a.241.241 0 0 1-.176-.074A.24.24 0 0 1 2 8.75v-1.5a.24.24 0 0 1 .074-.175A.242.242 0 0 1 2.25 7h1.5a.24.24 0 0 1 .176.075A.24.24 0 0 1 4 7.25m0 4v1.5a.24.24 0 0 1-.074.175.238.238 0 0 1-.176.074h-1.5a.241.241 0 0 1-.176-.074A.24.24 0 0 1 2 12.75v-1.5c0-.068.024-.126.074-.175A.242.242 0 0 1 2.25 11h1.5a.24.24 0 0 1 .176.075c.05.05.074.107.074.176m0 4v1.499a.24.24 0 0 1-.074.175.24.24 0 0 1-.176.075h-1.5a.242.242 0 0 1-.176-.075A.24.24 0 0 1 2 16.75v-1.5a.24.24 0 0 1 .074-.175A.242.242 0 0 1 2.25 15h1.5a.24.24 0 0 1 .176.075.24.24 0 0 1 .074.175" fill="#333"></path></g></svg>
                               </use></svg>
                           </label>
                           <label @click="switchList" data-original-title="Grid view" class="btn btn-default ng-untouched ng-valid ng-not-empty active ng-dirty ng-valid-parse" uib-btn-radio="viewModes.GRID" >
                               <svg class="os-svg-icon" icon="grid-view"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#svg-icon-grid-view">
                                   <svg viewBox="0 0 20 20" id="svg-icon-grid-view"   width="100%" height="100%"><g fill="none" fill-rule="evenodd"><path d="M0 0h20v20H0z"></path><path d="M18 4.2v3.6c0 .325-.116.606-.347.843-.23.238-.504.357-.82.357h-4.666c-.316 0-.59-.119-.82-.357A1.171 1.171 0 0 1 11 7.8V4.2c0-.324.116-.606.347-.843.231-.238.504-.357.82-.357h4.666c.317 0 .59.119.82.357.231.237.347.519.347.843m0 8v3.6c0 .324-.116.606-.347.843-.23.238-.504.356-.82.357h-4.666c-.316 0-.59-.119-.82-.357A1.171 1.171 0 0 1 11 15.8v-3.6c0-.325.116-.606.347-.843.231-.238.504-.357.82-.357h4.666c.317 0 .59.119.82.357.231.237.347.519.347.843m-9-8v3.6c0 .325-.116.606-.347.843a1.106 1.106 0 0 1-.82.357H3.167c-.317 0-.59-.119-.82-.357A1.167 1.167 0 0 1 2 7.8V4.2c0-.324.116-.606.347-.843.23-.238.504-.357.82-.357h4.666c.316 0 .59.119.82.357.231.237.346.519.347.843m0 8v3.6c0 .324-.116.606-.347.843a1.11 1.11 0 0 1-.82.357H3.167c-.317 0-.59-.119-.82-.357A1.167 1.167 0 0 1 2 15.8v-3.6c0-.325.116-.606.347-.843.23-.238.504-.357.82-.357h4.666c.316 0 .59.119.82.357.231.237.346.519.347.843" fill="#333"></path></g></svg>
                               </use></svg>
                           </label>
                       </div>
                   </el-col>
                   <!--<el-col :span="1.5">-->
                       <!--<i class="iconfont" id="listIcon" name="listIcon" @click="switchList" style="border: outset;">&#xe60b;</i>-->
                   <!--</el-col>-->
                   </el-row>
              <!--</div>-->
               </el-main>
                <hr style="width: 100%"/>
                <el-footer>
                    <el-row type="flex" >

                        <el-col :span='24' id="girdTable" >
                            <!--<packageList></packageList>-->

                            <template >
                                <section>
                                            <div id="packageList" style="display: none">
                                            <el-table
                                                    ref="singleTable"
                                                    :data="repositories"
                                                    border
                                                    @current-change="handleCurrentChange"
                                                    :default-sort = "{prop: 'createTime',prop:'name', order: 'descending'}"
                                                    style="width: 100%">
                                                <el-table-column
                                                        label="名称"
                                                        prop="name"
                                                        width="150" sortable>
                                                    <template scope="scope">
                                                        <div style="display: inline-block">
                                                            <img v-bind:src="scope.row.imageUrl" style="width: 60px;height: 60px;"/>
                                                        </div>
                                                        <div style="display: inline-block">
                                                            <el-popover trigger="hover" placement="top">
                                                                <p>名称: {{ scope.row.name }}</p>
                                                                <!--<p>住址: {{ scope.row.address }}</p>-->
                                                                <div slot="reference" class="name-wrapper">
                                                                    <el-tag>{{ scope.row.name }}</el-tag>
                                                                </div>
                                                            </el-popover>
                                                        </div>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column
                                                        label="日期"
                                                        prop="createTime"
                                                        width="150" sortable>
                                                    <template scope="scope">
                                                        <el-icon name="time"></el-icon>
                                                        <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
                                                    </template>
                                                </el-table-column>


                                                <el-table-column
                                                        label="模型库"
                                                        prop="repositoryName"
                                                        width="180" >
                                                    <template scope="scope">
                                                        <span style="margin-left: 10px">{{ scope.row.repositoryName }}</span>
                                                    </template>
                                                </el-table-column>

                                                <el-table-column
                                                        label="上传者"
                                                        prop="userName"
                                                        width="120" >
                                                    <template scope="scope">
                                                        <span style="margin-left: 10px">{{ scope.row.userName }}</span>
                                                    </template>
                                                </el-table-column>

                                                <el-table-column
                                                        label="描述"
                                                        prop="discription"
                                                        width="120" >
                                                    <template scope="scope">
                                                        <span style="margin-left: 10px">{{ scope.row.discription }}</span>
                                                    </template>
                                                </el-table-column>

                                                <el-table-column label="操作">
                                                    <template scope="scope">
                                                        <el-button
                                                                size="small"
                                                                type="primary"
                                                                @click="handleEdit(scope.$index, scope.row)">查看</el-button>
                                                        <el-button
                                                                size="small"
                                                                type="primary"
                                                                @click="handleDownload(scope.$index, scope.row)">下载</el-button>
                                                        <el-button
                                                                size="small"
                                                                type="danger"
                                                                @click="handleDeleted(scope.$index, scope.row)">删除</el-button>
                                                    </template>
                                                </el-table-column>
                                            </el-table>
                                            <el-pagination
                                                    @size-change="handleSizeChange"
                                                    @current-change="handleCurrent"
                                                    :current-page="pager.pageIndex"
                                                    :page-sizes="[10]"
                                                    :page-size="pager.pageSize"
                                                    layout="total, sizes, prev, pager, next, jumper"
                                                    :total="pager.total">
                                            </el-pagination>
                                    </div>


                                    <!--style="display: block;border: solid 2px #B0C4DE;"-->
                                    <div id="modelList" >
                                        <waterfall style="overflow-x: auto!important;overflow-y: auto!important;"
                                                :align="align"
                                                :line-gap="300"
                                                :min-line-gap="100"
                                                :max-line-gap="300"
                                                :single-max-width="260"
                                                :watch="repositories"
                                                @reflowed="reflowed"
                                                ref="waterfall"
                                        >
                                            <!-- each component is wrapped by a waterfall slot -->
                                            <waterfall-slot
                                                    v-for="(item, index) in repositories"
                                                    :width="250"
                                                    :height="300"
                                                    :order="index"
                                                    :key="item.index"
                                                    move-class="item-move"
                                            >
                                                <!-- :style="item.style" -->
                                                <div class="RepCard"   :index="item.index" v-on:dblclick="modelVar(item)" @click="modelVariable(item)">
                                                    <div class="center-aligned">
                                                        <img v-bind:src="item.imageUrl" class="image">
                                                    </div>
                                                    <div class="content">
                                                        <a href="javascript:void(0)">
                                                            <h4 >模型名称：{{item.name}}</h4>
                                                        </a>
                                                        <h4>模型库：{{item.repositoryName}}</h4>
                                                        <div >上传者：{{item.userName}}</div>
                                                        <div>
                                                            <div style="display: inline-block">
                                                                <!--<i class="iconfont_star"  style="border: inset;">&#xe6ed;</i>-->
                                                                <!--<div class="ui labeled button" tabindex="0">-->
                                                                    <a class="ui basic button" @click="addWatch(item)">
                                                                        <i class="iconfont_star"  >&#xe6ed;</i>
                                                                        {{item.numberWatch}}
                                                                    </a>
                                                                    <!--<a class="ui basic label" >-->

                                                                        <!--1-->
                                                                    <!--</a>-->
                                                                <!--</div>-->
                                                            </div>
                                                            <div style="display:inline-block;">
                                                                <!--<div class="ui labeled button" tabindex="0">-->
                                                                    <a class="ui basic button" @click="addStar(item)">
                                                                        <i class="iconfont_star" >&#xe733;</i>
                                                                        {{item.numberStar}}
                                                                    </a>
                                                                    <!--<a class="ui basic label" >-->
                                                                        <!--1-->
                                                                    <!--</a>-->
                                                                <!--</div>-->

                                                            </div>
                                                        </div>
                                                        <!--<h4>上传日期：{{item.createTime}}</h4>-->
                                                        <div >描述：{{item.discription}}</div>
                                                    </div>
                                                    <!--<div  class="cardButton">-->
                                                    <!--<a class="user username" href="javascript:void(0)">-->
                                                    <!--&lt;!&ndash;//TODO:用户名&ndash;&gt;-->
                                                    <!--{{item.type}}-->
                                                    <!--</a>-->
                                                    <!--<div class="right-buttons">-->
                                                    <!--</div>-->
                                                    <!--</div>-->
                                                </div>
                                            </waterfall-slot>
                                        </waterfall>

                                    </div>

                                </section>
                            </template>



                        </el-col>
              <!--模型列表-->

              <!--<div style="width: 900px;height:377px;">-->

                        <!--<el-col :span="24" id="modelList" style="display: block;">-->
                                  <!--<model-list ></model-list>-->
                                          <!--<div>-->
                                              <!--<v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>-->
                                          <!--</div>-->

                        <!--</el-col >-->

                          <el-col :span="6" style="display: none;border: 2px solid #B0C4DE" id="variable" >
                              <template>
                                  <section>
                                      <el-row>
                                          <el-col :span="24" v-for="(o, index) in variable" :key="o" :offset="index > 0 ? 2 : 0">
                                              <el-card :body-style="{ padding: '0px' }">
                                                  <img v-bind:src="o.imageUrl" class="image">
                                                  <div style="padding: 14px;">
                                                      <h4>模型：{{o.name}}</h4>
                                                      <h4>模型库：{{o.repositoryName}}</h4>
                                                      <h4>上传者：{{o.userName}}</h4>
                                                      <h4>上传日期：{{o.createTime}}</h4>
                                                      <div class="bottom clearfix">
                                                          <!--<time class="time">{{ currentDate }}</time>-->
                                                          <el-button type="text" class="button" @click="viewInfo">关闭</el-button>
                                                      </div>
                                                  </div>
                                              </el-card>
                                          </el-col>
                                      </el-row>
                                  </section>
                              </template>
                          </el-col>
                    </el-row>
                </el-footer>
            </el-container>
        </el-main>
    </el-container>
    </el-container>
</template>

<script>
import kzTree from './directory.vue';
import uploadFile from  '../nav3/Page6.vue'
import breadcrumb from '../nav3/breadcrumb.vue'
import {mapActions} from 'vuex'

import { getReposList} from '../../api/api'
//waterfall瀑布流组件
import Waterfall from 'vue-waterfall/lib/waterfall'
import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'
import { mapState,mapGetters} from 'vuex'
import sortableList from './sortable-list'
import Vuetify from 'vuetify';
import ElContainer from "../../../node_modules/element-ui/packages/container/src/main";
import ElHeader from "../../../node_modules/element-ui/packages/header/src/main";
import ElCol from "element-ui/packages/col/src/col";
import ElMain from "../../../node_modules/element-ui/packages/main/src/main";
import ElFooter from "../../../node_modules/element-ui/packages/footer/src/main";
import '../../assets/iconCss/onshape.bfe1aab6.css'
import '../../assets/iconCss/onshape.bfe1aab5.css'


export default {
  name: 'ModelContent',
  components: {
      ElFooter,
      ElMain,
      ElCol,
      ElHeader,
      ElContainer,
      kzTree,
      uploadFile,
      breadcrumb,
      Waterfall,
      WaterfallSlot,
      sortableList,
      Vuetify
  },
  data () {
    return {
        modelTotal :'',
        pager:{
            total:100,
            pageSize:10,
            pageIndex:1,
        },
        variable : [],
        drawer: false,
        sorttitles:[{
            key:'name',
            name:'名称'
        },{
            key:'uploadTime',
            name:'上传时间'
        },{
            key:'userName',
            name:'作者'
        },],
      tree: {
        url: {
//          C: '/directory/add',
//          U: '/directory/update',
//          R: '/directory/list',
//          D: '/directory/delete'
            C: '/api/directory/add',
            U: '/api/directory/update',
            R: 'api/directory/list',
            D: '/api/directory/delete'
        }
      },
      data :{
          treeItem : "",
      },
        filters:{
            name : ""
        },
        loading: false,
        align:'left',
        repositories:[
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.AerodynamicMoment.icon.svg","index":851,"name":"AerodynamicMoment","parentId":850,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.icon.svg","index":860,"name":"Components","parentId":862,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.AerodynamicMoment.icon.svg","index":851,"name":"AerodynamicMoment","parentId":850,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.icon.svg","index":860,"name":"Components","parentId":862,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.AerodynamicMoment.icon.svg","index":851,"name":"AerodynamicMoment","parentId":850,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.icon.svg","index":860,"name":"Components","parentId":862,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.AerodynamicMoment.icon.svg","index":851,"name":"AerodynamicMoment","parentId":850,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.icon.svg","index":860,"name":"Components","parentId":862,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.AerodynamicMoment.icon.svg","index":851,"name":"AerodynamicMoment","parentId":850,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"},
//            {"createTime":"2017-10-25","imageUrl":"http://gogs.modelica-china.com:8080/FileLibrarys\\FileLibrary\\xieyx\\2017-10-25_15-10-110\\syslink\\src\\test\\ModelDomLibrary/AircraftLib.Aerodynamics.Components.icon.svg","index":860,"name":"Components","parentId":862,"repositoryName":"AircraftLib","total":2,"type":"Modelica","uploadTime":1508860800000,"userName":"xyx"}
            ],
        isBusy: false,
    }
  },
    computed:{
        ...mapState({
            a:state =>state.a
        }),
        ...mapGetters(['amsg']),
        getRepos(){
            var _this = this;
            let para = {
                pageSize : this.pager.pageSize,
                pageIndex : this.pager.pageIndex
            };
            if(_this.amsg != null && _this.amsg != ""  ){
                var url = '/api/model/list?parent_id='+ _this.amsg
            }else{
                _this.amsg = 0;
                var url = '/api/model/list?parent_id='+ _this.amsg
            }
            _this.$http.post(url)
                .then(function (response) {
//                    _this.repositories = response.data.repositories;
                    _this.pager.total = response.data.repositories.length;
                    _this.modelTotal = response.data.repositories.length;
                    var filterModel = response.data.repositories.filter(
                        (u,index)=>{
                            if(index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)){
                                return true
                            }
                        }
                    )
                    _this.repositories = filterModel;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
  methods: {
    hanldeNodeClick (data) {
      //该目录下的模型列表
        this.treeItem = data;
//        this.$emit("node-click",data)
        this.$store.dispatch('sendA',data.parent_id);
        if(data.parent_id <= 0){
            this.$store.dispatch('sendB',-1);
        }
        else{
            this.$store.dispatch('sendB',data.parent_id);
        }
    },
      switchList(){
//          $("#searchMoudle")[0].style.display = "inline-block";
          $("#sortMoudle")[0].style.display = "inline-block";
          $("#modelNumb")[0].style.display = "none";
          document.getElementById("modelList").style.display="block";
          document.getElementById("packageList").style.display="none";
            this.getModel();
      },
      switchMenu(){
//          $("#searchMoudle")[0].style.display = "none";
          $("#sortMoudle")[0].style.display = "none";
          $("#modelNumb")[0].style.display = "inline-block";
          document.getElementById("packageList").style.display="block";
          document.getElementById("modelList").style.display="none";
          if($("#variable")[0].style.display == "block"){
              $("#variable")[0].style.display = "none";
              $("#girdTable")[0].style.width = "100%";

          }
      },
      //获取用户列表
      getModel() {
          let para = {
              name: this.filters.name,
              pageSize : this.pager.pageSize,
              pageIndex : this.pager.pageIndex
          };
          var _this = this;
          if(_this.amsg != null && _this.amsg != ""){
              var url = '/api/model/list?parent_id='+ _this.amsg
          }else{
              _this.amsg = 0;
              var url = '/api/model/list?parent_id='+ _this.amsg
          }
          _this.$http.post(url)
              .then(function (response) {
                  var searchModel = response.data.repositories.filter(model =>{
                      if(para.name && model.name.indexOf(para.name) == -1 ){
                              return false
                          }
                          else{
                              return true
                          }
                  })
                  var filterModel = searchModel.filter(
                      (u,index)=>{
                          if(index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)){
                              return true
                          }
                      }
                  )
                  _this.pager.total = searchModel.length;
                  _this.repositories = filterModel;
              })
              .catch(function (error) {
                  console.log(error);
              });
      },
      reflowed: function () {
          this.isBusy = false
      },
      modelVar : function (item) {
          this.$store.dispatch('sendModelId',item.parentId);
          this.$store.dispatch('sendTreeModelId',item.index);
          this.$router.push({path: '/model/packageDiagram'});
      },
      modelVariable : function (model){
          var modelVariable = new Array;
          modelVariable.push(model);
          this.variable = modelVariable;
          if(model == null){
              $("#variable")[0].style.display = "none";
          }
          console.log(model);
      },
      onSortFinsh(repositories){
          console.log('onSortFinsh');
          console.log(JSON.stringify(repositories));
          this.list=repositories
      },
      viewInfo(){
          if($("#variable")[0].style.display == "block"){
              $("#variable")[0].style.display = "none";
//              $("#modelList")[0].style.width = "100%";
//              $("#packageList")[0].style.width = "100%";
              $("#girdTable")[0].style.width = "100%";

          }
          else if($("#variable")[0].style.display == "none"){
//              $("#modelList")[0].style.width = "100%";
//              $("#packageList")[0].style.width = "100%";
              $("#girdTable")[0].style.width = "75%";
              if(this.variable.length != 0){
                  $("#variable")[0].style.display = "block";
              }
          }
      },
      handleCurrentChange(val) {
          this.$refs.singleTable.setCurrentRow(val);
          var modelVariable = new Array;
          modelVariable.push(val);
          this.variable = modelVariable;
          console.log(val);
      },
      handleCurrent(val) {
          console.log(`当前页: ${val}`);
          var filterModel = this.repositories.filter(
              (u,index)=>{
                  if(index < val * para.pageSize && index >= para.pageSize * (val - 1)){
                      return true
                  }
              }
          )
          _this.repositories = filterModel;
      },
      handleSizeChange(val){
          console.log(`每页 ${val} 条`);
          this.pager.pageSize = val;
          var filterModel = this.repositories.filter(
              (u,index)=>{
                  if(index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)){
                      return true
                  }
              }
          )
          _this.repositories = filterModel;
      },
      handleEdit(index, row) {
          console.log(index, row);
          this.$store.dispatch('sendModelId',row.parentId);
          this.$store.dispatch('sendTreeModelId',row.index);
          this.$router.push({path: '/model/packageDiagram'});
      },
      handleDownload(index, row){
          console.log(index, row);
          var _this = this;
          var url = '/api/model/download?modelId='+ row.index;
          _this.$http.post(url)
              .then(function (response) {
                  location.href= response.data.data;
              }).catch(function (error) {
              console.log(error);
          });
      },
      handleDeleted(index,row){
          console.log(index, row);
          var _this = this;
          var url = '/api/model/deleted?modelId='+ row.parentId;
          _this.$http.post(url)
              .then(function (response) {
                  if(response.data.msg == "ok"){
                      _this.$message({
                          message: '删除成功！',
                          type: 'warning',
                          duration: 2000
                      });
                      _this.getModel();
                  }
                  else{
                      _this.$message({
                          message: '删除失败！',
                          type: 'warning',
                          duration: 2000
                      });
                  }
              }).catch(function (error) {
              console.log(error);
          });
      },
      addStar(item){

      },
      addWatch(item){
          console.log(item);
          var _this = this;
          var url = '/api/watch/add?userId='+ item.userId +"&repoName=" +item.repositoryName
          _this.$http.post(url)
              .then(function (response) {
                  if(response.data.msg == "ok"){
                      _this.getModel();
                  }
              }).catch(function (error) {
              console.log(error);
          });
      }
  }
}
</script>
<style lang="stylus">
    /*@import '../../../node_modules/vuetify/src/stylus/main.styl';*/
    /*@import '../../styles/main.css';*/
.left {
  max-width: 400px;
  float: left;
}

.right{
    /*min-width: 750px;*/
    width: 75%;
    float : left;
    height: 100%
}
.dir{
    color: #666;
    height: 28px;
    line-height: 28px;
    font-size: 12px;
    position: relative;
    padding-top: 16px;
    padding-right: 28px;
    float: right;
}

.iconfont{
    font-family:"iconfont";
    font-size:24px;
    font-style:normal;
}

.iconfont_star{
    font-family:"iconfont";
    font-size:16px;
    font-style:normal;
}
@font-face {
    font-family: 'iconfont';  /* project id 445633 */
    src: url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.eot');
    src: url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.eot?#iefix') format('embedded-opentype'),
            url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.woff') format('woff'),
            url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.ttf') format('truetype'),
            url('//at.alicdn.com/t/font_445633_4mr7tossw8gjh5mi.svg#iconfont') format('svg');
}
.modelContent{
    height: 100%;
}




.RepCard {
    position: absolute;
    top: 10px;
    left: 10px;
    right: 10px;
    bottom: 10px;
    background-color: #FFF;
    border-radius: 5px;
    box-shadow: 0 2px 3px 0 #d4d4d5, 0 0 0 1px #ededed;
    transition: all 1s ease 0s;
    /*overflow: hidden;*/
}
.image{
    display: block;
    width: 100%;
    /*需要对高度做限制*/
    max-height: 140px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    transition: all 1s ease 0s;
}
.RepCard .content{
    padding: 6px 15px 6px 15px;
    height: 120px;
    overflow: hidden;
}
.RepCard .content h4{
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: keep-all;
    white-space: nowrap;
    display: block;
    font-weight: bold;
    line-height: 18px;
    margin-top: 7px;
    margin-bottom: 5px;
}
.RepCard .content a{
    color: #60BDE8;
    text-decoration: none;
}
.RepCard .content small {
    color: #838383;
    padding-bottom: 10px;
    font-size: 85%;
}
.RepCard .cardButton {
    margin-top: .5rem;
    padding: .5rem;
    text-align: left;
    background-color: #f8f8f8;
}
.username {
    text-transform: capitalize;
    color: #888;
}
.avatar.round {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    border: 1px solid rgba(139,191,229,.5);
}
.item-move {
    transition: all .5s cubic-bezier(.55,0,.1,1);
    -webkit-transition: all .5s cubic-bezier(.55,0,.1,1);
}

    /*排序*/
#appp {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    /*margin-top: 60px;*/
    max-width: 400px;
    min-width: 300px;
    margin: auto;
}

.item{
    cursor: pointer;
    position: relative;
    /*height: 11.625rem;*/
    /* 465px */
    /*background: #eaeaea;*/
}
.item>img{
    position: absolute;
    left: .7rem;/* 28px */
    top: .7rem;/* 28px */
    width: 7.75rem;/* 310px */
    height: 10.45rem;/* 418px */
    border-bottom: 1px solid #ddd;
    background: #eaeaea;
}

.text-container{
    position: absolute;
    left: 9.65rem;/* 386px */
    text-align: left;
    color: #999
}

.text-container >div{
    margin-bottom: 1.25rem;/* 50px */
}
.text-container .title{
    font-weight: bold;
    margin-top: 2rem;/* 80px */
    color: black
}

.item-complete-move{
    transition: transform 1s;
    -moz-transition: transform 1s;
    -webkit-transition: transform 1s;
    -o-transition: transform 1s;
}
/*排序*/

/*卡片    */
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
    /*卡片    */


</style>