package com.lty.socket.packet.appDriver.base;


import com.lty.socket.netty.filter.StringEncode;
import com.lty.socket.packet.base.HeaderPacket;

/**
 * 响应信息
 * @author zhouyongbo
 */
public class ResponseAppBasePacket {
    private HeaderPacket headerPacket;

    private Object body;


    public ResponseAppBasePacket(HeaderPacket headerPacket, Object body) {
        this.headerPacket = headerPacket;
        this.body = body;
    }

    public String encode(){
       return StringEncode.stringEncode(this);
    }

    public HeaderPacket getHeaderPacket() {
        return headerPacket;
    }

    public void setHeaderPacket(HeaderPacket headerPacket) {
        this.headerPacket = headerPacket;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
