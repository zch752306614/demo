package com.alice.novel.module.novel.controller;

import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.novel.service.NovelService;
import com.alice.support.common.base.controller.BaseController;
import com.alice.support.common.dto.ResponseInfo;
import com.alice.support.common.mybatis.page.TableDataInfo;
import io.swagger.annotations.*;
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
@Api(value = "小说管理（novel）", tags = "小说管理（novel）")
public class NovelController extends BaseController {

    @Resource
    private NovelService novelService;

    @PostMapping("/addNovelByBQG")
    @ApiOperation(value = "添加小说", httpMethod = "POST")
    public ResponseInfo<String> addNovelByBQG(@RequestBody @Validated BQGReptileInfoParamDTO reptileInfoParamDTO) {
        novelService.addNovelByBQG(reptileInfoParamDTO);
        return ResponseInfo.success();
    }

    @PostMapping("/addNovelByHTS")
    @ApiOperation(value = "添加小说", httpMethod = "POST")
    public ResponseInfo<String> addNovelByHTS(@RequestBody @Validated HTSReptileInfoParamDTO reptileInfoParamDTO) {
        novelService.addNovelByHTS(reptileInfoParamDTO);
        return ResponseInfo.success();
    }

    @GetMapping(value = "/queryNovelist")
    @ApiOperation(value = "获取小说列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前记录起始索引", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示记录数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderByColumn", value = "排序列", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isAsc", value = "排序的方向", dataType = "String", paramType = "query")
    })
    public ResponseInfo<TableDataInfo<List<NovelInfo>>> queryNovelist(@Validated NovelInfoQueryDTO novelInfoQueryDTO) {
        startPage();
        return ResponseInfo.success(getDataTable(novelService.queryNovelist(novelInfoQueryDTO)));
    }

    @GetMapping(value = "/queryChapterList")
    @ApiOperation(value = "获取小说章节", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前记录起始索引", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示记录数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderByColumn", value = "排序列", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isAsc", value = "排序的方向", dataType = "String", paramType = "query")
    })
    public ResponseInfo<TableDataInfo<List<NovelChapter>>> queryChapterList(@Validated ChapterInfoQueryDTO chapterInfoQueryDTO) {
        startPage();
        return ResponseInfo.success(getDataTable(novelService.queryChapterList(chapterInfoQueryDTO)));
    }

    @GetMapping(value = "/queryChapterListById")
    @ApiOperation(value = "根据ID获取章节", httpMethod = "GET")
    public ResponseInfo<List<NovelChapter>> queryChapterListById(@RequestParam @ApiParam(value = "小说章节ID", required = true) List<Long> idList) {
        return ResponseInfo.success(novelService.queryChapterListById(idList));
    }
}
