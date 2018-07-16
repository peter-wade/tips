package com.phfund.aplus.cms.oms.modular.fund.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.modular.fund.vo.FundCommentInfoVo;

/**
 * @author x_zhuxiaolong
 *
 */
public interface FundContentInfoService {
	// 查询客户对组合基金的评论
	public List<FundCommentInfoVo> queryFundContent(Page<FundCommentInfoVo> page,FundCommentInfoVo fundCommentInfo);
	// 审核
	void updateContent(String[] ids,String reviewStatus,String user);


}
