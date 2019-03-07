package com.lty.show.request;

/**
 * 通知请求参数
 * @author zhouyongbo
 */
public class RequestNoticeShow {
    /**
     * (终端编码)这里指司机ID
     */
    private Integer devCode;
    /**
     * 账户
     */
    private String account;
    /**
     * 当前业务数据唯一编号
     */
    private String msgNo;
    /**
     * 业务数据
     */
    private String data;
    /**
     * 是否要回调结果接口 (1:是。0:否)
     */
    private int isCallBack;
    /**
     *回调地址
     */
    private String callBackPath;

    public Integer getDevCode() {
        return devCode;
    }

    public void setDevCode(Integer devCode) {
        this.devCode = devCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMsgNo() {
        return msgNo;
    }

    public void setMsgNo(String msgNo) {
        this.msgNo = msgNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIsCallBack() {
        return isCallBack;
    }

    public void setIsCallBack(int isCallBack) {
        this.isCallBack = isCallBack;
    }

    public String getCallBackPath() {
        return callBackPath;
    }

    public void setCallBackPath(String callBackPath) {
        this.callBackPath = callBackPath;
    }
}
