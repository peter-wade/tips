package com.phfund.aplus.cms.oms.modular.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author x_zhuxiaolong
 * @since 2018-02-01
 */
public interface UserWhitelistService {

	/** 
     * 读取excel中的数据,生成MAP
     */  


	void delete(String fileId ); 

    Map<String, Object> readArticleExcelFile( MultipartFile file,String number,String user); 
    //根据资讯id查找出白名单手机号
    List<String> queryArticleWhitelist(String articleId);

	Map<String, Object> readExcelFile(MultipartFile file, String number, String user);
	
}
