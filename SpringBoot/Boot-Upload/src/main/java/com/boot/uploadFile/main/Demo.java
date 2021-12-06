package com.boot.uploadFile.main;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * @author binSin
 * @date 2021/12/3
 * <p> 关于 图片处理 </p>
 */
public class Demo {

    /**
     * 利用Graphics2D 绘图
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 获取源图片输入流
        FileInputStream imgFileStream = new FileInputStream("F:\\image\\1.png");
        // 指定输出路径
        File file2 = new File("F:\\image\\1.png");
        // 用ImageIO读取图片
        Image image = ImageIO.read(imgFileStream);
        // 利用BufferedImage,将图片加载到内存
        int height = image.getHeight(null);
        int width = image.getWidth(null);
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图像对象。来对图片进行处理
        Graphics2D graphics = bufImage.createGraphics();
        // 抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.setRenderingHints(renderingHints);

        graphics.drawImage(image, 0, 0, null);

        // stroke 属性控制线条的宽度、笔形样式、线段连接方式或短划线图案。
        // 该属性的设置需要先创建BasicStroke对象，再调用setStroke()方法来设置
        // cap是端点样：CAP_BUTT(无修饰)，CAP_ROUND(半圆形末端)，CAP_SQUARE(方形末端，默认值)。
        // Join定义两线段交汇处的连接方式：JOIN_BEVEL(无修饰),JOIN_MITER(尖形末端，默认值),JOIN_ROUND(圆形末端)。
        // miterlimit:修剪斜连接的限制
        // float[]:表示虚线段的数组 虚线段数组的长度 15，5 一段15 一段5
        // dash_phase:开始虚线图案的偏移量
        // 绘制直线
        BasicStroke stroke = new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        // 虚线
        // BasicStroke stroke = new BasicStroke(2.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[]{15, 10}, 0f);
        graphics.setStroke(stroke);

        // 颜色
        graphics.setColor(Color.red);

        // 新方法绘制矩形 Rectangle2D.Double(x,y,w,h) 起点(x,y) 宽高(w,h)
        Rectangle2D.Double rect = new Rectangle2D.Double(20, 120, 80, 80);
        graphics.draw(rect);
        // 旧方法绘制矩形
        // graphics.drawRect(56,64,80,80);

        // 绘制圆角矩形 起点(x,y) 宽高(w,h) 圆角的长轴短轴(arcw,arch)
        RoundRectangle2D.Double rectRound = new RoundRectangle2D.Double(80, 30, 130, 100, 18, 15);
        graphics.draw(rectRound);

        // 其他图形设置其他颜色
        graphics.setColor(Color.green);

        // 新方法绘制直线 Line2D.Double(x1,y1,x2,y2) 起点(x1,y1) 终点(x2,y2)
        Line2D.Double line = new Line2D.Double(130.0, 130.0, 340.0, 30.0);
        graphics.draw(line);
        // 旧方法绘制直线
        // graphics.drawLine(0, 0, 80, 80);
        // graphics.drawLine(50, 100, 120, 0);

        // 椭圆 Ellipse2D.Double(x,y,w,h) 左上角(x,y)宽高(w,h)
        Ellipse2D.Double ellipse = new Ellipse2D.Double(50, 50, 20, 20);
        graphics.draw(ellipse);

        // 绘制弧线 开弧:Arc2D.OPEN  弓弧:Arc2D.CHORD 饼弧:Arc2D.PIE
        // Arc2D.Double(x,y,w,h,start,extent) 左上角(x,y) 宽高(w,h) 起始角度:start 终止角度:extent
        Arc2D.Double arc = new Arc2D.Double(120, 165, 90, 70, 0, 180, Arc2D.CHORD);
        graphics.draw(arc);

        graphics.dispose();

        // 为了保证图片质量，用ImageWriter输出图片
        Iterator writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter imageWriter = (ImageWriter) writers.next();
        ImageOutputStream outputStream = ImageIO.createImageOutputStream(file2);
        // 修改ImageWriterParam,原质量输出图片
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        // 将BufferedImage转换为IIOImag,来输出图片
        IIOImage iioImage = new IIOImage(bufImage, null, null);
        // 输出
        imageWriter.setOutput(outputStream);
        imageWriter.write(null, iioImage, imageWriteParam);
    }
}
