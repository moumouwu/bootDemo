package com.boot.tenant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author binSin
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Rds extends Model<Rds> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 园区编号
     */
    private Long tenantId;

    /**
     * 数据库地址
     */
    private String dbUrl;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库端口
     */
    private Integer dbPort;

    /**
     * 数据库账号
     */
    private String dbAccount;

    /**
     * 数据库密码
     */
    private String dbPwd;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
