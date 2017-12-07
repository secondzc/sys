<template>

    <el-container class="main-container">
      <!--   <el-header  class="top-header">
            <span>标题栏</span>
        </el-header> -->
      <!--   <el-header class="middle-header">
            <span>#############</span>
        </el-header> -->
        <el-header  class="bottom-header" >
            <div style="padding: 10px;min-width: 200px;max-height: 50px;">

                <!--<el-button type="primary" size="small"  style="float: left;">添加分类</el-button>-->
                <!--<el-button type="primary" size="small"  style="float: left;">上传</el-button>-->



                


                      <div style="position: absolute;left: 20px;display: inline-flex;
                      min-width: 200px;">
                         <!--<upload-file ></upload-file>-->
                          <!--<myUpload></myUpload>-->
                          <el-button slot="trigger" size="small" type="primary" style="font-size: 12px;" @click="isSelectModel">上传文件</el-button>

                          <el-dialog
                                  title="上传压缩文件"
                                  :visible.sync="file.dialogVisible"
                                  width="30%"
                                  >
                              <!--<span>这是一段信息</span>-->
                              <myUpload @refreshMyModel="getModel" style="text-align: center;" ></myUpload>
                              <!--<span slot="footer" class="dialog-footer">-->
                              <!--<el-button @click="file.dialogVisible = false">取 消</el-button>-->
                              <!--<el-button type="primary" @click="file.dialogVisible = false">确 定</el-button>-->
                              <!--</span>-->
                          </el-dialog>

                      </div>

                      <div style="position: absolute;left: 100px;display: inline-flex;min-width: 200px;">
                          <el-button size="small"  type="primary" @click="treeAdd({ id: privateDirId })"  style="margin-left: 30px;">增加分类 <i class="el-icon-plus el-icon--right"></i></el-button>
                      </div>

                     

                     
                   <p>{{getRepos}}</p>
                    
                    <div style="position: absolute;left: 270px;display: inline-flex;
                    min-width: 300px;line-height: 30px">
                        <span> 当前分类:</span>
                          <breadcrumb ></breadcrumb>
                    </div>
                  
             




                  <div style="position: absolute;right: 50px;">
                             <el-button-group  >
                                 <el-tooltip class="item" effect="dark" content="列表视图" placement="top-start">
                    <el-button  icon="el-icon-tickets" size="small"  @click="listStatus=true"></el-button>
                </el-tooltip>
                 <el-tooltip class="item" effect="dark" content="卡片视图" placement="top-start">
                    <el-button  icon="el-icon-menu"  size="small" @click="listStatus=false"></el-button>
                </el-tooltip>

                </el-button-group>
                 <el-tooltip class="item" effect="dark" content="详细信息" placement="top-start">
                <el-button icon="el-icon-info"    size ="small"
                           @click="info=!info" ></el-button>
                       </el-tooltip>
                  </div>
         
            </div>

        </el-header>
        <el-container style="border-top:solid 1px #e7e7e7 ;height: 100%;overflow-y: hidden;">
            <el-aside class="left-aside">


               <div style="display: inline-block;height: 50px; overflow: hidden;" id="searchMoudle">
                  <!--<p>aaaa</p>-->
                  <div style="display: inline-block;">
                      <!--工具条-->
                      <!--<el-col :span="24" class="toolbar" >-->
                      <el-form :inline="true" :model="filters">
                         
                          <el-form-item style="margin-top: 5px;margin-left: 10px;">
                            
                              <el-input placeholder="模型名称" v-model="filters.name" class="input-with-select">
  
                        <el-button slot="append" icon="el-icon-search"  v-on:click="getModel"></el-button>
                       </el-input>
                      </el-form-item>
                      </el-form>
                      <!--</el-col>-->
                  </div>
              </div>

                <kz-tree :data="tree" @node-click="hanldeNodeClick" class="left-tree" ></kz-tree>
              

            </el-aside>
            <el-main class="list-main" v-show="listStatus">

           
