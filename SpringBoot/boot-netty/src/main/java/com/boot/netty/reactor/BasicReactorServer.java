package com.boot.netty.reactor;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author binSin
 * @date 2022/2/17
 */
public class BasicReactorServer {

    public static void start(int port){
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.bind(new InetSocketAddress(port), 128);

            //注册accept事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,new Acceptor(selector, serverSocketChannel));

            System.out.println("阻塞等待就绪事件");
            //阻塞等待就绪事件
            while (selector.select() > 0){
                System.out.println("有事件来。。");
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                //遍历就绪事件
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    Runnable handler = (Runnable) key.attachment();
                    System.out.println("**************run前**");
                    handler.run();
                    System.out.println("************run后****");
                    keys.remove(key);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 接受连接处理
     */
    public static class Acceptor implements Runnable{

        private Selector selector;

        private ServerSocketChannel serverSocketChannel;

        public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
            this.selector = selector;
            this.serverSocketChannel = serverSocketChannel;
        }

        public void run() {
            try {
                System.out.println("开始接受连接");
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ,new DispatchHandler(socketChannel));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取数据处理
     */
    public static class DispatchHandler implements Runnable{
        private SocketChannel socketChannel;

        public DispatchHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        public void run() {
            System.out.println("开始执行读写数据");
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int cnt = 0, total = 0;
                String msg = "";
                do {
                    cnt = socketChannel.read(buffer);
                    if (cnt > 0) {
                        total += cnt;
                        msg += new String(buffer.array());
                    }
                    buffer.clear();
                } while (cnt >= buffer.capacity());
                System.out.println("read data num:" + total);
                System.out.println("recv msg:" + msg);

                //回写数据
                ByteBuffer sendBuf = ByteBuffer.allocate(msg.getBytes().length + 1);
                sendBuf.put(msg.getBytes());
                socketChannel.write(sendBuf);

            }catch (Exception e){
                e.printStackTrace();
                if(socketChannel != null){
                    try {
                        socketChannel.close();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }





    public static void main(String[] args){
        BasicReactorServer.start(9999);
    }
}

