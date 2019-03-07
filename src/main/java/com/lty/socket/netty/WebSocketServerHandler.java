package com.lty.socket.netty;

import com.lty.exception.WSocketException;
import com.lty.service.IDriverUserService;
import com.lty.show.parameter.SessionRecordsParam;
import com.lty.socket.netty.filter.ParsingPacket;
import com.lty.socket.packet.domain.SessionRecord;
import com.lty.util.SpringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * ClassName:WebSocketServerHandler Function: TODO ADD FUNCTION.
 *
 * @author hxy
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);
    private WebSocketServerHandshaker handshaker;

    private static AttributeKey<String> appDriverKey = AttributeKey.valueOf("type");
    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        Global.group.add(ctx.channel());
//        logger.info("当前在线:"+Global.group.size());
        Global.addSessionRecords(new SessionRecord(System.currentTimeMillis(),
                ctx.channel()),ctx.channel());


    }
    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionRecordsParam sessionRecordsParam = new SessionRecordsParam(ctx.channel());

        SessionRecord sessionRecords = Global.getSessionRecords(sessionRecordsParam);
        if (sessionRecords!=null){
            String token = sessionRecords.getToken();
            if (token!=null){
                IDriverUserService iDriverUserService = SpringUtil.getBeanByClass
                        (IDriverUserService.class);
                iDriverUserService.loginOutNoToken(sessionRecords.getAppendBody());
            }
        }
        Global.removeSessionRecord(sessionRecordsParam);
    }
    /**
     * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    private void handlerWebSocketFrame2(ChannelHandlerContext ctx, WebSocketFrame frame) {
        /**
         * web socket 处理
         */
        /**
         * ping 消息
         */
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        /**
         * 文本消息
         */
        if (frame instanceof TextWebSocketFrame) {
            SessionRecord sessionRecords = Global.getSessionRecords(new SessionRecordsParam(ctx.channel()));
            if (sessionRecords != null ){
                sessionRecords.getStringDecode().decoed(ctx, (TextWebSocketFrame) frame);
            }
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
// 如果HTTP解码失败，返回HHTP异常
        if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
//获取url后置参数
        HttpMethod method=req.getMethod();
        String uri=req.getUri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        if(method== HttpMethod.GET&&"/webssss".equals(uri)){
//....处理
            ctx.attr(appDriverKey).set("anzhuo");
        }else if(method== HttpMethod.GET&&"/diverSocket".equals(uri.split("\\?")[0])){
//...处理
           ctx.attr(appDriverKey).set("driver");
        }

// 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://"+req.headers().get(HttpHeaders.Names.HOST)+uri, null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
// 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
// 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof WSocketException){
            logger.error(cause.getMessage());
        }else {
            cause.printStackTrace();
        }
        SessionRecordsParam sessionRecordsParam = new SessionRecordsParam(ctx.channel());
        SessionRecord sessionRecords = Global.getSessionRecords(sessionRecordsParam);
        if (sessionRecords!=null){
            String token = sessionRecords.getToken();
            if (token!=null){
                IDriverUserService iDriverUserService = SpringUtil.getBeanByClass
                        (IDriverUserService.class);
                iDriverUserService.loginOutNoToken(sessionRecords.getAppendBody());
            }
        }
        Global.removeSessionRecord(sessionRecordsParam);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s = ctx.attr(appDriverKey).get();
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
// WebSocket接入
        } else if (msg instanceof WebSocketFrame) {
                handlerWebSocketFrame2(ctx, (WebSocketFrame) msg);
        }
    }
}