package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;

import com.phfund.aplus.cms.oms.common.persistence.model.CmsContent;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface CmsContentMapper {
     /**
      * 
     * @Title: addContent 
     * @Description: 添加内容
     * @param @param cmsContent
     * @param @return    设定文件 
     * @return int    返回类型 
     * @throws
      */
	int addContent(CmsContent cmsContent);
	/**
	 * 
	* @Title: selectAll 
	* @Description: 查询所有的内容
	* @param @return    设定文件 
	* @return List<CmsContent>    返回类型 
	* @throws
	 */
	List<CmsContent> selectAll();
	/**
	 * 
	* @Title: selectById 
	* @Description: 根据Id查询具体的内容
	* @param @param id
	* @param @return    设定文件 
	* @return CmsContent    返回类型 
	* @throws
	 */
	CmsContent selectById(String id);
	
	int deleteById(String id);
	
	int updateById(CmsContent cmsContent);
	
	CmsContent selectByArticleId(String articleId);
	
	
}