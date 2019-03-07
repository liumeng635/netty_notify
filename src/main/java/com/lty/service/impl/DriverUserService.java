package com.lty.service.impl;

import com.alibaba.fastjson.JSON;
import com.lty.cache.DataCache;
import com.lty.cache.redis.ICacheService;
import com.lty.config.SocketServerConfig;
import com.lty.dao.primary.BaseDriverUserDao;
import com.lty.entity.primary.BaseDriver;
import com.lty.entity.primary.BaseDriverUser;
import com.lty.service.IBaseDriverService;
import com.lty.service.IDriverLoginInfoService;
import com.lty.service.IDriverUserService;
import com.lty.show.BaseDriverShow;
import com.lty.show.ReponseOnLineDriverShow;
import com.lty.show.ResultShow;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.show.request.OnLineDriverShow;
import com.lty.socket.netty.Global;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.AppendBody;
import com.lty.socket.packet.appDriver.base.ResponseAppBasePacket;
import com.lty.socket.packet.appDriver.request.LoginPackage;
import com.lty.socket.packet.appDriver.request.TokenLoginPacket;
import com.lty.socket.packet.domain.SessionRecord;
import com.lty.util.*;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 司机用户业务层
 * @author zhouyongbo
 */
@Service
public class DriverUserService implements IDriverUserService{

    @Autowired
    private BaseDriverUserDao baseDriverUserDao;

    @Autowired
    private IBaseDriverService iBaseDriverService;


    @Autowired
    private ICacheService iCacheService;

    @Autowired
    private SocketServerConfig socketServerConfig;


    @Autowired
    private IDriverLoginInfoService iDriverLoginInfoService;

    @Override
    @Transactional(value = "transactionManagerPrimary")
    public ResultShow loginDriverUser(String userName, String password,
                                      HttpServletResponse response) {
        if (!StringUtil.isBank(userName)
                || !StringUtil.isBank(password)){
            return new ResultShow(102,"账号密码错误");
        }
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("userName",userName);
        params.put("password",password);

        BaseDriverUser baseDriverUser =  baseDriverUserDao.findUserNameAndPassword(params);
        if (baseDriverUser == null ){
            return new ResultShow(102,"账号密码错误");
        }
        BaseDriver baseDriver = iBaseDriverService.findOne(baseDriverUser.getDriverId());
        if (baseDriver.getIsEnable() == 0){
            return new ResultShow(102,"账号未开通,请联系管理员");
        }
        SessionRecord sessionRecords = Global.getSessionRecords(new SessionRecordsParam(null, baseDriver.getId()));
        if (sessionRecords != null ){
            iCacheService.delete(CacheKeyUtils.getTokenKey(sessionRecords.getToken()));
            Global.removeSessionRecord(new SessionRecordsParam(null, baseDriver.getId()));
//            return new ResultShow(102,"账号已在其他设备登录");
        }
        //修改数据库 司机在线状态
//            baseDriverUser.setStatus(1);
//            baseDriverUser.setModifyTime(DatesUtils.format(new Date(),DatesUtils.FORMAT_10));
//            int ret = update(baseDriverUser);
//            if (ret < 0 ) {
//                //失败
//                return new ResultShow(102, "系统繁忙，请稍后再试");
//            }
            String token = UUIDUtils.getUUId();
            //存入token 信息
        iCacheService.save(new DataCache(CacheKeyUtils.getTokenKey(token),
                JSON.toJSONString(new BaseDriverShow(baseDriver,baseDriverUser))
                ,socketServerConfig.getServer().getTokenTimeout()));
//        Cookie cookie = new Cookie(CacheKeyUtils.DRIVER_TOKEN,token);
//        response.addCookie(cookie);
        Map<String,Object> objectMap = new HashMap<String,Object>();
        objectMap.put(CacheKeyUtils.DRIVER_TOKEN,token);
        return new ResultShow(101,"登录成功",objectMap);
    }

