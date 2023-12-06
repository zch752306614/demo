package com.alice.novel.module.novel.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alice.novel.module.common.entity.ReptileDetailInfo;
import com.alice.novel.module.novel.service.HTSService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public List<ReptileDetailInfo> getNovelChapterLink(String baseUrl, String novelNumber) {
        List<ReptileDetailInfo> reptileDetailInfoList = new ArrayList<>(2000);
        String url = baseUrl + "/" + novelNumber + "/dir.json";
        HttpUtil.get(url);
        return null;
    }

    public static void main(String[] args) {
        String result = HttpUtil.get("https://www.hetushu.com/book/38/dir.json");
        JSONObject obj = JSON.parseObject(result);
        System.out.println(obj);
    }
}
