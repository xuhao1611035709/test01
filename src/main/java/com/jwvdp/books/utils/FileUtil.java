package com.jwvdp.books.utils;

import com.jwvdp.xinma.exception.LogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FileUtil {
    /*
     *代码遍历这个数组，只将文件（不包括文件夹）添加到File对象的列表中。
     */
    public static ArrayList<File> listJpgFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        ArrayList<File> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains(".jpg")) fileList.add(file);
                }
            }
        }

        return fileList;
    }

    /*
     *代码遍历这个数组，只将文件（不包括文件夹）添加到File对象的列表中。
     */
    public static String getCsvFileFirstLine(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        String firstLine = null;
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains(".csv")) {String filePath = directoryPath + "\\" + file.getName();
                        // 指定字符编码
                        String charsetName = "UTF-8"; // 或者使用你需要的其他字符编码
                        try {
                            // 创建 FileInputStream 对象
                            FileInputStream fis = new FileInputStream(filePath);
                            // 创建 InputStreamReader 对象，并指定字符编码
                            InputStreamReader isr = new InputStreamReader(fis, charsetName);
                            // 创建 BufferedReader 对象，用于读取文件内容
                            BufferedReader br = new BufferedReader(isr);
                            firstLine = br.readLine();
                            // 关闭流
                            br.close();
                            return firstLine;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return null;
    }



    public static File getCsvFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        String firstLine = null;

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().contains(".csv")) {
                        return  file;
                    }

                }
            }
        }

        return null;
    }



    public static void createDir(String directoryPath) {
        // 创建File对象
        File directory = new File(directoryPath);
        // 检查文件夹是否存在
        if (!directory.exists()) {
            // 文件夹不存在，创建文件夹
            boolean wasSuccessful = directory.mkdirs();
            // 检查文件夹是否成功创建
            if (wasSuccessful) {
                System.out.println("文件夹创建成功: " + directoryPath);
            } else {
                System.out.println("文件夹创建失败，请检查路径是否正确，以及您是否有足够的权限在该路径下创建文件夹。");
            }
        } else {
            // 文件夹已存在
            System.out.println("文件夹已存在: " + directoryPath);
        }
    }

    public static int countLines(MultipartFile file) {
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (br.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    public static Map<String, ArrayList> splitListIntoEqualSublists(String dirPath, List<File> files, int numberOfSublists) {
        Map<String, ArrayList> sublists = new HashMap<>();
        // 计算每个子列表的目标大小
        int sublistSize = files.size() / numberOfSublists;
        int remainingElements = files.size() % numberOfSublists;

        // 定义起始和结束索引
        int start = 0;
        int end;

        // 创建子列表
        for (int i = 0; i < numberOfSublists; i++) {
            // 如果剩余元素不为零，这个子列表应该多一个元素
            if (remainingElements > 0) {
                end = start + sublistSize + 1;
                remainingElements--;
            } else {
                end = start + sublistSize;
            }

            // 创建子列表并添加到结果中
            sublists.put(dirPath + i, new ArrayList<>(files.subList(start, end)));

            // 更新下一个子列表的起始索引
            start = end;
        }

        return sublists;
    }

    public static void appendLineToTxt(String filePath, String line) {
        // 创建一个FileWriter对象，设置append为true
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter writer = new PrintWriter(fw)) {
            // 追加文本到文件
            writer.println(line);
        } catch (IOException e) {
            // 处理异常
            log.error("发生错误：" + e.getMessage());
        }
    }

    //覆盖原有文件
    public static void coverLineToTxt(List<String> data, String outputFilePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFilePath))) {
            pw.write(data.stream().collect(Collectors.joining("\n")));
        }
    }

    //统计文件中指定字段不同值各出现了多少次
    public static void count(String filePath, Integer index) {
        Map<String, Integer> dataCounts = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split.length > 0) {
                    String keyField = split[index];
                    dataCounts.put(keyField, dataCounts.getOrDefault(keyField, 0) + 1);
                }
            }
        } catch (IOException e) {
            log.error("统计异常" + e.getMessage());
        }
        for (Map.Entry<String, Integer> entry : dataCounts.entrySet()) {
            System.out.println("数据 \"" + entry.getKey() + "\" 出现了 \"" + entry.getValue() + "\" 次");
        }
    }

    public static void outPutStrToTxt(String filePath, String str) {
        // 创建一个File对象代表你的目标文件
        File file = new File(filePath);

        // 使用PrintWriter进行写操作
        try (PrintWriter writer = new PrintWriter(file)) {
            // 写入一些文本到文件
            writer.println(str);

        } catch (FileNotFoundException e) {
            // 如果文件没有找到，抛出异常
            log.error("文件没有找到：" + e.getMessage());
        }

    }


    public static void mkDir(String dirPath) throws LogicException {
        File directory = new File(dirPath);
        // 检查文件夹是否存在
        if (!directory.exists()) {
            // 文件夹不存在，创建文件夹
            boolean wasSuccessful = directory.mkdirs();
            // 检查文件夹是否成功创建
            if (wasSuccessful) {
                log.info("文件夹创建成功: {}", dirPath);
            } else {
                log.info("文件夹创建失败");
                throw new LogicException("文件夹创建失败，请检查路径是否正确，以及您是否有足够的权限在该路径下创建文件夹。");
            }
        } else {
            // 文件夹已存在
            log.info("文件夹已存在: {}", dirPath);
        }
    }


}
