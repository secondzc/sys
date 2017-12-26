package com.tongyuan.model.dao;

import com.tongyuan.model.domain.DirectoryAuth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by yh on 2017/10/26.
 */
@Mapper
public interface DirectoryAuthMapper {

    public boolean add(DirectoryAuth directoryAuth);

    public boolean deleteByUid(long uid);

    public List<DirectoryAuth> queryByUid(long uid);


    public boolean update(@RequestParam("uid") long uid, @RequestParam("diretoryId") long diretoryId, @RequestParam("mode") Integer mode);

    public boolean delete(@RequestParam("uid") long uid, @RequestParam("diretoryId") long diretoryId);


    public List<Map<String,Object>> queryUserDirectoryAuth(long uid, List<Integer> roleIds);



}
