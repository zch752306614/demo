package com.alice.novel.module.novel.controller;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Alice
 * @version 1.0
 * @date 2020/8/15 15:45
 */

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @PostMapping("/upload")
    public String uploadExcel(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        try {
            // 读取Excel文件
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            // 读取Excel工作表
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 遍历获取HTML源码，提取信息
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                //获取行
                XSSFRow row = sheet.getRow(i);
                //获取列
                XSSFCell cell = row.getCell(0);
                //获取url
                String url = cell.getStringCellValue();
                System.out.println(i + 1 + "、url=" + url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
