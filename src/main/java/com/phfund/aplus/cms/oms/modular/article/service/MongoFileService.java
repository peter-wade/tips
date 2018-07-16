package com.phfund.aplus.cms.oms.modular.article.service;

import java.util.List;

import com.phfund.aplus.cms.oms.common.persistence.model.MongoDBFile;


public interface MongoFileService {

	
	
	/**
	 * 保存文件
	 * 
	 * @param File
	 * @return
	 */
	MongoDBFile saveFile(MongoDBFile file);

	/**
	 * 删除文件
	 * 
	 * @param File
	 * @return
	 */
	void removeFile(String id);

	/**
	 * 根据id获取文件
	 * 
	 * @param File
	 * @return
	 */
	MongoDBFile getFileById(String id);

	/**
	 * 分页查询，按上传时间降序
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<MongoDBFile> listFilesByPage(int pageIndex, int pageSize);
}



