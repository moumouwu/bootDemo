package com.boot.netty.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author binSin
 * @date 2021/11/2
 * <p>
 * 服务端启动类
 * </p>
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = 8081;
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建EventLoopGroup
        // 负责接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 负责网络的读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 它允许轻松引导ServerChannel
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    /**用于从中创建Channel实例的Class */
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServerHandler 到子Channel的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //处理 I/O 事件或拦截 I/O 操作，并将其转发到其ChannelPipeline下一个处理程序
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            //EchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                            ch.pipeline()
                                    // 添加对Http的支持
                                    // http解析器
                                    // .addLast(new HttpServerCodec())
                                    // 支持写大数据流
                                    // .addLast(new ChunkedWriteHandler())
                                    // http聚合器
                                    // .addLast(new HttpObjectAggregator(1024 * 60))
                                    // websocket支持,设置路由
                                    // .addLast(new WebSocketServerProtocolHandler("/ws"))
                                    // 添加自定义租售类
                                    .addLast(serverHandler);
                        }
                    });
            /**
             * 异步地绑定服务器；调用 sync()方法阻塞等待直到绑定完成
             */
            ChannelFuture f = b.bind().sync();
            /**
             * 获取 Channel 的CloseFuture，并且阻塞当前线程直到它完成
             */
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully();
        }
    }
}

