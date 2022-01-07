package com.boot.tenant.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.tenant.config.druid.DynamicDataSource;
import com.boot.tenant.mapper.UserMapper;
import com.boot.tenant.pojo.Tenant;
import com.boot.tenant.pojo.User;
import com.boot.tenant.service.TenantRdsService;
import com.boot.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author binSin
 * @since 2021-12-08
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {


    @Autowired
    TenantService tenantService;

    // 获取所有租户信息
    @GetMapping("/all")
    public String all(HttpServletRequest request) {
        String tenantHeader = request.getHeader("tenant");
        System.out.println(tenantHeader);
        List<Tenant> list = tenantService.all();
        return JSON.toJSONString(list);
    }


    @Resource
    UserMapper userMapper;
    @Resource
    TenantRdsService tenantRdsServiceImpl;

    @PostMapping("/login")
    public String login(@RequestParam String tenantCode
//            , @RequestParam String name, @RequestParam Integer password
    ) {

        tenantRdsServiceImpl.switchRds(tenantCode);
//        tenantRdsServiceImpl.switchRds("dataSourceDb1");dataSourceMaster
        System.out.println((DynamicDataSource.getDataSourceKey()));
//        List<User> user = userMapper.selectAll();
//        System.out.println(JSON.toJSONString(user));
//        return JSON.toJSONString(user);

        return JSON.toJSONString(tenantCode);
    }
}
