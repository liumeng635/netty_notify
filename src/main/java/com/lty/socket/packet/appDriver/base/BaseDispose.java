package com.lty.socket.packet.appDriver.base;


import com.alibaba.fastjson.JSON;
import com.lty.enums.ResponseEnum;
import com.lty.exception.WSocketException;
import com.lty.util.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 包处理接口
 * @author zhouyongbo
 */
public abstract class BaseDispose {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDispose.class);

    public void poxyAppDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket){

        //协议验证
        if (simulatorValidation(ctx,appBasePacket)){
            appDispose(ctx,appBasePacket);
        }else {
            throw new WSocketException("协议验证失败");
        }
        return;
    }



    public abstract void appDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket);

    /**
     * 协议验证
     * @param ctx
     * @param appBasePacket
     * @return
     */
    private boolean simulatorValidation(ChannelHandlerContext ctx, AppBasePacket appBasePacket){
        AppendBody appendBody = appBasePacket.getAppendBody();
        if (appendBody != null){
                if (StringUtil.isBank(appendBody.getDevSn())
                        && appendBody.getAppVn() != null
                        && appendBody.getDevType() != null ){
                    return true;
                }
        }
        LOGGER.error("simulator:协议内容不符合;"+JSON.toJSONString(appBasePacket));
        return false;
    }

    public boolean send(Channel channel,Object obj, ResponseEnum responseEnum){
        Object data = null;
        if (responseEnum == ResponseEnum.TEXTFRAME){
            if (obj instanceof String){
                data = new TextWebSocketFrame(obj.toString());
            }else {
                data = new TextWebSocketFrame(JSON.toJSONString(obj));
            }

        }
        if (data != null ){
            return channel.writeAndFlush(data).isSuccess();
        }
        return false;

    }
}
