package com.jwvdp.books.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageUtil {
    public static BufferedImage convertMultipartFileToBufferedImage(MultipartFile multipartFile) throws IOException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return ImageIO.read(inputStream);
        }
    }

    public static BufferedImage copyBufferedImage(BufferedImage original) {
        // 创建一个新的BufferedImage，使用原图的宽度、高度和图像类型
        BufferedImage copy = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        // 使用Graphics2D绘制原图到新的BufferedImage上
        Graphics2D g = copy.createGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose(); // 释放Graphics2D资源
        return copy;
    }

    /**
     * 将原始图像分割成指定行列数的多个小图像。
     *
     * @param originalImage 原始图像
     * @param rows 分割的行数
     * @param cols 分割的列数
     * @return 分割后的图像数组
     */
    public static BufferedImage[] segmentImage(BufferedImage originalImage, int rows, int cols) {
        // 计算每个分割图像的尺寸
        int chunks = rows * cols;
        int chunkWidth = originalImage.getWidth() / cols; // 分割后每个图像的宽度
        int chunkHeight = originalImage.getHeight() / rows; // 分割后每个图像的高度
        int count = 0;
        BufferedImage[] imgs = new BufferedImage[chunks]; // 图像数组来保存分割后的图像
        try {
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    // 初始化BufferedImage，使用原始图像的类型
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, originalImage.getType());
                    // 绘制每个分割图像
                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(originalImage, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }

            // 输出分割图像
            for (int i = 0; i < imgs.length; i++) {
                ImageIO.write(imgs[i], "jpg", new File("image" + i + ".jpg"));
            }

            System.out.println("分割完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgs;
    }

    /**
     * 按指定位置和尺寸裁剪图像。
     *
     * @param originalImage 原始图像
     * @param x 裁剪的起始x坐标
     * @param y 裁剪的起始y坐标
     * @param width 裁剪的宽度
     * @param height 裁剪的高度
     * @return 裁剪后的图像
     */
    public static BufferedImage cutImage(BufferedImage originalImage, int x, int y, int width, int height) {
        Graphics2D graphics2D = originalImage.createGraphics();
        // 打开抗锯齿
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置插值
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // 设置Alpha值
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.fillRect(x, y, width, height);
        graphics2D.dispose();
        return originalImage;
    }

    /**
     * 使用指定的起始位置和尺寸裁剪图像。
     *
     * @param sourceImage 原始图像
     * @param x 裁剪的起始x坐标
     * @param y 裁剪的起始y坐标
     * @param width 裁剪的宽度
     * @param height 裁剪的高度
     * @return 裁剪后的图像
     * @throws IOException 如果发生I/O错误
     */
    public static BufferedImage cropImage(BufferedImage sourceImage, int x, int y, int width, int height) throws IOException {
        // 使用getSubimage方法来裁剪图片
        BufferedImage croppedImage = sourceImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    /**
     * 将图像转换为灰度图像。
     *
     * @param inputImage 输入图像
     * @return 灰度图像
     * @throws IOException 如果发生I/O错误
     */
    public static BufferedImage greyProcess(BufferedImage inputImage) throws IOException {
        // 创建灰度化后的图片缓冲区
        BufferedImage grayImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        // 将原始图片转换为灰度
        Graphics2D g2dGray = grayImage.createGraphics();
        g2dGray.drawImage(inputImage, 0, 0, null);
        g2dGray.dispose();
        return grayImage;
    }

    /**
     * 压缩图像到指定比例。
     *
     * @param inputImage 输入图像
     * @param x 宽度压缩比例
     * @param y 高度压缩比例
     * @return 压缩后的图像
     * @throws IOException 如果发生I/O错误
     */
    public static BufferedImage compressImage(BufferedImage inputImage, int x, int y) throws IOException {
        // 计算压缩后的尺寸
        int scaledWidth = inputImage.getWidth() / x;
        int scaledHeight = inputImage.getHeight() / y;
        // 创建压缩后的图片缓冲区
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_BYTE_GRAY);
        // 使用高质量压缩策略
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 绘制压缩后的灰度图片
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return outputImage;
    }

    /**
     * 将图像列表输出到磁盘。
     *
     * @param brs 图像列表
     * @param type 图像类型（如"jpg"）
     * @param dir 输出目录
     * @throws IOException 如果发生I/O错误
     */
    public static void outPutToDisk(ArrayList<BufferedImage> brs, String type, String dir) throws IOException {
        for (int i = 0; i < brs.size(); i++) {
            ImageIO.write(brs.get(i), type, new File(dir + "\\" + i + "." + type));
        }
    }

    /**
     * 将单个图像输出到磁盘。
     *
     * @param br 图像
     * @param type 图像类型（如"jpg"）
     * @param path 输出路径
     * @throws IOException 如果发生I/O错误
     */
    public static void outPutToDisk(BufferedImage br, String type, String path) throws IOException {
        ImageIO.write(br, type, new File(path));
    }
}
