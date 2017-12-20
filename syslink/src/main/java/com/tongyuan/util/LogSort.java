package com.tongyuan.util;


import com.tongyuan.model.domain.Operationlog;

import java.util.Comparator;

/**
 * [部门]比较器
 * 
 * @author 刘奇
 *
 */
public class LogSort implements Comparator<Operationlog> {

	public int compare(Operationlog o1,Operationlog o2){
		return o1.getCreateDate().compareTo(o2.getCreateDate());
	}
	
}
