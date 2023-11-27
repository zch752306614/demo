package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.novel.dto.URLInfoDTO;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.consts.SysConstants;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
@Service("reptileService")
public class ReptileServiceImpl implements ReptileService {

    @Resource
    private NovelInfoDao novelInfoDao;
    @Resource
    private NovelChapterDao novelChapterDao;

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
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:60.0) Gecko/20100101 Firefox/60.0");
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
     * 提取信息
     *
     * @param html String 完整html文本
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

    /**
     * 保存整本小说
     *
     * @param urlInfoDTO URLInfoDTO 路径结构信息
     */
    @Override
    public void saveNovel(URLInfoDTO urlInfoDTO) {
        String novelName = urlInfoDTO.getNovelName();
        int endIndex = urlInfoDTO.getEndIndex();
        if (ObjectUtil.isEmpty(endIndex)) {
            endIndex = SysConstants.MAX_CHAPTER;
        }
        int errorCount = 0;
        int chapterCount = 0;
        String url;
        List<NovelChapter> novelChapterList = new ArrayList<>();
        for (int index = urlInfoDTO.getStartIndex(); index <= endIndex; index += urlInfoDTO.getInterval()) {
            if (errorCount > 10) {
                break;
            }
            String title = "";
            StringBuilder content = new StringBuilder();
            if (urlInfoDTO.isPartFlag()) {
                int partIndex = urlInfoDTO.getPartStartIndex();
                boolean firstFlag = false;
                String preTitle = "";
                String preContent = "";
                while (true) {
                    if (firstFlag) {
                        partIndex += urlInfoDTO.getPartInterval();
                        url = urlInfoDTO.getBaseUrl() + index + urlInfoDTO.getPartSuffix() + partIndex + urlInfoDTO.getUrlSuffix();
                    } else {
                        url = urlInfoDTO.getBaseUrl() + index + urlInfoDTO.getUrlSuffix();
                    }
                    Map<String, String> data = getNovelInfo(url);
                    String titlePart = data.get("title");
                    String contentPart = data.get("content");
                    if (ObjectUtil.isEmpty(titlePart) || ObjectUtil.isEmpty(contentPart)
                            || SysConstants.TITLE_ERROR_FILTER.equals(titlePart)
                            || SysConstants.CONTENT_ERROR_FILTER.equals(contentPart)
                            || preTitle.equals(titlePart) || preContent.equals(contentPart)) {
                        break;
                    }
                    if (!firstFlag) {
                        firstFlag = true;
                        title = titlePart.substring(0, Math.max(titlePart.indexOf(urlInfoDTO.getTitleSeparator()), 0));
                        content.append(contentPart);
                    }
                    content.append(contentPart);
                    // 记录上一次的结果
                    preTitle = titlePart;
                    preContent = preTitle;
                }
            } else {
                url = urlInfoDTO.getBaseUrl() + index + urlInfoDTO.getUrlSuffix();
                Map<String, String> data = getNovelInfo(url);
                title = data.get("title");
                content.append(data.get("content"));
                if (ObjectUtil.isEmpty(title) || ObjectUtil.isEmpty(content)
                        || SysConstants.TITLE_ERROR_FILTER.equals(title)
                        || SysConstants.CONTENT_ERROR_FILTER.equals(content.toString())) {
                    errorCount++;
                    continue;
                }

            }
            NovelChapter novelChapter = NovelChapter.builder()
                    .chapterContent(content.toString())
                    .chapterName(title)
                    .chapterNumber(chapterCount)
                    .chapterWordsCount(content.toString().length())
                    .build();

            novelChapterList.add(novelChapter);
            chapterCount++;
            errorCount = 0;
            System.out.println(title);
//            System.out.println(content);
        }
        NovelInfo novelInfo = NovelInfo.builder()
                .novelName(urlInfoDTO.getNovelName())
                .novelAuthor(urlInfoDTO.getNovelAuthor())
                .novelChapterCount(chapterCount)
                .completedFlag(SysConstants.IS_YES)
                .build();
        novelInfoDao.insert(novelInfo);
        for (NovelChapter chapter : novelChapterList) {
            chapter.setNovelId(novelInfo.getId());
            novelChapterDao.insert(chapter);
        }
    }

}
