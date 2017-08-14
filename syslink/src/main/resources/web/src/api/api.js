import axios from 'axios';

let base = '';

export const requestLogin = params => { 
    return axios.post(`${base}/login`, params)
                .then(res => res.data); 
};

export const getUserList = params => { return axios.get(`${base}/api/user/list`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/api/user/listpage`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/api/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/api/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.get(`${base}/api/user/edit`, { params: params }); };

export const addUser = params => { return axios.get(`${base}/api/user/add`, { params: params }); };

export const getModelList = params =>{return axios.get(`${base}/api/model/list`,{params:params }); };

export const getReposList = params =>{return axios.get(`${base}/repository/list`,{params:params }); };

export const getDirectoryList = params =>{return axios.get(`${base}/directory/list`,{params:params});}