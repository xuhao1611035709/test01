package com.jwvdp.books.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * description:properties读取工具
 * @author: WangYaDong
 * @date: 2022/7/20 13:43
 */
public class PropertyUtil {

    public static Properties getFileInstance(String fileName) {
        Properties instance =getProps(fileName);
        return instance;
    }

    private static Properties getProps(String fileName) {
        Properties prop = new Properties();
        InputStream ins = null;
        try {
            ins=new FileInputStream(fileName);
            prop.load(ins); // 加载属性列表到Properties对象中
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("读取" + fileName + "文件失败，失败原因:" + e.getMessage());
        } finally {
            //关闭流
            if (null != ins) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭 读取properties的文件流失败，失败原因：" + e.getMessage());
                }
                ins = null;
            }
        }
        return prop;
    }

    public static String getversion(){
        return "1.0.3";
    }
}
