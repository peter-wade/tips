package com.phfund.aplus.cms.oms.modular.article.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.camp.core.common.ParameterGet.AttendType;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsArticleMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsEditorTemplateMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticle;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsEditorTemplate;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsReceive;
import com.phfund.aplus.cms.oms.modular.article.manager.SendMsgManager;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsArticleService;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsFileService;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsTagsService;
import com.phfund.aplus.cms.oms.modular.article.service.UserWhitelistService;
import com.phfund.aplus.cms.oms.util.MongoFileService;
import com.phfund.aplus.framework.base.beans.BaseResp;
import com.phfund.aplus.framework.base.beans.Result;
import com.phfund.aplus.framework.base.utils.IdGenerate;
import com.phfund.aplus.framework.base.utils.StrUtil;
import com.phfund.aplus.oms.boot.common.constant.factory.PageFactory;
import com.phfund.aplus.oms.boot.core.log.LogObjectHolder;
import com.phfund.aplus.oms.core.base.controller.BaseController;
import com.phfund.aplus.oms.core.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.beetl.core.BeetlKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 控制器
 *
 * @linan
 */
@Controller
@RequestMapping({ "/S100003", "/cmsArticle" })
public class CmsArticleController extends BaseController {

	protected final static Logger logger = LoggerFactory.getLogger(CmsArticleController.class);

	private String PREFIX = "/system/cmsArticle/";

	@Autowired
	private ICmsArticleService cmsArticleService;
	
	@Autowired
	private UserWhitelistService iUserWhitelistService;

	@Autowired
	private MongoFileService mongoFileService;

	@Autowired
	private ICmsTagsService cmsTagsService;

	@Value("${htmlfilepath}")
	private  String htmlfilepath;

	@Autowired
	private CmsArticleMapper articleMapper;

	@Autowired
	private CmsEditorTemplateMapper cmsEditorTemplateMapper ;
	
	@Value("${visiturl}")
	private  String visiturl;
	
	 @Autowired
	 private ICmsFileService cmsFileService;
	 
	 private  MimetypesFileTypeMap mtftp;
	 
	 @Autowired
	 private SendMsgManager sendMsgManager;

	/**
	 * 跳转到首页
	 */
	@RequestMapping("")
	@ResponseBody
	public String index() {
		return this.getCurrentAddressInfo() + "/S100003/index";
	}

	/**
	 * 跳转到aplus首页
	 */
	@RequestMapping("/index")
	public String index2() {
		return PREFIX + "cmsArticle.html";
	}

	/**
	 * 跳转到添加
	 */
	@RequestMapping("/cmsArticle_add")
	public String cmsArticleAdd() {
		return PREFIX + "cmsArticle_add.html";
	}

	/* *//**
			 * 跳转到修改
			 */
	@RequestMapping("/cmsArticle_update/{cmsArticleId}")
	public String cmsArticleUpdate(@PathVariable String cmsArticleId, Model model) {
		CmsReceive cmsReceive = cmsArticleService.selectById(cmsArticleId);
		model.addAttribute("item", cmsReceive);
		LogObjectHolder.me().set(cmsReceive);
		return PREFIX + "cmsArticle_edit.html";
	}
	
	/* *//**
	 * 跳转到修改
	 */
	@RequestMapping("/cmsArticle_datail/{cmsArticleId}")
	public String cmsArticleDetail(@PathVariable String cmsArticleId, Model model) {
			CmsReceive cmsReceive = cmsArticleService.selectById(cmsArticleId);
			model.addAttribute("item", cmsReceive);
			LogObjectHolder.me().set(cmsReceive);
			return PREFIX + "cmsArticle_detail.html";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String articleTitle, String showStatus, String status, String startTime, String endTime,
			String articleType) {
		System.out.println("===========" + articleTitle);
		// return cmsAdvService.selectList(null);
		Page<CmsReceive> page = new PageFactory<CmsReceive>().defaultPage();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("articleTitle", articleTitle);
		paramMap.put("showStatus", showStatus);
		paramMap.put("status", status);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("articleType", articleType);
		List<CmsReceive> list = cmsArticleService.selectList(page, paramMap);
		page.setRecords(list);
		return super.packForBT(page);
	}

