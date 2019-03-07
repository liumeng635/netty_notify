package com.lty.socket.packet.appDriver.response;


/**
 * 登录信息响应
 * @author zhouyongbo
 */
public class RsLoginPackage {
    /**
     * 101 成功
     * 102 失败
     * 103 账号密码错误
     * 104 token 过期
     */
    private int stateCode;
    private String message;
    private Object data;

    public RsLoginPackage() {
    }

    public RsLoginPackage(int stateCode, Object data) {
        this.stateCode = stateCode;
        this.data = data;
    }

    public RsLoginPackage(int stateCode, String message, Object data) {
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
