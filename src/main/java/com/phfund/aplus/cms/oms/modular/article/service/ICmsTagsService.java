package com.phfund.aplus.cms.oms.modular.article.service;

import java.util.List;

import com.phfund.aplus.cms.oms.common.persistence.model.CmsTags;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface ICmsTagsService {
	
	
	List<CmsTags> getTagList(String tagCatalog);
	
	
}
