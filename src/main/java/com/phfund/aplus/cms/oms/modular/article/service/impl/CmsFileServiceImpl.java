package com.phfund.aplus.cms.oms.modular.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phfund.aplus.cms.oms.common.persistence.dao.CmsFileMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsFileService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
@Service
public class CmsFileServiceImpl implements ICmsFileService {
	
	
	
	@Autowired
	private CmsFileMapper cmsFileMapper;

	@Override
	public int updateServerFileName(CmsFile cmsfile) {
		// TODO Auto-generated method stub
		
		return cmsFileMapper.updateFile(cmsfile);
	}
	
}
