package com.onet.common.dto;

/**
 * 分页请求基础类
 * @author chenjiajie
 *
 */
public class PageRequest implements IPageRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 每页大小
	 */
	private int pageSize = 10;

	/**
	 * 页码
	 */
	private int pageNo = 1;

	/**
	 * 当前页
	 */
	private int indexFrom;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	/**
	 * 只能从这里get
	 *
	 * @return
	 */
	public int getIndexFrom() {
		this.indexFrom = (this.getPageNo() - 1) * getPageSize();
		this.indexFrom = this.indexFrom < 0 ? 0 : this.indexFrom;
		return indexFrom;
	}
}
