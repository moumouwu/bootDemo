package com.boot.tenant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.tenant.mapper.TenantMapper;
import com.boot.tenant.pojo.Tenant;
import com.boot.tenant.service.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author binSin
 * @since 2021-12-08
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Resource
    TenantMapper tenantMapper;

    /**
     * 获取所有租户信息
     *
     * @return
     */
    @Override
    public List<Tenant> all() {
        return tenantMapper.selectList(null);
//        return null;
    }
}
