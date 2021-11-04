package com.boot.netty.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


/**
 * @author binSin
 * @date 2021/11/2
 * <p>
 * 服务端
 * @Sharable 表示可以将带注释的ChannelHandler的同一实例多次添加到一个或多个ChannelPipeline ，而不会出现竞争条件。
 * </p>
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道读取数据事件
     * 对于每个传入的消息都要调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        // 将接收到的消息写给发送者，而不冲刷出站消息

        ctx.write(in);
    }

    /**
     * 通道读取数据事件完毕
     * —通知ChannelInboundHandler 最后一次对channelRead()的调用是当前批量读取中的最后一条消息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("通道读取数据事件完毕！！！");
        // 将消息发送到远程节点，并且关闭该 Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 异常处理
     * 在读取操作期间，有异常抛出时会调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    //注册事件
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("注册了！！！");
    }

    //
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        System.out.println("channelUnregistered！！！");
    }

    //通道已经就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("通道已经就绪！！！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channelInactive！！！");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        System.out.println("userEventTriggered！！！");
    }

    //通道可写性已更改
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        System.out.println("通道可写性已更改！！！");
    }
}

