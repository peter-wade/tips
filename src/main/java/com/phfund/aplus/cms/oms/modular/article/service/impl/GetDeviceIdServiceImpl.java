package com.phfund.aplus.cms.oms.modular.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phfund.aplus.cms.oms.common.persistence.dao.CmsArticleMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.ActionInfo;
import com.phfund.aplus.cms.oms.modular.article.service.GetDeviceIdService;
import com.phfund.aplus.oms.core.mutidatasource.annotion.DataSource;
@Service
public class GetDeviceIdServiceImpl implements GetDeviceIdService{
	
	@Autowired
	private CmsArticleMapper cmsArticleMapper;

	@Override
	@DataSource(name="aj_ots")
	public ActionInfo getDeviceId(String phoneNo) {
		// TODO Auto-generated method stub
		return cmsArticleMapper.getDeviceId(phoneNo);
	}

}
