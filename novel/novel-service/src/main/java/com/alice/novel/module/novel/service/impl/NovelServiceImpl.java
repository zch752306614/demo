package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dao.NovelChapterDao;
import com.alice.novel.module.common.dao.NovelInfoDao;
import com.alice.novel.module.common.dto.param.ReptileInfoParamDTO;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.entity.ReptileInfo;
import com.alice.novel.module.novel.service.NovelService;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.QueryWrapperUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 小说信息查询
 * @DateTime 2023/11/28 17:40
 */
@Slf4j
@Service("novelService")
public class NovelServiceImpl implements NovelService {

    @Resource
    private ReptileService reptileService;
    @Resource
    private NovelInfoDao novelInfoDao;
    @Resource
    private NovelChapterDao novelChapterDao;

    /**
     * 添加小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNovel(ReptileInfoParamDTO reptileInfoParamDTO) {
        if (ObjectUtil.isEmpty(reptileInfoParamDTO.getIntervalValue())) {
            reptileInfoParamDTO.setIntervalValue(1);
        }
        ReptileInfo reptileInfo = reptileService.saveReptileInfo(reptileInfoParamDTO);
        NovelInfo novelInfo = reptileService.saveNovelInfo(reptileInfoParamDTO);
        Integer pauseIndex = reptileInfo.getPauseIndex();
        Integer startIndex = reptileInfo.getStartIndex();
        Integer endIndex = reptileInfo.getEndIndex();
        startIndex = ObjectUtil.isEmpty(pauseIndex) ? startIndex : Math.max(startIndex, pauseIndex);
        while (startIndex <= endIndex) {
            ReptileInfo temp = new ReptileInfo();
            BeanUtil.copyProperties(reptileInfo, temp);
            temp.setStartIndex(startIndex);
            temp.setEndIndex(Math.min(startIndex + SysConstants.MAX_BATCH, endIndex));
            reptileService.saveNovelDetails(temp, novelInfo);
            startIndex += SysConstants.MAX_BATCH;
        }
    }

    @Override
    public List<NovelInfo> queryNovelist(NovelInfoQueryDTO novelInfoQueryDTO) {
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(novelInfoQueryDTO, novelInfo);
        QueryWrapper<NovelInfo> queryWrapper = QueryWrapperUtil.initParams(novelInfo);
        return novelInfoDao.selectList(queryWrapper);
    }

    @Override
    public List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO) {
        NovelChapter novelChapter = new NovelChapter();
        BeanUtil.copyProperties(chapterInfoQueryDTO, novelChapter);
        QueryWrapper<NovelChapter> queryWrapper = QueryWrapperUtil.initParams(novelChapter);
        if (SysConstants.IS_YES.equals(chapterInfoQueryDTO.getContentFlag())) {
            return novelChapterDao.selectList(queryWrapper);
        } else {
            return novelChapterDao.queryChapterList(chapterInfoQueryDTO);
        }
    }
}
