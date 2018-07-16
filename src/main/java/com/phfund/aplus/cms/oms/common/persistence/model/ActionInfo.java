package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;

public class ActionInfo implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	private String actionSessid;
	
	private String deviceId;
	
	private String pageId;
	
	private String ip;
	
	private String phoneNo;
	
	private String umengId;

	public String getActionSessid() {
		return actionSessid;
	}

	public void setActionSessid(String actionSessid) {
		this.actionSessid = actionSessid;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUmengId() {
		return umengId;
	}

	public void setUmengId(String umengId) {
		this.umengId = umengId;
	}
	
	
	
	
}
