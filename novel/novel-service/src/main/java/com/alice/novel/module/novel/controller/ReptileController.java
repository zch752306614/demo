package com.alice.novel.module.novel.controller;

import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.novel.module.novel.service.impl.ReptileServiceImpl;
import com.alice.support.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/reptile")
public class ReptileController {

    @Resource
    private ReptileService reptileService;

    @GetMapping(value = "/getUrl")
    public ResponseInfo<String> getUrl() {
        return ResponseInfo.success("novel服务访问成功！");
    }

    @PostMapping("addNovel")
    public ResponseInfo<String> addNovel(@RequestBody ReptileInfoParamDTO reptileInfoParamDTO) {
        reptileService.saveNovel(reptileInfoParamDTO);
        return ResponseInfo.success();
    }

}
