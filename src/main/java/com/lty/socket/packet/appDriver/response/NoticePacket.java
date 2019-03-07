package com.lty.socket.packet.appDriver.response;

import com.lty.socket.packet.appDriver.base.ResponseAppBasePacket;
import com.lty.socket.packet.base.HeaderPacket;

/**
 * 通知包
 * @author zhouyongbo
 */
public class NoticePacket extends ResponseAppBasePacket {


    public NoticePacket(HeaderPacket headerPacket, Object body) {
        super(headerPacket, body);
    }
}
