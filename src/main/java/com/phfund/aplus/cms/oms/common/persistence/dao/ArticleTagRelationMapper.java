package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;

import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticleTagRelation;

public interface ArticleTagRelationMapper {
	
	int addOneRelation(CmsArticleTagRelation cmsArticleTagRelation);
	
	List<CmsArticleTagRelation> selectByArticle(String articleId);
	
	int deleteRelation(String articleId);
	
	

}
