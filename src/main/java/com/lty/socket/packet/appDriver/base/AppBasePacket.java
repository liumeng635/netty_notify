package com.lty.socket.packet.appDriver.base;


import com.lty.socket.packet.base.HeaderPacket;

/**
 * app基础包信息
 * @author zhouyongbo
 */
public class AppBasePacket {

    private HeaderPacket header;

    private AppendBody appendBody;

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HeaderPacket getHeader() {
        return header;
    }

    public void setHeader(HeaderPacket header) {
        this.header = header;
    }

    public AppendBody getAppendBody() {
        return appendBody;
    }

    public void setAppendBody(AppendBody appendBody) {
        this.appendBody = appendBody;
    }
}
