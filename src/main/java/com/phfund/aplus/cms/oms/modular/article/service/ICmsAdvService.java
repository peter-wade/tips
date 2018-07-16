package com.phfund.aplus.cms.oms.modular.article.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsAdv;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告信息 服务类
 * </p>
 *
 * @author linan
 * @since 2018-03-02
 */
public interface ICmsAdvService {
	
	Map<String,Object> getAdvList(String adv_position);
	
	int addadv(CmsAdv cmsAdv,CmsFile cmsFile);
	
	int deleteadv(String cmsAdvId);
	
	List<CmsAdv> selectListAll(Page page,Map paramMap);
	
	CmsAdv selectById(String id);
	
	int UpdateById(CmsAdv cmsAdv);
	
}
