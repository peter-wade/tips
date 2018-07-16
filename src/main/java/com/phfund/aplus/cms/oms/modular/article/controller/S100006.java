package com.phfund.aplus.cms.oms.modular.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.esotericsoftware.minlog.Log;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsFileMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.oms.boot.common.constant.factory.PageFactory;
import com.phfund.aplus.oms.core.base.controller.BaseController;

/**
 * 查询文件列表
 *
 * @author fengshuonan
 * @Date 2018-03-05 14:24:33
 */
@Controller
@RequestMapping("/S100006")
public class S100006 extends BaseController {
	
	@Autowired
	private CmsFileMapper cmsFileMapper;

    /**
     * 获取导入文件列表
     */
    @RequestMapping(value = "/list/{fileCatalog}")
    @ResponseBody
    public Object list(@PathVariable String fileCatalog,String operator,String startTime,String endTime) {
    	Map<String, Object> columnMap = new HashMap<String, Object>(); 
    	// 查询导入列表
    	columnMap.put("fileCatalog", fileCatalog);
    	columnMap.put("operator", operator);
    	columnMap.put("starttime", startTime);
    	columnMap.put("endTime", endTime);
    	Page<CmsFile> page = new PageFactory<CmsFile>().defaultPage();
         Log.info("请求参数=columnMap={}",JSON.toJSONString(columnMap));
    	List<CmsFile> cmsFileList = cmsFileMapper.queryFilelist(page,columnMap);
    	Log.info("-------------查询结果============"+ cmsFileList);
	    page.setRecords(cmsFileList);
    	return super.packForBT(page);
    }
    
    
    
}