    @Override
    public int update(BaseDriverUser baseDriverUser) {
        return baseDriverUserDao.update(baseDriverUser);
    }

    @Override
    public ResponseAppBasePacket loginAccount(AppBasePacket appBasePacket,
                                              LoginPackage loginPackage,
                                              Channel channel) {
        //校验数据库司机是否存在
        BaseDriverShow baseDriverShow = null;

        if (baseDriverShow.getBaseDriver() == null ){
            return loginPackage.errCode104(103,appBasePacket,null);
        }else if (baseDriverShow.getBaseDriverUser().getStatus() == 0 ){
            return loginPackage.errCode104(105,appBasePacket,null);
        }else {
            //查看司机是否已经登录
            SessionRecord sessionRecord = Global.getSessionRecords(new SessionRecordsParam(null, baseDriverShow.getBaseDriver().getId(),
                    null, null));
            if (sessionRecord!=null){
                return loginPackage.errCode104(105,appBasePacket,null);
            }
            //修改数据库 司机在线状态
            baseDriverShow.getBaseDriverUser().setStatus(0);
            int ret = update(baseDriverShow.getBaseDriverUser());
            if (ret < 0 ){
                //失败
                return loginPackage.errCode104(102,appBasePacket,null);
            }

            String token = UUIDUtils.getUUId();
            //存入token 信息
            iCacheService.save(new DataCache(CacheKeyUtils.getTokenKey(token),
                    JSON.toJSONString(baseDriverShow),socketServerConfig.getServer().getTokenTimeout()));
            //将此链接添加到已登录的链接中
            //1:先移除
            SessionRecord sessionRecordLogin = new SessionRecord(null, token,
                    baseDriverShow.getBaseDriver().getId(),
                    baseDriverShow.getBaseDriverUser().getUserName(),
                    baseDriverShow.getBaseDriver().getPhoneNo(), baseDriverShow);
            sessionRecordLogin.setChannel(channel);
            Global.loginSessionRecords(sessionRecordLogin);
            //todo 将登录信息加入进去
            //返回登录信息
            return loginPackage.succee(appBasePacket,baseDriverShow,token);
        }
    }

    @Override
    public ResponseAppBasePacket tokenLogin(AppBasePacket appBasePacket,
                                            TokenLoginPacket tokenLoginPacket,
                                            Channel channel) {
        AppendBody appendBody = appBasePacket.getAppendBody();
        String token = appendBody.getToken();
        ICacheService iCacheService = SpringUtil.getBeanByClass(ICacheService.class);
        Object o = iCacheService.get(CacheKeyUtils.getTokenKey(token));
        if (o == null ){
            return tokenLoginPacket.errCode104(104,appBasePacket,null);
        }

        BaseDriverShow baseDriverShow = JSON.parseObject(o.toString(),BaseDriverShow.class);
        //校验 设备是不是已经有其他设备登录 有登录则直接将其他设备挤下线
        SessionRecord sessionRecords = Global.getSessionRecords(new SessionRecordsParam(token, baseDriverShow.getBaseDriver().getId()));
        if (sessionRecords != null){
            iCacheService.delete(CacheKeyUtils.getTokenKey(token));
            Global.removeSessionRecord(new SessionRecordsParam(token, baseDriverShow.getBaseDriver().getId()));
//            return tokenLoginPacket.errCode104(105,appBasePacket,null);
        }
        SessionRecord sessionRecordToken = Global.getSessionRecords(new SessionRecordsParam(channel));
        if (sessionRecordToken == null){
            return null;
        }
        iDriverLoginInfoService.saveDriverInfo(baseDriverShow, appendBody,0);

        baseDriverShow.getBaseDriverUser().setStatus(1);
        baseDriverShow.getBaseDriverUser().setModifyTime(DatesUtils.format(new Date(),DatesUtils.FORMAT_10));
        update(baseDriverShow.getBaseDriverUser());
        //替换token
        String tokenNew = UUIDUtils.getUUId();
        //将token 登录 进行链接状态更改
        sessionRecordToken.setChannel(channel);
        sessionRecordToken.setRecordDate(System.currentTimeMillis());
        sessionRecordToken.setIsLogin(1);
        sessionRecordToken.setDriverId(baseDriverShow.getBaseDriver().getId());
        sessionRecordToken.setDriverPhone(baseDriverShow.getBaseDriver().getPhoneNo());
        sessionRecordToken.setDriverAccount(baseDriverShow.getBaseDriverUser().getUserName());
        sessionRecordToken.setBaseDriverShow(baseDriverShow);
        sessionRecordToken.setAppendBody(appendBody);
        iCacheService.save(new DataCache(CacheKeyUtils.getTokenKey(tokenNew),
                JSON.toJSONString(baseDriverShow),socketServerConfig.getServer().getTokenTimeout()));
        sessionRecordToken.setToken(tokenNew);
        iCacheService.delete(CacheKeyUtils.getTokenKey(token));
        //将成功信息返回
//        LoginReturnShow loginReturnShow = new LoginReturnShow(baseDriverShow.getBaseDriver().getId(),
//                baseDriverShow.getBaseDriver().getName(),tokenNew);
        return tokenLoginPacket.succee(appBasePacket,baseDriverShow,tokenNew);
    }

