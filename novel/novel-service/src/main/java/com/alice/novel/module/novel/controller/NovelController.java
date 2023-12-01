package com.alice.novel.module.novel.controller;

import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.novel.service.NovelService;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.base.controller.BaseController;
import com.alice.support.common.dto.ResponseInfo;
import com.alice.support.common.mybatis.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/27 12:56
 */
@Slf4j
@RestController
@RequestMapping(value = "/novel")
public class NovelController extends BaseController {

    @Resource
    private NovelService novelService;

    @PostMapping("addNovel")
    public ResponseInfo<String> addNovel(@RequestBody ReptileInfoParamDTO reptileInfoParamDTO) {
        novelService.addNovel(reptileInfoParamDTO);
        return ResponseInfo.success();
    }

    @GetMapping(value = "/queryNovelist")
    public ResponseInfo<TableDataInfo<List<NovelInfo>>> queryNovelist(@Validated NovelInfoQueryDTO novelInfoQueryDTO) {
        startPage();
        return ResponseInfo.success(getDataTable(novelService.queryNovelist(novelInfoQueryDTO)));
    }

    @GetMapping(value = "/queryChapterList")
    public ResponseInfo<TableDataInfo<List<NovelChapter>>> queryChapterList(@Validated ChapterInfoQueryDTO chapterInfoQueryDTO) {
        startPage();
        return ResponseInfo.success(getDataTable(novelService.queryChapterList(chapterInfoQueryDTO)));
    }

}
