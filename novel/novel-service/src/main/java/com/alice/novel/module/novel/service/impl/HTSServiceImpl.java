package com.alice.novel.module.novel.service.impl;

import com.alice.novel.module.novel.service.HTSService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 和图书爬虫
 * @DateTime 2023/12/5 14:28
 */
@Service("hTSService")
public class HTSServiceImpl implements HTSService {

    @Override
    public Map<String, String> getNovelInfo(String url) {
        return null;
    }

    @Override
    public Map<String, String> getData(String html) {
        return null;
    }
}
