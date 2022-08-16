package com.model2.mvc.service.domain;

public class Upload_Sub {
	
	private String fileName1;
	private String fileName2;
	private String fileName3;
	private String fileName4;
	private String fileName5;
	
	public Upload_Sub() {
	}

	public Upload_Sub(String fileName1, String fileName2, String fileName3, String fileName4, String fileName5) {
		this.fileName1 = fileName1;
		this.fileName2 = fileName2;
		this.fileName3 = fileName3;
		this.fileName4 = fileName4;
		this.fileName5 = fileName5;
	}

	public String getFileName1() {
		return fileName1;
	}

	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}

	public String getFileName2() {
		return fileName2;
	}

	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}

	public String getFileName3() {
		return fileName3;
	}

	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}

	public String getFileName4() {
		return fileName4;
	}

	public void setFileName4(String fileName4) {
		this.fileName4 = fileName4;
	}

	public String getFileName5() {
		return fileName5;
	}

	public void setFileName5(String fileName5) {
		this.fileName5 = fileName5;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Upload_Sub [fileName1=");
		builder.append(fileName1);
		builder.append(", fileName2=");
		builder.append(fileName2);
		builder.append(", fileName3=");
		builder.append(fileName3);
		builder.append(", fileName4=");
		builder.append(fileName4);
		builder.append(", fileName5=");
		builder.append(fileName5);
		builder.append("]");
		return builder.toString();
	}

}
