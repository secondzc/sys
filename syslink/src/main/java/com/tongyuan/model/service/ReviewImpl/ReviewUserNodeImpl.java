package com.tongyuan.model.service.ReviewImpl;

import com.tongyuan.gogs.domain.GUser;
import com.tongyuan.gogs.service.GUserService;
import com.tongyuan.model.dao.DepartMapper;
import com.tongyuan.model.dao.UserDepartMapper;
import com.tongyuan.model.domain.ReviewUserNode;
import com.tongyuan.model.domain.UserDepart;
import com.tongyuan.model.service.ReviewService.ReviewUserNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewUserNodeImpl implements ReviewUserNodeService{
    @Autowired
    private DepartMapper departMapper;
    @Autowired
    private UserDepartMapper userDepartMapper;
    @Autowired
    private GUserService gUserService;

    @Override
    public ReviewUserNode query() {
        ReviewUserNode root = new ReviewUserNode();
        //记录部门树的叶子节点的list
        List<ReviewUserNode> deptLeaf = new ArrayList<>();
        //构建部门树
        buildTree(0,root,deptLeaf);
        //添加员工
        addUser(deptLeaf);

        return root;
    }

    /**
     * @param rootId 节点的deptId
     * @param node   自己定义的数据类型
     * @param deptLeaf  所有部门的集合
     */
    public void buildTree(Integer rootId,ReviewUserNode node,List<ReviewUserNode> deptLeaf){
        deptLeaf.add(node);
        List<Map<String,Object>> children = departMapper.queryByPid(rootId);
        if(children.isEmpty()){
            //此部门下没有子部门了
            return ;
        }
        List<ReviewUserNode> reviewUserNodeList = new ArrayList<>();
        for(Map<String,Object> child:children){
            ReviewUserNode reviewUserNode = new ReviewUserNode();
            String label = (String) child.get("name");
            reviewUserNode.setLabel(label);
            reviewUserNode.setLeaf(false);
            reviewUserNode.setDisabled(true);
            Integer deptId = (Integer) child.get("id");
            reviewUserNode.setDeptId(deptId);
            reviewUserNodeList.add(reviewUserNode);

            buildTree(deptId,reviewUserNode,deptLeaf);
        }
        node.setChildren(reviewUserNodeList);
    }

    public void addUser(List<ReviewUserNode> deptLeaf){
        for(ReviewUserNode dept:deptLeaf){
            Integer deptId = dept.getDeptId();
            List<UserDepart> userDepartList = userDepartMapper.queryByDepartId(deptId);
            for(UserDepart userDepart:userDepartList){
                Long userId = userDepart.getUid();
                GUser user = gUserService.queryById(userId);
                String userName = user.getName();
                //包装
                ReviewUserNode reviewUserNode = new ReviewUserNode();
                reviewUserNode.setLeaf(true);
                reviewUserNode.setDisabled(false);
                reviewUserNode.setLabel(userName);
                dept.getChildren().add(reviewUserNode);
            }
        }
    }
}
