package com.boot.demo.controller;

import com.boot.demo.service.SysUserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binSin
 * @date 2021/9/2
 */
@RestController
public class TestController {

    @Resource
    SysUserServiceImpl sysUserService;


    @GetMapping("/search")
    public Map<String, Object> search() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "张三");
        map.put("pageNum", "1");
        return sysUserService.search(map);
    }

    @GetMapping("/save")
    public String save() {
        sysUserService.save();
        return "success";
    }

}
