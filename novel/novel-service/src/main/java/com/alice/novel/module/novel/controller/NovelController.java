package com.alice.novel.module.novel.controller;

import com.alice.support.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/27 12:56
 */
@Slf4j
@RestController
@RequestMapping(value = "/novel")
public class NovelController {

    @GetMapping(value = "/getNovelist")
    public ResponseInfo<String> getNovelist() {
        return ResponseInfo.success("novel服务访问成功！");
    }

    @GetMapping(value = "/getChapterList")
    public ResponseInfo<String> getChapterList() {
        return ResponseInfo.success("novel服务访问成功！");
    }

}
