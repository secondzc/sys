package com.tongyuan.model.service.ReviewService;

import com.tongyuan.exception.SqlNumberException;

/**
 * Created by Y470 on 2017/7/9.
 */

public interface StatusChangeService {
    void agree(Long id) throws SqlNumberException;
    void disagree(Long id);
    Boolean updateStatus(Long instanceId,String sequence,Byte newStatus) throws SqlNumberException;
}
