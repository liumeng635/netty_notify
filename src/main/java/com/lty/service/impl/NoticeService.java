package com.lty.service.impl;

import com.alibaba.fastjson.JSON;
import com.lty.service.INoticeService;
import com.lty.show.ResultShow;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.show.request.RequestNoticeShow;
import com.lty.socket.netty.Global;
import com.lty.socket.netty.filter.StringEncode;
import com.lty.socket.packet.base.HeaderPacket;
import com.lty.socket.packet.domain.NoticePacket;
import com.lty.socket.packet.domain.SessionRecord;
import com.lty.util.StringUtil;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 通知实现　
 * @author zhouyongbo
 */
@Service
public class NoticeService implements INoticeService {

    @Override
    public ResultShow noticeApp(RequestNoticeShow requestNoticeShow) {
        if (requestNoticeShow.getIsCallBack() == 1 &&
                !StringUtil.isBank(requestNoticeShow.getCallBackPath())){
            return new ResultShow(102,"回调地址错误");
        }

        if (!StringUtil.isBank(requestNoticeShow.getAccount()) &&
                requestNoticeShow.getDevCode()==null ||
                requestNoticeShow.getDevCode()<=0){
            //群发
            groupNotice(requestNoticeShow.getData());
            return new ResultShow(101,"成功");
        }
        Integer devCode = requestNoticeShow.getDevCode();
        String account = requestNoticeShow.getAccount();
        SessionRecord sessionRecords = Global.getSessionRecords(new SessionRecordsParam(
                null, devCode, account, null));
        if (sessionRecords == null){
            return new ResultShow(103,"终端不在线");
        }
        //生成 报文ID
        String longTime = System.currentTimeMillis()+""+sessionRecords.getDriverId();
        Integer integer = Integer.valueOf(longTime.substring(longTime.length() - 8, longTime.length()));
        HeaderPacket headerPacket = new HeaderPacket();
        headerPacket.setMsgId(0x301);
        headerPacket.setMsgSn(integer);
        headerPacket.setMsgVn(1.0f);
        com.lty.socket.packet.appDriver.response.NoticePacket noticePacket =
                new com.lty.socket.packet.appDriver.response.NoticePacket(headerPacket,JSON.parseObject(requestNoticeShow.getData()));
        sessionRecords.getChannel().writeAndFlush(
                new TextWebSocketFrame(StringEncode.stringEncode(noticePacket)));
        if (requestNoticeShow.getIsCallBack() == 1){
            Map<Integer, NoticePacket> callBackPath = sessionRecords.getCallBackPath();
            callBackPath.put(integer,
                    new NoticePacket(requestNoticeShow.getMsgNo(),requestNoticeShow.getCallBackPath()));
        }
        return new ResultShow(103,"发送成功");
    }

    public void groupNotice(String body){
        HeaderPacket headerPacket = new HeaderPacket();
        headerPacket.setMsgId(0x301);
        String longTime = System.currentTimeMillis()+"";
        Integer integer = Integer.valueOf(longTime.substring(longTime.length() - 8, longTime.length()));
        headerPacket.setMsgSn(integer);
        headerPacket.setMsgVn(1.0f);
        com.lty.socket.packet.appDriver.response.NoticePacket noticePacket =
                new com.lty.socket.packet.appDriver.response.NoticePacket(headerPacket, JSON.parseObject(body));
        List<SessionRecord> sessionRecords = Global.sessionRecords;
        for (SessionRecord sessionRecord:sessionRecords){
                if (sessionRecord.getIsLogin() == 1){
                    sessionRecord.getChannel().writeAndFlush(
                            new TextWebSocketFrame(StringEncode.stringEncode(noticePacket)));
                }
        }
    }


}
