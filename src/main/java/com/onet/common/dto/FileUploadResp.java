package com.onet.common.dto;

/**
 * Simditor图片上传返回的数据格式
 */
public class FileUploadResp {
	private boolean success;
	private String msg;
	private String file_path;
	public FileUploadResp() {
	}
	public FileUploadResp(boolean success, String file_path) {
		this.success = success;
		this.file_path = file_path;
	}
	public FileUploadResp(boolean success, String msg, String file_path) {
		this.success = success;
		this.msg = msg;
		this.file_path = file_path;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
}
