package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;

import com.phfund.aplus.cms.oms.common.persistence.model.CmsTags;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface CmsTagsMapper  {
   /**
    * 
   * @Title: addTag 
   * @Description: 插入单个tag
   * @param @param cmsTags
   * @param @return    设定文件 
   * @return int    返回类型 
   * @throws
    */
	int addTag(CmsTags cmsTags);
	/**
	 * 
	* @Title: selectAll 
	* @Description: 查询所有的tag
	* @param @return    设定文件 
	* @return List<CmsTags>    返回类型 
	* @throws
	 */
	
	List<CmsTags> selectAll(String tagCatalog);
	
	/**
	 * 
	* @Title: selectById 
	* @Description: 根据id查询tag
	* @param @param id
	* @param @return    设定文件 
	* @return CmsTags    返回类型 
	* @throws
	 */
	CmsTags selectById(String tagId);
	
	int deleteById(String tagId);
	
	int updateById(CmsTags cmsTags);
	
	CmsTags selectByName(String tagName);
	
	
	
}