package com.boot.netty.example.bio;


import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author binSin
 * @date 2021/11/2
 * <p>
 * 客户端
 * </p>
 */

public class BIOEchoClient {


    public static void main(String[] args) throws Exception {
        Socket client = new Socket("localhost", 18888);
        PrintStream out = new PrintStream(client.getOutputStream());
        boolean flag = true;
        while (flag) {
            Scanner scanner = new Scanner(System.in);
            String inputData = scanner.nextLine().trim();
            out.println(inputData);
            if ("byebye".equalsIgnoreCase(inputData)) {
                flag = false;
                System.out.println("和客户端说再见拉!!!");
            }
        }
        client.close();
    }
}
