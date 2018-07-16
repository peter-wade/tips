package com.phfund.aplus.cms.oms.modular.editor.controller;

import com.phfund.aplus.cms.oms.common.persistence.dao.CmsEditorTemplateMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsEditorTemplate;
import com.phfund.aplus.oms.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询文件列表
 *
 * @author fengshuonan
 * @Date 2018-03-05 14:24:33
 */
@RestController
@RequestMapping("/S100007")
public class S100007 extends BaseController {
	
	@Autowired
	private CmsEditorTemplateMapper cmsEditorTemplateMapper;

    /**
     * 获取导入文件列表
     */
    @PostMapping(value = "/")
    public Object list(String groupCode) {
        Map paramMap = new HashMap();
        paramMap.put("groupCode",groupCode) ;
		List<CmsEditorTemplate> list = cmsEditorTemplateMapper.queryCmsEditorTemplateList(paramMap);
    	return list;
    }
    
    
    
}
