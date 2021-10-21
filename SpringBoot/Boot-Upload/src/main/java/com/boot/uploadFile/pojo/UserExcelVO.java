package com.boot.uploadFile.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vicente
 * @since 2021-03-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExcelVO {

    @Excel(name = "证书名称", width = 25, orderNum = "0", groupName = "基本信息")
    private String certName;

    @Excel(name = "证书编号", width = 25, orderNum = "1", groupName = "基本信息")
    private String certNum;

    @Excel(name = "扫描件图片", width = 15, height = 30, orderNum = "2", type = 2, savePath = "C:/jgg/excel/image", groupName = "基本信息")
    private String scanImg;

    @Excel(name = "到期时间", format = "yyyy-MM-dd", orderNum = "3", width = 25, groupName = "附加信息")
    private String certEndTimeStr;

}
