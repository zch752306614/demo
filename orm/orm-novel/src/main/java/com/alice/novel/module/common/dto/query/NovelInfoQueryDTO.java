package com.alice.novel.module.common.dto.query;

import lombok.Data;

/**
 * @Description 查询小说入参
 * @DateTime 2023/11/28 17:42
 */
@Data
public class NovelInfoQueryDTO {

    private String novelName;

    private String novelAuthor;

}
