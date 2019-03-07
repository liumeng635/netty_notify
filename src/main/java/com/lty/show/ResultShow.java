package com.lty.show;


/**
 * 返回值
 * @author zhouyongbo
 */
public class ResultShow {
    /**
     * 返回编码
     */
    private int stateCode;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回信息
     */
    private Object data;


    public ResultShow(int stateCode, String message) {
        this.stateCode = stateCode;
        this.message = message;
    }

    public ResultShow(int stateCode, String message, Object data) {
        this.stateCode = stateCode;
        this.message = message;
        this.data = data;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
