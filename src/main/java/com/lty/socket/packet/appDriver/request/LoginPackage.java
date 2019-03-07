package com.lty.socket.packet.appDriver.request;


import com.lty.enums.ResponseEnum;
import com.lty.service.IDriverUserService;
import com.lty.show.BaseDriverShow;
import com.lty.show.LoginReturnShow;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.PacketDispose;
import com.lty.socket.packet.appDriver.base.ResponseAppBasePacket;
import com.lty.socket.packet.appDriver.response.RsLoginPackage;
import com.lty.socket.packet.base.HeaderPacket;
import com.lty.util.SpringUtil;
import com.lty.util.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 *  登录信息包
 * @author zhouyongbo
 */
public final class LoginPackage extends PacketDispose {



    @Override
    public void appDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        Channel channel = ctx.channel();
        //验证账户和密码合法性
//        if (!StringUtil.isBank(account)
//                || !StringUtil.isBank(pwd)){
//            send(channel,errCode104(103,appBasePacket,null), ResponseEnum.TEXTFRAME);
//            return;
//        }
        IDriverUserService iDriverUserService = SpringUtil.getBeanByClass(IDriverUserService.class);
        ResponseAppBasePacket rsLoginPackage = iDriverUserService.loginAccount(appBasePacket,
                this,ctx.channel());
        if (rsLoginPackage != null ){
            send(channel,rsLoginPackage.encode(), ResponseEnum.TEXTFRAME);
        }
    }


    /**
     * 返回成功信息
     * @param appBasePacket
     * @param baseDriverShow
     * @param token
     * @return
     */
    public ResponseAppBasePacket succee(AppBasePacket appBasePacket,BaseDriverShow baseDriverShow,String token){
        HeaderPacket headerPacket = appBasePacket.getHeader();
        headerPacket.setMsgId(0x104);
        LoginReturnShow loginReturnShow = new LoginReturnShow(baseDriverShow.getBaseDriver().getId(), baseDriverShow.getBaseDriver().getName(),
                token);

        ResponseAppBasePacket rsPacket = new ResponseAppBasePacket(headerPacket,
                new RsLoginPackage(101,"登录成功", loginReturnShow));
        return rsPacket;
    }


    public ResponseAppBasePacket errCode104(int code,AppBasePacket appBasePacket,Object data){
        HeaderPacket headerPacket = appBasePacket.getHeader();
        headerPacket.setMsgId(0x104);
        RsLoginPackage rsLoginPackage =
                new RsLoginPackage(code,data);
        switch (code){
            case 102:
                rsLoginPackage.setMessage("系统繁忙;请稍后登录");
                break;
            case 103:
                rsLoginPackage.setMessage("账号或密码错误");
                break;
            case 105:
                rsLoginPackage.setMessage("已在别处设备登录，请退出重新登录");
                break;
            default:
                return null;
        }
        return new ResponseAppBasePacket(headerPacket,
                rsLoginPackage);
    }

}
