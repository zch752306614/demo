package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.NovelInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description 笔趣阁爬虫（https://m.ytryx.com/b3909/）
 * @DateTime 2023/12/5 14:23
 */
public interface BQGService {

    /**
     * 提取信息
     *
     * @param url String 爬取小说章节的路径
     * @return Map<String, String>
     */
    Map<String, String> getNovelInfo(String url);

    /**
     * 获取小说每个章节的链接
     *
     * @param baseUrl 链接
     * @param novelNumber 小说编号
     * @return List<ReptileDetailInfo> 小说章节链接信息
     */
    List<ReptileJobDetailResultDTO> getNovelChapterLink(String baseUrl, String novelNumber);

}
