package com.boot.juc.controller;

import com.boot.juc.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binSin
 * @date 2021/11/18
 */
@RestController
public class ThreadController{

    private static final Logger logger = LoggerFactory.getLogger(ThreadController.class);

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/test")
    public String sss(){

        //调用service层的任务
        asyncService.executeAsync();

        return "OK";
    }

}
