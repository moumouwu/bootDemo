package com.boot.netty.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.netty.constant.Constant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerHandler.class);

    /**
     * 定义一个channle 组，管理所有的channel
     * GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

//    @Autowired
//    private ChatService chatService;


    /**
     * 描述：读取完连接的消息后，对消息进行处理。
     * 这里主要是处理WebSocket请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        System.out.println("WebSocketServerHandler====channelRead0");
        handlerWebSocketFrame(ctx, msg);
    }

    /**
     * 描述：处理WebSocketFrame
     *
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker =
                    Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                sendErrorMessage(ctx, "不存在的客户端连接！");
            } else {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, "仅支持文本(Text)格式，不支持二进制消息");
        }

        // 客服端发送过来的消息
        LOGGER.info("加入了连接：{}", ctx.channel().remoteAddress());
        String request = ((TextWebSocketFrame) frame).text();
        LOGGER.info("服务端收到新信息：" + request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (Exception e) {
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            e.printStackTrace();
        }
        if (param == null) {
            sendErrorMessage(ctx, "参数为空！");
            return;
        }

        String type = (String) param.get("type");
//        switch (type) {
//            case "REGISTER":
//                chatService.register(param, ctx);
//                break;
//            case "SINGLE_SENDING":
//                chatService.singleSend(param, ctx);
//                break;
//            case "GROUP_SENDING":
//                chatService.groupSend(param, ctx);
//                break;
//            case "FILE_MSG_SINGLE_SENDING":
//                chatService.FileMsgSingleSend(param, ctx);
//                break;
//            case "FILE_MSG_GROUP_SENDING":
//                chatService.FileMsgGroupSend(param, ctx);
//                break;
//            default:
//                chatService.typeError(ctx);
//                break;
//        }
    }

    /**
     * 描述：客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        chatService.remove(ctx);
        System.out.println("WebSocketServerHandler====channelInactive");
    }

    /**
     * 异常处理：关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    private void sendErrorMessage(ChannelHandlerContext ctx, String errorMsg) {
//        String responseJson = new ResponseJson()
//                .error(errorMsg)
//                .toString();
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "errorMsg");
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(map)));
    }

}
