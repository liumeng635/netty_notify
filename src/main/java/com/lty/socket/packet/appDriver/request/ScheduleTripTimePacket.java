package com.lty.socket.packet.appDriver.request;


import com.lty.mq.produce.ScheduleTripTimeProduce;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.PacketDisposeToken;
import com.lty.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 行程处理
 * @author zhouyongbo
 */
public final class ScheduleTripTimePacket extends PacketDisposeToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(GpsPacket.class);

    @Override
    public void doAppDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        String body = appBasePacket.getBody();
        LOGGER.info("trip_time:"+body);
        ScheduleTripTimeProduce scheduleTripTimeProduce =
                SpringUtil.getBeanByClass(ScheduleTripTimeProduce.class);
        scheduleTripTimeProduce.send(body);
    }
}
