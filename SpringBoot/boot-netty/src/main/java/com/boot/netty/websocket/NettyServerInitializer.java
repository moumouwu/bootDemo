package com.boot.netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author binSin
 * @date 2021/11/4
 */
@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource(name = "webSocketServerHandler")
    private ChannelHandler webSocketServerHandler;

    @Resource(name = "httpRequestHandler")
    private ChannelHandler httpRequestHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // HTTP编码解码器
        ch.pipeline().addLast("http-codec", new HttpServerCodec());
        // 把HTTP头、HTTP体拼成完整的HTTP请求
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // 方便大文件传输，不过实质上都是短的文本数据
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        ch.pipeline().addLast("http-handler", httpRequestHandler);
        ch.pipeline().addLast("websocket-handler",webSocketServerHandler);
    }

}

