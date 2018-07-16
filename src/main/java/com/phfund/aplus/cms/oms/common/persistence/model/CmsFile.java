package com.phfund.aplus.cms.oms.common.persistence.model;


import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
public class CmsFile  {

    private static final long serialVersionUID = 1L;

	private String fileId;
	private String fileName;
	private Date uploadTime;
	private String serverFileName;
	private String fileType;
	private String fileCatalog;
	private String id;
	
	private Date createDate;
    /**
     * 序号
     */
	private String seqNum;
	private String createBy;


	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getServerFileName() {
		return serverFileName;
	}

	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileCatalog() {
		return fileCatalog;
	}

	public void setFileCatalog(String fileCatalog) {
		this.fileCatalog = fileCatalog;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	@Override
	public String toString() {
		return "CmsFile{" +
			"fileId=" + fileId +
			", fileName=" + fileName +
			", uploadTime=" + uploadTime +
			", serverFileName=" + serverFileName +
			", fileType=" + fileType +
			", fileCatalog=" + fileCatalog +
			", id=" + id +
			", seqNum=" + seqNum +
			", createBy=" + createBy +
			"}";
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
