package com.lty.socket.packet.appDriver.request;

import com.lty.enums.ResponseEnum;
import com.lty.service.IDriverUserService;
import com.lty.show.BaseDriverShow;
import com.lty.show.LoginReturnShow;
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
 * token登录
 * @author zhouyongbo
 */
public final class TokenLoginPacket extends PacketDispose {


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

        IDriverUserService iDriverUserService = SpringUtil.getBeanByClass(IDriverUserService.class);
        ResponseAppBasePacket responseAppBasePacket = null;
        try{
            responseAppBasePacket = iDriverUserService.tokenLogin(appBasePacket, this, channel);
        }catch (Exception e){
            e.printStackTrace();
            send(channel, StringEncode.stringEncode(errCode104(102,
                    appBasePacket,null)),
                    ResponseEnum.TEXTFRAME);
        }

        if (responseAppBasePacket != null){
            send(channel, StringEncode.stringEncode(responseAppBasePacket),
                    ResponseEnum.TEXTFRAME);
        }
    }

    /**
     * 返回成功信息
     * @param appBasePackets
     * @param baseDriverShow
     * @param token
     * @return
     */
    public ResponseAppBasePacket succee(AppBasePacket appBasePackets,BaseDriverShow baseDriverShow,String token){
        HeaderPacket headerPacket = appBasePackets.getHeader();
        headerPacket.setMsgId(0x104);
        LoginReturnShow loginReturnShow = new LoginReturnShow(baseDriverShow.getBaseDriver().getId(), baseDriverShow.getBaseDriver().getName(),
                token);
        loginReturnShow.setAccount(baseDriverShow.getBaseDriverUser().getUserName());
        ResponseAppBasePacket rsPackets = new ResponseAppBasePacket(headerPacket,
                new RsLoginPackage(101,"登录成功", loginReturnShow));
        return rsPackets;
    }


    public ResponseAppBasePacket errCode104(int code, AppBasePacket appBasePacket, Object data){
        HeaderPacket headerPacket = appBasePacket.getHeader();
        headerPacket.setMsgId(0x104);
        RsLoginPackage rsLoginPackage =
                new RsLoginPackage(code,data);
        switch (code){
            case 101:
                rsLoginPackage.setMessage("登录成功");
                break;
            case 102:
                rsLoginPackage.setMessage("token 错误");
                break;
            case 104:
                rsLoginPackage.setMessage("token 过期");
                break;
            case 105:
                rsLoginPackage.setMessage("已有设备登录");
                break;
            default:
                return null;
        }
        return new ResponseAppBasePacket(headerPacket,
                rsLoginPackage);
    }
}
