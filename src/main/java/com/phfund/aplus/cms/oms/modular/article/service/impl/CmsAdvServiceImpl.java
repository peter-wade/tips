package com.phfund.aplus.cms.oms.modular.article.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phfund.aplus.cms.oms.util.HttpUrlConvert;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.netflix.discovery.converters.Auto;
import com.phfund.aplus.camp.core.common.AdvContents.AdvType;
import com.phfund.aplus.cms.oms.common.config.CmsAdvConfigProperties;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsAdvMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsFileMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsAdv;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.common.persistence.model.advList;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsAdvService;
import com.phfund.aplus.cms.oms.util.PositionContent.Positionupdate;
import com.phfund.aplus.framework.base.utils.IdGenerate;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 广告信息 服务实现类
 * </p>
 *
 * @author linan
 * @since 2018-03-02
 */
@Service
public class CmsAdvServiceImpl implements ICmsAdvService {

	@Autowired
	private CmsAdvMapper cmsAdvMapper;
	
	@Autowired
	private CmsFileMapper cmsFileMapper;
	
	@Autowired
	private CmsAdvConfigProperties cmsAdvConfigProperties;
	
	
	protected final static Logger logger = LoggerFactory.getLogger(CmsAdvServiceImpl.class);
	
	@Override
	public Map<String ,Object> getAdvList(String adv_position) {
		// TODO Auto-generated method stub
		int size=cmsAdvMapper.getSize();
		logger.info("CmsAdvServiceImpl==========size="+size);
		List<advList> list=cmsAdvMapper.getAdvList(adv_position,size);
		
		Map<String ,Object> map=new HashMap<>();
		map.put("respCode", "0000");
		map.put("respMsg", "success");
		Map<String ,Object> maplist=new HashMap<>();
		maplist.put("picList", list);
		map.put("respData", maplist);
		return map;
	}

	@Override
	public int addadv(CmsAdv cmsAdv,CmsFile cmsFile) {
		// TODO Auto-generated method stub
		String id=IdGenerate.getNextId();
		cmsAdv.setAdvId(id);
		String fileId=IdGenerate.getNextId();
		cmsFile.setId(id);
		cmsFile.setFileId(fileId);
		cmsFileMapper.addFile(cmsFile);
		if(AdvType.CAMPADV.equals(cmsAdv.getAdvType())) {
			cmsAdv.setAdvTypeAttr(cmsAdv.getAdvCampCode());;
		}else if(AdvType.FUNDDETAIL.equals(cmsAdv.getAdvType())){
			cmsAdv.setAdvTypeAttr(cmsAdv.getAdvFundCode());
		}else {
			cmsAdv.setAdvTypeAttr(null);
		}
		cmsAdv.setAdvUrl(cmsAdv.getAdvUrl().trim());
		cmsAdvMapper.addAdv(cmsAdv);
		return 0;
	}

	@Override
	public int deleteadv(String cmsAdvId) {
		// TODO Auto-generated method stub
		
		int j=cmsAdvMapper.deleteAddv(cmsAdvId);
		return j;
	}

	@Override
	public List<CmsAdv> selectListAll(Page page,Map paramMap) {
		// TODO Auto-generated method stub
		return cmsAdvMapper.selectList(page,paramMap);
	}

	@Override
	public CmsAdv selectById(String id) {
		// TODO Auto-generated method stub
		
		CmsAdv cmsAdv=cmsAdvMapper.selectById(id);
		logger.info("CmsAdvServiceImpl=========cmsAdv="+JSON.toJSONString(cmsAdv));
		String Serviceurl=cmsAdvConfigProperties.getPicUrl();
		logger.info("CmsAdvServiceImpl=========Serviceurl={}"+Serviceurl);
		String serviceFileName=cmsAdv.getImgUrl();
		logger.info("CmsAdvServiceImpl=========serviceFileName={}"+serviceFileName);
		String imgurl="<img src='"+Serviceurl+"?name="+serviceFileName+"' style='width:auto;height:160px;' />";
		logger.info("CmsAdvServiceImpl=========imgurl={}"+imgurl);
		cmsAdv.setImgUrl(imgurl);
		
		if(AdvType.CAMPADV.equals(cmsAdv.getAdvType())) {
			cmsAdv.setAdvCampCode(cmsAdv.getAdvTypeAttr());
		}else if(AdvType.FUNDDETAIL.equals(cmsAdv.getAdvType())){
			cmsAdv.setAdvFundCode(cmsAdv.getAdvTypeAttr());
		}else {
			cmsAdv.setAdvTypeAttr(null);
		}
		return cmsAdv;
	}

	@Override
	public int UpdateById(CmsAdv cmsAdv) {
		// TODO Auto-generated method stub
		if(AdvType.CAMPADV.equals(cmsAdv.getAdvType())) {
			cmsAdv.setAdvTypeAttr(cmsAdv.getAdvCampCode());
		}else if(AdvType.FUNDDETAIL.equals(cmsAdv.getAdvType())){
			cmsAdv.setAdvTypeAttr(cmsAdv.getAdvFundCode());
		}else {
			cmsAdv.setAdvTypeAttr(null);
		}
		cmsAdv.setAdvUrl(cmsAdv.getAdvUrl().trim());
		int k=cmsAdvMapper.updateOne(cmsAdv);
		if(null!=cmsAdv.getMaxSize() && cmsAdv.getMaxSize()>=0){
		int y=cmsAdvMapper.updateMaxSize(cmsAdv.getMaxSize());
		}
		return k;
	}
	
}
