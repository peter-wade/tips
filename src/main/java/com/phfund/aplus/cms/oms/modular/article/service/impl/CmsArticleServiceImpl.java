package com.phfund.aplus.cms.oms.modular.article.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.phfund.aplus.framework.base.utils.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.phfund.aplus.camp.core.common.ParameterGet.AttendType;
import com.phfund.aplus.camp.core.common.ParameterGet.SendType;
import com.phfund.aplus.cms.oms.common.config.CmsAdvConfigProperties;
import com.phfund.aplus.cms.oms.common.persistence.dao.ArticleTagRelationMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsArticleMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsContentMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsFileMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.CmsTagsMapper;
import com.phfund.aplus.cms.oms.common.persistence.dao.UserWhitelistMapper;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticle;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsArticleTagRelation;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsContent;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsFile;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsReceive;
import com.phfund.aplus.cms.oms.common.persistence.model.CmsTags;
import com.phfund.aplus.cms.oms.modular.article.service.ICmsArticleService;
import com.phfund.aplus.framework.base.beans.BaseResp;
import com.phfund.aplus.framework.base.utils.IdGenerate;

/**
 * <p>
 * 文章信息 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-02-02
 */
@Service
public class CmsArticleServiceImpl implements ICmsArticleService {

    protected final static Logger logger = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

    @Autowired
    private CmsArticleMapper articleMapper;

    @Autowired
    private CmsContentMapper contentMapper;

    @Autowired
    private CmsTagsMapper tagMapper;

    @Autowired
    private CmsFileMapper cmsFileMapper;

    @Autowired
    private ArticleTagRelationMapper articleTagrelationMapper;

    @Autowired
    private CmsAdvConfigProperties cmsAdvConfigProperties;

    @Value("${articleImagePath}")
    private String articleImagePath;


    @Value("${detailurl}")
    private String detailurl;

    @Autowired
    private UserWhitelistMapper userWhitelistMapper;

    @Override
    @Transactional
    public int insert(CmsFile cmsFile, CmsReceive cmsReceive) {
        // TODO Auto-generated method stub
        //添加文章缩略图
        String id = IdGenerate.getNextId();
        String fileId = IdGenerate.getNextId();
        cmsFile.setId(id);
        cmsFile.setFileId(fileId);
        cmsFileMapper.addFile(cmsFile);

        //添加文章主体
        CmsArticle articleVo = new CmsArticle();
        articleVo.setArticleTitle(cmsReceive.getArticleTitle());
        articleVo.setAuthor(cmsReceive.getAuthor());
        articleVo.setArticleTitleShortname(cmsReceive.getArticleTitleShortname());
        articleVo.setArticleId(id);
        articleVo.setSummary(cmsReceive.getSummary());
        articleVo.setStartTime(cmsReceive.getStartTime());
        articleVo.setEndTime(cmsReceive.getEndTime());
        articleVo.setCreateBy(cmsReceive.getCreateBy());
        articleVo.setArticleType(cmsReceive.getArticleType());
        articleVo.setUpdateBy(cmsReceive.getCreateBy());
        articleVo.setArticleAttendType(cmsReceive.getArticleAttendType());
        articleVo.setShowStatus(cmsReceive.getShowStatus());
        articleVo.setArticleSort(cmsReceive.getArticleSort());
        articleVo.setArticleSourceType(cmsReceive.getArticleSourceType());
        articleVo.setArticleUrl(cmsReceive.getArticleUrl());
        articleVo.setArticleAcctTime(cmsReceive.getArticleAcctTime());
        articleVo.setFundCode(cmsReceive.getFundCode());

        articleMapper.addArticle(articleVo);
        //添加文章内容
        if (!StrUtil.isEmpty(cmsReceive.getContent())) {
            CmsContent contentVo = new CmsContent();
            String contentId = IdGenerate.getNextId();
            contentVo.setId(contentId);
            contentVo.setEditorTemplateCode(cmsReceive.getEditorTemplateCode());
            contentVo.setContent(cmsReceive.getContent());
            contentVo.setContentType("txt");
            contentVo.setArticleId(id);
            contentMapper.addContent(contentVo);
        }
        //判断tag是否存在 没有添加
        if (!StrUtil.isEmpty(cmsReceive.getTagsNames())) {
            String[] tagnames = cmsReceive.getTagsNames().split(",");
            for (String tagname : tagnames) {
                String tagId = IdGenerate.getNextId();

                CmsTags cmsTags = tagMapper.selectByName(tagname);
                if (null == cmsTags) {
                    CmsTags tagVo = new CmsTags();
                    tagVo.setTagId(tagId);
                    tagVo.setTagName(tagname);
                    tagVo.setTagCatalog("article");
                    tagMapper.addTag(tagVo);
                }
                CmsArticleTagRelation relationVo = new CmsArticleTagRelation();
                relationVo.setArticleId(id);
                if (null != cmsTags) {
                    //非新增tag 取原数据库中id
                    relationVo.setTagId(cmsTags.getTagId());
                } else {
                    //原数据库中没有这个tag
                    relationVo.setTagId(tagId);
                }
                relationVo.setId(IdGenerate.getNextId());
                relationVo.setTagName(tagname);
                articleTagrelationMapper.addOneRelation(relationVo);
            }
        }

        return 0;
    }

