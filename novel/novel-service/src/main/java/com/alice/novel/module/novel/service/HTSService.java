package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileDetailInfo;
import com.alice.novel.module.common.entity.ReptileJobDetail;

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
    List<ReptileJobDetailResultDTO> getNovelChapterLink(String baseUrl, String novelNumber);

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 任务信息
     */
    List<ReptileJobDetailResultDTO> saveReptileJob(HTSReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 保存小说信息
     *
     * @param reptileInfoParamDTO 爬虫信息
     */
    void saveNovelInfo(HTSReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 保存章节信息
     *
     * @param novelInfo 小说信息
     * @param reptileJobDetailResultDTOList 爬虫任务明细信息
     */
    void saveChapterInfo(NovelInfo novelInfo, List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList);

}
