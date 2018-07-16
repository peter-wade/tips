package com.phfund.aplus.cms.oms.common.persistence.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.phfund.aplus.cms.oms.common.persistence.model.MongoDBFile;


public interface MongoFileRepository extends MongoRepository<MongoDBFile, String> {
	
	
	

}
