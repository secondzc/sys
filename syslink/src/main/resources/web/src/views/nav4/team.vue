<template>

  <section>
    <!--工具条-->


   <el-form label-width="80px"   :inline="true"  >
    <el-form-item>
   <el-card class="box-card"  >
  <div slot="header" class="clearfix" >
    <span style="line-height: 36px;">{{this.$route.params.teamName}}</span>
    <el-button style="float: right;" type="danger" v-if="isMember" @click="deleteTeamUser">离开团队</el-button>
    <el-button style="float: right;" type="primary" v-else  @click="addTeamUser">加入团队</el-button>

  </div>

  <div class="text item">
   {{this.team.description}}
   <hr/>
  </div>
  <div class="text item">
  <span style="line-height: 36px;">{{team.numMembers}}名成员-{{team.numRepos}}个仓库</span>
  <hr/>
  </div>
   <div class="text item">
  <span style="line-height: 36px;">{{team.authDes}}</span>
  </div>
   <div >
   <el-button style="float: right;" type="primary"  @click.native="showTeamManage" >团队设置</el-button>
  </div>
  </el-card>
  </el-form-item>


  <el-form-item>
  <el-card class="box-card1">
  <div slot="header" class="clearfix" >
    <span style="line-height: 36px;">团队成员</span>
 
  </div>
  <div  v-for="(item,index) in team.member" :key=item.id>

    {{item.name}}
     <el-button type="danger" size="small" @click="deleteTeamMember(item)" >移除成员</el-button>
  </div>
    <el-form   >
           <el-autocomplete  class="inline-input"  v-model="inviteUser.name" :fetch-suggestions="querySearch" placeholder="搜索用户" 
      :trigger-on-focus="false" :props="search"></el-autocomplete>
      <el-button type="primary" @click="inviteSubmit"  >邀请他人加入</el-button>
    </el-form>


   </el-card>
 </el-form-item>
   </el-form>
   

   <el-dialog :title="title" v-model="teamMange1" :close-on-click-modal="false"   >
          <el-form :model="teamMange" label-width="80px"    ref="teamMange"    >
          <el-form-item label="团队名称" prop="name"  :rules="[{required:true,message:'请输入团队名称',trigger:'blur'}]">
          <el-input v-model="teamMange.name" auto-complete="off"></el-input>
          <label>您可以使用该名称来通知该组全体成员</label>
        </el-form-item>
        <el-form-item label="团队描述" prop="description"    >
          <el-input v-model="teamMange.description" auto-complete="off"></el-input>
          <label>一句话描述这个团队是做什么的</label>
        </el-form-item>
    </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="teamMange1 = false">取消</el-button>
        <el-button type="primary" @click.native="updateTeam" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>


     <el-dialog :title="title" v-model="teamMange2" :close-on-click-modal="false"   >
          <el-form :model="teamMange" label-width="80px"    ref="teamMange"    >
         
         <el-form-item label="团队名称" prop="name"  :rules="[{required:true,message:'请输入团队名称',trigger:'blur'}]">
          <el-input v-model="teamMange.name" auto-complete="off"></el-input>
           <label>您可以使用该名称来通知该组全体成员</label>
        </el-form-item>
        <el-form-item label="团队描述" prop="description"    >
          <el-input v-model="teamMange.description" auto-complete="off"></el-input>
            <label>一句话描述这个团队是做什么的</label>
        </el-form-item>
        <div>
        <label>请选择该团队所具有的权限等级：</label>
        </div>
        <el-radio-group v-model="teamMange.authorize">
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
        <el-button @click.native="teamMange2 = false">取消</el-button>
        <el-button type="primary" @click.native="updateTeam" :loading="addLoading">提交</el-button>
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
          orgName:'',
          teamName:''
        },
        activeName2:'first',
         orgUser:[],
         team:{},
         currentUserId:'',
         isOwner:false,
         isMember:false,
         teamMange1:false,
         teamMange2:false,
         addLoading:false,
         teamMange:{},
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
      showTeamManage()
      {
          if(this.team.authorize==4)
          {
            this.teamMange1=true;
             this.teamMange= Object.assign({}, this.team);
          //   this.team;
          }
          else
          {
            this.teamMange2=true;
             this.teamMange=Object.assign({}, this.team);
           //  this.team;
          }
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
        let para =  {teamName:this.$route.params.teamName,orgName:this.$route.params.orgName};
    //    console.log(para);
        this.$http({method:'POST',url:'/api/team/queryTeamByName',data:para})
           .then((response)=> {
                  this.team = response.data.team;
                  let teamMember = new Set();
                  let member = this.team.member;
                   member.forEach(x => teamMember.add(x.id));
                   console.log(teamMember);
                   if(teamMember.has(this.currentUserId))
                   {
                    this.isMember=true;
                   }
                   else
                   {
                    this.isMember=false;
                   }
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
    

     
     inviteSubmit()
     {
       
          
              this.addLoading = true;
              //NProgress.start();
              this.inviteUser.orgName=this.$route.params.orgName;
              this.inviteUser.teamName=this.$route.params.teamName;
              let para = this.inviteUser;
              this.$http({method:'post',
                url:'api/team/addTeamMember',
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
                this.getTeam();
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
      deleteTeamMember(item)
      {
         let para = {teamId:this.team.id,orgName:this.$route.params.orgName,uid:item.id};
          this.$http({method:'post',
                url:'api/team/deleteTeamMember',
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
             
                this.getTeam();
              });
      },
      deleteTeamUser()
      {
         let para = {teamId:this.team.id,orgName:this.$route.params.orgName};
          this.$http({method:'post',
                url:'api/team/deleteTeamUser',
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
             
                this.getTeam();
              });
      },
       addTeamUser()
      {
         let para = {teamId:this.team.id,orgName:this.$route.params.orgName};
          this.$http({method:'post',
                url:'api/team/addTeamUser',
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
             
                this.getTeam();
              });
      },
       updateTeam()
      {



          this.addLoading=true;
         let para = {id:this.team.id,name:this.teamMange.name,description:this.teamMange.description,
          authorize:this.teamMange.authorize,orgName:this.$route.params.orgName};
          this.$http({method:'post',
                url:'api/team/update',
                data:para}).then((res)=>{
                this.addLoading=false;
                this.teamMange1=false;
                this.teamMange2=false;
                this.getTeam();
              });
      },

      
     
   

     
    
    },
    mounted() {
    // this.currentUserId = sessionStorage.getItem('userId');
    // console.log(userId);
    
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
    width: 380px;
  }
   .box-card1 {
    width: 600px;
  }
</style>
