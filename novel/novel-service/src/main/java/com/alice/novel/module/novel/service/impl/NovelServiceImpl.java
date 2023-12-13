package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.entity.NovelChapter;
import com.alice.novel.module.common.entity.NovelInfo;
import com.alice.novel.module.common.mapper.NovelChapterMapper;
import com.alice.novel.module.common.mapper.NovelInfoMapper;
import com.alice.novel.module.common.service.NovelChapterService;
import com.alice.novel.module.common.service.NovelInfoService;
import com.alice.novel.module.novel.service.NovelService;
import com.alice.novel.module.novel.service.ReptileService;
import com.alice.novel.module.novel.service.reptile.impl.BQGReptileServiceImpl;
import com.alice.novel.module.novel.service.reptile.impl.HTSReptileServiceImpl;
import com.alice.support.common.consts.SysConstants;
import com.alice.support.common.util.DateUtil;
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
@Service
public class NovelServiceImpl implements NovelService {

    @Resource
    private ReptileService reptileService;
    @Resource
    private NovelInfoService novelInfoService;
    @Resource
    private NovelChapterService novelChapterService;
    @Resource
    private NovelInfoMapper novelInfoMapper;
    @Resource
    private NovelChapterMapper novelChapterMapper;

    /**
     * 添加小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNovelByBQG(BQGReptileInfoParamDTO reptileInfoParamDTO) {
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = reptileService.saveReptileJob(reptileInfoParamDTO, BQGReptileServiceImpl.class);
        List<List<ReptileJobDetailResultDTO>> splitList = CollUtil.split(reptileJobDetailResultDTOList, SysConstants.MAX_BATCH);
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
        novelInfo.setLastUpdateTime(DateUtil.defaultFormatDateToString());
        novelInfoService.save(novelInfo);
        for (List<ReptileJobDetailResultDTO> list : splitList) {
            reptileService.saveChapterInfo(novelInfo, list, BQGReptileServiceImpl.class);
        }
    }

    /**
     * 添加和图书小说
     *
     * @param reptileInfoParamDTO 要添加的小说信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNovelByHTS(HTSReptileInfoParamDTO reptileInfoParamDTO) {
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = reptileService.saveReptileJob(reptileInfoParamDTO, HTSReptileServiceImpl.class);
        List<List<ReptileJobDetailResultDTO>> splitList = CollUtil.split(reptileJobDetailResultDTOList, SysConstants.MAX_BATCH);
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
        novelInfo.setLastUpdateTime(DateUtil.defaultFormatDateToString());
        novelInfoService.save(novelInfo);
        for (List<ReptileJobDetailResultDTO> list : splitList) {
            reptileService.saveChapterInfo(novelInfo, list, HTSReptileServiceImpl.class);
        }
    }

    @Override
    public List<NovelInfo> queryNovelist(NovelInfoQueryDTO novelInfoQueryDTO) {
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(novelInfoQueryDTO, novelInfo);
        QueryWrapper<NovelInfo> queryWrapper = QueryWrapperUtil.initParams(novelInfo);
        return novelInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO) {
        NovelChapter novelChapter = new NovelChapter();
        BeanUtil.copyProperties(chapterInfoQueryDTO, novelChapter);
        QueryWrapper<NovelChapter> queryWrapper = QueryWrapperUtil.initParams(novelChapter);
        queryWrapper.orderByAsc("CHAPTER_NUMBER");
        if (SysConstants.IS_YES.equals(chapterInfoQueryDTO.getContentFlag())) {
            return novelChapterMapper.selectList(queryWrapper);
        } else {
            return novelChapterMapper.queryChapterList(chapterInfoQueryDTO);
        }
    }

    @Override
    public List<NovelChapter> queryChapterListById(List<Long> idList) {
        return novelChapterService.query().in("ID", idList).list();
    }
}
