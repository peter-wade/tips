package com.phfund.aplus.cms.oms.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


@TableName("cms_editor_template")
public class CmsEditorTemplate extends Model<CmsEditorTemplate> {

    private static final long serialVersionUID = 1L;

    @TableField("group_code")
    private String groupCode ;

	@TableId("template_code")
	private String templateCode ;

	@TableField("template_name")
	private String templateName ;

	@TableField("template_path")
	private String templatePath ;

	@TableField("sort_no")
	private String sortNo ;

	@TableField("content")
	private String content ;


	@Override
	protected Serializable pkVal() {
		return this.templateCode ;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
