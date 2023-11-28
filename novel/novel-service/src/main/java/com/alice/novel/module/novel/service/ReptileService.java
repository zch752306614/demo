package com.alice.novel.module.novel.service;

import com.alice.novel.module.novel.dto.ReptileInfo;

import java.util.Map;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
public interface ReptileService {

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    Map<String, String> getNovelInfo(String url);

    /**
     * 提取信息
     *
     * @param html String 完整html文本
     * @return Map<String, String>
     */
    Map<String, String> getData(String html);

    /**
     * 保存整本小说
     *
     * @param reptileInfo ReptileInfo 路径结构信息
     */
    void saveNovel(ReptileInfo reptileInfo);

}