<!-- 
                <template >
                    <section>
                        <div id="packageList" style="display: none"> -->

                        
                              <div style="height: 100%; overflow-y: hidden;display: flex;flex-direction: column;flex-wrap: nowrap;">
                                <el-table
                                    ref="singleTable"
                                    :data="repositories"
                                    height="100%"
                                
                                    @current-change="handleCurrentChange"
                                    :default-sort = "{prop: 'createTime',prop:'name', order: 'descending'}"
                                    style="width: 100%">
                                <el-table-column
                                        label=""
                                        width="150" >
                                    <template scope="scope">
                                       
                                            <img v-bind:src="scope.row.imageUrl" style="width: 150px;height: 100px;"/>
                                        
                                        
                                    </template>
                                </el-table-column>

                                <el-table-column  label="名称"
                                        prop="name"
                                        min-width=100
                                   sortable>
                                 
                                </el-table-column>
                                <el-table-column
                                        label="创建日期"
                                        prop="createTime"
                                        min-width=100
                                         sortable>
                                    
                                </el-table-column>
                                <el-table-column
                                        label="修改日期"
                                        prop="updateTime"
                                        min-width=100
                                         sortable>
                                  
                                </el-table-column>


                                <el-table-column
                                        label="类型"
                                        prop="classes"
                                        min-width=100
                                         >
                                
                                </el-table-column>

                                <el-table-column
                                        label="上传者"
                                        prop="userName"
                                        min-width=100
                                         >
                                   
                                </el-table-column>

                                <el-table-column
                                        label="描述"
                                        prop="discription"
                                         >
                                   
                                </el-table-column>

                                <el-table-column min-width=150 label="操作">
                                    <template scope="scope">
                                       <!--  <el-button
                                                size="small"
                                                type="primary"
                                                @click="handleEdit(scope.$index, scope.row)">查看</el-button> -->
                                        <!--<el-button-->
                                        <!--size="small"-->
                                        <!--type="primary"-->
                                        <!--@click="handleDownload(scope.$index, scope.row)">下载</el-button>-->
                                      <!--   <el-button
                                                size="small"
                                                type="danger"
                                                @click="handleDeleted(scope.$index, scope.row)">删除</el-button> -->


                                    <el-button-group>
                                        <el-tooltip class="item" effect="dark" content="查看" placement="top-start">
                                         <el-button type="primary" 
                                     size="small"
                                     icon="el-icon-search"   @click="handleEdit(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>
                                    
                                  <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                                  <el-button   size="small" type="danger" icon="el-icon-delete"   @click="handleDeleted(scope.$index, scope.row)"></el-button>
                                    </el-tooltip>
                                     </el-button-group>
                                    </template>
                                </el-table-column>
                            </el-table>
                             
                                

                            <el-pagination
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrent"
                                    :current-page="pager.pageIndex"
                                    :page-sizes="[10,30,50,100]"
                                    :page-size="pager.pageSize"
                                    layout="total, sizes, prev, pager, next, jumper"
                                    :total="pager.total"  style="max-height: 40px;min-height: 30px;">
                            </el-pagination>
                           </div>
                            
                           
                   <!--      </div> -->


                        <!--style="display: block;border: solid 2px #B0C4DE;"-->
                <!--         <div id="modelList" style="display: block;"  >
                           
                        </div>
                    </section>
                </template> -->

            </el-main>


            <el-main class="card-main" v-show="!listStatus">
                <div style="overflow-y: hidden;border-bottom: solid 1px  #e7e7e7;height: 30px;">
                    <!--<span>排序</span>-->
                    <div style="display: inline-block;line-height: 30px;"><p>排序：</p></div>
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
                   <!--  <div style=""  id="modelNumb">
                        <div style="font-size:14px;" v-if="this.amsg == 0">
                            总共有个{{modelTotal}}模型库
                        </div>
                        <div v-else style="font-size:14px;">
                            该类下有{{modelTotal}}个模型库
                        </div>
                    </div> -->
                </div>
                <div  style="display: flex;flex-direction: column;height:inherit;width: auto;">

                    <div  style=" overflow-x: hidden;overflow-y: auto;justify-content: center;
              display: flex;flex-wrap: wrap;height:calc(100% - 66px);border-bottom: solid 1px #e6e6e6;">
                    

                    
                                    <el-card class="Card" style="height: 350px;width: 250px; margin: 12px;"   v-for="(o, index) in repositories" :key="o.id" :offset="index > 0 ? 2 : 0" 
                                    >
                                       <div slot="header"  style="width: inherit;height: inherit;">
                                   <span style="font-weight: bold;">{{o.name}}</span>
    
                                 </div>
                             
                                        <div :index="o.index" v-on:dblclick="modelVar(o)" @click="modelVariable(o)" >
                                            <div style="border-bottom:  solid 1px #e6e6e6;margin-top:  -10px;" >
                                                <img v-bind:src="o.imageUrl" style="height: 160px;width: 200px;margin-bottom: 10px;">
                                            </div>
                                            <div style="margin-top: 10px;"   >
                                              <!--   <h4 >模型名称：{{o.name}}</h4> -->
                                               <!--  <h4>模型库：{{o.repositoryName}}</h4>
                                                <div >上传者：{{o.userName}}</div> -->
                                                 <div><span>模型库：{{o.repositoryName}}</span></div>
                                                  <div><span>上传者：{{o.userName}}</span></div>
                                                   <div><span>上传日期：{{o.createTime}}</span></div>
                                                <div>
                                                  
                                                
                                               
                                                    <div style="display: inline-block">
                                                        <a class="ui basic button" @click="addWatch(item)">
                                                            <i v-if="o.alreadyWatch == true" class="iconfont icon-guanzhu"  ></i>
                                                            <i v-else="o.alreadyWatch == false" class="iconfont icon-quxiaoguanzhu01"  ></i>
                                                            {{o.numberWatch}}
                                                        </a>
                                                    </div>
                                                    <div style="display:inline-block;">
                                                        <a class="ui basic button" @click="addStar(item)">
                                                            <i v-if="o.alreadyStar == true" class="iconfont icon-guanzhu3" ></i>
                                                            <i v-else="o.alreadyStar == false" class="iconfont icon-guanzhu4"  ></i>
                                                            {{o.numberStar}}
                                                        </a>
                                                    </div>
                                                </div>
                                                <!--<h4>上传日期：{{item.createTime}}</h4>-->
                                                <!--<div >描述：{{item.discription}}</div>-->
                                            </div>
                                        </div>
                                    </el-card>
                           


                    </div>
                  
                       <el-pagination
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrent"
                                    :current-page="pager.pageIndex"
                                    :page-sizes="[10,30,50,100]"
                                    :page-size="pager.pageSize"
                                    layout="total, sizes, prev, pager, next, jumper"
                                    :total="pager.total"  style="min-height: 30px;max-height: 40px;">
                            </el-pagination>
                </div>

              

            </el-main>
            <el-aside class="right-aside" v-show="info">
                <div v-if="varLength == 0" style="height: inherit;">
                    <div style="height: inherit;overflow-y: hidden;">
                    <el-card :body-style="{ padding: '0px' }" style="height: inherit;overflow-y: auto;">
                    <div slot="header" class="clearfix">
                        <span style="font-weight: bold;">我的模型</span>
                        <i class="el-icon-close" style="float: right;"  @click="info=!info"></i>
                    </div>
                    <div style="padding: 14px;">
                        <!--<div class="card-column">-->
                            <h4 class="card-column-title">请选择一个模型！</h4>
                        <!--</div>-->
                    </div>
                    </el-card>
                    </div>
                </div>

                 <div v-else="this.varLength != 0" v-for="(o, index) in variable" :key="o.id" :offset="index > 0 ? 2 : 0" style="height: inherit;overflow-y: hidden;">
                        <el-card :body-style="{ padding: '0px' }" style="height: inherit;overflow-y: auto;">
                          <div slot="header" class="clearfix">
                        <span style="font-weight: bold;">{{o.name}}</span>
                      <i class="el-icon-close" style="float: right;"  @click="info=!info"></i>
    
                          </div>
                        <img v-bind:src="o.imageUrl" style="height: 200px;width: 270px; margin-top: 10px;">
                        <div style="padding: 14px;">
                           <!--  <h4>模型：{{o.name}}</h4> -->
                           <div class="card-column">
                                   <h4 class="card-column-title">模型库</h4>
                               <div class="card-column-content">
                                   <span >{{o.repositoryName}}</span>
                              </div>
                           </div>
                           <div class="card-column">
                                   <h4 class="card-column-title">上传者</h4>
                               <div class="card-column-content">
                                   <span >{{o.userName}}</span>
                              </div>
                           </div>
                           <div class="card-column">
                                   <h4 class="card-column-title">创建日期</h4>
                               <div class="card-column-content">
                                   <span >{{o.createTime}}</span>
                              </div>
                           </div>
                           <div class="card-column">
                                   <h4 class="card-column-title">修改日期</h4>
                               <div class="card-column-content">
                                   <span >{{o.updateTime}}</span>
                              </div>
                           </div>







                           
                         
                            <div class="card-column">
                                <div style="display: inline-block">
                                    <a class="ui basic button" @click="addWatch(o)">
                                    <i v-if="o.alreadyWatch == true" class="iconfont icon-guanzhu"  ></i>
                                    <i v-else="o.alreadyWatch == false" class="iconfont icon-quxiaoguanzhu01"  ></i>
                                        {{o.numberWatch}}
                                    </a>
                                </div>
                                <div style="display:inline-block;">
                                    <a class="ui basic button" @click="addStar(o)">
                                      <i v-if="o.alreadyStar == true" class="iconfont icon-guanzhu3" ></i>
                                      <i v-else="o.alreadyStar == false" class="iconfont icon-guanzhu4"  ></i>
                                        {{o.numberStar}}
                                    </a>
                                </div>
                            </div>
                            <div class="card-column">
                                   <h4 class="card-column-title">描述</h4>
                               <div class="card-column-content">
                                   <span >{{o.discription}}</span>
                              </div>
                           </div>



                            
                          <!--   <div class="bottom clearfix"> -->
                                <!--<time class="time">{{ currentDate }}</time>-->
                               <!--  <el-button type="text" class="button" @click="viewInfo">关闭</el-button>
                            </div> -->
                        </div>

                    </el-card>  

                 </div>
               <!--  <el-col :span="24" v-for="(o, index) in variable" :key="o" :offset="index > 0 ? 2 : 0"> -->
               
              <!--   </el-col> -->


            </el-aside>
        </el-container>
        <!--<el-footer class="main-footer">-->
      <!--<span >-->
        <!--################################################################-->
      <!--</span>-->
        <!--</el-footer>-->


         <el-dialog :title="dialog.title" :visible.sync="dialog.dialogVisible" :close-on-click-modal="false">
      <el-form :model="dialog.form" ref="dialogForm" :rules="dialog.rules"

     >
        <el-form-item label="分类名称" prop="name" label-width="120" required>
          <el-input v-model="dialog.form.name" auto-complete="off" class="el-col-12"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelSubmit">取 消</el-button>
        <el-button type="primary" @click="submitForm" :loading="dialog.submiting">确 定</el-button>
      </div>
    </el-dialog>

    </el-container>

   

