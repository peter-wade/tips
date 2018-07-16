package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsAdv;
import com.phfund.aplus.cms.oms.common.persistence.model.advList;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * <p>
  * 广告信息 Mapper 接口
 * </p>
 *
 * @author linan
 * @since 2018-03-02
 */
public interface CmsAdvMapper {
	
	List<advList> getAdvList(@Param("adv_position")String adv_position, @Param("size")int size);
	
	int addAdv(CmsAdv cmsAdv);
	
	int deleteAddv(String cmsAdvId);
	
	List<CmsAdv> selectList(Page page , Map paramMap);
	
	CmsAdv selectById(String id);
	
	int updateOne(CmsAdv cmsAdv);
	
	int updateMaxSize(int maxSize);
	
	int getSize();

}