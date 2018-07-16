package com.phfund.aplus.cms.oms.modular.article.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.esotericsoftware.minlog.Log;
import com.phfund.aplus.cms.oms.common.persistence.dao.UserWhitelistMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.ImportArticleInfo;
import com.phfund.aplus.oms.boot.common.constant.factory.PageFactory;
import com.phfund.aplus.oms.core.base.controller.BaseController;

/**
 * 查询资讯导入列表详情
 *
 * @author x_dengjicai
 * @Date 2018-02-01 19:57:42
 */
@Controller
@RequestMapping("/S100005")
public class S100005 extends BaseController {

	private String PREFIX = "/system/userWhitelist/";
	
	@Autowired
	private UserWhitelistMapper userWhitelistMapper;

	/**
	 * 跳转到用户导入首页
	 */
	@RequestMapping("/index/{fileId}")
	public String index(@PathVariable String fileId, Model model) {
		model.addAttribute("fileId", fileId);
		return PREFIX + "userWhitelistlist.html";
	}
	
	/**
	 * 列表查询
	 */
	@ResponseBody
	@RequestMapping("/list/{fileId}")
	public Object index(@PathVariable String fileId,String condition) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		// 只获取有效数据
		columnMap.put("file_id", fileId);
		columnMap.put("phoneNo", condition);
		//return campattendMapper.selectByMap(columnMap);
    	Page<ImportArticleInfo> page = new PageFactory<ImportArticleInfo>().defaultPage();
		 List<ImportArticleInfo> campInfo = userWhitelistMapper.queryArticleInfo(page,columnMap);
		//return campInfo;
	    page.setRecords(campInfo);
    	return super.packForBT(page);
	}
}