	  public  boolean isImage(MultipartFile file){
	        String mimetype= mtftp.getContentType((File) file);
	        String type = mimetype.split("/")[0];
	        return type.equals("image");
	    }
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile articleImage, CmsReceive cmsReceive) {
		logger.info("=============添加资讯时添加的参数=" + JSON.toJSONString(cmsReceive));
		logger.info("=============已有的置顶咨询数={}",cmsArticleService.getOnTopSize());
		if(1==cmsReceive.getArticleSort() && cmsArticleService.getOnTopSize()>0) {
			return Result.failure("1002", "对不起,置顶资讯最多为1条！"); 
         }
		if(!checkImageFile(articleImage)) {
			return Result.failure("1002", "对不起,文章缩略图只能上传图片！"); 
		}
		if("Y".equals(cmsReceive.getArticleSourceType()) && "".equals(cmsReceive.getArticleUrl())) {
			return Result.failure("1003", "对不起,请填写外部资讯地址！"); 
		}
		if("N".equals(cmsReceive.getArticleSourceType()) && (null==cmsReceive.getContent() ||"".equals(cmsReceive.getContent()))) {
			return Result.failure("1004", "对不起,请填写资讯内容！"); 
		}
		try {
			if (articleImage != null) {
				String fileName = "CMS" + DateUtil.getAllTime() + IdGenerate.getNextId();
				mongoFileService.SaveFile("phfund_fs", articleImage, fileName, articleImage.getOriginalFilename());
				logger.info("articlecontrollrt=================filename=" + fileName);
				CmsFile cmsfile = new CmsFile();
				if (null != articleImage.getName() && !"".equals(articleImage.getName())) {
					cmsfile.setFileName(articleImage.getName());
				}
				if (null != fileName && !"".equals(fileName)) {
					cmsfile.setServerFileName(fileName);
				}
				if (null != articleImage.getContentType() && !"".equals(articleImage.getContentType())) {
					cmsfile.setFileType(articleImage.getContentType());
				}
				cmsfile.setFileCatalog("forarticle");
				cmsArticleService.insert(cmsfile, cmsReceive);
				
			}
		} catch (Exception e) {
			logger.error("资讯新增失败", e);
		}
		return super.SUCCESS_TIP;
	}

	public static void instreamputstreamtofile(String ins, File file) throws IOException {
		OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
		int bytesRead = 0;
		oStreamWriter.write(ins);
		oStreamWriter.close();
	}


	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam String ids) {
		cmsArticleService.deleteById(ids);
		return SUCCESS_TIP;
	}

	/**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response,
    			@RequestParam(required = false) MultipartFile articleImage,CmsReceive cmsReceive) {
        	logger.info("articlecontroller================cmsReceive="+JSON.toJSONString(cmsReceive));
        	if("Y".equals(cmsReceive.getArticleSourceType()) && "".equals(cmsReceive.getArticleUrl())) {
    			return Result.failure("1003", "对不起,请填写外部资讯地址！"); 
    		}
        	if("N".equals(cmsReceive.getArticleSourceType()) && (null==cmsReceive.getContent() || "".equals(cmsReceive.getContent()))) {
    			return Result.failure("1003", "对不起,请填写资讯内容！"); 
    		}
        	try {
    			if(null != articleImage){
    				String fileName = "CMS" + DateUtil.getAllTime() + IdGenerate.getNextId(); 
    				mongoFileService.SaveFile("phfund_fs", articleImage, fileName, articleImage.getOriginalFilename());
    				logger.info("articlecontroller=================fileName="+fileName);
    				CmsFile cmsfile=new CmsFile();
    				if(null!=articleImage.getName() && !"".equals(articleImage.getName())) {
    					cmsfile.setFileName(articleImage.getName());
    				}
    				if(null!=fileName && !"".equals(fileName)) {
    					cmsfile.setServerFileName(fileName);
    				}
    				cmsfile.setId(cmsReceive.getArticleId());
    				cmsFileService.updateServerFileName(cmsfile);
    			}else {
    				logger.info("articlecontroller=========no image need to upload========");
    			}
    		} catch (Exception e) {
                logger.error("资讯更新失败",e);
    		}
        	 cmsArticleService.updateById(cmsReceive);
        return super.SUCCESS_TIP;
    }

	/**
	 * 查询tag列表
	 */
	@RequestMapping(value = "/taglist")
	@ResponseBody
	public Object gettaglist() {
		return cmsTagsService.getTagList("article");
	}

	/**
	 * 资讯发布
	 */
	@RequestMapping(value = "/pub")
	@ResponseBody
	public Object pub(@RequestParam String ids) {
		if ("".equals(ids) || null == ids) {
			return Result.failure("1003", "请选择要发布的资讯！");
		}
		CmsReceive articleVo = cmsArticleService.selectById(ids);
		if (null == articleVo) {
			return Result.failure("1003", "此资讯不存在！");
		}
		try {
			CmsReceive cmsReceiveVo=cmsArticleService.selectById(ids);
			if(AttendType.PRIVATE_ARTICLE.equals(cmsReceiveVo.getArticleAttendType())) {
				List<String> uList=iUserWhitelistService.queryArticleWhitelist(ids);
				   if(null==uList || uList.size()==0) {
					   return Result.failure("1003", "此活动为定向活动，你还未导入定向名单！");
				   }
			}
			SimpleDateFormat stf=new SimpleDateFormat("yyyy-MM-dd");
			String pubTime=stf.format(cmsReceiveVo.getCreateDate());
			Map<String, Object> mappara = new HashMap<String, Object>();
			mappara.put("articleTitle", cmsReceiveVo.getArticleTitle());
			mappara.put("pubTime", pubTime);
			mappara.put("readSize", cmsReceiveVo.getReadSize());
			mappara.put("articleId",cmsReceiveVo.getArticleId()) ; 
			mappara.put("summary",cmsReceiveVo.getSummary()) ;
			mappara.put("url", visiturl);
			if("N".equals(cmsReceiveVo.getArticleSourceType())) {
				mappara.put("content", cmsReceiveVo.getContent());
				//查询模板
				if(!StrUtil.isEmpty(cmsReceiveVo.getEditorTemplateCode())) {
					CmsEditorTemplate cmsEditorTemplate = cmsEditorTemplateMapper.selectById(cmsReceiveVo.getEditorTemplateCode());
					if(cmsEditorTemplate != null){
						mappara.put("templateCssPath",cmsEditorTemplate.getTemplatePath()) ;
					}
				}
				//不能用于jar包取东西 本地取东西
				//File file = ResourceUtils.getFile("classpath:templates/index.html");
				String pathname=this.getClass().getResource("/").getPath()+File.separator+"html"+File.separator+"index.html";
				File file=new File(pathname);
				BufferedReader in = new BufferedReader(new FileReader(file));
				StringBuilder str = new StringBuilder();
				String string;
				while ((string = in.readLine()) != null) {
					str.append(new String(string.getBytes(),"UTF-8"));
				}
				in.close();
				String xml = BeetlKit.render(str.toString(), mappara);
				String filepath = htmlfilepath+cmsReceiveVo.getArticleId()+".html";
				File file2 = new File(filepath);
				instreamputstreamtofile(xml, file2);
			}else {
				mappara.put("articleUrl", cmsReceiveVo.getArticleUrl());
				mappara.put("articleId", cmsReceiveVo.getArticleId());
//				File file = ResourceUtils.getFile("classpath:static/templates/out.html");
				String pathname=this.getClass().getResource("/").getPath()+File.separator+"html"+File.separator+"out.html";
				File file=new File(pathname);
				BufferedReader in = new BufferedReader(new FileReader(file));
				StringBuilder str = new StringBuilder();
				String string;
				while ((string = in.readLine()) != null) {
					str.append(string);
				}
				in.close();
				String xml = BeetlKit.render(str.toString(), mappara);
				String filepath = htmlfilepath+cmsReceiveVo.getArticleId()+".html";
				File file2 = new File(filepath);
				instreamputstreamtofile(xml, file2);
			}
		} catch (Exception e) {
			logger.error("生成静态页面发生异常！{}",e);
			return Result.failure("1003", "此资讯发布失败，生成页面异常！");
		}
		if(!"5".equals(articleVo.getStatus())) {
	 	    cmsArticleService.pub(ids);
		}
		return super.SUCCESS_TIP;
	}

	/**
	 * 资讯下线
	 */
	@RequestMapping(value = "/offline")
	@ResponseBody
	public Object offline(@RequestParam String ids) {

		if ("".equals(ids) || null == ids) {
			return Result.failure("1003", "请选择要下线的资讯！");
		} else {
			CmsReceive articleVo = cmsArticleService.selectById(ids);
			if (null == articleVo) {
				return Result.failure("1003", "此资讯不存在！");
			} else {
				if ("6".equals(articleVo.getStatus())) {
					return Result.failure("1003", "此资讯已经下线，无需重复操作！");
				}
			}
		}
		cmsArticleService.offline(ids);
		return super.SUCCESS_TIP;
	}

	@RequestMapping(value = "/queryArticleRead")
	@ResponseBody
	public Object queryArticleRead(@RequestParam String articleId) {
		CmsArticle article = new CmsArticle();
		if (StringUtils.isBlank(articleId)) {
			return Result.failure("1003", "请选择要下线的资讯！");
		} else {
			article = articleMapper.selectById(articleId);
			if (null == article) {
				return Result.failure("1003", "此资讯不存在！");
			} else {
				if ("6".equals(article.getStatus())) {
					return Result.failure("1003", "此资讯已经下线，无需重复操作！");
				}
			}
		}

		return article;

	}

	@RequestMapping(value = "/queryRecommendCrticle")
	@ResponseBody
	public Object queryRecommendCrticle(@RequestParam String articleId ,String phoneNo,String articleType) {
		 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("articleId", articleId);
		paramMap.put("status", "5");
		
		paramMap.put("articleType", articleType);
		paramMap.put("showStatus", "Y");
		paramMap.put("phoneNo", phoneNo);
		List<CmsArticle> list = cmsArticleService.queryRecommendCrticleList(paramMap);
		return list;
		
	}

	@PostMapping(value = "/preview")
	public String preview(String articleTitleShortname,String content,String editorTemplateCode,ModelMap modelMap) {
		modelMap.put("url","") ;
		modelMap.put("articleId","") ;
		modelMap.put("articleTitle",articleTitleShortname) ;
		modelMap.put("summary","") ;
		modelMap.put("content",content) ;
		/**
		 * 根据模板，查询模板所使用的样式
		 */
		if(StrUtil.isNotEmpty(editorTemplateCode)) {
			CmsEditorTemplate template = cmsEditorTemplateMapper.selectById(editorTemplateCode);
			if(template!=null) {
				modelMap.put("templateCssPath", template.getTemplatePath());
			}
		}
		return PREFIX + "preview.html";
	}

	
	
	private boolean checkImageFile(MultipartFile uploadImg) {


        String fileName = uploadImg.getOriginalFilename();
        String extUpp =(fileName.substring(fileName.lastIndexOf(".") + 1)).toUpperCase();


        //根据扩展名判断是否为要求的图片

if (!extUpp.matches("^[(JPG)|(PNG)|(GIF)]+$")) {
            return false;
        }
        return true;
    }
	
	@RequestMapping(value = "/sendMsg")
	@ResponseBody
	public Result sendMsg(@RequestParam String ids) {
		if (StringUtils.isEmpty(ids)) {
			return Result.failure("1005", "请选择要发送推送消息的资讯！");
		}
		CmsReceive articleVo = cmsArticleService.selectById(ids);
		if (null == articleVo) {
			return Result.failure("1003", "此资讯不存在！");
		} else {
			if (!"5".equals(articleVo.getStatus())) {
				return Result.failure("1003", "请资讯发布成功后再来推送消息！");
			} 
			if(!isEffectiveDate(new Date(),articleVo.getStartTime(),articleVo.getEndTime())) {
				return Result.failure("1003", "当前时间不在资讯的有效时间内，不能推送消息！");
			}
		}
	    BaseResp resp= sendMsgManager.sendMsg(ids);
	     if("0001".equals(resp.getRespCode())) {
	    	 return Result.success("已发送推送消息"+resp.getBizSeqNo()+"条!");
	     }else if("0002".equals(resp.getRespCode())){
	    	 return Result.success("群播消息已发出！");
	     }else {
	    	 return Result.failure("1005", "发送失败！"); 
	     }
	}
	
	/**
	* 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致	* @param nowTime 当前时间
	* @param startTime 开始时间
	* @param endTime 结束时间
	*/
	public  boolean isEffectiveDate(Date nowTime, String startTime, String endTime) {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startdate;
		try {
			startdate = sdf.parse(startTime);
			Date enddate=sdf.parse(endTime);
			
			if (nowTime.getTime() == startdate.getTime()
			|| nowTime.getTime() == enddate.getTime()) {
			return true;
			}

			Calendar date = Calendar.getInstance();
			date.setTime(nowTime);

			Calendar begin = Calendar.getInstance();
			begin.setTime(startdate);

			Calendar end = Calendar.getInstance();
			end.setTime(enddate);

			if (date.after(begin) && date.before(end)) {
			return true;
			} else {
			return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("时间转换错误");
		}
	return false;
	}
}
