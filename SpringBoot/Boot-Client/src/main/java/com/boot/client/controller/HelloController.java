package com.boot.client.controller;

import com.alibaba.fastjson.JSON;
import com.boot.client.domain.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binSin
 * @date 2021/10/14
 */
@Slf4j
@RestController
@RequestMapping("api")
public class HelloController {


    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 获取系统信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/server")
    public String getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return JSON.toJSONString(server);
    }

    @GetMapping("count")
    private String count() {
        // 每次进来如打印下日志

        log.info("{} 啪...我第{}次进来了.", LocalDateTime.now(), count.addAndGet(1));

        // 每次进来new 个大对象，便于监控观察堆内存变化

        byte[] bytes = new byte[100 * 1024 * 1024];

        log.info("new了 100MB");

        return "hi springboot admin " + LocalDateTime.now();
    }
}
