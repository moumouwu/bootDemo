package com.boot.demo.dao;

import com.boot.demo.pojo.SysUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author binSin
 * @date 2021/9/2
 */
public interface UserRepository extends ElasticsearchRepository<SysUser,Long> {
}
