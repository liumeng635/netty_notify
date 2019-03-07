package com.lty.socket.packet.appDriver.request;

import com.alibaba.fastjson.JSONObject;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.socket.netty.Global;
import com.lty.socket.packet.appDriver.base.AppBasePacket;
import com.lty.socket.packet.appDriver.base.PacketDisposeToken;
import com.lty.socket.packet.base.HeaderPacket;
import com.lty.socket.packet.domain.NoticePacket;
import com.lty.socket.packet.domain.SessionRecord;
import com.lty.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 *  客户端响应通知
 * @author zhouyongbo
 */
public final class RequestNoticePacket  extends PacketDisposeToken {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestNoticePacket.class);


    @Override
    public void doAppDispose(ChannelHandlerContext ctx, AppBasePacket appBasePacket) {
        HeaderPacket header = appBasePacket.getHeader();
        //报文序列号
        int msgSn = header.getMsgSn();

        SessionRecord sessionRecords = Global.getSessionRecords(new SessionRecordsParam(ctx.channel()));
        if (sessionRecords == null || sessionRecords.getCallBackPath().get(msgSn) == null){
            return;
        }
        NoticePacket noticePacket = sessionRecords.getCallBackPath().get(msgSn);
        if (noticePacket == null){
            return;
        }

        //发起请求
        JSONObject jsoParas = new JSONObject();
        String mgnNo = noticePacket.getMgnNo();
        jsoParas.put("mgnNo",mgnNo);
        jsoParas.put("data",appBasePacket.getBody());

        try{
            RestTemplate restTemplate = SpringUtil.getBeanByClass(RestTemplate.class);
            String result = restTemplate.postForObject(noticePacket.getCallbackPath(), jsoParas.toJSONString(),
                    String.class);
            LOGGER.info("notice  account: "+sessionRecords.getDriverAccount()+" result: "+result);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("notice  account: "+sessionRecords.getDriverAccount()+" exception:"+e.getMessage());
        }

    }
}
