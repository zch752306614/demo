package com.alice.novel.module.novel.service.reptile.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.novel.service.reptile.CommonReptileService;
import com.alice.support.common.consts.SysConstants;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 笔趣阁爬虫
 * @DateTime 2023/12/5 14:31
 */
@Slf4j
@Service
public class BQGReptileServiceImpl implements CommonReptileService {

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getNovelInfo(String url) {
        Map<String, String> result = new HashMap<>(10);
        try {
            String htmlString = HttpUtil.get(url);
            result = this.getData(htmlString);
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", SysConstants.CODE_FAIL);
            result.put("msg", ex.getMessage());
        }
        return result;
    }

    /**
     * 根据html文本提取小说信息
     *
     * @param htmlString 完整html文本
     * @return Map<String, String>
     */
    public Map<String, String> getData(String htmlString) {
        // 获取的数据，存放在集合中
        Map<String, String> result = new HashMap<>(10);
        try {
            // 使用Jsoup解析HTML
            Document document = Jsoup.parse(htmlString);
            // 查找id为"content"下的所有div元素
            Element contentDiv = document.getElementById("chaptercontent");
            // 获取小说正文
            result.put("code", SysConstants.CODE_SUCCESS);
            result.put("content", contentDiv.text());
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", SysConstants.CODE_FAIL);
            result.put("msg", ex.getMessage());
        }
        return result;
    }

    @Override
    public List<ReptileJobDetailResultDTO> getNovelChapterLink(String baseUrl, String midUrl, String novelNumber) {
        List<ReptileJobDetailResultDTO> reptileJobDetailArrayList = new ArrayList<>(3000);
        String url;
        if (ObjectUtil.isNotEmpty(midUrl)) {
            url = baseUrl + "/" + midUrl + "/" + novelNumber + "/";
        } else {
            url = baseUrl + "/" + novelNumber + "/";
        }
        String htmlString = HttpUtil.get(url);
        // 使用Jsoup解析HTML
        Document document = Jsoup.parse(htmlString);
        Elements elements = document.getElementsByClass("listmain").get(0).getElementsByTag("dl").get(0).getElementsByTag("dd");
        int chapterNumber = 1;
        for (Element element : elements) {
            ReptileJobDetailResultDTO reptileJobDetailResultDTO = new ReptileJobDetailResultDTO();
            Element elementA = element.getElementsByTag("a").get(0);
            String chapterUrl = elementA.attr("href");
            String chapterName = elementA.text();
            if (!chapterUrl.contains(".html")) {
                continue;
            }
            reptileJobDetailResultDTO.setChapterNumber(chapterNumber++);
            reptileJobDetailResultDTO.setChapterName(chapterName);
            reptileJobDetailResultDTO.setReptileUrl(url + chapterUrl.replaceAll(midUrl, "").replaceAll(novelNumber, "").replace("/", ""));
            reptileJobDetailArrayList.add(reptileJobDetailResultDTO);
        }
        return reptileJobDetailArrayList;
    }

}
