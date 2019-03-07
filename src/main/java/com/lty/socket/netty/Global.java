package com.lty.socket.netty;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.socket.packet.domain.SessionRecord;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:Global
 * Function: TODO ADD FUNCTION.
 * @author zhouyongbo 2018/06/08
 */
public  class Global {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

//
//    /**
//     * 未鉴权的 session 链接记录
//     */
//    private static Map<Channel,Long> noSessionRecords = new HashMap<Channel, Long>();


    /**
     * 已鉴权的session 信息
     * 线程安全
     */
    public static List<SessionRecord> sessionRecords =
            Collections.synchronizedList(new ArrayList<SessionRecord>());


    /**
     * 添加 session
     * @param sessionRecord
     * @param channel
     */
    public static void addSessionRecords(SessionRecord sessionRecord,Channel channel){
            group.add(channel);
            sessionRecord.setRecordDate(System.currentTimeMillis());
            sessionRecord.setChannel(channel);
            sessionRecords.add(sessionRecord);
        logger.info("当前已登录设备:"+Global.sessionRecords.size());
//        logger.info("未鉴权链接数:"+Global.sessionRecords.size());
    }

    /**
     * 将此链接改为已鉴权
     * @param sessionRecord
     */
    public static void loginSessionRecords(SessionRecord sessionRecord){
        SessionRecordsParam sessionRecordsParam = new SessionRecordsParam(sessionRecord.getChannel());
        if (sessionRecords.contains(sessionRecordsParam)) {
            SessionRecord sessionRecords = getSessionRecords(sessionRecordsParam);
            sessionRecords.setRecordDate(System.currentTimeMillis());
            sessionRecords.setIsLogin(1);
        }
    }

    /**
     * 根据司机的 token 或者是 司机Id 来获取
     * @param sessionRecordsParam
     */
    public static SessionRecord getSessionRecords(SessionRecordsParam sessionRecordsParam){
        SessionRecord sessionRecord = new SessionRecord(sessionRecordsParam.getToken(),
                sessionRecordsParam.getDriverId(),
                sessionRecordsParam.getDriverAccount(), sessionRecordsParam.getDriverPhone());
        sessionRecord.setChannel(sessionRecordsParam.getChannel());
        if (!contains(sessionRecordsParam)){
            return null;
        }
        return sessionRecords.get(sessionRecords.indexOf(sessionRecord));
    }

    /**
     * 移除已鉴权的 session session 退出
     * @param sessionRecordsParam
     */
    public static void removeSessionRecord(SessionRecordsParam sessionRecordsParam){
        SessionRecord sessionRecordp = new SessionRecord(sessionRecordsParam.getToken(),
                sessionRecordsParam.getDriverId(),
                sessionRecordsParam.getDriverAccount(), sessionRecordsParam.getDriverPhone());
        sessionRecordp.setChannel(sessionRecordsParam.getChannel());

        SessionRecord sessionRecord = getSessionRecords(sessionRecordsParam);
        if (sessionRecord == null ){
            return;
        }
        group.remove(sessionRecord.getChannel());
        boolean remove = sessionRecords.remove(sessionRecord);

        if (sessionRecord.getChannel()!=null &&
                sessionRecord.getChannel().isOpen()){
            sessionRecord.getChannel().close();
        }
        logger.info("当前已登录设备:"+Global.sessionRecords.size());
//        logger.info("未鉴权链接数:"+Global.sessionRecords.size());
    }

    /**
     * 看是否存在session 链接
     * @param sessionRecordsParam
     * @return
     */
    public static boolean contains(SessionRecordsParam sessionRecordsParam){
        SessionRecord sessionRecordp = new SessionRecord(sessionRecordsParam.getToken(),
                sessionRecordsParam.getDriverId(),
                sessionRecordsParam.getDriverAccount(), sessionRecordsParam.getDriverPhone());
        sessionRecordp.setChannel(sessionRecordsParam.getChannel());
        return sessionRecords.contains(sessionRecordp);
    }


    public static void timedTask(){
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("session-pool-%d").build());
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                logger.info("定时清理未鉴权的链接开始");
                logger.info("链接数："+sessionRecords.size());
                for (int i =0;i< sessionRecords.size();i++){
                    logger.info("设备详情："+sessionRecords.get(i));
                    SessionRecord sessionRecord = sessionRecords.get(i);
                    if (sessionRecord.getIsLogin() == 1 && sessionRecord.getChannel().isOpen()
                            && sessionRecord.getChannel().isActive()){
                        continue;
                    }
                    Channel channel = sessionRecord.getChannel();
                    Long recordDate = sessionRecord.getRecordDate();
                    if (System.currentTimeMillis()-recordDate > 60*10*1000){
                        SessionRecordsParam sessionRecordsParam = new SessionRecordsParam(channel);
                        if (Global.contains(sessionRecordsParam)){
                            SessionRecord sessionRecord1 = Global.getSessionRecords(sessionRecordsParam);
                            if (sessionRecord1.getIsLogin() == 0 || !sessionRecord.getChannel().isOpen()
                                    || !sessionRecord.getChannel().isActive()
                                     ){
                                Global.removeSessionRecord(sessionRecordsParam);
                                continue;
//                                sessionRecords.remove(sessionRecords);
                            }
                        }
                    }
                }
//                logger.info("定时清理未鉴权的链接结束");
            }
        },1,30, TimeUnit.SECONDS);
    }


}
