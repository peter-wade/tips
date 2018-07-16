package com.phfund.aplus.cms.oms.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aplus.cms.adv")
public class CmsAdvConfigProperties {
	
    private String picUrl ;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

    
    
}
