package com.model2.mvc.service.domain;

import java.util.List;

public class Upload {
	
	private String fileNo;
	private int fileCount;
	private List<String> fileName;
	
	public Upload() {
	}

	public Upload(String fileNo, int fileCount, List<String> fileName) {
		super();
		this.fileNo = fileNo;
		this.fileCount = fileCount;
		this.fileName = fileName;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public List<String> getFileName() {
		return fileName;
	}

	public void setFileName(List<String> fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Upload [fileNo=");
		builder.append(fileNo);
		builder.append(", fileCount=");
		builder.append(fileCount);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append("]");
		return builder.toString();
	}

}
