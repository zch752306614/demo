package com.alice.novel.module.novel.service.reptile.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.dto.result.ReptileJobResultDTO;
import com.alice.novel.module.novel.service.reptile.CommonReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.MyFileUtils;
import com.alice.support.common.util.MyStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
            List<Node> chapterContentNodes = contentDiv.childNodes();
            StringBuilder content = new StringBuilder();
            for (Node chapterContentNode : chapterContentNodes) {
                if (ObjectUtil.isNotEmpty(chapterContentNode)) {
                    String contentStr = chapterContentNode.toString();
                    if (!MyStringUtils.containsAny(contentStr, "请收藏本站：", "<p class")) {
                        content.append(contentStr.replaceAll("<br>", "</p><p>"));
                    }
                }
            }
            // 获取小说正文
            result.put("code", SysConstants.CODE_SUCCESS);
            result.put("content", "<p>" + content + "</p>");
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
        Element info = document.getElementsByClass("info").get(0);
        Element cover = info.getElementsByClass("cover").get(0);
        Element small = info.getElementsByClass("small").get(0);
        Element intro = info.getElementsByClass("intro").get(0);
        Elements spanBySmall = small.getElementsByTag("span");
        String novelName = info.getElementsByTag("h1").get(0).text();
        String novelAuthorP = spanBySmall.get(0).text();
        String novelCompleteFlagP = spanBySmall.get(1).text();
        String novelLastUpdateTimeP = spanBySmall.get(2).text();
        String novelAuthor = novelAuthorP.substring(novelAuthorP.indexOf("：") + 1);
        String novelCompleteFlag = novelCompleteFlagP.substring(novelCompleteFlagP.indexOf("：") + 1);
        String novelLastUpdateTime = novelLastUpdateTimeP.substring(novelLastUpdateTimeP.indexOf("：") + 1);
        String novelIntroduction = intro.getElementsByTag("dl").get(0).getElementsByTag("dd").get(0).text();
        String imgUrl = cover.getElementsByTag("img").get(0).attr("src");
        String savaUrl = SysConstants.SERVICE_IP + SysConstants.SAVE_NOVEL_COVER_BASE_URL + "/" + imgUrl.substring(imgUrl.lastIndexOf("/"));
        String showUrl = SysConstants.SERVICE_IP + SysConstants.SHOW_NOVEL_COVER_BASE_URL + "/" + imgUrl.substring(imgUrl.lastIndexOf("/"));
        // 下载封面并保存到服务器
        MyFileUtils.downloadFile(imgUrl, savaUrl);
        // 获取小说章节信息
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
            reptileJobDetailResultDTO.setReptileUrl(url + MyStringUtils.removeStrings(chapterUrl, midUrl, novelNumber, "/"));
            reptileJobDetailArrayList.add(reptileJobDetailResultDTO);
        }
        ReptileJobResultDTO reptileJobResultDTO = new ReptileJobResultDTO();
        reptileJobResultDTO.setReptileJobDetailResultDTOList(reptileJobDetailArrayList);
        reptileJobResultDTO.setNovelName(novelName);
        reptileJobResultDTO.setNovelAuthor(novelAuthor);
        reptileJobResultDTO.setNovelIntroduction(novelIntroduction);
        reptileJobResultDTO.setLastUpdateTime(novelLastUpdateTime);
        reptileJobResultDTO.setCompletedFlag(SysConstants.NOVEL_COMPLETE_FLAG_NAME_BQG.equals(novelCompleteFlag) ? SysConstants.IS_NO : SysConstants.IS_YES);
        reptileJobResultDTO.setNovelCover(showUrl);
        return reptileJobResultDTO;
    }

    public static void main(String[] args) {
        String url = "https://www.bqgi.cc/book/10376/1.html";
        String htmlString = HttpUtil.get(url);
        // 使用Jsoup解析HTML
        Document document = Jsoup.parse(htmlString);
        // 查找id为"content"下的所有div元素
        Element chapterContentDiv = document.getElementById("chaptercontent");
        List<Node> chapterContentNodes = document.getElementById("chaptercontent").childNodes();
        StringBuilder content = new StringBuilder();
        content.append("<p>");
        for (Node chapterContentNode : chapterContentNodes) {
            if (ObjectUtil.isNotEmpty(chapterContentNode)) {
                String contentStr = chapterContentNode.toString();
                if (!MyStringUtils.containsAny(contentStr, "请收藏本站：", "<p class")) {
                    content.append(contentStr.replaceAll("<br>", "</p><p>"));
                }
            }
        }
        content.append("</p>");
        System.out.println(content.toString().replaceAll(" ", "").replaceAll("　", "").replaceAll("<p></p>", "").replaceAll("\n", ""));
    }
}
