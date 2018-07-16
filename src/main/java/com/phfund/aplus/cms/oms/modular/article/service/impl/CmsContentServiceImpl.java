package com.phfund.aplus.cms.oms.modular.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phfund.aplus.cms.oms.common.persistence.dao.CmsContentMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsContent;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsContentService;
import com.phfund.aplus.framework.base.utils.IdGenerate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
@Service
public class CmsContentServiceImpl implements ICmsContentService {

	
	@Autowired
	private CmsContentMapper contentMapper;
	@Override
	public List<CmsContent> selectAll() {
		// TODO Auto-generated method stub
		
		
		
		return contentMapper.selectAll();
	}
	@Override
	public int insert(CmsContent cmsContent) {
		// TODO Auto-generated method stub
		
		cmsContent.setId(IdGenerate.getNextId());
		int i=contentMapper.addContent(cmsContent);
		
		return i;
	}
	
}
