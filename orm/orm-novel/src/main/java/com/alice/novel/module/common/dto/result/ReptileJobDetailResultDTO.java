package com.alice.novel.module.common.dto.result;

import lombok.Data;

/**
 * @Description 爬虫任务明细信息
 * @DateTime 2023/12/8 14:04
 */
@Data
public class ReptileJobDetailResultDTO {

    /**
     * 爬虫明细表主键
     */
    private Long ReptileJobDetailId;

    /**
     * 爬虫任务明细地址
     */
    private String reptileUrl;

    /**
     * 小说章节
     */
    private Integer chapterNumber;

    /**
     * 小说章节名
     */
    private String chapterName;

    /**
     * 小说章节卷名
     */
    private String chapterPart;

}
