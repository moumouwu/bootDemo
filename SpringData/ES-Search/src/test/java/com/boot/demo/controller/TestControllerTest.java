package com.boot.demo.controller;

import com.boot.demo.service.SysUserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author binSin
 * @date 2021/9/3
 */
class TestControllerTest {

    @Resource
    SysUserServiceImpl sysUserService;

    @Test
    void save() {
        sysUserService.save();
    }
}