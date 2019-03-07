package com.lty.socket.packet.appDriver.base;

import com.lty.cache.redis.ICacheService;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.socket.netty.Global;
import com.lty.util.CacheKeyUtils;
import com.lty.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * token验证包处理
 * @author zhouyongbo
 */
public abstract class PacketDisposeToken extends BaseDispose {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketDisposeToken.class);

    @Override
    public void appDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        AppendBody appendBody = appBasePacket.getAppendBody();
        //token 验证
        if (appendBody.getToken()== null || Global.getSessionRecords(
                new SessionRecordsParam(appendBody.getToken(),null)) == null){
            LOGGER.info("token 验证 ： token 为空值；则链接断开");
            Global.removeSessionRecord(new SessionRecordsParam(ctx.channel()));
            return;
        }

        ICacheService iCacheService = SpringUtil.getBeanByClass(ICacheService.class);
        if (iCacheService.get(CacheKeyUtils.getTokenKey(appendBody.getToken())) == null){
            return;
        };
        //数据 处理
        doAppDispose(ctx,appBasePacket);
    }

    public abstract void doAppDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket);
}
