package com.jwvdp.books.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

public class BarcodeReader {

    public static   String readBarcodeWithPreprocessing(BufferedImage originalImage) {
        // 尝试原始图像
        String barcode = tryDecode(originalImage);
        if (barcode != null) return barcode;

        // 调整亮度和对比度
        BufferedImage adjustedImage = adjustBrightnessContrast(originalImage, 1.2f, 20);
        barcode = tryDecode(adjustedImage);
        if (barcode != null) return barcode;

        // 应用清晰化滤镜
        BufferedImage sharpenedImage = applySharpenFilter(adjustedImage);
        barcode = tryDecode(sharpenedImage);
        if (barcode != null) return barcode;

        // 放大图片
        BufferedImage scaledImage = scaleImage(sharpenedImage, 2);
        barcode = tryDecode(scaledImage);
        return barcode;
    }

    private static String tryDecode(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            return null;
        }
    }

    private static BufferedImage adjustBrightnessContrast(BufferedImage original, float scaleFactor, float offset) {
        RescaleOp rescaleOp = new RescaleOp(scaleFactor, offset, null);
        BufferedImage adjustedImage = rescaleOp.filter(original, null);
        return adjustedImage;
    }

    private static BufferedImage applySharpenFilter(BufferedImage original) {
        // 一个简单的3x3锐化核
        float[] sharpenMatrix = {
                0.0f, -1.0f, 0.0f,
                -1.0f, 5.0f, -1.0f,
                0.0f, -1.0f, 0.0f
        };

        Kernel kernel = new Kernel(3, 3, sharpenMatrix);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage sharpenedImage = convolveOp.filter(original, null);
        return sharpenedImage;
    }

    private static BufferedImage scaleImage(BufferedImage originalImage, double scaleFactor) {
        int scaledWidth = (int) (originalImage.getWidth() * scaleFactor);
        int scaledHeight = (int) (originalImage.getHeight() * scaleFactor);
        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, originalImage.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        graphics2D.dispose();
        return scaledImage;
    }
}
