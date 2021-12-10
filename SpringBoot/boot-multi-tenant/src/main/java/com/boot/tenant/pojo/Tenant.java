package com.boot.tenant.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.boot.tenant.pojo.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Tenant extends Model<Tenant> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户编号
     */
    private String tenantCode;

    /**
     * 园区名称
     */
    private String parkName;

    /**
     * 园区面积
     */
    private String parkAcreage;

    /**
     * 园区地址
     */
    private String parkAddr;

    /**
     * 园区联系人
     */
    private String parkContactName;

    /**
     * 园区联系方式
     */
    private String parkContactPhone;

    /**
     * 会员到期时间
     */
    private LocalDateTime vipEndTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
