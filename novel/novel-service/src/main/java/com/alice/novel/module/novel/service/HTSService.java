package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.entity.ReptileDetailInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description 和图书爬虫（https://www.hetushu.com/index.php）
 * @DateTime 2023/12/5 14:24
 */
public interface HTSService {

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    Map<String, String> getNovelInfo(String url);

    /**
     * 根据html文本提取小说信息
     *
     * @param html 完整html文本
     * @return Map<String, String>
     */
    Map<String, String> getData(String html);
    
    /**
     * 获取小说每个章节的链接
     *
     * @param baseUrl 链接
     * @param novelNumber 小说编号
     * @return List<ReptileDetailInfo> 小说章节链接信息
     */
    List<ReptileDetailInfo> getNovelChapterLink(String baseUrl, String novelNumber);

}
