package com.tongyuan.model.dao;

import com.tongyuan.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-7-11.
 */
@Mapper
public interface UserMapper {
    public boolean add(User user);
    public boolean update(User user);
    public boolean deleteByIds(String[] ids);
    public Map<String,Object> queryUserById(Long id);
    public User querUserByName(String userName);


    public boolean delete(Long id);

     public  List<Map<String,Object>> findAllUsers(Map<String,Object> map);

     public  List<Map<String ,Object>>findAllName();
}
