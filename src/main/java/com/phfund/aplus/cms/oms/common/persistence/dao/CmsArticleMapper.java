package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.ActionInfo;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticle;

/**
 * <p>
  * 文章信息 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface CmsArticleMapper {
	
	int addArticle(CmsArticle cmsArticle);
	
	List<CmsArticle> selectList(Page page , Map paramMap);
	
	int deleteById(String id);
	
	int updateById(CmsArticle cmsArticle);
	
	CmsArticle selectById(String id);
	
	int pub(String articleId);
	
	int offline(String articleId);

	List<CmsArticle> queryRecommendCrticleList(Map<String, Object> paramMap);

	CmsArticle queryCrticleTagId(Map<String, Object> paramMap);
	
	int getOnTopSize();
	
	ActionInfo getDeviceId(String phoneNo);
	
	
}