package com.alice.novel.module.novel.controller;

import com.alice.support.common.dto.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 爬取小说
 * @DateTime 2023/11/27 17:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/reptile")
public class ReptileController {

    @GetMapping(value = "/getUrl")
    public ResponseInfo<String> getUrl() {
        return ResponseInfo.success("novel服务访问成功！");
    }

}
