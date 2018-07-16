package com.phfund.aplus.cms.oms.modular.article.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.camp.core.common.AdvContents.AdvType;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsAdv;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsAdvService;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsFileService;
import com.phfund.aplus.cms.oms.proxy.CampOmsProxy;
import com.phfund.aplus.cms.oms.util.MongoFileService;
import com.phfund.aplus.framework.base.beans.Result;
import com.phfund.aplus.framework.base.utils.IdGenerate;
import com.phfund.aplus.framework.base.utils.StrUtil;
import com.phfund.aplus.oms.boot.common.constant.factory.PageFactory;
import com.phfund.aplus.oms.boot.common.controller.DataOprBaseContrller;
import com.phfund.aplus.oms.boot.core.log.LogObjectHolder;
import com.phfund.aplus.oms.boot.proxy.OmsAdminProxy;
import com.phfund.aplus.oms.core.util.DateUtil;
import com.phfund.aplus.oms.core.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-03-02 09:57:16
 */
@Controller
//@RequestMapping("/cmsAdv")
@RequestMapping({"/S100001","/cmsAdv"})
public class CmsAdvController extends DataOprBaseContrller {
	
	protected final static Logger logger = LoggerFactory.getLogger(CmsAdvController.class);
	

    private String PREFIX = "/system/cmsAdv/";

    @Autowired
    private ICmsAdvService cmsAdvService;

    @Autowired
	private MongoFileService mongoFileService;
    
    @Autowired
    private ICmsFileService cmsFileService;
    
