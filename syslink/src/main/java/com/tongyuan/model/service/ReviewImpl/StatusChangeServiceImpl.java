package com.tongyuan.model.service.ReviewImpl;

import com.tongyuan.exception.SqlNumberException;
import com.tongyuan.model.domain.ReviewFlowInstance;
import com.tongyuan.model.domain.ReviewNode;
import com.tongyuan.model.domain.ReviewNodeInstance;
import com.tongyuan.model.domain.enums.ConstNodeInstanceStatus;
import com.tongyuan.model.domain.enums.ConstReviewFlowInstanceStatus;
import com.tongyuan.model.service.*;
import com.tongyuan.model.service.ReviewService.NodeInstanceService;
import com.tongyuan.model.service.ReviewService.NodeService;
import com.tongyuan.model.service.ReviewService.ReviewFlowInstanceService;
import com.tongyuan.model.service.ReviewService.StatusChangeService;
import com.tongyuan.tools.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Y470 on 2017/7/9.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class StatusChangeServiceImpl implements StatusChangeService {
    @Autowired
    private NodeInstanceService nodeInstanceService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private CheckorService checkorService;
    @Autowired
    private ReviewFlowInstanceService reviewFlowInstanceService;
    @Autowired
    private ModelService modelService;

    @Override
    public void agree(Long id) throws SqlNumberException{
        //1.将实例表中的status由2变为3, 调整其最后修改时间
        ReviewNodeInstance reviewNodeInstance = changeOwnNodeStatus(id, ConstNodeInstanceStatus.AGREE);
        //2.查找下一个节点实例对应节点的sequence
        String sequence = getNextSequence(reviewNodeInstance);
        //3.改变下一个节点和整个流程实例的状态（多表联合操作）
        Long instanceId = reviewNodeInstance.getInstanceId();
        Boolean hasNext = updateStatus(instanceId,sequence,ConstNodeInstanceStatus.ACTIVE);
        //4.改变整个流程状态
        if(!hasNext){
            changeFlowInstance(instanceId, ConstReviewFlowInstanceStatus.FINISH);
        }

    }

    @Override
    public void disagree(Long id) {
        //1.本节点实例状态变化
        ReviewNodeInstance reviewNodeInstance = changeOwnNodeStatus(id,ConstNodeInstanceStatus.NOT_AGREE);
        //2.本流程实例状态变化
        Long instanceId = reviewNodeInstance.getInstanceId();
        changeFlowInstance(instanceId,ConstReviewFlowInstanceStatus.REFUSE);
    }

    /**
     * 得到下一节点的sequence
     * @param reviewNodeInstance  本节点实例
     * @return 下一节点的sequence
     */
    public String getNextSequence(ReviewNodeInstance reviewNodeInstance){
        Long nodeId = reviewNodeInstance.getNodeId();
        ReviewNode reviewNode = nodeService.queryByNodeId(nodeId);
        String sequence = reviewNode.getSequence();
        Integer s = Integer.parseInt(sequence)+1;
        sequence = s.toString();
        return sequence;
    }

    /**
     * 若存在下一个节点，改变下一个节点和状态
     * @param instanceId 流程实例id
     * @param sequence 下一个节点在流程中的次序
     * @param   newStatus 新status
     * @return  是否存在下一个节点
     * @throws SqlNumberException
     */
    @Override
    public Boolean updateStatus(Long instanceId,String sequence,Byte newStatus) throws SqlNumberException{
        Boolean hasNext = false;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sequence",sequence);
        map.put("instanceId",instanceId);
        List<ReviewNodeInstance> reviewNodeInstances = checkorService.queryAfterAgree(map);
        if(reviewNodeInstances.size()==1){
            hasNext = true;
            ReviewNodeInstance reviewNodeInstance2 = reviewNodeInstances.get(0);
            Long id=reviewNodeInstance2.getId();
            Byte status2 = reviewNodeInstance2.getStatus();
            if(status2.toString().equals("1")){
                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("status",newStatus);
                map2.put("id",id);
                nodeInstanceService.updateStatus(map2);
            }
        }else if(reviewNodeInstances.size()==0) {      //已经成功完成审签流程
            hasNext = false;
        }else{
            throw new SqlNumberException("查询结果数目应该唯一！");
        }
        return hasNext;
    }

    /**
     * 改变本节点状态
     * @param id   本节点实例的id
     * @param setStatus   本节点修改后的status值
     * @return   本节点实例
     */
    public ReviewNodeInstance changeOwnNodeStatus(Long id, Byte setStatus){
        ReviewNodeInstance reviewNodeInstance = nodeInstanceService.queryById(id);
        Byte status = reviewNodeInstance.getStatus();
        if(status.toString().equals("2")){
            Map<String,Object> map = new HashMap<>();
            map.put("id",id);
            map.put("status",setStatus);
            nodeInstanceService.updateStatus(map);

            Map<String,Object> map1 = new HashMap<>();
            map1.put("id",id);
            map1.put("lastUpdateTime", DateUtil.getCurrentTime());
            nodeInstanceService.updateTime(map1);
        }
        return reviewNodeInstance;
    }


    /**
     * 改变审签实例status和最后修改时间
     * @param instanceId  审签实例id
     * @param setStatus   审签实例修改后的status值
     */
    public void changeFlowInstance(Long instanceId,Byte setStatus){
        ReviewFlowInstance reviewFlowInstance = reviewFlowInstanceService.queryByInstanceId(instanceId);
        Byte status = reviewFlowInstance.getStatus();
        if(status.toString().equals("1")){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("status",setStatus);
            map.put("instanceId",instanceId);
            reviewFlowInstanceService.setStatus(map);
        }
        //对审签流程实例的 最后修改时间做调整
        Map<String,Object> map = new HashMap<>();
        map.put("instanceId",instanceId);
        map.put("lastUpdateTime",DateUtil.getCurrentTime());
        reviewFlowInstanceService.updateTime(map);
    }
}
