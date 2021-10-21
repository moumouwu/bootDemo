package com.boot.uploadFile.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.boot.uploadFile.exception.CustomException;
import com.boot.uploadFile.pojo.User;
import com.boot.uploadFile.pojo.UserExcelVO;
import com.boot.uploadFile.utils.poi.ExcelUtilsPoi;
import com.boot.uploadFile.utils.result.JsonResult;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author binSin
 * @date 2021/10/15
 * <p>
 * easyPoi --> Excel的导入导出
 * </p>
 */
@RestController
@RequestMapping("excel")
@Slf4j
public class ExcelController {

    /**
     * 单sheet导出
     *
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/export")
    public JsonResult exportExcel(
            HttpServletResponse response) throws IOException {
        List<UserExcelVO> userExcelVOS = getList();
        ExcelUtilsPoi.exportExcel(userExcelVOS, "证书列表信息", "证书列表信息sheet", UserExcelVO.class, "证书列表信息表", response);
        return new JsonResult();
    }


    /**
     * 多sheet导出
     *
     * @param response
     * @return
     */
    @GetMapping(value = "/export2")
    public static String exportExcelManySheet(HttpServletResponse response) {
        Workbook workBook = null;
        try {
            List<UserExcelVO> exportList = getList();
            // 创建参数对象（用来设定excel得sheet得内容等信息）
            ExportParams deptExportParams = new ExportParams();
            // 设置sheet得名称
            deptExportParams.setSheetName("员工报表1");
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", UserExcelVO.class);
            // sheet中要填充得数据
            deptExportMap.put("data", exportList);

            ExportParams empExportParams = new ExportParams();
            empExportParams.setSheetName("员工报表2");

            // 创建sheet2使用得map
            List<User> userList = getUserList();
            Map<String, Object> empExportMap = new HashMap<>();
            deptExportMap.put("title", empExportParams);
            empExportMap.put("entity", User.class);
            empExportMap.put("data", userList);

            // 将sheet1、sheet2、sheet3使用得map进行包装
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            sheetsList.add(deptExportMap);
            sheetsList.add(empExportMap);

            // 执行方法
            workBook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);

            ExcelUtilsPoi.downLoadExcel("员工报表", response, workBook);
        } catch (Exception e) {
            throw new CustomException("导出失败", 40000);
        }
        return "success";
    }

    /**
     * excel当sheet导入
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserExcelVO> userExcelVOS = ExcelUtilsPoi.importExcel(file, UserExcelVO.class);
        userExcelVOS.forEach(System.out::println);
        return new JsonResult();
    }


    public static List<UserExcelVO> getList() {
        List<UserExcelVO> exportList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserExcelVO certExcelVO = new UserExcelVO();
            certExcelVO.setCertName("iso证书" + i);
            certExcelVO.setCertNum("XC123456  " + i);
            certExcelVO.setScanImg("https://jgg.xmlcitc.com/v1/image/merchant/admin/e3b3e8d0c46d4a5cbb671ba9ffc1271e.png");
            certExcelVO.setCertEndTimeStr("2021-10-15 00:00:00");
            exportList.add(certExcelVO);
        }
        return exportList;
    }

    public static List<User> getUserList() {
        List<User> exportList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("张三" + i);
            user.setAge(i);
            user.setSex(1);
            user.setBirthday(LocalDateTime.now());
            exportList.add(user);
        }
        return exportList;
    }
}
