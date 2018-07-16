package com.phfund.aplus.cms.oms.common.persistence.dao;

import com.phfund.aplus.cms.oms.common.persistence.model.CmsEditorTemplate;

import java.util.List;
import java.util.Map;

public interface CmsEditorTemplateMapper {

    public List<CmsEditorTemplate> queryCmsEditorTemplateList(Map paramMap);
    public CmsEditorTemplate selectById(String templateCode);

}
