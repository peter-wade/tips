package com.phfund.aplus.cms.oms.modular.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phfund.aplus.cms.oms.common.persistence.dao.CmsTagsMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsTags;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsTagsService;

/**
 * 
 * @author x_linan
 *
 */
@Service
public class CmsTagsServiceImpl implements ICmsTagsService {

	
	@Autowired
	private CmsTagsMapper cmsTagsMapper;
	@Override
	public List<CmsTags> getTagList(String tagCatalog) {
		// TODO Auto-generated method stub
		return cmsTagsMapper.selectAll(tagCatalog);
	}
	
}
