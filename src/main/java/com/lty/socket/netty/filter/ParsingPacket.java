package com.lty.socket.netty.filter;

import com.alibaba.fastjson.JSON;
import com.lty.exception.WSocketException;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.request.*;
import com.lty.socket.packet.appDriver.base.BaseDispose;
import com.lty.socket.packet.base.HeaderPacket;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 解析字符串
 * @author zhouyongbo
 */
public class ParsingPacket {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParsingPacket.class);

    public final static Map<Integer,BaseDispose> PACKET_MAP = new HashMap<Integer, BaseDispose>();

    static {
        PACKET_MAP.put(0x101, new LoginPackage());
        PACKET_MAP.put(0x102, new TokenLoginPacket());
        PACKET_MAP.put(0x103, new LoginOutPackage());
        PACKET_MAP.put(0x201, new GpsPacket());
        PACKET_MAP.put(0x202, new ScheduleTripTimePacket());
        PACKET_MAP.put(0x302, new RequestNoticePacket());
    }
    /**
     * 进行数据解析
     */
    public static void parsing(ChannelHandlerContext ctx,String decodStr){
        AppBasePacket  appBasePacket = null;
        try{
            appBasePacket = JSON.parseObject(decodStr, AppBasePacket.class);
            if (appBasePacket == null){
                LOGGER.error("ParsingPacket：字符串json格式错误:"+decodStr);
                throw new WSocketException("ParsingPacket：字符串json格式错误:"+decodStr);
            }

        }catch (Exception e ){
            throw new WSocketException("ParsingPacket： 字符串json格式异常:"+decodStr);
        }
        HeaderPacket headerPacket = appBasePacket.getHeader();
        BaseDispose dispose = PACKET_MAP.get(headerPacket.getMsgId());
        if(dispose==null){
            LOGGER.warn("ParsingPacket： 无此协议:"+decodStr);
            throw new WSocketException("ParsingPacket： 无此协议:"+decodStr);
        }
//        BaseDispose  baseDispose = null;
//        try{
//            if (appBasePacket.getBody() == null ){
//                baseDispose = JSON.parseObject("{}", dispose.getClass());
//            }else {
//                baseDispose = JSON.parseObject(appBasePacket.getBody(), dispose.getClass());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            LOGGER.error("ParsingPacket： body格式错误:"+appBasePacket.getBody());
//            throw new WSocketException("ParsingPacket： body格式错误:"+appBasePacket.getBody());
//        }
//        if (baseDispose == null){
//            baseDispose = new BaseDispose();
//        }
//        if (baseDispose == null ){
//            LOGGER.error("ParsingPacket： body格式错误:"+appBasePacket.getBody());
//            throw new WSocketException("ParsingPacket： body格式错误:"+appBasePacket.getBody());
//        }
        //业务处理
        dispose.poxyAppDispose(ctx,appBasePacket);
        return;
    }
}
