<template>
  <section>
    <!--工具条-->



   
 <el-tabs v-model="activeName2" type="card" >
    <el-tab-pane label="组织成员" name="first">   

      
   <!--   <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" >
        <el-form-item>
          <el-autocomplete  class="inline-input"  v-model="inviteUser.name" :fetch-suggestions="querySearch" placeholder="搜索用户" 
      :trigger-on-focus="false" :props="search"></el-autocomplete>
          <el-button type="primary" @click="inviteSubmit"  >邀请他人加入</el-button>
        </el-form-item>
      </el-form>
       <hr/>
    <!-- </el-col> -->


    <!--列表-->
    <el-table :data="orgUser" highlight-current-row   stripe  ref="multipleTable"  style="width: 100%;">
      <el-table-column type="selection" min-width="100">
      </el-table-column>
      <el-table-column type="index" min-width="120">
      </el-table-column>
      <el-table-column prop="name" label="用户名" min-width="60" >
      </el-table-column>
      <el-table-column prop="fullName" label="全名" min-width="120" >
       </el-table-column>
      <el-table-column prop="isPublic" label="成员可见性" min-width="120" >
       <template slot-scope="scope">
        <label v-if="scope.row.isPublic">私有成员</label>
          <label v-else>公开成员</label>
            <el-button type="text" v-if="scope.row.isPublic"  @click="changePublic(scope.$index,scope.row)">（设为公开）</el-button>
          <el-button type="text" v-else @click="changePublic(scope.$index,scope.row)">（设为私有）</el-button>
        </template>
       </el-table-column>
      </el-table-column>
      <el-table-column prop="isOwner" label="成员角色" min-width="60" >
       <template slot-scope="scope">
          <label v-if="scope.row.isOwner">管理员</label>
          <label v-else>普通成员</label>
        </template>
       </el-table-column>
      
         <el-table-column label="操作" width="300">
        <template slot-scope="scope">
        <el-button type="danger" size="small" @click="deleteOrgUser(scope.$index, scope.row)" v-if="Owner(scope.$index,scope.row)" >移除成员</el-button>
         

       <el-button type="danger" size="small" @click="deleteOrgUser(scope.$index, scope.row)" v-if="member(scope.$index,scope.row)">离开组织</el-button> 
   
        </template>
      </el-table-column>
    </el-table>
  
    </el-tab-pane>

    <el-tab-pane label="组织团队" name="second">

    <!--  <el-col :span="24" class="toolbar" style="padding-bottom: 0px;"> -->
      <el-form :inline="true" >
        <el-form-item>
          <el-button type="primary" @click="addTeam"  >创建新的团队</el-button>
        </el-form-item>
      </el-form>
      <hr/>
       
    <!-- </el-col> -->

  <template >
    <el-form label-width="80px"   :inline="true"  >
    <el-form-item  v-for="(item,index) in team" :key=item.id>  
    <el-card class="box-card" v-if="showTeam(item)">
    <div slot="header" class="clearfix" >
    <el-button type="text" @click="toTeam(item)">{{item.name}}</el-button>
   
    <el-button style="float: right;" type="danger" v-if="teamMember(item)" @click="deleteTeamUser(item)" >离开团队</el-button>
     <el-button style="float: right;" type="primary" v-else @click="addTeamUser(item)">加入团队</el-button>

    </div>
    <div v-for="user in item.member" :key=item.member.id class="text item">
    {{user.name }}
    <hr/>
    </div>

      <div class="bottom clearfix">
    <span style="line-height: 36px;">{{item.numMembers}}名成员-{{item.numRepos}}个仓库</span>
        </div>
    </el-card>
    </el-form-item>
    </el-form>
  </template>

    </el-tab-pane>
  
  </el-tabs>


 <!--新增界面-->
    <el-dialog :title="title" :visible="addFormVisible" :close-on-click-modal="false"   >
          <el-form :model="addForm" label-width="80px"    ref="addForm"   :inline="true"  >
        

      <el-form-item label="团队名称" prop="name"  :rules="[{required:true,message:'请输入团队名称',trigger:'blur'}]">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
           <label>您可以使用该名称来通知该组全体成员</label>
        </el-form-item>
        <el-form-item label="团队描述" prop="description"    >
          <el-input v-model="addForm.description" auto-complete="off"></el-input>
            <label>一句话描述这个团队是做什么的</label>
        </el-form-item>
        <div>
        <label>请选择该团队所具有的权限等级：</label>
        </div>
        <el-radio-group v-model="addForm.authorize">
        <div>
        <el-radio :label="1">读取权限</el-radio>
        </div>
        <div>
        <el-radio :label="2">写入权限</el-radio>
        </div>
        <div>
        <el-radio :label="3">管理权限</el-radio>
        </div>
       </el-radio-group>

    </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addTeamSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

  </section>
