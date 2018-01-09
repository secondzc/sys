<template>
  <section>
    <!--工具条-->
    <!-- <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" >
        <el-form-item>
          <el-button size="small" type="primary" @click="handleAdd"  >新建</el-button>
        </el-form-item>
      </el-form>

    <!-- </el-col> -->
    <hr/>

    <!--列表-->
      <div  style="display: flex;flex-direction: column;height:92%;width: auto;">

          <div  style=" overflow-x: hidden;overflow-y: auto;justify-content: flex-start;

              display: flex;flex-wrap: wrap;height:calc(100% - 66px);border-bottom: solid 1px #e6e6e6;">



              <el-card class="Card" style="height: 265px;width: 250px; margin: 12px;"   v-for="(o, index) in modelTypes" :key="o.id" :offset="index > 0 ? 2 : 0"
              >
                  <div slot="header"  style="width: inherit;height: inherit;">
                      <span style="font-weight: bold;">{{o.name}}</span>
                      <i class="iconfont icon-lajitong" style="max-width: 14px;float: right;font-size: 20px;" @click="handleDel(o)" > </i>
                      <i class="el-icon-search" style="max-width: 14px;float: right;font-size: 20px;margin-right: 20px" @click="handleEdit(o)" > </i>
                  </div>

                  <div :index="o.index"  >
                      <div style="border-bottom:  solid 1px #e6e6e6;margin-top:  -10px;" >
                          <img v-bind:src="o.filePath" style="height: 160px;width: 200px;margin-bottom: 10px;">
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


    <!--编辑角色界面-->
    <el-dialog title="编辑模型类型" :visible.sync="editFormVisible" v-if="editFormVisible" :close-on-click-modal="false">
        <el-form ref="editForm"   :model="editForm" label-width="80px" class="demo-form-inline">


            <el-form-item label="模型名">
                <el-col :span="6">
                    <el-input  v-model="editForm.name" disabled="disabled"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="模型图标">
                <template>
                    <section>
                        <el-upload
                                ref="EditModelTypePicture"
                                class="avatar-uploader"
                                :action = "photo()"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </section>
                </template>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="editCancelSubmit">取 消</el-button>
            <el-button type="primary" @click="editSubmitForm" >确 定</el-button>
        </div>

    </el-dialog>


    <!--新增角色界面-->
    <el-dialog title="新建模型类型" :visible.sync="addFormVisible" v-if="addFormVisible" :close-on-click-modal="false"   >
        <!--<ModelTypePicture></ModelTypePicture>-->
        <el-form ref="form" :rules="form.rules"  :model="form" label-width="80px" class="demo-form-inline">


            <el-form-item label="模型名">
                <el-col :span="6">
                    <el-input  v-model="form.name" ></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="模型图标">
                <template>
                    <section>
                        <el-upload
                                ref="ModelTypePicture"
                                class="avatar-uploader"
                                :action = "photo()"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar">
                            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </section>
                </template>

            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="cancelSubmit">取 消</el-button>
            <el-button type="primary" @click="submitForm" >确 定</el-button>
        </div>
    </el-dialog>

  </section>
</template>

