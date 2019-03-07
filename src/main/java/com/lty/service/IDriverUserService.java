package com.lty.service;

import com.lty.entity.primary.BaseDriverUser;
import com.lty.show.ResultShow;
import com.lty.show.request.OnLineDriverShow;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.AppendBody;
import com.lty.socket.packet.appDriver.base.ResponseAppBasePacket;
import com.lty.socket.packet.appDriver.request.LoginPackage;
import com.lty.socket.packet.appDriver.request.TokenLoginPacket;
import io.netty.channel.Channel;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhouyongbo
 */
public interface IDriverUserService {

    /**
     * 获取登录信息
     * @param userName
     * @param password
     * @return
     */
    ResultShow loginDriverUser(String userName, String password,
                               HttpServletResponse response);


    /**
     * 修改司机用户
     * @param baseDriverUser
     * @return
     */
    int update(BaseDriverUser baseDriverUser);


    /**
     * 处理司机登录业务
     * @param appBasePacket
     * @param loginPackage
     * @param channel
     * @return
     */
    ResponseAppBasePacket loginAccount(AppBasePacket appBasePacket, LoginPackage loginPackage,
                                       Channel channel);


    /**
     * token 登录
     * @param appBasePacket
     * @param tokenLoginPacket
     * @param channel
     * @return
     */
    ResponseAppBasePacket tokenLogin(AppBasePacket appBasePacket, TokenLoginPacket tokenLoginPacket,
                                     Channel channel);

    /**
     * 退出接口  删除token
     * @param token
     */
    void loginOut(AppBasePacket token);


    /**
     * 掉线接口 调用  非删除token
     * @param appendBody
     */
    void loginOutNoToken(AppendBody appendBody);

    /**
     * 查询司机在线接口
     * @param onLineDriverShow
     * @return
     */
    ResultShow onLineDriver(OnLineDriverShow onLineDriverShow);
}
