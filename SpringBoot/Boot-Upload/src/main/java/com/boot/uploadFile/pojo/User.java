package com.boot.uploadFile.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author binSin
 * @date 2021/10/15
 */
@Data
public class User implements Serializable {

    @Excel(name = "姓名", width = 25, orderNum = "0", groupName = "基本信息")
    private String name;

    @Excel(name = "年龄", width = 25, orderNum = "1", groupName = "基本信息")
    private Integer age;

    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, orderNum = "2", suffix = "生", groupName = "基本信息")
    private Integer sex;

    @Excel(name = "出生日期",orderNum = "3", format = "yyyy-MM-dd", width = 20, groupName = "基本信息")
    private LocalDateTime birthday;


}
