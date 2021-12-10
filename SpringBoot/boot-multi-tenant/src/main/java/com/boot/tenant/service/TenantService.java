package com.boot.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.tenant.pojo.Tenant;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author binSin
 * @since 2021-12-08
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 获取所有租户信息
     * @return
     */
    List<Tenant> all();
}