    @Override
    public List<CmsReceive> selectList(Page page, Map paramMap) {
        // TODO Auto-generated method stub
        List<CmsArticle> articleList = articleMapper.selectList(page, paramMap);
        List<CmsReceive> resultList = new ArrayList<>();
        if (null != articleList && articleList.size() > 0) {
            for (CmsArticle article : articleList) {
                //新建一个返回的vo
                CmsReceive receiveVo = new CmsReceive();
                //查询此id对应的内容和标签
                String id = article.getArticleId();
                receiveVo.setArticleId(id);
                receiveVo.setArticleTitle(article.getArticleTitle());
                receiveVo.setSummary(article.getSummary());
                receiveVo.setStartTime(article.getStartTime());
                receiveVo.setEndTime(article.getEndTime());
                receiveVo.setAuthor(article.getAuthor());
                receiveVo.setCreateBy(article.getCreateBy());
                receiveVo.setCreateDate(article.getCreateDate());
                receiveVo.setUpdateDate(article.getUpdateDate());
                receiveVo.setArticleType(article.getArticleType());
                receiveVo.setStatus(article.getStatus());
                receiveVo.setReadSize(article.getReadSize());
                receiveVo.setCollectionSize(article.getCollectionSize());
                receiveVo.setThumbsUpSize(article.getThumbsUpSize());
                receiveVo.setShareSize(article.getShareSize());
                receiveVo.setShowStatus(article.getShowStatus());
                receiveVo.setArticleSort(article.getArticleSort());
                receiveVo.setArticleSourceType(article.getArticleSourceType());
                receiveVo.setArticleUrl(article.getArticleUrl());
                receiveVo.setArticleAcctTime(article.getArticleAcctTime());
                receiveVo.setFundCode(article.getFundCode());                

                List<CmsArticleTagRelation> tagList = articleTagrelationMapper.selectByArticle(id);
                if (null != tagList && tagList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (CmsArticleTagRelation tag : tagList) {
                        sb.append(tag.getTagName()).append("|");
                    }
                    receiveVo.setTagsNames(sb.toString());
                } else {
                    receiveVo.setTagsNames("");
                }
                //receiveVo.setTagName(tagVo.getTagName());
                resultList.add(receiveVo);

            }

        }
        return resultList;
    }

    @Override
    @Transactional
    public int deleteById(String id) {
        // TODO Auto-generated method stub

        articleMapper.deleteById(id);
        return 3;
    }

