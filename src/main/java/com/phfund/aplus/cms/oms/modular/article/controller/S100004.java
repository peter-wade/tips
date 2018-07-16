package com.phfund.aplus.cms.oms.modular.article.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.phfund.aplus.cms.oms.modular.article.service.UserWhitelistService;
import com.phfund.aplus.oms.boot.core.shiro.ShiroKit;
import com.phfund.aplus.oms.core.base.controller.BaseController;

import ch.qos.logback.classic.Logger;

/**
 *导入资讯白名单
 *
 * @author zhuxiaolong
 * @Date 2018-02-01 19:57:42
 */
@Controller
@RequestMapping("/S100004")
public class S100004 extends BaseController {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(S100004.class);

	private String PREFIX = "/system/userWhitelist/";

	@Autowired
	private UserWhitelistService userWhitelistService;

	/**
	 *跳转到aplus首页
	 */
	@ResponseBody
	@RequestMapping("")
	public String index() {
		return this.getCurrentAddressInfo() + "/S100004/index";
	}

	/**
	 * 跳转到aplus首页
	 */
	@RequestMapping("/index")
	public String index2() {
		return PREFIX + "userListImport.html";
	}


	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, String ids) {
		
	    userWhitelistService.delete(ids);
		return SUCCESS_TIP;
	}

    @RequestMapping(value="/upload",method = RequestMethod.POST)  
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value="file",required = false)MultipartFile file, String number,HttpServletRequest request, HttpServletResponse response){  
    	String user = ShiroKit.getUser().getAccount();
    	Map<String, Object> resultMap = userWhitelistService.readExcelFile(file,number,user);  
        return resultMap;  
    } 
    
    
    @RequestMapping(value="/uploadart",method = RequestMethod.POST)  
    @ResponseBody
    public Map<String, Object> uploadart(@RequestParam(value="file",required = false)MultipartFile file, String articleId,HttpServletRequest request, HttpServletResponse response){  
    	String user = ShiroKit.getUser().getAccount();
    	Map<String, Object> resultMap = userWhitelistService.readArticleExcelFile(file,articleId,user);  
        return resultMap;  
    } 
    /**
     * 资讯白名单模板
     * @param request
     * @param response
     * @throws IOException
     */
    	@RequestMapping( value = "/excelDownLoad" ,method = RequestMethod.GET)
    	@ResponseBody
	    public void excelStandardTemplateOut(HttpServletRequest request,HttpServletResponse response) throws IOException{
     	   InputStream is = this.getClass().getResourceAsStream("/static/file/advisoryTemplate.xlsx") ; 
	         // 设置response参数，可以打开下载页面
	       response.reset();
	       response.setContentType("application/vnd.ms-excel;charset=utf-8");
	       try {
	           response.setHeader("Content-Disposition", "attachment;filename="+ new String(("资讯白名单模板" + ".xlsx").getBytes(), "iso-8859-1")); //下载文件的名称
	       } catch (UnsupportedEncodingException e) {
	           e.printStackTrace();
	       }
	       OutputStream out = response.getOutputStream();
	       BufferedInputStream bis = null;
	       BufferedOutputStream bos = null;
	       try {
	           bis = new BufferedInputStream(is);
	           bos = new BufferedOutputStream(out);
	           byte[] buffer = new byte[4096];
	           int bytesRead;
	           while (-1 != (bytesRead = bis.read(buffer, 0, buffer.length))) {
	               bos.write(buffer, 0, bytesRead);
	           }
	       } catch (final IOException e) {
	           throw e;
	       } finally {
	           if (bis != null)
	               bis.close();
	           if (bos != null)
	               bos.close();
	           if(is != null)
	        	   is.close();
	       }
    }        
 
}
