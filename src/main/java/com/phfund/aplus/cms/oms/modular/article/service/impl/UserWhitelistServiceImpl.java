package com.phfund.aplus.cms.oms.modular.article.service.impl;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.esotericsoftware.minlog.Log;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsFileMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.UserWhitelistMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.modular.article.service.UserWhitelistService;
import com.phfund.aplus.cms.oms.util.MongoFileService;
import com.phfund.aplus.cms.oms.util.ReadExcelUtils;
import com.phfund.aplus.framework.base.utils.IdGenerate;
import com.phfund.aplus.framework.base.utils.StrUtil;
import com.phfund.aplus.oms.boot.common.constant.Const;
import com.phfund.aplus.oms.core.util.DateUtil;

/**
 * 活动用户名单导入
 * @author x_zhuxiaolong
 * @since 2018-02-01
 */
@Service
public class UserWhitelistServiceImpl implements UserWhitelistService {
	
	@Autowired
	private UserWhitelistMapper userWhitelistMapper;
	@Autowired
	private MongoFileService mongoFileService;
	@Autowired
	private CmsFileMapper cmsFileMapper;
	
	
	
	@Override  
	@Transactional
    public Map<String, Object> readExcelFile(MultipartFile file,String number,String user) {  
       	Map<String, Object> resultMap = new HashMap<String, Object>();
       	if(file == null){
       		resultMap.put("code", "202");
       		resultMap.put("msg", "请选择上传文件!");
       		return resultMap; 
       	}
        //创建处理EXCEL的类  
        ReadExcelUtils readExcel = new ReadExcelUtils();  
        //解析excel，获取上传的事件单  
        List<List<Object>> excelData = readExcel.ReadExcelUtils(file,number);
       	/**
       	 * 图片上传到mongoDB
       	 */
       	String fileId = IdGenerate.getNextId();
       	try {
       		// 存储到MongoDB的文件名称
			String fileName = "ARTICLEUSER" + DateUtil.getAllTime() + IdGenerate.getNextId();
			mongoFileService.SaveFile("phfund_fs", file, fileName, file.getOriginalFilename());
       		// 保存文件上传记录
       		CmsFile cmsFile = new CmsFile();
       		cmsFile.setFileId(fileId);
       		cmsFile.setId(fileId);
       		cmsFile.setFileName(file.getOriginalFilename());
       		cmsFile.setServerFileName(fileName);
       		cmsFile.setFileType(StringUtils.split(file.getOriginalFilename(), ".")[1]);
       		cmsFile.setFileCatalog("article");
       		cmsFile.setCreateBy(user);
       		cmsFile.setUploadTime(new Date());
       		cmsFileMapper.addFile(cmsFile);
       	} catch (Exception e) {
       		Log.info("添加文件失败！={}",e);
       	}
       
        //至此已经将excel中的数据转换到list中了,接下来就可以操作MAP,可以进行保存到数据库,或者其他操作,
        // 循环遍历嵌套list（活动用户参与名单)
        // 遍历集合
        for(int i = 0; i < excelData.size(); i++) {
        	Map<String, Object> mapParam = new HashMap<String, Object>();
        	// 手机号码
        	String phoneNo = null;
        	// 名单类型
        	String listType = null;//BLACK黑名单，WHITE白名单
        	String businessType = null;
        	String businessId=null;
        	String remark=null;
        	for(int j = 0; j < excelData.get(i).size(); j++) {
        		String para = null;
               	switch(j) {
    	        		case 0:
    	        			para = "businessId";//业务id
    	        			businessId = String.valueOf(excelData.get(i).get(j));
    	        		break; 	
    	        		case 1:
    	        			para = "listType";//列表类型
    	        			listType = String.valueOf(excelData.get(i).get(j));
    	        		break; 	
    	        		case 2:
    	        			para = "phoneNo";//手机号
    	        			phoneNo = String.valueOf(excelData.get(i).get(j));
    	        		break; 
    	        		case 3:
    	        			para = "dataSource";//数据来源
    	        		break; 
    	        		case 4:
    	        			para = "remark";//备注
    	        			remark = String.valueOf(excelData.get(i).get(j));
    	        		break; 
    	        		case 5:
    	        			para = "businessType";//业务类型
    	        			businessType = String.valueOf(excelData.get(i).get(j));
    	        		break; 
                	}
               	Object value = excelData.get(i).get(j);
               	if(null == value) {
               		mapParam.put(para, value);
               	}else {
                    //  判断是否为科学计数法（包含E、e、+等符号）
                    if (((""+value).indexOf("E")!=-1) && !StrUtil.isEqual(para, "listType")) {
                        BigDecimal bd = new BigDecimal(""+value);
                        mapParam.put(para, bd.toString().trim());   
                    }else{
                    	mapParam.put(para, value.toString().trim());
                    }    
               	}
        	}
           	if(StrUtil.isEmpty(mapParam.get("businessId").toString().trim())) {
           		continue;
           	}
           	mapParam.put("businessId",businessId);
        	// 文件ID
        	mapParam.put("fileId", fileId);
        	mapParam.put("userName", "admin");
        	mapParam.put("updateBy", "admin");        	// 数据状态
        	mapParam.put("dataState", Const.DATA_STATUS_EFFEC);
        	userWhitelistMapper.addUserWhiteList(mapParam);
        }
    	resultMap.put("code", "200");
    	resultMap.put("msg", "上传成功!");
        return resultMap; 
    }