	@Autowired
	private CampOmsProxy campOmsProxy;
    
    
    /**
     * 跳转到aplus首页
     */
    @RequestMapping("/index")
    public String index2() {
        return PREFIX+ "cmsAdv.html";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    @ResponseBody
    public String index() {
    	return this.getCurrentAddressInfo() + "/S100001/index";
        //return PREFIX + "cmsAdv.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/cmsAdv_add")
    public String cmsAdvAdd() {
        OmsAdminProxy proxy = SpringContextHolder.getBean("omsAdminProxy") ;
        Map param = proxy.getParam("aplus-cms") ;
        return PREFIX + "cmsAdv_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/cmsAdv_update/{cmsAdvId}")
    public String cmsAdvUpdate(@PathVariable String cmsAdvId, Model model) {
        CmsAdv cmsAdv = cmsAdvService.selectById(cmsAdvId);
        model.addAttribute("item",cmsAdv);
        LogObjectHolder.me().set(cmsAdv);
        return PREFIX + "cmsAdv_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String advName,String status,String advPosition,String startTime,String endTime) {
        //return cmsAdvService.selectList(null);
    	Page<CmsAdv> page =new  PageFactory<CmsAdv>().defaultPage() ;
    	Map paramMap = new HashMap();
        paramMap.put("advName",advName) ;
        paramMap.put("status",status) ;
        paramMap.put("advPosition",advPosition) ;
        paramMap.put("startTime",startTime) ;
        paramMap.put("endTime",endTime) ;
      	List<CmsAdv> list = cmsAdvService.selectListAll(page,paramMap);
      	page.setRecords(list);
      	return super.packForBT(page);
    }
    
    /**
     * 获取列表
     */
    @RequestMapping(value = "/camplist")
    @ResponseBody
    public Object camplist() {
    	logger.info("=======begin to get camplist=========");
    	Object obj=campOmsProxy.getCampList();
		return obj;
    }
    
    /**
     * 为前端提供列表
     */
    @RequestMapping(value = "/advlist")
    @ResponseBody
    public Object advlist(HttpServletRequest request,@RequestBody String adv_position) {
    	logger.info("===========adv_position="+adv_position);
        return cmsAdvService.getAdvList(adv_position);
    }

    /**
     * 新增
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile advPic,CmsAdv cmsAdv) {
      //  cmsAdvService.insert(cmsAdv);
        if(AdvType.CAMPADV.equals(cmsAdv.getAdvType()) && "PUB".equals(cmsAdv.getStatus())) {
     	   if(!campOmsProxy.isOkCamp(cmsAdv.getAdvCampCode())) {
     		//  return  new ErrorTip(1002,"对不起，您选择的活动不可用，不能发布此广告！");
     		 return Result.failure("1002", "对不起，您选择的活动不可用，不能发布此广告！");
     	         }
           }else if(AdvType.FUNDDETAIL.equals(cmsAdv.getAdvType()) && "".equals(cmsAdv.getAdvFundCode())) {
        	  // return  new ErrorTip(1003,"基金类型的广告必须输入基金代码！"); 
        	   return Result.failure("1003","基金类型的广告必须输入基金代码！");
           }else if(AdvType.OTHERS.equals(cmsAdv.getAdvType()) && "".equals(cmsAdv.getAdvUrl())) {
        	  // return  new ErrorTip(1004,"广告url没有输入！"); 
        	   return Result.failure("1004","广告url没有输入！");
           }else if(AdvType.CAMPADV.equals(cmsAdv.getAdvType()) && ("".equals(cmsAdv.getAdvCampCode()) || null==cmsAdv.getAdvCampCode())) {
        	   //return  new ErrorTip(1005,"活动类型的广告必须选择活动！"); 
        	   return Result.failure("1005","活动类型的广告必须选择活动！");
           }
    	
    	try {
			if(advPic != null){
//				File file=multipartToFile(advPic);
//				String filename=mongoDBFileUtil.upload(file);
				String fileName = "CMS" + DateUtil.getAllTime() + IdGenerate.getNextId(); 
				mongoFileService.SaveFile("phfund_fs", advPic, fileName, advPic.getOriginalFilename());
				logger.info("advcontrollrt=================filename="+fileName);
				CmsFile cmsfile=new CmsFile();
				if(null!=advPic.getName() && !"".equals(advPic.getName())) {
					cmsfile.setFileName(advPic.getName());
				}
				if(null!=fileName && !"".equals(fileName)) {
					cmsfile.setServerFileName(fileName);
				}
				if(null!=advPic.getContentType()&& !"".equals(advPic.getContentType())) {
					cmsfile.setFileType(advPic.getContentType());
				}
				cmsfile.setFileCatalog("foradv");
                cmsAdv.setAdvUrl(StrUtil.htmlEncode(cmsAdv.getAdvUrl()));
                
                
				cmsAdvService.addadv(cmsAdv,cmsfile);
			}
		} catch (Exception e) {
            logger.error("广告信息新增失败",e);
		}
        return super.SUCCESS_TIP;
       // return super.ERROR;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String ids) {
        cmsAdvService.deleteadv(ids);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile advPic,CmsAdv cmsAdv) {
    	logger.info("advcontrollrt=================cmsAdv="+JSON.toJSONString(cmsAdv));
        if(AdvType.CAMPADV.equals(cmsAdv.getAdvType()) && "PUB".equals(cmsAdv.getStatus())) {
      	   if(!campOmsProxy.isOkCamp(cmsAdv.getAdvCampCode())) {
      		 return Result.failure("1006","对不起，您选择的活动不可用，不能发布此广告！"); 
      		  //return  new ErrorTip(1002,"对不起，您选择的活动不可用，不能发布此广告！");
      	         }
      }
    	try {
			if(null != advPic){
//				File file=multipartToFile(advPic);
//				String filename=mongoDBFileUtil.upload(file);
				String fileName = "CMS" + DateUtil.getAllTime() + IdGenerate.getNextId(); 
				mongoFileService.SaveFile("phfund_fs", advPic, fileName, advPic.getOriginalFilename());
				
				logger.info("advcontrollrt=================fileName="+fileName);
				CmsFile cmsfile=new CmsFile();
				if(null!=advPic.getName() && !"".equals(advPic.getName())) {
					cmsfile.setFileName(advPic.getName());
				}
				if(null!=fileName && !"".equals(fileName)) {
					cmsfile.setServerFileName(fileName);
				}
				cmsfile.setId(cmsAdv.getAdvId());
				cmsFileService.updateServerFileName(cmsfile);
			}else {
				logger.info("advcontrollrt=========no image need to upload========");
			}
		} catch (Exception e) {
            logger.error("广告信息更新失败",e);
		}
        cmsAdv.setAdvUrl(StrUtil.htmlEncode(cmsAdv.getAdvUrl()));
        cmsAdvService.UpdateById(cmsAdv);
        
      
        return super.SUCCESS_TIP;
    }
    

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{cmsAdvId}")
    @ResponseBody
    public Object detail(@PathVariable("cmsAdvId") String cmsAdvId) {
        return cmsAdvService.selectById(cmsAdvId);
    }
    
    
    /**
     * MultipartFile 转换成File
     * 
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */ 
    private File multipartToFile(MultipartFile multfile) throws IOException { 
    	 String fileName = multfile.getOriginalFilename();
         // 获取文件后缀
         String prefix=fileName.substring(fileName.lastIndexOf("."));
         // 用uuid作为文件名，防止生成的临时文件重复
         final File excelFile = File.createTempFile(IdGenerate.getNextId(), prefix);
         multfile.transferTo(excelFile);

        return excelFile; 
    }
    
}
