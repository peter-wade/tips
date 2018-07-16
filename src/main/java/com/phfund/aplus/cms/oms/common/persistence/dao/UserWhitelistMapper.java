package com.phfund.aplus.cms.oms.common.persistence.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.common.persistence.model.ImportArticleInfo;
import com.phfund.aplus.cms.oms.common.persistence.model.UserWhitelistVo;

public interface UserWhitelistMapper {
	
	// 活动用户参与名单插入
	void addUserWhiteList(Map<String, Object> params);
	
	// 查询CRM导入数据
	List<UserWhitelistVo> selectCampattendInfo(Map<String, Object> params);
	// 查询资讯信息
	List<ImportArticleInfo> queryArticleInfo(Page<ImportArticleInfo> page,Map<String, Object> map);
	
	List<String> queryArticleWhitelist(String articleId);

	void deleteUserWhite(String articleId, String fileId);

	void updateUserWhite(Map<String, Object> paraMap);
	
	int queryUserCount(Map<String, Object> map);

}
