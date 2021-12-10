package com.boot.tenant.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author binSin
 * @date 2021/12/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> implements Serializable {

    private Integer id;

    private String name;
}