</template>




<script >
    import errGif from '@/assets/401_images/401.gif'
    import kzTree from './directory.vue';
//    import uploadFile from  '../nav3/Page6.vue'
    import myUpload from '../nav3/myUpload.vue'
    import breadcrumb from '../nav3/breadcrumb.vue'
    import sortableList from './sortable-list'
    import { mapState,mapGetters} from 'vuex'
    import {mapActions} from 'vuex'


    export default {
        components: {
            kzTree,
//            uploadFile,
            myUpload,
            breadcrumb,
            sortableList,
        },
        data() {
            this.__currentNode = null;
            var validateName = (rule, value, callback) => {
                if (value.trim() == '') {
                    callback(new Error('不能全为空格和空值'));
                }else {
                    callback();
                }
            };
            return {

                       url: {
                      C: '',
                      U: '',
                      R: '',
                      D: ''
                    },
                        errGif: errGif + '?' + +new Date(),
                        props: {
                            label: 'name',
                            children: 'zones'
                        },
                        count: 1,
                        info: false,
                        listStatus:'true',


                        modelTotal: '',
                        pager: {
                            total: 0,
                            pageSize: 10,
                            pageIndex: 1,
                        },
                        variable: [],
                        varLength : 0,
                        drawer: false,
                        sorttitles: [{
                            key: 'name',
                            name: '名称'
                        }, {
                            key: 'uploadTime',
                            name: '上传时间'
                        }, {
                            key: 'userName',
                            name: '作者'
                        },],
                        tree: {
                            url: {
                                C: '/api/directory/add?userName='+ this.$store.state.userInfo.profile.name +"&",
                                U: '/api/directory/update',
                                R: 'api/directory/list?scope='+false +"&userName="+ this.$store.state.userInfo.profile.name +"&",
                                D: '/api/directory/delete'
                            }
                        },
                        data: {
                            treeItem: "",
                        },
                        filters: {
                            name: ""
                        },
                        loading: false,
                        isBusy: false,
                        align: 'center',
                        repositories: [],
                          dialog: {
                  title: '增加分类',
                  dialogVisible: false,
                  submiting: false,
                  form: {
                    name: '',
                    id: '',
                    parent_id: 0
                  },
                  rules: {
                    name: {
                      required: true,
                        validator : validateName,
//                      message: '请输入分类名称',
                      trigger: 'blur'
                    }
                  }
                },
                file:{
                    dialogVisible: false,
                },
                name : this.$store.state.userInfo.profile.name,
                privateDirId : this.$store.getters.privateDirId.data.id,

            };
        },
        computed: {
            ...mapState({
                a: state => state.a
            }),
            ...mapGetters(['amsg']),
            getRepos(){
                var _this = this;
                let para = {
                    pageSize: this.pager.pageSize,
                    pageIndex: this.pager.pageIndex
                };
                if (_this.amsg != null && _this.amsg != "") {
                    var url = '/api/model/list?parent_id=' + _this.amsg + "&scope=" + false + "&userId=" + _this.$store.state.userInfo.profile.iD
                } else {
                    _this.$store.state.amsg = 0;
                    var url = '/api/model/list?parent_id=' + _this.amsg + "&scope=" + false + "&userId=" + _this.$store.state.userInfo.profile.iD
                }
                console.log(url);
                _this.$http.post(url)
                    .then(function (response) {
//                    _this.repositories = response.data.repositories;
                        _this.pager.total = response.data.repositories.length;
                        _this.modelTotal = response.data.repositories.length;
                        _this.varLength = _this.variable.length;
                        var filterModel = response.data.repositories.filter(
                            (u, index) => {
                                if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
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
            treeAdd (treeItem, event, node) {
        this.__currentNode = node
        Object.assign(this.dialog, {
          title: '增加分类',
          dialogVisible: true,
          form: {
            name: '',
            id: '',
            parent_id: treeItem.id
          }
        })
      },
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
                    return resolve([{name: 'region1'}, {name: 'region2'}]);
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
            },
        


        hanldeNodeClick (data) {
            //该目录下的模型列表
            this.treeItem = data;
//        this.$emit("node-click",data)
            this.$store.dispatch('sendA', data.parent_id);
            if (data.parent_id <= 0) {
                this.$store.dispatch('sendB', -1);
            }
            else {
                this.$store.dispatch('sendB', data.parent_id);
            }
        },
        //获取用户列表
        getModel() {
            let para = {
                name: this.filters.name,
                pageSize: this.pager.pageSize,
                pageIndex: this.pager.pageIndex
            };
            var _this = this;
            if (_this.amsg != null && _this.amsg != "") {
                var url = '/api/model/list?parent_id=' + _this.amsg
            } else {
                _this.amsg = 0;
                var url = '/api/model/list?parent_id=' + _this.amsg
            }
            _this.$http.post(url)
                .then(function (response) {
                    var searchModel = response.data.repositories.filter(model => {
                        if (para.name && model.name.indexOf(para.name) == -1) {
                            return false
                        }
                        else {
                            return true
                        }
                    })
                    var filterModel = searchModel.filter(
                        (u, index) => {
                            if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
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
        modelVar: function (item) {
            this.$store.dispatch('sendModelId', item.parentId);
            this.$store.dispatch('sendTreeModelId', item.index);
            this.$router.push({path: '/model/myPackageDiagram'});
        },
        modelVariable: function (model) {
            var modelVariable = new Array;
            modelVariable.push(model);
            this.variable = modelVariable;
            this.varLength = this.variable.length;
            if (model == null) {
                $("#variable")[0].style.display = "none";
            }
            console.log(model);
        },
        onSortFinsh(repositories){
            console.log('onSortFinsh');
            console.log(JSON.stringify(repositories));
            console.log(this.list);
            this.list = repositories
        },
        handleCurrentChange(val) {
            this.$refs.singleTable.setCurrentRow(val);
            var modelVariable = new Array;
            modelVariable.push(val);
            this.variable = modelVariable;
            this.varLength = this.variable.length;
            console.log(val);
        },
        handleCurrent(val) {
            console.log(`当前页: ${val}`);
            var filterModel = this.repositories.filter(
                (u, index) => {
                    if (index < val * para.pageSize && index >= para.pageSize * (val - 1)) {
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
                (u, index) => {
                    if (index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)) {
                        return true
                    }
                }
            )
            _this.repositories = filterModel;
        },
        handleEdit(index, row) {
            console.log(index, row);
            this.$store.dispatch('sendModelId', row.index);
            this.$store.dispatch('sendTreeModelId', row.index);
            this.$router.push({path: '/model/myPackageDiagram'});
        },
        handleDownload(index, row){
            console.log(index, row);
            var _this = this;
            var url = '/api/model/download?modelId=' + row.index;
            _this.$http.post(url)
                .then(function (response) {
                    location.href = response.data.data;
                }).catch(function (error) {
                console.log(error);
            });
        },
        handleDeleted(index, row){
            console.log(index, row);
            var _this = this;
            this.$confirm('确认删除该模型吗?', '提示', {
                type: 'warning'
            }).then(() => {
            var url = '/api/model/deleted?modelId=' + row.parentId;
            _this.$http.post(url)
                .then(function (response) {
                    if (response.data.msg == "ok") {
                        _this.$message({
                            message: '删除成功！',
                            type: 'warning',
                            duration: 2000
                        });
                        _this.getModel();
                    }
                    else {
                        _this.$message({
                            message: '删除失败！',
                            type: 'warning',
                            duration: 2000
                        });
                    }
                }).catch(function (error) {
                console.log(error);
            });
            }).catch(() => {
            });
        },
        addStar(item){
            if (item.alreadyStar == false) {
                var _this = this;
                var url = '/api/star/add?userId=' + item.userId + "&repoName=" + item.repositoryName
                _this.$http.post(url)
                    .then(function (response) {
                        if (response.data.msg == "ok") {
                            _this.getModel();
                        }
                    }).catch(function (error) {
                    console.log(error);
                });
            }
            if (item.alreadyStar == true) {
                var _this = this;
                var url = '/api/star/delete?userId=' + item.userId + "&repoName=" + item.repositoryName
                _this.$http.post(url)
                    .then(function (response) {
                        if (response.data.msg == "ok") {
                            _this.getModel();
                        }
                    }).catch(function (error) {
                    console.log(error);
                });
            }

        },
        addWatch(item){
            if (item.alreadyWatch == false) {
                var _this = this;
                var url = '/api/watch/add?userId=' + item.userId + "&repoName=" + item.repositoryName
                _this.$http.post(url)
                    .then(function (response) {
                        if (response.data.msg == "ok") {
                            _this.getModel();
                        }
                    }).catch(function (error) {
                    console.log(error);
                });
            }
            if (item.alreadyWatch == true) {
                var _this = this;
                var url = '/api/watch/delete?userId=' + item.userId + "&repoName=" + item.repositoryName
                _this.$http.post(url)
                    .then(function (response) {
                        if (response.data.msg == "ok") {
                            _this.getModel();
                        }
                    }).catch(function (error) {
                    console.log(error);
                });
            }
        },
           cancelSubmit () {
        this.dialog.dialogVisible = false
        this.$refs.dialogForm.resetFields()
      },
      submitForm () {
        this.$refs.dialogForm.validate((valid) => {
          if (valid) { // 验证通过
              var checkDirNameUrl = '/api/directory/checkRootDir?dirParentId='+ this.$refs.dialogForm.model.parent_id +'&dirName='+ this.$refs.dialogForm.model.name
                  +'&userName='+ this.$store.state.userInfo.profile.name
              var _this = this;
              _this.$http.post(checkDirNameUrl)
                  .then(function (response) {
                      if(response.data.state == 1){
                          _this.fetchAddTreeNode()
                      }else{
                          _this.$message({
                              message: '请重新输入模型分类名称！',
                              type: 'warning',
                              duration: 2000
                          });
                      }
                  })
                  .catch(function (error) {
                      console.log(error)
                      _this.$message({
                          message: '请重新输入模型分类名称！',
                          type: 'warning',
                          duration: 2000
                      });
                  })
//            this.fetchAddTreeNode()
          } else {
            return false
          }
        })
      },
      fetchAddTreeNode () {
        const url = 'api/directory/add';
        this.dialog.submiting = true
          var _this = this;
        this.fetch(url, this.dialog.form, 'post')
            .then(data => {
              /* 隐藏dialog */
              Object.assign(this.dialog, {
                submiting: false,
                dialogVisible: false
              })
                _this.$refs.dialogForm.resetFields()
              /* 提示结果 */
              const message = this.dialog.form.id ? '编辑成功' : '添加成功'
              this.$message({ message: message, type: 'success' })

              if (_this.dialog.form.id) { // 编辑
                  _this.__currentNode && _this.$set(_this.__currentNode, 'data', data)
              } else { // 新增
                /* treeNode api */
                if (_this.__currentNode) { // 子分类添加子类
                    _this.__currentNode.doCreateChildren([data])
                } else if (data.data.parentId === (_this.privateDirId +"")) { // 顶级添加子类
//                    _this.$refs.kzTree.root.doCreateChildren([data])
                    _this.loadTreeNode();
//                    var url = _this.tree.url.R
//                    _this.fetch(url, {parent_id: _this.privateDirId })
//                        .then(data => {
//                            resolve(data)
//                        });
                }
              }
            })
      },
       fetch (url, data, type = 'POST') {
            const success = (data, resolve, reject) => {
                if (data.status === 1) {
                    resolve(data)
                } else {
               //     console.error(data.data.code+":"+ data.data.message)
                    reject(data)
                }
            }
            return new Promise((resolve, reject) => {
                if (type.toLowerCase() === 'post') {
                    if (data) {
                        var dataUrl = Object.keys(data).map(item => item+"="+data[item])
                        url = url + (url.indexOf('?') > -1 ? '' : '?') + dataUrl.join('&')
                    }
                    this.$http.post(url)
                        .then(res => res)
                        .then(data => success(data.data, resolve, reject))
                } else {
                    if (data) {
                       var dataUrl = Object.keys(data).map(item => item+"="+data[item])
                        url = url + (url.indexOf('?') > -1 ? '' : '?') + dataUrl.join('&')
                }

                    this.$http.get(url)
                        .then(res => res)
                        .then(data => success(data.data, resolve, reject))
                }
            })
        },

            /* 加载子分类 */
            loadTreeNode (treeItem, resolve) {
                const url = this.tree.url.R ;
                var para = {parent_id: this.privateDirId};
                this.$emit("node-click",para);
                var _this = this ;
                _this. fetch(url, para)
                    .then(data => {
                        _this.$store.dispatch('sendTreeData',data);
                        resolve(data)
                    });
            },
            isSelectModel(){
                if(this.$store.state.bmsg >= 0){
                    this.file.dialogVisible = true;
                }else{
                    this.$message({
                        message: '请选择模型目录！',
                        type: 'warning',
                        duration: 2000
                    });
                }

            },
//            handleClose(done) {
//                this.$confirm('确认关闭？')
//                    .then(_ => {
//                        done();
//                    })
//                    .catch(_ => {});
//            }

    },


        mounted() {
        }
    };

</script>

<style scoped lang="scss">
    @import '~scss_vars';

    .main-container{
        height: 100%;
        overflow: hidden;
    }
    .top-header{
        max-height: 40px;
        background-color: #f8f8f8;
    }
    .middle-header{
        max-height: 50px;
        border-top: solid 1px #e7e7e7;
    }
    .bottom-header{
        max-height:  50px;
        border-top: solid 1px #cfdbe5;
    }
    .main-footer{
        max-height: 30px;
        text-align: center;
        border-top: solid 1px #e7e7e7;

    }
    .left-aside{
        border-right: solid 1px #e7e7e7;
        min-width: 150px;
        max-width: 250px;
        overflow-x:hidden;
        overflow-y:auto;
    }
    .left-tree{
        max-width: 150px;
    }
    .list-main{
        height: inherit;
        padding: 0px;
        overflow: hidden;
        width: inherit;
        min-width: 250px;
    }
    .card-main{
        height: inherit;
        padding: 0px;
        width: inherit;
        overflow: hidden;
        min-width: 250px;
    }
    .right-aside{
        height: 100%;
        overflow-x: hidden;
        overflow-y:auto;
    }
    /*.el-table__body-wrapper {
        overflow-x: hidden;
        overflow-y: auto;
        position: relative;
        height: inherit;
        text-overflow: ellipsis;
        width: inherit;
    }
    .el-table .cell{
        white-space: nowrap;
        text-overflow: ellipsis;
    }*/
    .el-table
    {
        height: inherit;
    }
    .el-table__body-wrapper
    {
        height: inherit;
        overflow-x: hidden;
    }
    .is-scroll-left
    {
        height: inherit;
        overflow-x: hidden;
    }
    .is-scroll-right
    {
        height: inherit;
        overflow-x: hidden;
    }

    .el-table--fit
    {
        height: inherit;
        overflow-x: hidden;
    }
    .el-table--enable-row-hover
    {
        height: inherit;
        overflow-x: hidden;
    }
    .el-table--enable-row-transition
    {
        height: inherit;
        overflow-x: hidden;
    }

    .el-table__body
    {
        overflow-x: hidden;

    }
    .el-card__header
    {
      background-color: #e6e6e6;
    }

     .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .card-column
  {
    border-top: solid 1px #e6e6e6;
  }
  .card-column-title
  {
      font-weight: 600;
      margin-bottom: 2px;
      margin-top: 5px;
     
  }
  .card-column-content
  {
     margin-bottom: 5px;
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





</style>