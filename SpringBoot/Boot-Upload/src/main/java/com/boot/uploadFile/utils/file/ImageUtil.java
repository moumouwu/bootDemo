package com.boot.uploadFile.utils.file;

import com.boot.uploadFile.config.BootConfig;
import com.boot.uploadFile.constant.Constants;
import com.boot.uploadFile.utils.DateUtils;
import com.boot.uploadFile.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author binSin
 * @date 2021/10/9
 */
@Slf4j
public class ImageUtil {

    /**
     * 根据上传的视频 获取缩略图
     *
     * @param baseFile 相对应用的基目录
     * @param fileName 上传视频生成的文件名
     * @return 文件名称
     * @throws Exception
     */
    public static String getThumbnail(String baseFile, String fileName) throws Exception {
        String filePath = BootConfig.getProfile() + fileName.replaceAll(Constants.RESOURCE_PREFIX, "");
        String targetFilePath = baseFile + "/" + DateUtils.timePath() + "/";
        String thumbnail = randomGrabberFFmpegImage(filePath, targetFilePath, IdUtils.fastSimpleUUID());
        return thumbnail.replaceAll(BootConfig.getProfile(), Constants.RESOURCE_PREFIX);
    }

    /**
     * 生成截图
     *
     * @param filePath       视频文件本地路径
     * @param targetFilePath 目标文件夹
     * @param targetFileName 目标文件名
     * @return 图片文件路径
     * @throws Exception
     */
    public static String randomGrabberFFmpegImage(String filePath, String targetFilePath, String targetFileName)
            throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        Frame f;
        int length = ff.getLengthInFrames();
        log.error("长度:{}", length);
        int i = 0;
        String path = null;
        while (i < length) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 100) && (f.image != null)) {
                path = doExecuteFrame(f, targetFilePath, targetFileName);
                break;
            }
            i++;
        }
        ff.stop();
        log.error("path:{}", path);
        return path;
    }

    public static String doExecuteFrame(Frame f, String targetFilePath, String targetFileName) {
        if (null == f || null == f.image) {
            throw new RuntimeException("获取缩略图失败");
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        String imageMat = "jpg";
        String FileName = targetFilePath + targetFileName + "." + imageMat;
        // 截取的帧图片
        BufferedImage bi = converter.getBufferedImage(f);
        int srcImageWidth = bi.getWidth();
        int srcImageHeight = bi.getHeight();
        // 对截图进行等比例缩放(缩略图)
        int width = 480;
        int height = (int) (((double) width / srcImageWidth) * srcImageHeight);
        BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        thumbnailImage.getGraphics().drawImage(bi.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
//        log.info("高度:{}px <==> 宽度:{}px <==> 等比例缩放后高度:{}px <==> 等比例缩放后宽度:{}px <==> 缩略图保存路径:{}", srcImageWidth, srcImageHeight, thumbnailImage.getHeight(), thumbnailImage.getWidth(), FileName);
        File output = new File(FileName);
        try {
            // 写入图片  ImageIO.write(bi, imageMat, output);
            ImageIO.write(thumbnailImage, imageMat, output);
        } catch (IOException e) {
            throw new RuntimeException("缩略图写入文件夹失败");
        }
        return FileName;
    }
}
