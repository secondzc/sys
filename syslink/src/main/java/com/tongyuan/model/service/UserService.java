package com.tongyuan.model.service;

import com.github.pagehelper.Page;
import com.tongyuan.model.domain.User;
import com.tongyuan.model.domainmodel.LoginedUserModel;
import com.tongyuan.gogs.domain.GUser1;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-11.
 */
public interface UserService {



    public boolean add(User user);
    public boolean update(User user);

    public boolean delete(long id);
    public  User queryUserById(Long id);
    public User querUserByName(Map<String,Object>params);

//    public List<Map<String,Object>>findAllUsers();

    public Page<Map<String,Object>> findAllUsers(Map<String,Object> map);

    public List<Map<String,Object>> findAllName();


    public LoginedUserModel CreateLoginedUser(User user);

    public LoginedUserModel CreateLoginedUser(GUser1 guser);

    public void updateLoginstate(long userId, String loginIp, Date loginDate);



 //   public void setDept(UserModel model);

  //  public List<UserModel> getAllUsers();

    public boolean addGUser(Map<String,Object>map);

    public boolean updateUser(Map<String,Object>map);

    public List<Map<String,Object>> queryUser(Map<String,Object>map);

    public boolean deleteUser(long id);


    public boolean nameExist(Map<String,Object>map);

    public boolean emailExist(Map<String,Object>map);



    public User getUserByName(String userName);





}
