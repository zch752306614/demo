package com.alice.novel.module.novel.controller;

import com.alice.novel.module.novel.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.novel.module.novel.service.impl.ReptileServiceImpl;
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

    /**
     * 笔趣阁-凡人修仙传
     */
    public static void main(String[] args) {
        ReptileInfoParamDTO reptileInfoParamDTO = ReptileInfoParamDTO.builder()
                .novelName("凡人修仙传")
                .baseUrl("https://m.ytryx.com/b3909/")
                .urlSuffix(".html")
                .startIndex(4342526)
//                .endIndex(4347799)
                .endIndex(4342550)
                .interval(1)
                .partFlag(true)
                .partInterval(1)
                .partStartIndex(1)
                .partSuffix("_")
                .titleSeparator("1/")
                .novelAuthor("忘语")
                .build();
        ReptileService reptileService = new ReptileServiceImpl();
        reptileService.saveNovel(reptileInfoParamDTO);
    }

}