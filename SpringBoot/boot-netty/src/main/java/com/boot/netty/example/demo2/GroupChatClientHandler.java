package com.boot.netty.example.demo2;



import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * @author binSin
 * @date 2021/11/3
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {

    // 当前Channel 已从对方读取消息时调用。
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}

