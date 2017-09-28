package com.tongyuan.util;

import java.util.List;

/**
 * Pager
 * 
 * @author liuq
 *
 */
public class Pager {
	
	//页索引
    private int pageIndex; 
    //每页显示记录数目
    private int pageSize; 
    //记录总数
    private Long totalCount;
    //页总数
    private Long totalPages;
    //上一页
    private boolean hasPreviousPage; 
    //下一页
    private boolean hasNextPage; 
    private List rows;
    
 /**
  * Pager构造函数
  * 
  * @param pageIndex
  * @param pageSize
  * @param totalCount
  * @param rows
  */
    public Pager(int pageIndex,int pageSize,Long totalCount,List rows)
    {
    	this.pageIndex = pageIndex;
    	this.pageSize = pageSize;
    	this.totalCount = totalCount;
    	this.totalPages = totalCount/pageSize;
    	if (totalCount%pageSize>0) {
			this.totalPages++;
		}
    	this.setRows(rows);
    }
      
    /**
     * 获取页索引
     * 
     * @return
     */
	public int getPageIndex() {
		return pageIndex;
	}
	
	/**
	 * 设置页索引
	 * 
	 * @param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	/**
	 * 获取每页显示记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取记录总数
	 * 
	 * @return
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 设置记录总数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public Long getTotalPages() {
		return totalPages;
	}
	
	/**
	 * 设置总页数
	 * 
	 * @param totalPages
	 */
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	
	/**
	 * 是否为上一页
	 * 
	 * @return
	 */
	public boolean isHasPreviousPage() {
		return pageIndex>1;
	}

	/**
	 * 是否为下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		return pageIndex<totalPages;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
