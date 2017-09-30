import axios from "axios";
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
	
}