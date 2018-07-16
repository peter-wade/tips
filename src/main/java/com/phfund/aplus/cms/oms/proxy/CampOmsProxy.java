package com.phfund.aplus.cms.oms.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CampOmsProxy {
	
	private static final Logger log = LoggerFactory.getLogger(CampOmsProxy.class);
	
	@Autowired
	private RestTemplate restTemplate;

	private static String CAMPOMS = "APLUS-CAMP-OMS";
	private static String PROTOCOL = "http://";
	// 查询活动列表
	private static final String CAMP_LIST = "/S080003/advcamplist";
	// 是否为可用的广告活动
    private static final String CAMP_OK = "/S080003/isOkforAdv";
	
	
	// 客户活动列表查询
	public Object  getCampList() {
		Object returnStr = restTemplate.postForObject(PROTOCOL+CAMPOMS+CAMP_LIST,new Object(),Object.class);
		log.info("end invoke {}'s service {},resp:",CAMPOMS,CAMP_LIST,returnStr);
		return returnStr;
	}
	
	// 客户活动列表查询
		public Boolean  isOkCamp(String campCode) {
			log.info("campCode==={}",campCode);
			Boolean returnStr = (Boolean) restTemplate.postForObject(PROTOCOL+CAMPOMS+CAMP_OK,campCode,Object.class);
			log.info("end invoke {}'s service {},resp:",CAMPOMS,CAMP_OK,returnStr);
			return returnStr;
		}
	
}