    @Override
    @Transactional
    public int updateById(CmsReceive cmsReceive) {
        // TODO Auto-generated method stub
        String id = cmsReceive.getArticleId();
        logger.info("需要更新的资讯id={}", id);
        CmsArticle articleVo = new CmsArticle();
        articleVo.setArticleTitle(cmsReceive.getArticleTitle());
        articleVo.setAuthor(cmsReceive.getAuthor());
        articleVo.setArticleTitleShortname(cmsReceive.getArticleTitleShortname());
        articleVo.setArticleId(id);
        articleVo.setSummary(cmsReceive.getSummary());
        articleVo.setStartTime(cmsReceive.getStartTime());
        articleVo.setEndTime(cmsReceive.getEndTime());
        articleVo.setCreateBy(cmsReceive.getCreateBy());
        articleVo.setArticleType(cmsReceive.getArticleType());
        articleVo.setUpdateBy(cmsReceive.getCreateBy());
        articleVo.setArticleAttendType(cmsReceive.getArticleAttendType());
        articleVo.setShowStatus(cmsReceive.getShowStatus());
        articleVo.setArticleSort(cmsReceive.getArticleSort());
        articleVo.setArticleSourceType(cmsReceive.getArticleSourceType());
        articleVo.setArticleUrl(cmsReceive.getArticleUrl());
        articleVo.setArticleAcctTime(cmsReceive.getArticleAcctTime());
        articleVo.setFundCode(cmsReceive.getFundCode());    
        //添加文章内容
        if (!StringUtils.isEmpty(cmsReceive.getContent())) {
            CmsContent contentVo = new CmsContent();
            contentVo.setContent(cmsReceive.getContent());
            contentVo.setEditorTemplateCode(cmsReceive.getEditorTemplateCode());
            contentVo.setArticleId(id);
            contentMapper.updateById(contentVo);
        }
      
      
        //判断tag是否存在 没有添加
        if (!StringUtils.isEmpty(cmsReceive.getTagsNames())) {
        	  articleTagrelationMapper.deleteRelation(id);
            String[] tagnames = cmsReceive.getTagsNames().split(",");
            for (String tagname : tagnames) {
                String tagId = IdGenerate.getNextId();
                CmsTags cmsTags = tagMapper.selectByName(tagname);
                if (null == cmsTags) {
                    CmsTags tagVo = new CmsTags();
                    tagVo.setTagId(tagId);
                    tagVo.setTagName(tagname);
                    tagVo.setTagCatalog("article");
                    tagMapper.addTag(tagVo);
                }
                CmsArticleTagRelation relationVo = new CmsArticleTagRelation();
                relationVo.setArticleId(id);
                if (null != cmsTags) {
                    //非新增tag 取原数据库中id
                    relationVo.setTagId(cmsTags.getTagId());
                } else {
                    //原数据库中没有这个tag
                    relationVo.setTagId(tagId);
                }
                relationVo.setId(IdGenerate.getNextId());
                relationVo.setTagName(tagname);
                articleTagrelationMapper.addOneRelation(relationVo);
            }
        }
        articleMapper.updateById(articleVo);

        return 0;
    }

    /**
     * 编辑页面调用即详情页面
     */
    public CmsReceive selectById(String id) {
        logger.info("CmsArticleServiceImpl=====================id=" + id);
        CmsReceive receiveVo = new CmsReceive();
        CmsArticle article = articleMapper.selectById(id);

        receiveVo.setArticleId(id);
        receiveVo.setArticleTitle(article.getArticleTitle());
        receiveVo.setSummary(article.getSummary());
        receiveVo.setStartTime(article.getStartTime());
        receiveVo.setEndTime(article.getEndTime());
        receiveVo.setAuthor(article.getAuthor());
        receiveVo.setCreateBy(article.getCreateBy());
        receiveVo.setCreateDate(article.getCreateDate());
        receiveVo.setUpdateDate(article.getUpdateDate());
        receiveVo.setArticleType(article.getArticleType());
        receiveVo.setStatus(article.getStatus());
        receiveVo.setArticleTitleShortname(article.getArticleTitleShortname());
        receiveVo.setShowStatus(article.getShowStatus());
        receiveVo.setArticleSort(article.getArticleSort());
        receiveVo.setArticleSourceType(article.getArticleSourceType());
        receiveVo.setArticleUrl(article.getArticleUrl());
        receiveVo.setArticleAttendType(article.getArticleAttendType());
        receiveVo.setArticleAcctTime(article.getArticleAcctTime());
        receiveVo.setFundCode(article.getFundCode());   
        //文章缩略图
        String Serviceurl = cmsAdvConfigProperties.getPicUrl();
        logger.info("CmsAdvServiceImpl=========Serviceurl={}" + Serviceurl);
        String serviceFileName = article.getArticleImage();
        logger.info("CmsAdvServiceImpl=========serviceFileName={}" + serviceFileName);
        String imgurl = "<img src='" + Serviceurl + "?name=" + serviceFileName + "' style='width:auto;height:160px;' />";
        logger.info("CmsAdvServiceImpl=========imgurl={}" + imgurl);
        receiveVo.setArticlePic(imgurl);
        CmsContent contentVo = contentMapper.selectByArticleId(id);
        receiveVo.setContent(contentVo.getContent());
        receiveVo.setEditorTemplateCode(contentVo.getEditorTemplateCode());
        List<CmsArticleTagRelation> taglist = articleTagrelationMapper.selectByArticle(id);
        StringBuilder sb = new StringBuilder();
        if (null != taglist && taglist.size() > 0) {
            for (CmsArticleTagRelation tag : taglist) {
                sb.append(tag.getTagName()).append(",");
            }
            receiveVo.setTagsNames(sb.toString().substring(0, sb.toString().length() - 1));
        } else {
            receiveVo.setTagsNames(sb.toString());
        }
        return receiveVo;
    }

