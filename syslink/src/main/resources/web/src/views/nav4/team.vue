<template>
  <section>
    <!--工具条-->


<label>{{this.$route.params.name}}</label>

  

   
 <el-tabs v-model="activeName2" type="card" >
    <el-tab-pane label="组织成员" name="first">   

    


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
       <template scope="scope">
        <label v-if="scope.row.isOwner">私有成员(设为公开)</label>
          <label v-else>公开成员（设为私有）</label>
        </template>
       </el-table-column>
      </el-table-column>
      <el-table-column prop="isOwner" label="成员角色" min-width="60" >
       <template scope="scope">
          <label v-if="scope.row.isOwner">管理员</label>
          <label v-else>普通成员</label>
        </template>
       </el-table-column>
      
         <el-table-column label="操作" width="300">
        <template scope="scope">
        <el-button type="danger" size="small"  v-if="isOwner" >移除成员</el-button>
        <el-button type="danger" size="small"  v-if="member(scope.$index,scope.row)">离开组织</el-button>
   
        </template>
      </el-table-column>
    </el-table>
  
    </el-tab-pane>

    <el-tab-pane label="组织团队" name="second">

  <template >
    <el-card class="box-card" v-for="item in team">
    <div slot="header" class="clearfix">
    <span style="line-height: 36px;"></span>
    <el-button style="float: right;" type="primary">操作按钮</el-button>
    </div>
    <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}
    </div>
    </el-card>
  </template>

    </el-tab-pane>
  
  </el-tabs>



    
  </section>
</template>

<script>
  import util from '../../common/js/util'
  //import NProgress from 'nprogress'

  export default {
    data() {
       
      return {
        department:[],
        activeName2:'first',
         orgUser:[],
         team:[],
         currentUserId:'',
         isOwner:false,
         isMember:false
       
      
      }
    },
    methods: {
        rowChange(now,old)
      {
        this.selection=now;
      },
        selsChange(val) {
        this.selection = val;
      },

      getOrgUser()
      {
        let para =  {name:this.$route.params.name};
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

          })
          .catch(function (error) {
              console.log(error);
          });
         
      },
       getTeam()
      {
        let para =  {name:this.$route.params.name};
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

         if((!row.isOwner)&&(row.uid==this.currentUserId))
         {
          return true;
         }
         else
         {
          return false;
         }

      }

     
     
    

      
    },
    mounted() {
     this.getOrgUser();
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
