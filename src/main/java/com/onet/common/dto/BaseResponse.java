package com.onet.common.dto;


/**
 * 返回对象基类
 *
 * @author chenjiajie
 */
public class BaseResponse implements IBaseResponse {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回结果true/false
     */
    protected boolean result;

    /**
     * 返回信息的编码
     */
    protected String returnCode = "0";

    /**
     * 返回的信息
     */
    protected String returnMessage;

    public BaseResponse() {
    }

    public BaseResponse(String returnMessage) {
        this.result = false;
        this.returnMessage = returnMessage;
    }

    public BaseResponse(boolean result, String returnMessage) {
        this.result = result;
        this.returnMessage = returnMessage;
    }

    public BaseResponse(boolean result) {
        this.result = result;
    }

    public BaseResponse(boolean result, String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.result = result;
        this.returnMessage = returnMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