</template>

<script>
  import util from '../../common/js/util'
  //import NProgress from 'nprogress'

  export default {
    data() {
       
      return {
        department:[],
        inviteUser:{
          name:'',
          orgName:''
        },
        activeName2:'first',
         orgUser:[],
         team:[],
         currentUserId:'',
         isOwner:false,
         isMember:false,
         addFormVisible:false,
         addLoading:false,
         addForm:{
          name:''
         },
         title:'',
         simpleUsers:[],
          search:{
          label:'name',
          value:'name'
        },

      
      }
    },
    methods: {

        querySearch(queryString, cb) {
        var simpleUsers = this.simpleUsers;
        var results = queryString ? simpleUsers.filter(this.createFilter(queryString)) : simpleUsers;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },
      createFilter(queryString) {
        return (simpleUser) => {
          return (simpleUser.name.indexOf(queryString.toLowerCase()) === 0);
        };
      },

     

        rowChange(now,old)
      {
        this.selection=now;
      },
        selsChange(val) {
        this.selection = val;
      },

      getOrgUser()
      {
        let para =  {name:this.$route.params.orgName};
    //    console.log(para);
        this.$http({method:'POST',url:'/api/org/orgUser',data:para})
           .then((response)=> {
                  this.orgUser = response.data.orgUser;
                   for(let a=0;a<this.orgUser.length;a++)
                   {
                    if(this.orgUser[a].isOwner)
                    {
                        if(this.orgUser[a].uid==this.currentUserId)
                       {
                         this.isOwner=true;
                       }
                    }
                   }
                   for(let a = 0;a<this.orgUser.length;a++)
                   {
                    if(!this.orgUser[a].isOwner)
                    {
                      if(this.orgUser[a].uid==this.currentUserId)
                       {
                         this.isMember=true;
                       }
                    }
                   }
                console.log(this.isOwner);
          })
          .catch(function (error) {
              console.log(error);
          });
         
      },
       getSimplerUser()
      {
        let para =  {id:''};
    //    console.log(para);
        this.$http({method:'POST',url:'/api/user/querySimpleUser',data:para})
           .then((response)=> {
                  this.simpleUsers = response.data.simpleUser;
                   console.log(this.simpleUsers);
          })
          .catch(function (error) {
              console.log(error);
          });
         
      },
       getTeam()
      {
        let para =  {orgName:this.$route.params.orgName};
    //    console.log(para);
        this.$http({method:'POST',url:'/api/org/team',data:para})
           .then((response)=> {
                  this.team = response.data.team;
                  console.log(this.team);
                 
          })
          .catch(function (error) {
              console.log(error);
          });
         
      },
      getUserId()
      {
        this.$http.post('api/user/getUserId').
        then((response)=>{
                this.currentUserId = response.data.userId;
                console.log(this.currentUserId);
        })
        .catch(function(error){
          console.log(error);
        });
        
      },
      judgeOwner()
      {
        /**
        for(let a=0;a<this.orgUser.length;a++)
        {
           if(orgUser[a].isOwner)
           {
            if(orgUser[a].uid==this.currentUserId)
            {
              this.isOwner=true;
            }
           }
        }
        **/
        console.log(this.currentUserId);
    //    console.log(this.orgUser);
        
      },
      member(index,row)
      {
       // console.log(this.currentUserId);
        // console.log(row.isOwner);
        // console.log(row.uid);

         if((row.uid==this.currentUserId))
         {
          return true;
         }
         else
         {
          return false;
         }

      },
      Owner(index,row)
      {
          if(this.isOwner)
          {
            if(row.uid==this.currentUserId)
            {
              return false;
            }
            else
            {
              return true;
            }
          }
          else
          {
            false;
          }
      },
      changePublic(index,row)
      {
        console.log(row.isPublic);
        let para ={};
          if(row.isPublic==true)
          {
             para = {uid:row.uid,isPublic:false};
          }
          else
          {
             para = {uid:row.uid,isPublic:true};
          }
          

            this.$http({method:'POST',url:'/api/org/updateOrgUser',data:para})
           .then((response)=> {
                 this.getOrgUser();
        });
      },

      deleteOrgUser(index,row)
      {

        let para ={uid:row.uid,orgId:row.orgId};
         this.$http({method:'POST',url:'/api/org/deleteOrgUser',data:para})
           .then((response)=> {

                 this.getOrgUser();
                 this.getTeam();
                 if(response.data.flag)
                {
                  this.$message({
                  message: response.data.msg,
                  type: 'success'
                });
                }
                else
                {
                  this.$message({
                  message: response.data.msg,
                  type: 'error'
                });

                }
        });

      }
     ,
     inviteSubmit()
     {
        
          
              this.addLoading = true;
              //NProgress.start();
              this.inviteUser.orgName=this.$route.params.orgName;
              let para = this.inviteUser;
              this.$http({method:'post',
                url:'api/org/addOrgUser',
                data:para}).then((res)=>{
                this.addLoading = false;
                //NProgress.done();
                if(res.data.flag)
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'success'
                });
                }
                else
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'error'
                });

                }
              
               this.inviteUser={};
                this.getOrgUser();
              });
          
       
      },
      teamMember(item)
      {
        let user = item.member;
        let userIds = new Set();
     //   if(user.length>0)
    //    {
          user.forEach(x => userIds.add(x.id));
            console.log(userIds);
            console.log(this.currentUserId);
            if(userIds.has(this.currentUserId))
            {
              return true;
            }
            else
            {
              return false;
            }

     //    return userIds.has(this.userId);
    //    }
      
       
      
      },
      showTeam(item)
      {
       if(this.isOwner)
       {
        return true;
       }
       else
       {
           let user = item.member;
        let userIds = new Set();
         user.forEach(x => userIds.add(x.id));
          if(userIds.has(this.currentUserId))
            {
              return true;
            }
            else
            {
              return false;
            }

       }
      },
      deleteTeamUser(item)
      {
         let para = {teamId:item.id,orgName:this.$route.params.orgName};
          this.$http({method:'post',
                url:'api/team/deleteTeamUser',
                data:para}).then((res)=>{
                this.addLoading = false;
                if(res.data.flag)
                {

                }
                else
                {
                     this.$message({
                  message: res.data.msg,
                  type: 'error'
                });
                }
                //NProgress.done();
              
             
             
                this.getOrgUser();
                this.getTeam();
              });
      },
      addTeamUser(item)
      {
         let para = {teamId:item.id,orgName:this.$route.params.orgName};
          this.$http({method:'post',
                url:'api/team/addTeamUser',
                data:para}).then((res)=>{
                this.addLoading = false;
                //NProgress.done();
                this.getTeam();
                this.getOrgUser();
              });
      },
       toTeam(item)
      {
         this.$router.push({ name: '团队管理', params: { teamName:item.name}});
      },
      addTeam()
      {
         this.addForm={};
         this.addFormVisible=true;
      },
      addTeamSubmit()
      {
         this.$refs.addForm.validate((valid) => {
          if (valid) {
              this.addLoading = true;
              //NProgress.start();
              this.addForm.orgName=this.$route.params.orgName;
              let para = Object.assign({}, this.addForm);
              
              this.$http({method:'post',
                url:'api/team/add',
                data:para}).then((res)=>{
                this.addLoading = false;
                //NProgress.done();
                if(res.data.flag)
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'success'
                });
                }
                else
                {
                  this.$message({
                  message: res.data.msg,
                  type: 'error'
                });

                }
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getTeam();
              });
          }
        });
      }
   

     
    
    },
    mounted() {
    // this.currentUserId = sessionStorage.getItem('userId');
    // console.log(userId);
     this.getOrgUser();
     this.getSimplerUser();
     this.getUserId();
     this.getTeam();
  //   this.judgeOwner();
  
    }
  }



</script>


<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  .clearfix:after {
      clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
