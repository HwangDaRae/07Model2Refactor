package com.model2.mvc.service.upload.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Upload;
import com.model2.mvc.service.upload.UploadDao;

@Repository("UploadDaoImpl")
public class UploadDaoImpl implements UploadDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;

	public UploadDaoImpl() {
		System.out.println(getClass() + " default Constructor");
	}

	@Override
	public void addUpload(Upload upload) throws Exception {
		System.out.println(getClass() + ".addUpload(Upload upload) start...");
		sqlSession.insert("UploadMapper.addUpload", upload);
	}

}
