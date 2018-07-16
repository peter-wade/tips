package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 广告信息
 * </p>
 *
 * @author linan
 * @since 2018-03-02
 */
public class CmsAdv extends Model<CmsAdv> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String advId;
    /**
     * 广告名称
     */
	private String advName;
    /**
     * 广告编码
     */
	private String advCode;
    /**
     * 开始时间
     */
	private String startTime;
    /**
     * 结束时间
     */
	private String endTime;
    /**
     * 广告位置
     */
	private String advPosition;
    /**
     * 内部地址，外部地址（带http）
     */
	private String urlType;
    /**
     * 地址
     */
	private String advUrl;
    /**
     * 备注说明
     */
	private String remark;
    /**
     * SAVE已保存PUB已发布
     */
	private String status;
    /**
     * 数据状态
     */
	private String dataState;
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
     * 最大图片展示数量
     */
	private Integer maxSize;
	/**
     * 排序
     */
	private Integer advSort;
	/**
	 * 广告图片地址
	 */
	private String imgUrl;
	/**
	 * 广告关联的活动属性
	 */
	private String advTypeAttr;
	/**
	 * 广告图片地址
	 */
	private String advType;
	/**
	 * 广告关联的活动code
	 */
	private String advCampCode;
	/**
	 * 广告关联基金code
	 */
	private String advFundCode;
	


	public String getAdvId() {
		return advId;
	}

	public void setAdvId(String advId) {
		this.advId = advId;
	}

	public String getAdvName() {
		return advName;
	}

	public void setAdvName(String advName) {
		this.advName = advName;
	}

	public String getAdvCode() {
		return advCode;
	}

	public void setAdvCode(String advCode) {
		this.advCode = advCode;
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

	public String getAdvPosition() {
		return advPosition;
	}

	public void setAdvPosition(String advPosition) {
		this.advPosition = advPosition;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getAdvUrl() {
		return advUrl;
	}

	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
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
		return this.advId;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getAdvSort() {
		return advSort;
	}

	public void setAdvSort(Integer advSort) {
		this.advSort = advSort;
	}


	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getAdvTypeAttr() {
		return advTypeAttr;
	}

	public void setAdvTypeAttr(String advTypeAttr) {
		this.advTypeAttr = advTypeAttr;
	}


	public String getAdvCampCode() {
		return advCampCode;
	}

	public void setAdvCampCode(String advCampCode) {
		this.advCampCode = advCampCode;
	}

	public String getAdvFundCode() {
		return advFundCode;
	}

	public void setAdvFundCode(String advFundCode) {
		this.advFundCode = advFundCode;
	}

	@Override
	public String toString() {
		return "CmsAdv [advId=" + advId + ", advName=" + advName + ", advCode=" + advCode + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", advPosition=" + advPosition + ", urlType=" + urlType + ", advUrl="
				+ advUrl + ", remark=" + remark + ", status=" + status + ", dataState=" + dataState + ", createBy="
				+ createBy + ", createDate=" + createDate + ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", maxSize=" + maxSize + ", advSort=" + advSort + ", imgUrl=" + imgUrl + ", advTypeAttr="
				+ advTypeAttr + ", advType=" + advType + ", advCampCode=" + advCampCode + ", advFundCode=" + advFundCode
				+ "]";
	}
	
	
	
	
}
