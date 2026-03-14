package com.suoyike.weblog.web.service.impl;

import com.suoyike.weblog.common.domain.dos.WikiCatalogDO;
import com.suoyike.weblog.common.domain.dos.WikiDO;
import com.suoyike.weblog.common.domain.mapper.WikiCatalogMapper;
import com.suoyike.weblog.common.domain.mapper.WikiMapper;
import com.suoyike.weblog.common.utils.Response;
import com.suoyike.weblog.web.model.vo.wiki.FindWikiListRspVO;
import com.suoyike.weblog.web.service.WikiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WikiServiceImpl implements WikiService {

    @Autowired
    private WikiMapper wikiMapper;
    @Autowired
    private WikiCatalogMapper wikiCatalogMapper;

    /**
     * 获取知识库
     *
     * @return
     */
    @Override
    public Response findWikiList() {
        // 查询已发布的知识库
        List<WikiDO> wikiDOS = wikiMapper.selectPublished();

        // DO 转 VO
        List<FindWikiListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(wikiDOS)) {
            vos = wikiDOS.stream()
                    .map(wikiDO -> FindWikiListRspVO.builder()
                            .id(wikiDO.getId())
                            .title(wikiDO.getTitle())
                            .cover(wikiDO.getCover())
                            .summary(wikiDO.getSummary())
                            .isTop(wikiDO.getWeight() > 0)
                            .build())
                    .collect(Collectors.toList());

            // 设置每个知识库的第一篇文章 ID，方便前端跳转
            vos.forEach(vo -> {
                Long wikiId = vo.getId();
                WikiCatalogDO wikiCatalogDO = wikiCatalogMapper.selectFirstArticleId(wikiId);
                vo.setFirstArticleId(Objects.nonNull(wikiCatalogDO) ? wikiCatalogDO.getArticleId() : null);
            });
        }

        return Response.success(vos);
    }

}