    @Override
    public int pub(String articleId) {
        // TODO Auto-generated method stub
        return articleMapper.pub(articleId);
    }

    @Override
    public int offline(String articleId) {
        // TODO Auto-generated method stub
        return articleMapper.offline(articleId);
    }

    @Override
    public List<CmsArticle> queryRecommendCrticleList(Map<String, Object> paramMap) {

        List<CmsArticle> returnList = new ArrayList<CmsArticle>();

        //查询用户标签信息
        CmsArticle cmsReceive = articleMapper.queryCrticleTagId(paramMap);

        List<String> tagIdList = new ArrayList<String>();
        if (null == cmsReceive) {
            return null;
        } else {
            String tagIds = cmsReceive.getTagId();
            String[] tagId = tagIds.split(",");
            for (int i = 0; i < tagId.length; i++) {
                tagIdList.add(tagId[i]);
            }
        }

        List<String> tempList = new ArrayList<>(tagIdList); //深度赋值
        //查询推荐的标签信息
        List<CmsArticle> list = articleMapper.queryRecommendCrticleList(paramMap);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            int size = tagIdList.size(); //用户标签数量
            int forSize = size;          //循环次数大小，用户标签数
            if (size > 0) {  //用户标签数<=0 没有意思
                for (int i = 0; i < forSize; i++) {
                    for (Iterator<CmsArticle> it = list.iterator(); it.hasNext(); ) {

                        CmsArticle receive = it.next();
                        String retagIds = receive.getTagId();
                        String[] retagId = retagIds.split(",");
                        List<String> retagIdList = new ArrayList<String>();
                        for (int j = 0; j < retagId.length; j++) {
                            retagIdList.add(retagId[j]);
                        }

                        tagIdList.retainAll(retagIdList);//两个集合的并集
                        if (tagIdList.size() == size) { //并集匹配
                            tagIdList = new ArrayList<String>(tempList);//深度赋值重新初始化用户标List
                            returnList.add(receive);  //添加到输出的list
                            if (returnList.size() == 3) { //直接输出，这里的3个推荐信息，后面可能会配置化
                                returnList = this.articleList(returnList); //遍历处理图片信息
                                return returnList;
                            }
                            it.remove(); //删除已添加到输出的数据
                        } else {
                            tagIdList = new ArrayList<String>(tempList);//深度赋值重新初始化用户标List
                        }
                    }
                    size--; //减少并集匹配数量
                    if (size == 0) {
                        break;
                    }
                }
            }
        }
        returnList = this.articleList(returnList);
        return returnList;
    }


    private List<CmsArticle> articleList(List<CmsArticle> list) {
        for (CmsArticle article : list) {
            if (StringUtils.isBlank(article.getImageUrl())) {
                article.setImageUrl("");
            } else {
                article.setImageUrl(articleImagePath + "name=" + article.getImageUrl());
            }
        }
        return list;
    }

    @Override
    public int getOnTopSize() {
        // TODO Auto-generated method stub
        return articleMapper.getOnTopSize();
    }
	@Override
	public BaseResp sendMsg(String articleId) {
		// TODO Auto-generated method stub
		BaseResp resp=new BaseResp();
		CmsArticle article=articleMapper.selectById(articleId);
		Map<String, Object> sendMap=new HashMap<String,Object>();
		Map<String, Object> inputMap=new HashMap<String,Object>();
		inputMap.put("transCode", "sendMsg");
		inputMap.put("title", article.getArticleTitle());
		//在appweb端进行区分，2代表活动中心资讯专区
		inputMap.put("subtype", "2");
		inputMap.put("url",detailurl+articleId+".html");
		inputMap.put("synops", article.getSummary());
		List<String> uList=new ArrayList<String>();
		if(AttendType.PRIVATE_ARTICLE.equals(article.getArticleAttendType())) {
			//定向资讯的消息推送
			inputMap.put("type", SendType.PRIVATE_SEND);
			uList=userWhitelistMapper.queryArticleWhitelist(articleId);
			resp.setRespCode("0001");
			resp.setBizSeqNo(String.valueOf(uList.size()));
		}else if(AttendType.PUBLIC_ARTICLE.equals(article.getArticleAttendType())){
			//公共资讯的消息推送
			inputMap.put("type", SendType.PUBLIC_SEND);
			resp.setRespCode("0002");
		}
		inputMap.put("uList", uList);
		sendMap.put("input", inputMap);

		return resp;
	}

}
