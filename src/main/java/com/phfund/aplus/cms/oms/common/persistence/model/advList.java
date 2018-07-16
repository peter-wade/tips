package com.phfund.aplus.cms.oms.common.persistence.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class advList implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	private String adv_url;
  	private String file_name;
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getAdv_url() {
		return adv_url;
	}
	public void setAdv_url(String adv_url) {
		this.adv_url = adv_url;
	}

}
