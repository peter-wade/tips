package com.phfund.aplus.cms.oms.modular.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mongodb.gridfs.GridFSDBFile;
import com.phfund.aplus.cms.oms.common.persistence.dao.MongoFileRepository;
import com.phfund.aplus.cms.oms.common.persistence.model.MongoDBFile;
import com.phfund.aplus.cms.oms.modular.article.service.MongoFileService;


@Service
public class MongoFileServiceImpl implements MongoFileService {

	@Autowired
	public MongoFileRepository mongoFileRepository;

	@Override
	public MongoDBFile saveFile(MongoDBFile file) {
		// TODO Auto-generated method stub
		return mongoFileRepository.save(file);
	}

	@Override
	public void removeFile(String id) {
		// TODO Auto-generated method stub
		mongoFileRepository.delete(id);
	}

	@Override
	public MongoDBFile getFileById(String id) {
		// TODO Auto-generated method stub
		return mongoFileRepository.findOne(id);
	}

	@Override
	public List<MongoDBFile> listFilesByPage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		Page<MongoDBFile> page = null;
		List<MongoDBFile> list = null;

		Sort sort = new Sort(Direction.DESC, "uploadDate");
		Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

		page = mongoFileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}

	


}
