package com.lty.socket.packet.domain;

/**
 * 通知包
 * @author zhouyongbo
 */
public class NoticePacket {
    private String mgnNo;
    private String callbackPath;

    public NoticePacket(String mgnNo, String callbackPath) {
        this.mgnNo = mgnNo;
        this.callbackPath = callbackPath;
    }

    public String getMgnNo() {
        return mgnNo;
    }

    public void setMgnNo(String mgnNo) {
        this.mgnNo = mgnNo;
    }

    public String getCallbackPath() {
        return callbackPath;
    }

    public void setCallbackPath(String callbackPath) {
        this.callbackPath = callbackPath;
    }
}
