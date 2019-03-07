package com.lty.socket.packet.appDriver.request;

import com.lty.mq.produce.GpsProduce;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.PacketDisposeToken;
import com.lty.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * gps 轨迹上传
 * @author zhouyongbo
 */
public final class GpsPacket extends PacketDisposeToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(GpsPacket.class);

    @Override
    public void doAppDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        String body = appBasePacket.getBody();
        LOGGER.info("GPS:"+body);
        if (body==null){
            return;
        }
        GpsProduce gpsProduce = SpringUtil.getBeanByClass(GpsProduce.class);
        gpsProduce.send(body);
    }
}
