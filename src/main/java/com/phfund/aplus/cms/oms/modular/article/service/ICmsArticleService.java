package com.phfund.aplus.cms.oms.modular.article.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticle;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsReceive;
import com.phfund.aplus.framework.base.beans.BaseResp;

/**
 * <p>
 * 文章信息 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public interface ICmsArticleService {
    /**
     * 
    * @Title: insert 
    * @Description: 插入一个文章
    * @param @param cmsReceive
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
	int insert(CmsFile cmsFile,CmsReceive cmsReceive);
	/**
	 * 
	* @Title: selectList 
	* @Description: 查出所有的文章
	* @param @return    设定文件 
	* @return List<CmsReceive>    返回类型 
	* @throws
	 */
	List<CmsReceive> selectList(Page page,Map paramMap);
	
	/**
	 * 
	* @Title: deleteById 
	* @Description: 根据id删除数据
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	int deleteById(String id);
	
	
	int updateById(CmsReceive cmsReceive);
	
	CmsReceive selectById(String id);
	
	
	int pub(String articleId);
	
	int offline(String articleId);
	
	
	List<CmsArticle> queryRecommendCrticleList(Map<String, Object> paramMap);
	
	
	int getOnTopSize();
	
	BaseResp sendMsg(String articleId);
	
}
