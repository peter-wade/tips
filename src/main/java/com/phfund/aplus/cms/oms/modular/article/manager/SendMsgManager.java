package com.phfund.aplus.cms.oms.modular.article.manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.phfund.aplus.camp.core.common.ParameterGet.AttendType;
import com.phfund.aplus.camp.core.common.ParameterGet.SendType;
import com.phfund.aplus.cms.oms.common.persistence.model.ActionInfo;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsReceive;
import com.phfund.aplus.cms.oms.modular.article.service.GetDeviceIdService;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsArticleService;
import com.phfund.aplus.cms.oms.modular.article.service.UserWhitelistService;
import com.phfund.aplus.cms.oms.util.HttpRequest;
import com.phfund.aplus.framework.base.beans.BaseResp;
import net.sf.json.JSONObject;
@Service
public class SendMsgManager {
	
	protected final static Logger logger = LoggerFactory.getLogger(SendMsgManager.class);
	
	@Autowired
	private GetDeviceIdService getDeviceIdService;
	
	
	@Autowired
	private ICmsArticleService iCmsArticleService;
	
	@Autowired
	private UserWhitelistService iUserWhitelistService;
	
	@Autowired
	private HttpRequest HttpRequest;
	
	@Value("${detailurl}")
	private String detailurl;
	
	@Value("${aplus.cms.oms.umengUrl}")
	private String umenglurl;
	
	public BaseResp sendMsg(String articleId) {
		// TODO Auto-generated method stub
		BaseResp resp=new BaseResp();
		CmsReceive article=iCmsArticleService.selectById(articleId);
		Map<String, Object> sysMap=new HashMap<String,Object>();
		Map<String, Object> inputMap=new HashMap<String,Object>();
		Map<String, Object> sendMap=new HashMap<String,Object>();
		sysMap.put("chnlcd", "youMengPush");
		sendMap.put("sys", sysMap);
		inputMap.put("title", article.getArticleTitle());
		inputMap.put("id",article.getArticleType()+","+article.getArticleId()+","+SendType.ARTICLE_SEND+","+detailurl+articleId+".html");
		inputMap.put("text", article.getSummary());
		List<String> uList=new ArrayList<String>();
		if(AttendType.PRIVATE_ARTICLE.equals(article.getArticleAttendType())) {
			//定向资讯的消息推送	
			inputMap.put("pushType", SendType.PRIVATE_SEND);
			uList=iUserWhitelistService.queryArticleWhitelist(articleId);
		   if(null!=uList && uList.size()>0) {
			   for(String phoneNo:uList) {
				   try {
					   ActionInfo deviceInfo=getDeviceIdService.getDeviceId(phoneNo);
					   logger.info("====deviceInfo={}",JSON.toJSONString(deviceInfo));
					   inputMap.put("deviceTokens", deviceInfo.getUmengId());
					   sendMap.put("input", inputMap);
					   HttpRequest.doHttp(umenglurl,JSONObject.fromObject(sendMap));
				} catch (Exception e) {
					// TODO: handle exception
					//有错误继续发送
					logger.error("发送推送消息异常！");
				}
			   }
			   resp.setRespCode("0001");
			   resp.setBizSeqNo(String.valueOf(uList.size()));
		   }else {
			   resp.setRespCode("100001");
			   resp.setRespMsg("定向资讯但是没有查询到白名单！");
		   }
			
		}else if(AttendType.PUBLIC_ARTICLE.equals(article.getArticleAttendType())){
			//公共资讯的消息推送
			try {
				inputMap.put("pushType", SendType.PUBLIC_SEND);
				sendMap.put("input", inputMap);
				resp.setRespCode("0002");
				HttpRequest.doHttp(umenglurl,JSONObject.fromObject(sendMap));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("发送推送消息异常！");
			}
		}
		return resp;
	}

}
