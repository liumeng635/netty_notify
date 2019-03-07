package com.lty.socket.netty.filter;

import com.lty.util.MD5Utils;
import com.lty.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

/**
 * webSocket 字符串解码 app
 * @author zhouyongbo
 */
public class StringDecode {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StringDecode.class);
    private String decodeString = "";

    public void decoed(ChannelHandlerContext ctx,TextWebSocketFrame frame){
        // 利用 # 分割
        String text = frame.text().trim();
        text = decodeString+ text;
        decodeString = "";
        String[] split = text.split("#");
        for (int i = 0;i <split.length;i++){
            String strDecode = split[i];
            if (!StringUtil.isBank(strDecode)){
                continue;
            }
            //先将 $$ 替换成 #
            String newStr = strDecode.replaceAll("\\$\\$", "#");

            //校验数据
            if (newStr.length()<16){
                decodeString = decodeString+ strDecode;
                return;
            }
//            String jsonAgreement =  newStr.substring(0,newStr.length()-16);
            String checkCode = newStr.substring(newStr.length()-16,newStr.length());
            boolean equalsToMd5 = MD5Utils.isEqualsToMd5((newStr.length() - 16) + "", checkCode);
            if (!equalsToMd5){
                LOGGER.error("校验码错误 data:"+text);
                if (i == split.length-1){
                    decodeString = decodeString+ strDecode;
                    return;
                }
                    return;
            }
            //然后开始解析数据
            String jsonAgreement =  newStr.substring(0,newStr.length()-16);
            ParsingPacket.parsing(ctx,jsonAgreement);
        }

    }


}
