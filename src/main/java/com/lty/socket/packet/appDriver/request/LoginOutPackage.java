package com.lty.socket.packet.appDriver.request;

import com.lty.enums.ResponseEnum;
import com.lty.service.IDriverUserService;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.socket.netty.Global;
import com.lty.socket.netty.filter.StringEncode;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.AppendBody;
import com.lty.socket.packet.appDriver.base.PacketDispose;
import com.lty.socket.packet.appDriver.base.ResponseAppBasePacket;
import com.lty.socket.packet.appDriver.response.RsLoginPackage;
import com.lty.socket.packet.base.HeaderPacket;
import com.lty.util.SpringUtil;
import com.lty.util.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author zhouyongbo
 */
public final class LoginOutPackage  extends PacketDispose {


    @Override
    public void appDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        //验证token 信息
        Channel channel = ctx.channel();
        appBasePacket.getHeader();
        AppendBody appendBody = appBasePacket.getAppendBody();
        if (appendBody == null || !StringUtil.isBank(appendBody.getToken())){
            send(channel, StringEncode.stringEncode(errCode104(102,appBasePacket,null)),
                    ResponseEnum.TEXTFRAME);
        }
        //链接 移除
        Global.removeSessionRecord(new SessionRecordsParam(appendBody.getToken(),null));
        IDriverUserService iDriverUserService = SpringUtil.getBeanByClass(IDriverUserService.class);
        iDriverUserService.loginOut(appBasePacket);
        channel.close();
    }


    public ResponseAppBasePacket errCode104(int code, AppBasePacket appBasePacket, Object data){
        HeaderPacket headerPacket = appBasePacket.getHeader();
        headerPacket.setMsgId(0x104);
        RsLoginPackage rsLoginPackage =
                new RsLoginPackage(code,data);
        switch (code){
            case 101:
                rsLoginPackage.setMessage("退出成功");
                break;
            case 102:
                rsLoginPackage.setMessage("token 错误");
                break;
            default:
                return null;
        }
        return new ResponseAppBasePacket(headerPacket,
                rsLoginPackage);
    }
}
