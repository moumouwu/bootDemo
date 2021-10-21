package com.boot.uploadFile.controller;

import com.boot.uploadFile.config.BootConfig;
import com.boot.uploadFile.config.ServerConfig;
import com.boot.uploadFile.utils.StringUtils;
import com.boot.uploadFile.utils.file.FileUploadUtils;
import com.boot.uploadFile.utils.file.FileUtils;
import com.boot.uploadFile.utils.file.ImageUtil;
import com.boot.uploadFile.utils.file.MimeTypeUtils;
import com.boot.uploadFile.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binSin
 * @date 2021/10/20
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private ServerConfig serverConfig;



    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = BootConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }


    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    public JsonResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = BootConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("url", url);
            return JsonResult.success(map);
        } catch (Exception e) {
            return JsonResult.fail( e.getMessage(), null);
        }
    }

    /**
     * 上传视频到本地 并且生成缩略图
     *
     * @param file
     * @return
     */
    @PostMapping("/video")
    public JsonResult uploadVideoComment(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = BootConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file, MimeTypeUtils.MEDIA_EXTENSION);
            String url = serverConfig.getUrl() + fileName;
            String thumbnail = ImageUtil.getThumbnail(filePath, fileName);
            String thumbnailUrl = serverConfig.getUrl() + thumbnail;
            Map<String, Object> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("url", url);
            map.put("thumbnail", thumbnail);
            map.put("thumbnailUrl", thumbnailUrl);
            return JsonResult.success(map);
        } catch (Exception e) {
            return JsonResult.fail( e.getMessage(), null);
        }
    }
}
