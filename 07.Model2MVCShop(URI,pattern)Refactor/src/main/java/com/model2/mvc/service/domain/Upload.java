package com.model2.mvc.service.domain;

import java.util.Arrays;

public class Upload {
	
	private String fileNo;
	private int fileCount;
	private String[] fileName;
	
	public Upload() {
	}

	public Upload(String fileNo, int fileCount, String[] fileName) {
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

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
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
		builder.append(Arrays.toString(fileName));
		builder.append("]");
		return builder.toString();
	}

}
