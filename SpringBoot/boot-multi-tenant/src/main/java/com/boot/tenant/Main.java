package com.boot.tenant;

import jdk.internal.util.xml.impl.Input;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.sql.*;

/**
 * @author binSin
 * @date 2021/12/8
 */
public class Main {


    /**
     * 创建数据库 并执行数据库文件
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://localhost:3306/db?useUnicode=true&useSSL=false&characterEncoding=utf-8&serverTimezone=GMT";
        Connection conn = DriverManager.getConnection(url, "root", "root");
        Statement stat = conn.createStatement();

        String dbName = "db151";
        //创建数据库hello
        stat.executeUpdate("create database " + dbName);

        //打开创建的数据库
        stat.close();
        conn.close();
        url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useSSL=false&characterEncoding=utf-8&serverTimezone=GMT";
        conn = DriverManager.getConnection(url, "root", "root");

//        stat = conn.createStatement();

        //创建表test
//        stat.executeUpdate("create table test(id int, name varchar(80))");

        //添加数据
//        stat.executeUpdate("insert into test values(1, '张三')");
//        stat.executeUpdate("insert into test values(2, '李四')");

        ClassPathResource classPathResource = new ClassPathResource("/static/db3.sql");
        File sql = classPathResource.getFile();
//        File sql = new File("/db3.sql");

            ScriptRunner runner = new ScriptRunner(conn);
        try {
            runner.setStopOnError(true);
            runner.runScript(new FileReader(sql));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查询数据
//        ResultSet result = stat.executeQuery("select * from test");
//        while (result.next()) {
//            System.out.println(result.getInt("id") + " " + result.getString("name"));
//        }

        //关闭数据库
//        result.close();
//        stat.close();
        conn.close();
    }
}
