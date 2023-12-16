package com.alice.novel.module.novel.service.reptile.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.dto.result.ReptileJobResultDTO;
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
            Element contentDiv = document.getElementById("content");
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
    public ReptileJobResultDTO getNovelChapterLink(String baseUrl, String midUrl, String novelNumber) {
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
        // 获取小说信息
        String novelName = document.getElementById("info").getElementsByTag("h1").get(0).text();
        Elements elementsByTag = document.getElementById("info").getElementsByTag("p");
        Element ElementByCover = document.getElementById("fmimg").getElementsByTag("img").get(0);
        String novelAuthorP = elementsByTag.get(0).text();
        String novelCompleteFlagP = elementsByTag.get(1).text();
        String novelLastUpdateTimeP = elementsByTag.get(2).text();
        String novelAuthor = novelAuthorP.substring(novelAuthorP.indexOf("：") + 1);
        String novelCompleteFlag = novelCompleteFlagP.substring(novelCompleteFlagP.indexOf("：") + 1);
        String novelLastUpdateTime = novelLastUpdateTimeP.substring(novelLastUpdateTimeP.indexOf("：") + 1);
        String imgUrl = ElementByCover.attr("src");
        String novelCoverUrl = baseUrl + imgUrl;
        // 下载封面并保存到服务器

        // 获取小说章节信息
        Elements elements = document.getElementById("list").getElementsByTag("dl").get(0).getElementsByTag("dd");
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
        ReptileJobResultDTO reptileJobResultDTO = new ReptileJobResultDTO();
        reptileJobResultDTO.setReptileJobDetailResultDTOList(reptileJobDetailArrayList);
        reptileJobResultDTO.setNovelName(novelName);
        reptileJobResultDTO.setNovelAuthor(novelAuthor);
        reptileJobResultDTO.setLastUpdateTime(novelLastUpdateTime);
        reptileJobResultDTO.setCompletedFlag(SysConstants.NOVEL_COMPLETE_FLAG_NAME_BQG.equals(novelCompleteFlag) ? "0" : "1");
        reptileJobResultDTO.setNovelCover(SysConstants.NOVEL_COVER_BASE_URL + imgUrl);
        return reptileJobResultDTO;
    }

    public static void main(String[] args) {
        String date = "最后更新：2023-12-16 18:12:35";
        System.out.println(date.substring(date.indexOf("：") + 1));
    }

}
