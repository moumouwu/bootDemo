package com.boot.netty.example.demo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author binSin
 * @date 2021/11/2
 * <p>
 * 客户端
 * </p>
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 在到服务器的连接已经建立之后将被调用；
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知 Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks! 你好丫！！",
                CharsetUtil.UTF_8));
    }


    /**
     * 当从服务器接收到一条消息时被调用；
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        // 记录已接收消息的转储
        System.out.println(
                "Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时被调用。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

