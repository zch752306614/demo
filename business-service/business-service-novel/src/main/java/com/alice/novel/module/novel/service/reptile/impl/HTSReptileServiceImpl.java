package com.alice.novel.module.novel.service.reptile.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.dto.result.ReptileJobResultDTO;
import com.alice.novel.module.novel.service.reptile.CommonReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.ChineseAndArabicNumUtil;
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
 * @Description 和图书爬虫
 * @DateTime 2023/12/5 14:28
 */
@Slf4j
@Service
public class HTSReptileServiceImpl implements CommonReptileService {

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
            Elements contentDivs = document.getElementById("content").getElementsByTag("div");
            // 提取小说正文内容
            StringBuilder novelContent = new StringBuilder();
            for (Element contentDiv : contentDivs) {
                novelContent.append(contentDiv.text()).append("\n");
            }
            // 获取小说正文
            result.put("code", SysConstants.CODE_SUCCESS);
            result.put("content", novelContent.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("code", SysConstants.CODE_FAIL);
            result.put("msg", ex.getMessage());
        }
        return result;
    }

    /**
     * 获取小说每个章节的链接
     *
     * @param baseUrl     链接
     * @param novelNumber 小说编号
     * @return List<ReptileDetailInfo> 小说章节链接信息
     */
    @Override
    public ReptileJobResultDTO getNovelChapterLink(String baseUrl, String midUrl, String novelNumber) {
        String url;
        if (ObjectUtil.isNotEmpty(midUrl)) {
            url = baseUrl + "/" + midUrl + "/" + novelNumber + "/dir.json";
        } else {
            url = baseUrl + "/" + novelNumber + "/dir.json";
        }
        String result = HttpUtil.get(url);
        JSONArray dirArray = JSON.parseArray(result);
        List<ReptileJobDetailResultDTO> reptileJobDetailArrayList = new ArrayList<>(3000);
        String chapterPart = "";
        for (Object dir : dirArray) {
            ReptileJobDetailResultDTO reptileJobDetailResultDTO = new ReptileJobDetailResultDTO();
            JSONArray tempArray = JSON.parseArray(dir.toString());
            // 判断是否为卷名
            String str0 = tempArray.getString(0);
            if (SysConstants.STRING_DT.equals(str0)) {
                chapterPart = tempArray.getString(1);
                reptileJobDetailResultDTO.setChapterName(tempArray.getString(1));
                reptileJobDetailResultDTO.setChapterNumber(-1);
            } else if (SysConstants.STRING_DD.equals(str0)) {
                String chapterName = tempArray.getString(1);
                reptileJobDetailResultDTO.setChapterName(chapterName);
                int start = chapterName.indexOf("第") + 1;
                int end = chapterName.indexOf("章");
                if (start < end && chapterName.contains("第") && chapterName.contains("章")) {
                    reptileJobDetailResultDTO.setChapterNumber(ChineseAndArabicNumUtil.chineseNumToArabicNum(chapterName.substring(start, end)));
                } else {
                    reptileJobDetailResultDTO.setChapterNumber(-1);
                }
            }
            // 判断是否存在路径
            if (tempArray.size() > 2 && ObjectUtil.isNotEmpty(tempArray.getString(2))) {
                String reptileUrl = baseUrl + "/" + novelNumber + "/" + tempArray.getString(2) + ".html";
                reptileJobDetailResultDTO.setChapterPart(chapterPart);
                reptileJobDetailResultDTO.setReptileUrl(reptileUrl);
                reptileJobDetailArrayList.add(reptileJobDetailResultDTO);
            }
        }
        ReptileJobResultDTO reptileJobResultDTO = new ReptileJobResultDTO();
        return reptileJobResultDTO;
    }

}
