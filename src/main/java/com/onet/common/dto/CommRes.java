package com.onet.common.dto;


/**
 * 基本的response dto
 *
 * @param <T>
 */
public class CommRes<T> implements java.io.Serializable{
	private static final long serialVersionUID = -7891775887690266939L;
	private int rs = 1;//是否请求成功,默认成功
	private String msg;//一般是错误信息
	private String code;//返回编码
	private T data;//数据对象
	public CommRes(){
		super();
	}
	public CommRes(T data){
		this.data = data;
	}
	public CommRes(int rs,String code){
		this.rs = rs;
		this.code = code;
	}
	public CommRes(int rs,String code,String msg){
		this.rs = rs;
		this.code = code;
		this.msg = msg;
	}
	public void setParam(int rs,String code){
		this.rs = rs;
		this.code = code;
	}
	public void setParam(int rs,String code,String msg){
		this.rs = rs;
		this.code = code;
		this.msg = msg;
	}
    public static <T> CommRes<T> errorRes(String msg){
        return new CommRes<>(0,null,msg);
    }
    public static <T> CommRes<T> errorRes(String msg,T data){
        CommRes<T> res =  new CommRes<>(0,null,msg);
        res.setData(data);
        return res;
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
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
    public boolean success(){
        return rs ==1;
    }
}
