package com.alice.novel.module.novel.controller;

import com.alice.novel.module.common.util.FileReadAndWrite;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

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

                // 判断url不为空并且包含http
                if (!url.isEmpty() && url.contains("http")) {
                    // 获取信息集合
                    Map<String, String> data = getNovelInfo(url);
                    String localUrl = "D:\\小说\\斗罗大陆4\\" + data.get("title") + ".txt";
                    FileReadAndWrite.fileWrite(localUrl, data.get("content"));
                    System.out.println(data.get("title") + "写入成功！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 提取信息
     */
    private static Map<String, String> getNovelInfo(String url) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        // 模拟浏览器浏览
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
        CloseableHttpResponse response1 = httpclient.execute(httpGet);

        // 结果集合
        Map<String, String> result = null;

        //获取响应状态码
        int statusCode = response1.getStatusLine().getStatusCode();

        try {
            HttpEntity entity1 = response1.getEntity();

            int responseCode = 200;
            //如果状态响应码为200，则获取html实体内容或者json文件
            if (statusCode == responseCode) {
                String html = EntityUtils.toString(entity1, Consts.UTF_8);
                // 提取HTML得到商品信息结果
                result = getData(html);
            }
            // 消耗掉实体
            EntityUtils.consume(response1.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> getData(String html) {
        // 获取的数据，存放在集合中
        Map<String, String> data = new HashMap<>(10);

        // 采用Jsoup解析
        Document doc = Jsoup.parse(html);

        // 获取标题
        Elements content = doc.getElementsByClass("reader-main");
        Elements elements = content.get(0).getElementsByTag("h1");
        String title = elements.text();
        if (title != null) {
            data.put("title", title);
        }

        // 获取正文
        content = doc.getElementsByClass("content");
        elements = content.get(0).getElementsByTag("p");
        StringBuilder text = new StringBuilder();
        for (Element element : elements) {
            String str = element.text();
            str = str.replaceAll("\\s*", "");
            str = str.replaceAll("记住搜索求书阁qiushuge.net提前看书。", "");
            str = str.replaceAll(",", "，");
            str = str.replaceAll("\\.", "。");
            str = str.replaceAll("\\\"", "” ");
            str = str.replaceAll("!", "！");
            str = str.replaceAll(":", "：");
            str = str.replaceAll("\\?", "？");
            text.append("   ".toCharArray(), 0, 3);
            int len = 0;
            while (len < str.length()) {
                int add = 50;
                if (len == 0) {
                    add = 48;
                }
                text.append(str, len, Math.min(len + add, str.length())).append("\n");
                len += add;
            }
        }
        data.put("content", text.toString());

        //System.out.println("data=" + data);

        //返回的数据
        return data;
    }

    public static void main(String[] args) {
        int max = 50;
        int star = 4342526;
        int count = 0;

        //存储路径--获取桌面位置
        FileSystemView view = FileSystemView.getFileSystemView();
        File directory = view.getHomeDirectory();
        //存储Excel的路径
        String path = directory + "\\url.xlsx";

        File file = new File(path);

        //定义一个Excel表格
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("sheet1");

        String baseUrl = "https://m.ytryx.com/b3909/";
        for (int i = 0; i < 5000; i++) {
            if (count > max) {
                break;
            }
            String url = baseUrl + (star + i);
            try {
                int part = 1;
                while (true) {
                    if(part > 1) {
                        url += "_" + part;
                    }
                    url += ".html";
                    Map<String, String> data = getNovelInfo(url);
                    String title = data.get("title");
                    if ("1/1".equals(title)) {
                        break;
                    }
                    System.out.println("                            " + title.substring(0, title.indexOf("1/")));
                    System.out.println(data.get("content"));
                    XSSFRow row = sheet.createRow(count);
                    row.createCell(0).setCellValue(url);
                    part++;
//                Scanner scanner = new Scanner(System.in);
//                String str = scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("错误链接=" + url);
            }
            count++;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
            System.out.println("写入成功");
        } catch (Exception e) {
            System.out.println("写入失败");
        }
    }

}
