package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface CmsFileMapper extends BaseMapper<CmsFile> {
	
	int addFile(CmsFile cmsFile);
	
	int updateFile(CmsFile cmsFile);
	

	// 查询列表
	List<CmsFile> queryFilelist(Page<CmsFile> page,Map<String,Object> paraMap);
	// 删除该文件
	int deleteCmsFile(Map<String,Object> paraMap);

	
	

}