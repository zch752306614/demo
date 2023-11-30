package com.alice.novel.module.novel.service;

import com.alice.novel.module.common.entity.ReptileInfo;
import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;

import java.util.List;
import java.util.Map;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:27
 */
public interface ReptileService {

    /**
     * 提取信息
     *
     * @param url 爬取小说章节的路径
     * @return Map<String, String>
     */
    Map<String, String> getNovelInfo(String url);

    /**
     * 提取信息
     *
     * @param html 完整html文本
     * @return Map<String, String>
     */
    Map<String, String> getData(String html);

    /**
     * 保存整本小说
     *
     * @param reptileInfoParamDTO 路径结构信息
     */
    void saveNovel(ReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 保存爬虫任务
     *
     * @param reptileInfoParamDTO 爬虫信息
     */
    Long saveReptileJob(ReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 添加小说
     *
     * @param reptileInfoParamDTO 路径结构信息
     */
    void addNovel(ReptileInfoParamDTO reptileInfoParamDTO);

    /**
     * 获取任务列表
     *
     * @param reptileInfoParamDTO 任务信息
     * @return List<ReptileInfo>
     */
    List<ReptileInfo> getReptileInfoList(ReptileInfoParamDTO reptileInfoParamDTO);

}