<script>
  import util from '../../common/js/util'
  import global_ from '../global.vue'
  import ModelTypePicture from '../nav3/ModelTypePicture.vue'
  import { mapState,mapGetters} from 'vuex'
  import {mapActions} from 'vuex'
  export default {
      components: {
          global_,
          ModelTypePicture
      },
    data() {
        var validateName = (rule, value, callback) => {
            if (value.trim() == '') {
                callback(new Error('不能全为空格和空值'));
            }else {
                callback();
            }
        };
      return {
          currentRow:null,
          modelTypes : [],
          allModelType : [],
          editForm :{
              name : '',
              photoName : '',
          },
          form: {
              name: '',
              photoName : '',
              rules: {
                  name: {
                      required: true,
                      validator : validateName,
                      trigger: 'blur'
                  }
              }
          },
          imageUrl: '',
          pager: {
              total: 0,
              pageSize: 10,
              pageIndex: 1,
          },
          editFormVisible: false,//编辑界面是否显示
          addFormVisible : false,
          name : this.$store.state.userInfo.profile.name,

      }
    },
      computed: {
          ...mapState({
              modelId: state => state.modelId,
              a: state => state.a
          }),
          ...mapGetters(['modelId', 'bmsg', 'treeModelId']),
      },
    methods: {
        handleAdd(){
            this.addFormVisible = true;
        },
        handleEdit(val){
            this.editForm.name = val.name;
            this.imageUrl = val.filePath;
            this.editFormVisible = true;
        },
        handleDel(val){
            console.log(val);
            this.$confirm('确认删除该模型类型吗?', '提示', {
                type: 'warning'
            }).then(() => {
                var _this = this;
                var url = '/api/modeltype/deleted?modelTypeName=' + val.name;
                _this.$http.post(url)
                    .then(function (response) {
                        if (response.data.msg == "ok") {
                            _this.$message({
                                message: '删除成功！',
                                type: 'warning',
                                duration: 2000
                            });
                            _this.getModelTypeList();

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
        handleCurrentChange(val) {
            this.currentRow = val;
        },
        handleSizeChange(val){
            console.log(`每页 ${val} 条`);
            this.pager.pageSize = val;
            var filterModel = this.allModelType.filter(
                (u, index) => {
                    if (index < this.pager.pageIndex * val && index >= val * (this.pager.pageIndex - 1)) {
                        return true
                    }
                }
            )
            _this.modelTypes = filterModel;
        },
        handleCurrent(val) {
            let para = {
                pageSize: this.pager.pageSize,
                pageIndex: this.pager.pageIndex
            };
            console.log(`当前页: ${val}`);
            var filterModel = this.allModelType.filter(
                (u, index) => {
                    if (index < val * para.pageSize && index >= para.pageSize * (val - 1)) {
                        return true
                    }
                }
            )
            this.modelTypes = filterModel;
        },
        submitForm () {
            this.$refs.form.validate((valid) => {
                if (valid) { // 验证通过
                    var checkModelTypeName = '/api/modeltype/checkName?modelTypeName=' + this.form.name
                    var _this = this;
                    _this.$http.post(checkModelTypeName)
                        .then(function (response) {
                            if (response.data.msg == "ok") {
                                _this.addModelType();
                            }else{
                                _this.$message({
                                    message: '请重新输入模型分类名称！',
                                    type: 'warning',
                                    duration: 2000
                                });
                            }
                        }).catch(function (error) {
                        console.log(error)
                        _this.$message({
                            message: '请重新输入模型分类名称！',
                            type: 'warning',
                            duration: 2000
                        });
                    })
                }
            })
        },
        cancelSubmit () {
            this.addFormVisible = false
            this.$refs['form'].resetFields();
        },
        getModelTypeList(){
            let para = {
                pageSize: this.pager.pageSize,
                pageIndex: this.pager.pageIndex
            };
            var _this = this;
            _this.$http.post('/api/modeltype/getModelTypeList')
                .then(function (response) {
                    _this.allModelType = response.data.modelTypeList;
                    _this.pager.total = response.data.modelTypeList.length;
                    var filterModel = response.data.modelTypeList.filter(
                        (u, index) => {
                            if (index < para.pageIndex * para.pageSize && index >= para.pageSize * (para.pageIndex - 1)) {
                                return true
                            }
                        }
                    )
                    _this.modelTypes = filterModel;
                })
                .catch(function (error) {
                });
        },
        addModelType(){
            this.form.photoName = this.$refs.ModelTypePicture.uploadFiles[0].name;
            var _this = this;
            let para = Object.assign({}, _this.form);
            _this.$http({method:'post',
                url:'api/modeltype/addModelType',
                data:para})
                .then(function (response) {
                    if(response.data.msg == "ok"){
                        _this.$message({
                            message: '添加成功！',
                            type: 'success',
                            duration: 2000
                        });
                        _this.addFormVisible = false;
                        _this.$refs['form'].resetFields();
                        _this.getModelTypeList();
                    }
                })
                .catch(function (error) {
                    console.log(error);
                    _this.$message({
                        message: '添加失败！',
                        type: 'error',
                        duration: 2000
                    });
                });
        },
        editCancelSubmit(){
            this.editFormVisible = false;
            this.$refs['editForm'].resetFields();
        },
        editSubmitForm(){
            this.editForm.photoName = this.$refs.EditModelTypePicture.uploadFiles[0].name;
            var _this = this;
            let para = Object.assign({}, _this.editForm);
            _this.$http({method:'post',
                url:'api/modeltype/updateModelType',
                data:para})
                .then(function (response) {
                    if(response.data.msg == "ok"){
                        _this.$message({
                            message: '修改成功！',
                            type: 'success',
                            duration: 2000
                        });
                        _this.editFormVisible = false;
                        _this.$refs['editForm'].resetFields();
                        _this.getModelTypeList();
                    }
                })
                .catch(function (error) {
                    console.log(error);
                    _this.$message({
                        message: '修改失败！',
                        type: 'error',
                        duration: 2000
                    });
                });
        },

        //--------------------------------icon----------------------
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
        },
        photo(){
            return "http://"+global_.HostPath+ "/api/model/uploadModelIcon?name="+this.name
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        //---------------------icon-----------------------------

    },
    mounted() {
        this.getModelTypeList();
    }
  }



</script>


<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 120px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 20%;
  }
  .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
  }
  .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
  }
  .avatar {
      width: 178px;
      height: 178px;
      display: block;
  }
</style>
