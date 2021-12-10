package com.boot.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.boot.tenant.config.druid.DynamicDataSource;
import com.boot.tenant.pojo.User;
import com.boot.tenant.mapper.UserMapper;
import com.boot.tenant.service.TenantRdsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author binSin
 * @date 2021/12/7
 */
@RestController
public class HelloController {
    /**
     * 切换到主数据源方式1
     */
//    @GetMapping("/masterFirst")
//    @SwitchRds(dataSource = DataSourceConstant.DATA_SOURCE_MASTER)
//    public Object masterFirst() {
//        // todo
//    }
//
//    /**
//     * 切换到主数据源方式2
//     */
//    @GetMapping("/masterSecond")
//    @SwitchMasterRds
//    public Object masterSecond() {
//        // todo
//    }
//
//    /**
//     * 切换到其他已配置的数据源
//     */
//    @GetMapping("/other")
//    @SwitchRds(dataSource = DataSourceConstant.DATA_SOURCE_DB_1)
//    public Object other() {
//        // todo
//    }
//
//    /**
//     * 根据租户代码切换
//     */
//    @GetMapping("/tenant")
//    @SwitchRds(tenantCode = "tenantxxx")
//    public Object tenant() {
//        // todo
//    }

    @Resource
    TenantRdsService tenantRdsServiceImpl;

    @GetMapping("test")
    public String test(String dbName) {
        tenantRdsServiceImpl.switchRds(dbName);
//        tenantRdsServiceImpl.switchRds("dataSourceDb1");dataSourceMaster
        System.out.println((DynamicDataSource.getDataSourceKey()));
        List<User> user = userMapper.selectAll();
        System.out.println(JSON.toJSONString(user));
        return JSON.toJSONString(user);
    }


    @Resource
    UserMapper userMapper;

    @GetMapping("test1")
    public String test1() {

        System.out.println((DynamicDataSource.getDataSourceKey()));
        List<User> user = userMapper.selectAll();
        System.out.println(JSON.toJSONString(user));
        return JSON.toJSONString(user);
    }

//    @GetMapping("test2")
//    @SwitchRds(dataSource = DataSourceConstant.DATA_SOURCE_DB_1)
//    public String test2() {
//        System.out.println((DynamicDataSource.getDataSourceKey()));
//        List<User> user = userMapper.selectAll();
//        System.out.println(JSON.toJSONString(user));
//        return JSON.toJSONString(user);
//    }

    @GetMapping("test3")
    public String test3() {
//        tenantRdsServiceImpl.switchRds();
        DynamicDataSource.dataSourceMap.forEach((key, value) -> {
            System.out.println(key + "====" + value);
        });
        return "SUCCESS";
    }
}
