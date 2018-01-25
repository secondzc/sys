import axios from "axios";
import store from '../../vuex/store';
export default {
  freshPage(path,page){
    //页数只使用一次，用后销毁,起到‘只是详情后返回的时候记录页数’的作用，而没有记忆效果
    let storedPage = store.getters.curPage;
    if(storedPage[path] === undefined){
      return page;
    }else{
      let temp = storedPage[path];
      storedPage[path] = undefined;
      return temp;
    }
  },
	ajaxGet (api, cb) {
		axios.get(api)
		    .then(cb)
		    .catch(err =>{
		    	console.log(err);
		    })
	},
	ajaxPost (url, data, cb){
		var dataUrl = Object.keys(data).map(item => item+"="+data[item]);
        url = url + (url.indexOf('?') > -1 ? '' : '?') + dataUrl.join('&');
		axios.post(url,{})
		      .then(cb)
		      .catch(err =>{
		      	console.log(err);
		      });
	},
	isNull (str) {
		var strr = str.replace(/^\s+|\s+$/gm,'');
		if(strr==null||strr==""||strr==undefined){
			return true;
		}
		return false;
	},
	authJudge  (authCode) {
  //   let userInfo = JSON.parse( sessionStorage.getItem('userInfo'));
     let userInfo = store.getters.userInfo; 
     const auth = userInfo.auths;
     const role = userInfo.roles;
    if(auth.includes(authCode)||role.includes(1))
    {
  	  return true;
    }
    else
    {
  	  return false;
    }
    }
    ,
    // modelJudge  (authCode) {
    //  let userInfo = store.getters.userInfo; 
    //  const modelAuths = userInfo.modelAuths;
    
    //   const role = userInfo.roles;
    // if(modelAuths.includes(authCode)||role.includes(1))
    // {
    //   return true;
    // }
    // else
    // {
    //   return false;
    // }
    // }
    // ,
    directoryJudge  (userId,directoryId,mode) {
     let userInfo = store.getters.userInfo; 
     const directoryAuths = userInfo.directoryAuths;
       const role = userInfo.roles;
        const uid = userInfo.profile.iD;
    if(role.includes(1))
    {
      return true;
    }
    if(uid==userId)
    {
      return true;
    }

    for(var a of directoryAuths)
    {
      if(a.directoryId==directoryId&&a.mode>=mode)
      {
        return true;
        break;
      }
    }


    }
    ,
	changeDate (){
  	console.log('123');
    } , 
   isLogin()
    {
    
      if(store.getters.session)
      {
        return true;
      }
      else
      {
        return false;
      }
    },
      isOpened()
    {
    
      if(store.getters.opened)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
}