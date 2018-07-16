package com.phfund.aplus.cms.oms.modular.article.service;

import com.mongodb.gridfs.GridFSDBFile;

public interface MongodbService {
	
	
	GridFSDBFile findById(String id);

}
