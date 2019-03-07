package com.lty.socket.packet.base;


/**
 *
 *  包头
 * @author zhouyongbo
 */
public class HeaderPacket {
    /**
     * 报文序列号
     */
    private int msgSn;
    /**
     * 业务Id
     */
    private int msgId;

    /**
     * 报文版本号
     */
    private float msgVn;

    public int getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(int msgSn) {
        this.msgSn = msgSn;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public float getMsgVn() {
        return msgVn;
    }

    public void setMsgVn(float msgVn) {
        this.msgVn = msgVn;
    }
}
