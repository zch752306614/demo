package com.alice.novel.module.common.dto.result;

import lombok.Data;

import java.util.List;

@Data
public class ReptileJobResultDTO {

    /**
     * 小说名称
     */
    private String novelName;

    /**
     * 作者
     */
    private String novelAuthor;

    /**
     * 是否完结
     */
    private String completedFlag;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    /**
     * 小说类型
     */
    private String novelType;

    /**
     * 小说简介
     */
    private String novelIntro;

    /**
     * 小说封面地址
     */
    private String novelCover;

    /**
     * 小说爬虫明细信息
     */
    List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList;

}
