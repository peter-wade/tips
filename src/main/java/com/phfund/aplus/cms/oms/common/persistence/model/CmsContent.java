package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public class CmsContent extends Model<CmsContent> {

    private static final long serialVersionUID = 1L;

    /**
     * 内容归属类型 
     */
	private String contentType;
	/**
     * 内容关联文章id
     */
	private String articleId;
    /**
     * 内容模板
     */
	private String editorTemplateCode;
    /**
     * 内容
     */
	private String content;
    /**
     * 主键
     */
	private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CmsContent{" +
			"contentType=" + contentType +
			", content=" + content +
			", id=" + id +
			", createBy=" + createBy +
			", createDate=" + createDate +
			", updateBy=" + updateBy +
			", updateDate=" + updateDate +
			"}";
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
}
