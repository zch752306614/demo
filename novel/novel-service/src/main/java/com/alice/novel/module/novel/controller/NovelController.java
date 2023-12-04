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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("addNovel")
    @ApiOperation(value = "添加小说", httpMethod = "GET")
    public ResponseInfo<String> addNovel(@RequestBody ReptileInfoParamDTO reptileInfoParamDTO) {
        novelService.addNovel(reptileInfoParamDTO);
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

}
