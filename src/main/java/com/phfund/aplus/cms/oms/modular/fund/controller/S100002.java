package com.phfund.aplus.cms.oms.modular.fund.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.esotericsoftware.minlog.Log;
import com.phfund.aplus.cms.oms.modular.fund.service.impl.FundContentInfoServiceImpl;
import com.phfund.aplus.cms.oms.modular.fund.vo.FundCommentInfoVo;
import com.phfund.aplus.framework.base.beans.Result;
import com.phfund.aplus.framework.base.utils.StrUtil;
import com.phfund.aplus.oms.boot.common.annotion.Permission;
import com.phfund.aplus.oms.boot.common.constant.factory.PageFactory;
import com.phfund.aplus.oms.boot.core.shiro.ShiroKit;
import com.phfund.aplus.oms.core.base.controller.BaseController;
import com.phfund.aplus.oms.core.util.DateUtil;

/**
 * @author x_zhuxiaolong
 *	评论审核功能
 */
@Controller
@RequestMapping("/S100002")
public class S100002 extends BaseController{
    protected final static Logger logger = LoggerFactory.getLogger(S100002.class);
	@Autowired
	private FundContentInfoServiceImpl fundContentInfoServiceImpl;
	private String PREFIX = "/system/fund/";
	
	/**
	 * 跳转到查询首页
	 */
	@RequestMapping("/index")
	public String index2() {
		return PREFIX + "fundCommentInfo.html";
	}
	
	
	
	/**
	 * 跳转到camp首页
	 */
    @RequestMapping("")
    @ResponseBody
	public String index() {	
		return  this.getCurrentAddressInfo() + "/S100002/index";
	}
	
	/**
	 * 获取后端数据
	 * @return
	 */
//    @Permission
    @ResponseBody
    @RequestMapping("/list")
    public Object list(String portfolioCode, String portfolioName, String commentsCode,
    		String custShortName, String startTime, String endTime,String reviewStatus) {
    	Log.info("-------------startTime============"+ startTime);
    	Log.info("-------------endTime============"+ endTime);
       	if(StrUtil.isNotEmpty(startTime)) {
    		startTime = DateUtil.date(startTime) + "000000";
    	}
    	if(StrUtil.isNotEmpty(endTime)) {
    		endTime = DateUtil.date(endTime) + "235959";
    	}
    	FundCommentInfoVo fundCommentInfoVo = new FundCommentInfoVo();
    	fundCommentInfoVo.setPortfolioCode(portfolioCode);
    	fundCommentInfoVo.setPortfolioName(portfolioName);
    	fundCommentInfoVo.setCommentsCode(commentsCode);
    	fundCommentInfoVo.setCustShortName(custShortName);
    	fundCommentInfoVo.setStartTime(startTime);
    	fundCommentInfoVo.setEndTime(endTime);
    	fundCommentInfoVo.setReviewStatus(reviewStatus);
    	Log.info("-------------fundCommentInfoVo============"+ fundCommentInfoVo);
    	 Page<FundCommentInfoVo> page = new PageFactory<FundCommentInfoVo>().defaultPage();
    	List<FundCommentInfoVo> fundCommentInfo = fundContentInfoServiceImpl.queryFundContent(page,fundCommentInfoVo);
    	Log.info("-------------查询结果============"+ fundCommentInfo);
	    page.setRecords(fundCommentInfo);
	    return super.packForBT(page);
    }
	/**
	 * 审核通过
	 */
	@PostMapping(value = "/update")
	@ResponseBody
	@Permission
	public Object updateAdopt(String[] ids,String reviewStatus) {
		String user = ShiroKit.getUser().getAccount();
		Result result = null;
		if(ids.length > 0) {
			fundContentInfoServiceImpl.updateContent(ids, reviewStatus,user);
			result = Result.success("审核数据成功");
		}else {
			return Result.failure("1001","请选择需要审核的数据");
		}

		
		return  result ;
	}
	

}
