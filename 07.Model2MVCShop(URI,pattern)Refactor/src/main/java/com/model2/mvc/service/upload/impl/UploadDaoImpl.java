package com.model2.mvc.service.upload.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.service.domain.Upload;
import com.model2.mvc.service.domain.Upload_Sub;
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
	public void addUpload(Upload upload, Upload_Sub upload_sub) throws Exception {
		System.out.println(getClass() + ".addUpload(Upload upload, Upload_Sub upload_sub) start...");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("upload", upload);
		map.put("upload_sub", upload_sub);
		sqlSession.insert("UploadMapper.addUpload", map);
	}

	@Override
	public Upload getUploadFile(String fileName) throws Exception {
		System.out.println(getClass() + ".getUpload(String fileName) start...");
		return sqlSession.selectOne("UploadMapper.getUploadFile", fileName);
	}

	@Override
	public Upload updateUpload(Upload upload, Upload_Sub upload_sub) throws Exception {
		System.out.println(getClass() + ".updateUpload(Upload upload, Upload_Sub upload_sub) start...");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("upload", upload);
		map.put("upload_sub", upload_sub);
		int i = sqlSession.update("UploadMapper.updateUpload", map);
		if(i==1) {
			System.out.println("업로드 파일 수정 성공");
			return sqlSession.selectOne("UploadMapper.getUploadFile", upload.getFileNo());
		}else {
			System.out.println("업로드 파일 수정 실패");
			return null;
		}
	}

}
