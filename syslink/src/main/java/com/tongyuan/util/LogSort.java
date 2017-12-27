package com.tongyuan.util;


import com.tongyuan.model.domain.Log;

import java.util.Comparator;

/**
 * [部门]比较器
 * 
 * @author 刘奇
 *
 */
public class LogSort implements Comparator<Log> {

	public int compare(Log o1, Log o2){
		return o1.getCreateDate().compareTo(o2.getCreateDate());
	}
	
}
