package com.alice.novel.module.novel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alice.novel.module.common.dto.param.BQGReptileInfoParamDTO;
import com.alice.novel.module.common.dto.param.HTSReptileInfoParamDTO;
import com.alice.novel.module.common.dto.query.ChapterInfoQueryDTO;
import com.alice.novel.module.common.dto.query.NovelInfoQueryDTO;
import com.alice.novel.module.common.dto.result.ReptileJobDetailResultDTO;
import com.alice.novel.module.common.dto.result.ReptileJobResultDTO;
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
import com.alice.support.common.util.MyDateUtil;
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
        ReptileJobResultDTO reptileJobResultDTO = reptileService.saveReptileJob(reptileInfoParamDTO, BQGReptileServiceImpl.class);
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = reptileJobResultDTO.getReptileJobDetailResultDTOList();
        List<List<ReptileJobDetailResultDTO>> splitList = CollUtil.split(reptileJobDetailResultDTOList, SysConstants.MAX_BATCH);
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getNovelName())) {
            novelInfo.setNovelName(reptileJobResultDTO.getNovelName());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getNovelAuthor())) {
            novelInfo.setNovelAuthor(reptileJobResultDTO.getNovelAuthor());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getCompletedFlag())) {
            novelInfo.setCompletedFlag(reptileJobResultDTO.getCompletedFlag());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getNovelType())) {
            novelInfo.setNovelType(reptileJobResultDTO.getNovelType());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getNovelIntroduction())) {
            novelInfo.setNovelIntroduction(reptileJobResultDTO.getNovelIntroduction());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getLastUpdateTime())) {
            novelInfo.setLastUpdateTime(reptileJobResultDTO.getLastUpdateTime());
        }
        if (ObjectUtil.isNotEmpty(reptileJobResultDTO.getNovelCover())) {
            novelInfo.setNovelCover(reptileJobResultDTO.getNovelCover());
        }
        novelInfo.setCreateTime(DateUtil.dateSecond().toTimestamp());
        novelInfo.setUpdateTime(DateUtil.dateSecond().toTimestamp());

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
        ReptileJobResultDTO reptileJobResultDTO = reptileService.saveReptileJob(reptileInfoParamDTO, HTSReptileServiceImpl.class);
        List<ReptileJobDetailResultDTO> reptileJobDetailResultDTOList = reptileJobResultDTO.getReptileJobDetailResultDTOList();
        List<List<ReptileJobDetailResultDTO>> splitList = CollUtil.split(reptileJobDetailResultDTOList, SysConstants.MAX_BATCH);
        NovelInfo novelInfo = new NovelInfo();
        BeanUtil.copyProperties(reptileInfoParamDTO, novelInfo);
        novelInfo.setLastUpdateTime(MyDateUtil.defaultFormatDateToString());
        novelInfoService.save(novelInfo);
        for (List<ReptileJobDetailResultDTO> list : splitList) {
            reptileService.saveChapterInfo(novelInfo, list, HTSReptileServiceImpl.class);
        }
    }

    @Override
    public List<NovelInfo> queryNovelist(NovelInfoQueryDTO novelInfoQueryDTO) {
        String novelName = novelInfoQueryDTO.getNovelName();
        String novelAuthor = novelInfoQueryDTO.getNovelAuthor();
        String searchName = novelInfoQueryDTO.getSearchName();
        List<String> idList = novelInfoQueryDTO.getIdList();
        QueryWrapper<NovelInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(novelAuthor), "NOVEL_AUTHOR", novelAuthor);
        queryWrapper.like(ObjectUtil.isNotEmpty(novelName), "NOVEL_NAME", novelName);
        queryWrapper.and(ObjectUtil.isNotEmpty(searchName), wrapper -> wrapper.like("NOVEL_AUTHOR", searchName).or().like("NOVEL_NAME", searchName));
        queryWrapper.in(ObjectUtil.isNotEmpty(idList), "ID", idList);
        return novelInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<NovelChapter> queryChapterList(ChapterInfoQueryDTO chapterInfoQueryDTO) {
        if (SysConstants.IS_YES.equals(chapterInfoQueryDTO.getContentFlag())) {
            NovelChapter novelChapter = new NovelChapter();
            BeanUtil.copyProperties(chapterInfoQueryDTO, novelChapter);
            QueryWrapper<NovelChapter> queryWrapper = QueryWrapperUtil.initParams(novelChapter);
            queryWrapper.orderByAsc("CHAPTER_NUMBER");
            return novelChapterMapper.selectList(queryWrapper);
        } else {
            return novelChapterMapper.queryChapterList(chapterInfoQueryDTO);
        }
    }

    @Override
    public List<NovelChapter> queryChapterListById(List<Long> idList) {
        return novelChapterService.query().in("ID", idList).list();
    }

    @Override
    public List<NovelChapter> queryChapterListByConditions(ChapterInfoQueryDTO chapterInfoQueryDTO) {
        if (SysConstants.IS_YES.equals(chapterInfoQueryDTO.getContentFlag())) {
            NovelChapter novelChapter = new NovelChapter();
            BeanUtil.copyProperties(chapterInfoQueryDTO, novelChapter);
            QueryWrapper<NovelChapter> queryWrapper = QueryWrapperUtil.initParams(novelChapter);
            queryWrapper.in(ObjectUtil.isNotEmpty(chapterInfoQueryDTO.getIdList()), "ID", chapterInfoQueryDTO.getIdList())
                    .in(ObjectUtil.isNotEmpty(chapterInfoQueryDTO.getChapterNumberList()), "CHAPTER_NUMBER", chapterInfoQueryDTO.getChapterNumberList())
                    .orderByAsc("CHAPTER_NUMBER");
            return novelChapterMapper.selectList(queryWrapper);
        } else {
            return novelChapterMapper.queryChapterList(chapterInfoQueryDTO);
        }
    }
}
