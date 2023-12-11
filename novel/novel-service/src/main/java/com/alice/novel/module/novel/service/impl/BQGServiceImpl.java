package com.alice.novel.module.novel.service.impl;

import com.alice.novel.module.novel.service.BQGService;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 笔趣阁爬虫
 * @DateTime 2023/12/5 14:31
 */
@Service("bQGService")
public class BQGServiceImpl implements BQGService {

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getNovelInfo(String url) {
        // 结果集合
        Map<String, String> result = new HashMap<>(10);
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            // 模拟浏览器浏览
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36 Edg/119.0.0.0");
            CloseableHttpResponse response = httpclient.execute(httpGet);

            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            int responseCode = 200;
            //如果状态响应码为200，则获取html实体内容或者json文件
            if (statusCode == responseCode) {
                String html = EntityUtils.toString(entity, Consts.UTF_8);
                // 提取HTML得到商品信息结果
                result = getData(html);
            }
            // 消耗掉实体
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据html文本提取小说信息
     *
     * @param html 完整html文本
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getData(String html) {
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
//            String str = element.text();
//            text.append("   ".toCharArray(), 0, 3);
//            int len = 0;
//            while (len < str.length()) {
//                int add = 50;
//                if (len == 0) {
//                    add = 48;
//                }
//                text.append(str, len, Math.min(len + add, str.length())).append("\n");
//                len += add;
//            }
            text.append(element.text()).append("\n");
        }
        data.put("content", text.toString());

        //System.out.println("data=" + data);

        //返回的数据
        return data;
    }
}
