package com.model2.mvc.service.upload;

import com.model2.mvc.service.domain.Upload;
import com.model2.mvc.service.domain.Upload_Sub;

public interface UploadDao {
	
	public void addUpload(Upload upload, Upload_Sub upload_sub) throws Exception;
	
	public Upload getUploadFile(String fileName) throws Exception;
	
	public Upload updateUpload(Upload upload, Upload_Sub upload_sub) throws Exception;

}
