package com.onet.common.dto;

import java.util.ArrayList;
import java.util.List;
/**
 * common分页responseDTO
 * @author luo
 *
 * @param <T>
 */
public class PageRes<T> implements java.io.Serializable{
	private static final long serialVersionUID = -5748348491078889629L;
	private int rs = 1;//是否请求成功
	private String msg;//一般是错误信息
	private String code;//返回编码
	private int pageNo = 1;//当前页数
	private int pageSize = 5;//分页大小
	private int total = 0;//记录总数
	private List<T> list;//数据记录
	
	public int getIndexFrom() {
		int indexFrom = (this.getPageNo() - 1) * getPageSize();
		indexFrom = indexFrom < 0 ? 0 : indexFrom;
		return indexFrom;
	}
	
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean getHasPrev(){
		return getPageNo() > 1;
	}
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean getHasNext(){
		return getPageNo() * getPageSize() < total;
	}
	public int getTotalPage(){
		return getTotal()%getPageSize() > 0 ? getTotal()/getPageSize() + 1 : getTotal()/getPageSize();
	}
	/***************************getter and setter***************************/
	public int getPageNo() {
		return pageNo;
	}
	public int getRs() {
		return rs;
	}
	public void setRs(int rs) {
		this.rs = rs;
	}
	public String getMsg() {
		return msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize <= 0 ? 5 : pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total < 0 ? 0 : total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getList() {
		if(list == null) list = new ArrayList<T>();
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