    @Override
    public void loginOut(AppBasePacket appBasePacket) {
        try{
            loginOutNoToken(appBasePacket.getAppendBody());
            //todo 记录登出时间
            iCacheService.delete(CacheKeyUtils.getTokenKey
                    (appBasePacket.getAppendBody().getToken()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loginOutNoToken(AppendBody appendBody){
        String token = appendBody.getToken();
        Object o = iCacheService.get(CacheKeyUtils.getTokenKey(token));
        if (o == null){
            return;
        }
        BaseDriverShow baseDriverShow = JSON.parseObject(o.toString(), BaseDriverShow.class);
        iDriverLoginInfoService.saveDriverInfo(baseDriverShow,appendBody,1);
    }

    @Override
    public ResultShow onLineDriver(OnLineDriverShow onLineDriverShow) {
        List<ReponseOnLineDriverShow> reponseOnLineDriverShows =
                new ArrayList<ReponseOnLineDriverShow>();
        List<SessionRecord> sessionRecords = Global.sessionRecords;
        if (onLineDriverShow.getCityCode() == null){
            for (SessionRecord sessionRecord : sessionRecords) {
                if (1 == sessionRecord.getIsLogin()){
                    ReponseOnLineDriverShow reponseOnLineDriverShow = new ReponseOnLineDriverShow(sessionRecord.getDriverId(),
                            sessionRecord.getDriverAccount(),
                            sessionRecord.getBaseDriverShow().getBaseDriver().getName(),
                            sessionRecord.getBaseDriverShow().getBaseDriverUser().getUserName());
                    reponseOnLineDriverShows.add(reponseOnLineDriverShow);
                }
            }
            return new ResultShow(101,"登录成功",reponseOnLineDriverShows);
        }
        for (SessionRecord sessionRecord : sessionRecords) {
            if (1 == sessionRecord.getIsLogin() &&
                    onLineDriverShow.getCityCode().equals( sessionRecord.getBaseDriverShow().getBaseDriver().getCityCode())
                           ){
                ReponseOnLineDriverShow reponseOnLineDriverShow =
                        new ReponseOnLineDriverShow(sessionRecord.getDriverId(),
                        sessionRecord.getDriverAccount(),
                        sessionRecord.getBaseDriverShow().getBaseDriver().getName(),
                        sessionRecord.getBaseDriverShow().getBaseDriverUser().getUserName());
                reponseOnLineDriverShows.add(reponseOnLineDriverShow);
            }
        }
         return new ResultShow(101,"登录成功",reponseOnLineDriverShows);
    }


}
