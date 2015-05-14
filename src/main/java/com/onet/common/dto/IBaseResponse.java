package com.onet.common.dto;

import java.io.Serializable;

/**
 * 返回对象基类接口
 * 
 * @author chenjiajie
 * 
 */
public interface IBaseResponse extends Serializable {
	
	/**
	 * 返回信息的编码
	 * @return
	 */
	public String getReturnCode();

	/**
	 * 返回结果true/false
	 * @return
	 */
	public boolean getResult();

	/**
	 * 返回的信息
	 * @return
	 */
	public String getReturnMessage();

	/**
	 * 返回信息的编码
	 * @param returnCode
	 */
	public void setReturnCode(String returnCode);
	
	/**
	 * 返回结果true/false
	 * @param result
	 */
	public void setResult(boolean result);

	/**
	 * 返回的信息
	 * @param returnMessage
	 */
	public void setReturnMessage(String returnMessage);
}
