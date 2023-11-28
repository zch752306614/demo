package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.novel.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.novel.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.novel.service.NovelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/28 17:40
 */
@Service("novelService")
public class NovelServiceImpl implements NovelService {

    @Resource
    private NovelInfoDao novelInfoDao;
    @Resource
    private NovelChapterDao novelChapterDao;

    @Override
    public List<NovelInfo> queryNovelist(NovelInfoQueryDTO novelInfoQueryDTO) {
        return novelInfoDao.selectByMap(BeanUtil.beanToMap(novelInfoQueryDTO));
    }

    @Override
    public List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO) {
        return novelChapterDao.selectByMap(BeanUtil.beanToMap(chapterInfoQueryDTO));
    }
}
