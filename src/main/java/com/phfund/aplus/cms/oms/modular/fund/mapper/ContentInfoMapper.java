package com.phfund.aplus.cms.oms.modular.fund.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.modular.fund.vo.FundCommentInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @author x_zhuxiaolong
 *
 */
public interface ContentInfoMapper {
	// 查询组合评论数据
	List<FundCommentInfoVo> queryFundContentInfo(Page<FundCommentInfoVo> page , Map<String,Object> paraMap);
	// 更新审核状态
	int updateContentStatus(Map<String,Object> paraMap);
}
