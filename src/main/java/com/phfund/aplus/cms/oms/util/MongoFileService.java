package com.phfund.aplus.cms.oms.util;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.phfund.aplus.framework.base.utils.IdGenerate;

@Service
public class MongoFileService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 存储文件
	 * @param collectionName 集合名
	 * @param file 文件
	 * @param serverFileName 服务文件名称
	 * @param filename 文件名称
	 */
	public void SaveFile(String collectionName, MultipartFile file, String serverFileName, String filename) {
		try {
			DB db = mongoTemplate.getDb();
			// 存储fs的根节点
			GridFS gridFS = new GridFS(db, collectionName);
			GridFSInputFile gfs = gridFS.createFile(this.multipartToFile(file));
			gfs.put("aliases", "phfund");
			gfs.put("filename", serverFileName);
			gfs.put("contentType", filename.substring(filename.lastIndexOf(".")));
			gfs.save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("存储文件时发生错误！！！");
		}
	}

	// 取出文件
	public GridFSDBFile retrieveFileOne(String collectionName, String filename) {
		try {
			DB db = mongoTemplate.getDb();
			// 获取fs的根节点
			GridFS gridFS = new GridFS(db, collectionName);
			GridFSDBFile dbfile = gridFS.findOne(filename);
			if (dbfile != null) {
				return dbfile;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/** 
     * MultipartFile 转换成File 
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
