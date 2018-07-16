package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class CmsReceive implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String articleId;
	/**
	 * 文件类型（字典：1公司公告）
	 */
	private String articleType;
	/**
	 * 标题
	 */
	private String articleTitle;
	/**
	 * 标题简称
	 */
	private String articleTitleShortname;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 发布时间
	 */
	private String pubTime;
	/**
	 * 审核通过时间
	 */
	private String auditTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 概要
	 */
	private String summary;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateDate;

	/**
	 * 内容归属类型
	 */
	private String contentType;
	/**
	 * 内容模板，主要是控制编辑框内容样式
	 */
	private  String editorTemplateCode;
    /**
     * 内容
     */
	private  String content;
	/**
	 * 标签名称
	 */
	private String tagsNames;
	/**
	 * 文章来源类型
	 */
	private String articleSourceType;
	/**
	 * 文章url
	 */
	private String articleUrl;
	/**
	 * 文章排序
	 */
	private int articleSort;
	/**
	 * 文章是否显示
	 */
	private String showStatus;

	/**
	 * 资讯参与类型
	 */
	private String articleAttendType;

	private int shareSize;

	private int readSize;

	private int thumbsUpSize;

	private int collectionSize;

	private String tagId;

	private String[] tags;

	private String articlePic;

	/**
	 * 资讯显示位置
	 */
	private String articleLocationShow;

	/**
	 * 资讯所属时间
	 */
	private String articleAcctTime;

	/**
	 * 基金代码
	 */
	private String fundCode;
	
	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
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

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEditorTemplateCode() {
		return editorTemplateCode;
	}

	public void setEditorTemplateCode(String editorTemplateCode) {
		this.editorTemplateCode = editorTemplateCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getArticleSourceType() {
		return articleSourceType;
	}

	public void setArticleSourceType(String articleSourceType) {
		this.articleSourceType = articleSourceType;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getArticleTitleShortname() {
		return articleTitleShortname;
	}

	public void setArticleTitleShortname(String articleTitleShortname) {
		this.articleTitleShortname = articleTitleShortname;
	}

	public int getArticleSort() {
		return articleSort;
	}

	public void setArticleSort(int articleSort) {
		this.articleSort = articleSort;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getTagsNames() {
		return tagsNames;
	}

	public void setTagsNames(String tagsNames) {
		this.tagsNames = tagsNames;
	}

	public String getArticleAttendType() {
		return articleAttendType;
	}

	public void setArticleAttendType(String articleAttendType) {
		this.articleAttendType = articleAttendType;
	}

	public int getShareSize() {
		return shareSize;
	}

	public void setShareSize(int shareSize) {
		this.shareSize = shareSize;
	}

	public int getReadSize() {
		return readSize;
	}

	public void setReadSize(int readSize) {
		this.readSize = readSize;
	}

	public int getThumbsUpSize() {
		return thumbsUpSize;
	}

	public void setThumbsUpSize(int thumbsUpSize) {
		this.thumbsUpSize = thumbsUpSize;
	}

	public int getCollectionSize() {
		return collectionSize;
	}

	public void setCollectionSize(int collectionSize) {
		this.collectionSize = collectionSize;
	}

	public String getArticlePic() {
		return articlePic;
	}

	public void setArticlePic(String articlePic) {
		this.articlePic = articlePic;
	}

	public String getArticleLocationShow() {
		return articleLocationShow;
	}

	public void setArticleLocationShow(String articleLocationShow) {
		this.articleLocationShow = articleLocationShow;
	}

	public String getArticleAcctTime() {
		return articleAcctTime;
	}

	public void setArticleAcctTime(String articleAcctTime) {
		this.articleAcctTime = articleAcctTime;
	}

}