	@Override
	@Transactional
	public void delete(String fileId ) {
       	Map<String, Object> paraMap = new HashMap<String, Object>();
       	paraMap.put("fileId", fileId);
		userWhitelistMapper.updateUserWhite(paraMap);
		cmsFileMapper.deleteCmsFile(paraMap);
	}  

	public List<String> queryArticleWhitelist(String articleId) {
		// TODO Auto-generated method stub
		return userWhitelistMapper.queryArticleWhitelist(articleId);
	}  
	
	
	
	
	@Transactional
    public Map<String, Object> readArticleExcelFile(MultipartFile file,String articleId,String user) {  
       	Map<String, Object> resultMap = new HashMap<String, Object>();
       	if(file == null){
       		resultMap.put("code", "202");
       		resultMap.put("msg", "请选择上传文件!");
       		return resultMap; 
       	}
        //创建处理EXCEL的类  
        ReadExcelUtils readExcel = new ReadExcelUtils();  
        //解析excel，获取上传的事件单  
        List<List<Object>> excelData = readExcel.ReadExcelUtils(file,"2");
       	/**
       	 * 图片上传到mongoDB
       	 */
       	String fileId = IdGenerate.getNextId();
       	try {
       		// 存储到MongoDB的文件名称
			String fileName = "ARTICLEUSER" + DateUtil.getAllTime() + IdGenerate.getNextId();
			mongoFileService.SaveFile("phfund_fs", file, fileName, file.getOriginalFilename());
       		// 保存文件上传记录
       		CmsFile cmsFile = new CmsFile();
       		cmsFile.setFileId(fileId);
       		cmsFile.setId(fileId);
       		cmsFile.setFileName(file.getOriginalFilename());
       		cmsFile.setServerFileName(fileName);
       		cmsFile.setFileType(StringUtils.split(file.getOriginalFilename(), ".")[1]);
       		cmsFile.setFileCatalog("article");
       		cmsFile.setCreateBy(user);
       		cmsFile.setUploadTime(new Date());
       		cmsFileMapper.addFile(cmsFile);
       	} catch (Exception e) {
       		Log.info("添加文件失败！={}",e);
       	}
       
        //至此已经将excel中的数据转换到list中了,接下来就可以操作MAP,可以进行保存到数据库,或者其他操作,
        // 循环遍历嵌套list（活动用户参与名单)
        // 遍历集合
        for(int i = 0; i < excelData.size(); i++) {
        	Map<String, Object> mapParam = new HashMap<String, Object>();
        	// 手机号码
        	String phoneNo = null;
        	// 名单类型
        	String listType = null;//BLACK黑名单，WHITE白名单
        	String businessType = null;
        	String businessId=null;
        	String remark=null;
        	for(int j = 0; j < excelData.get(i).size(); j++) {
        		String para = null;
               	switch(j) {
    	        		case 0:
    	        			para = "listType";//列表类型
    	        			listType = String.valueOf(excelData.get(i).get(j));
    	        		break; 	
    	        		case 1:
    	        			para = "phoneNo";//手机号
    	        			phoneNo = String.valueOf(excelData.get(i).get(j));
    	        		break; 
    	        		case 2:
    	        			para = "dataSource";//数据来源
    	        		break; 
    	        		case 3:
    	        			para = "remark";//备注
    	        			remark = String.valueOf(excelData.get(i).get(j));
    	        		break; 
    	        		case 4:
    	        			para = "businessType";//业务类型
    	        			businessType = String.valueOf(excelData.get(i).get(j));
    	        		break; 
                	}
               	Object value = excelData.get(i).get(j);
               	if(null == value) {
               		mapParam.put(para, value);
               	}else {
                    //  判断是否为科学计数法（包含E、e、+等符号）
                    if (((""+value).indexOf("E")!=-1) && !StrUtil.isEqual(para, "listType")) {
                        BigDecimal bd = new BigDecimal(""+value);
                        mapParam.put(para, bd.toString().trim());   
                    }else{
                    	mapParam.put(para, value.toString().trim());
                    }    
               	}
        	}
           	mapParam.put("businessId",articleId);
        	// 文件ID
        	mapParam.put("fileId", fileId);
        	mapParam.put("userName", "admin");
        	mapParam.put("updateBy", "admin");        	// 数据状态
        	mapParam.put("dataState", Const.DATA_STATUS_EFFEC);
        	/*//导入白名单判重处理
        	int isalreadyin=userWhitelistMapper.queryUserCount(mapParam);
        	if(isalreadyin>0) {
        		continue;
        	}*/
        	userWhitelistMapper.addUserWhiteList(mapParam);
        }
    	resultMap.put("code", "200");
    	resultMap.put("msg", "上传成功!");
        return resultMap; 
    }

}
