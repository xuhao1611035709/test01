package com.jwvdp.books.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    public static String readCellFromExcel(File file, int x, int y) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            Row row = sheet.getRow(x); // 获取第一行
            if (row != null) {
                Cell cell = row.getCell(y); // 获取第二列的单元格（索引从0开始，所以1代表第二列）
                if (cell != null) {
                    // 返回单元格的内容
                    return cell.toString();
                }
            }
        }

        return null;
    }
}
