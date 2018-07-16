package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

/**
 * <p>
 * 
 * </p>
 *
 * @author lin
 * @since 2018-02-02
 */
public class CmsTags extends Model<CmsTags> {

    private static final long serialVersionUID = 1L;

	private String tagId;
	private String tagCatalog;
	private String tagName;
    /**
     * 创建人
     */
	@TableField("create_by")
	private String createBy;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 更新人
     */
	@TableField("update_by")
	private String updateBy;
    /**
     * 更新时间
     */
	@TableField("update_date")
	private Date updateDate;


	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagCatalog() {
		return tagCatalog;
	}

	public void setTagCatalog(String tagCatalog) {
		this.tagCatalog = tagCatalog;
	}


	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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
		// TODO Auto-generated method stub
		return null;
	}


}
