package com.phfund.aplus.cms.oms.util;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class HttpRequest {

	protected static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
	
	public String doHttp(String url,JSONObject obj) throws ClientProtocolException, IOException {
		logger.info("HttpRequest==========obj="+obj);
		logger.info("HttpRequest==========url="+url);
		  HttpUtil httpUtil = new HttpUtil("30000");
		  String str = httpUtil.doPost(url, obj.toString());
		logger.info("HttpRequest============str="+str);
		  return str;

	}

}
