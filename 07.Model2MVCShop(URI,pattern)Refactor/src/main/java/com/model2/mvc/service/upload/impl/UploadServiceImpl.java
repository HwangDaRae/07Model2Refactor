package com.model2.mvc.service.upload.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.service.domain.Upload;
import com.model2.mvc.service.upload.UploadDao;
import com.model2.mvc.service.upload.UploadService;

@Service("uploadServiceImpl")
public class UploadServiceImpl implements UploadService {
	
	@Autowired
	@Qualifier("UploadDaoImpl")
	private UploadDao UploadDaoImpl;

	public UploadServiceImpl() {
		System.out.println(getClass() + " default Constructor");
	}

	@Override
	public void addUpload(Upload upload) throws Exception {
		System.out.println(getClass() + ".addUpload(Upload upload) start...");
		UploadDaoImpl.addUpload(upload);
	}

}
