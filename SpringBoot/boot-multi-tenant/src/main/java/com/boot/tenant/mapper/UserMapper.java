package com.boot.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.tenant.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author binSin
 * @date 2021/12/7
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> selectAll();
}
