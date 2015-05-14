package com.onet.common.dto;

import java.io.Serializable;

/**
 * 分页请求基础接口
 * @author chenjiajie
 *
 */
public interface IPageRequest extends Serializable{
	
	/**
	 * 每页大小
	 * @return
	 */
	public int getPageSize();

	/**
	 * 每页大小
	 * @param pageSize
	 */
	public void setPageSize(int pageSize);

	/**
	 * 页码
	 * @return
	 */
	public int getPageNo();
	
	/**
	 * 页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo);

	/**
	 * 当前页
	 * @return
	 */
	public int getIndexFrom();
}
