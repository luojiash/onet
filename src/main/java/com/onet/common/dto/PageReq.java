package com.onet.common.dto;

public class PageReq<T> implements java.io.Serializable{
	private static final long serialVersionUID = -2345709954115007302L;
	private int pageNo = 1;
	private int pageSize = 10;
	private T cond;//请求条件对象
	/**
	 * 从index起
	 * @return
	 */
	public int getIndexFrom(){
		return (getPageNo() - 1) * getPageSize();
	}
	
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getCond() {
		return cond;
	}
	public void setCond(T cond) {
		this.cond = cond;
	}
	
}
