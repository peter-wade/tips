package com.phfund.aplus.cms.oms.modular.fund.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.cms.oms.modular.fund.mapper.ContentInfoMapper;
import com.phfund.aplus.cms.oms.modular.fund.service.FundContentInfoService;
import com.phfund.aplus.cms.oms.modular.fund.vo.FundCommentInfoVo;
import com.phfund.aplus.cms.oms.util.DecodeUtil;
import com.phfund.aplus.oms.core.mutidatasource.annotion.DataSource;

@Service
public class FundContentInfoServiceImpl implements FundContentInfoService{
	private static final Logger log = LoggerFactory.getLogger(FundContentInfoServiceImpl.class);
	@Autowired
	private ContentInfoMapper contentInfoMapper;
	@Override
	@DataSource(name="aj_tsp")
	public List<FundCommentInfoVo> queryFundContent(Page<FundCommentInfoVo> page,FundCommentInfoVo fundCommentInfo) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("portfolioCode", fundCommentInfo.getPortfolioCode());// 组合代码
		paraMap.put("portfolioName", fundCommentInfo.getPortfolioName());// 组合名称
		paraMap.put("commentsCode", fundCommentInfo.getCommentsCode());// 评论代码
		paraMap.put("custShortName", fundCommentInfo.getCustShortName());// 客户昵称
		paraMap.put("startTime", fundCommentInfo.getStartTime());// 评论开始时间
		paraMap.put("endTime", fundCommentInfo.getEndTime());// 评论结束时间
		paraMap.put("reviewStatus", fundCommentInfo.getReviewStatus());// 审核状态
		log.info("-----------------查询参数-----------" + paraMap);
		List<FundCommentInfoVo>  commentInfoList = new ArrayList<FundCommentInfoVo>();
		List<FundCommentInfoVo> fundCommentInfoList = contentInfoMapper.queryFundContentInfo(page,paraMap);
		log.info("-----------------查询结果-----------" + fundCommentInfoList);
		// 对评论内容进行解密
		for(FundCommentInfoVo fundComment : fundCommentInfoList) {
			String content = fundComment.getContent();
			content = DecodeUtil.decodeBase64(content);
			fundComment.setContent(content);
			commentInfoList.add(fundComment);
		}
		return commentInfoList;
	}
	// 审核
	@Override
	@DataSource(name="aj_tsp")
	@Transactional
	public void updateContent(String[] ids,String reviewStatus,String user) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for(String id :ids) {
			String commentsCode = id;
			paraMap.put("commentsCode", commentsCode);// 评论代码
			paraMap.put("reviewStatus", reviewStatus);// 审核状态
			paraMap.put("user", user);//更新人
			contentInfoMapper.updateContentStatus(paraMap);
		}
		

	}


}
