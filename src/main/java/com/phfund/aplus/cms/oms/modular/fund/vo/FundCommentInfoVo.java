package com.phfund.aplus.cms.oms.modular.fund.vo;

import com.phfund.aplus.framework.base.beans.BaseRespData;

/**
 * @author x_zhuxiaolong
 *
 */
public class FundCommentInfoVo extends BaseRespData{

	private static final long serialVersionUID = 1L;
	private String portfolioCode;// 组合代码
	private String portfolioName;// 组合名称
	private String commentsCode;// 评论代码
	private String custShortName;// 客户昵称
	private String commentsTime;// 评论时间
	private String content;// 评论内容
	private String reviewStatus;//审核状态
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	public String getPortfolioCode() {
		return portfolioCode;
	}
	public void setPortfolioCode(String portfolioCode) {
		this.portfolioCode = portfolioCode;
	}
	public String getPortfolioName() {
		return portfolioName;
	}
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
	public String getCommentsCode() {
		return commentsCode;
	}
	public void setCommentsCode(String commentsCode) {
		this.commentsCode = commentsCode;
	}
	public String getCustShortName() {
		return custShortName;
	}
	public void setCustShortName(String custShortName) {
		this.custShortName = custShortName;
	}
	public String getCommentsTime() {
		return commentsTime;
	}
	public void setCommentsTime(String commentsTime) {
		this.commentsTime = commentsTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
