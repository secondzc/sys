import axios from "axios";
import store from '../../vuex/store';
export default {
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
    modelJudge  (authCode) {
     let userInfo = store.getters.userInfo; 
     const modelAuths = userInfo.modelAuths;
    
      const role = userInfo.roles;
    if(modelAuths.includes(authCode)||role.includes(1))
    {
      return true;
    }
    else
    {
      return false;
    }
    }
    ,
    directoryJudge  (authCode,userId) {
     let userInfo = store.getters.userInfo; 
     const directoryAuths = userInfo.directoryAuths;
       const role = userInfo.roles;
        const uid = userInfo.profile.iD;
    if(directoryAuths.includes(authCode)||role.includes(1)||uid==userId)
    {
      return true;
    }
    else
    {
      return false;
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