package com.boot.netty.main;


import java.nio.ByteBuffer;

/**
 * @author binSin
 * @date 2021/11/9
 */
public class demo {
    
    public static void main(String[] args) throws Exception {
        long l = System.currentTimeMillis();
        Thread.sleep(2000);
        String msg = "这波是肉蛋冲击！！！";
        // 创建一个固定大小的Buffer(返回的是HeapByteBuffer)
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = msg.getBytes();
        System.out.println("Byte数组的长度" + bytes.length);
        // 写入数据到buffer中
        byteBuffer.put(bytes);
        // 切换成读模式。关键一步
        byteBuffer.flip();
        // 创建一个临时数组，用户村粗获取到的数据
        byte[] tempByte = new byte[bytes.length];
        int i = 0;
        // 如果还有数据，就循环 循环判断条件
        while (byteBuffer.hasRemaining()) {
            // 获取byteBuffer 中的数据
            byte b = byteBuffer.get();
            // 存放到临时数组中
            tempByte[i] = b;
            i++;
        }
        System.out.println(new String(tempByte));
        System.out.println(System.currentTimeMillis() - l);

    }
}
