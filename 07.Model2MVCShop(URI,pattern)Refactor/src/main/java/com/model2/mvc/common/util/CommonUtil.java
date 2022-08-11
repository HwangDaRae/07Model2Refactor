package com.model2.mvc.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	/// Field

	/// Constructor

	/// Method

	//문자 숫자 판단
	public static boolean parsingCheck(String org) {
		try {
			Double.parseDouble(org);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	// String이 null이거나 nullString일때 가져온 매개변수 String으로 리턴
	public static String null2str(String org, String converted) {
		if (org == null || org.trim().length() == 0)
			return converted;
		else
			return org.trim();
	}

	// String이 null이면 nulString으로 변환 ==> NullPointerException 방지
	public static String null2str(String org) {
		return CommonUtil.null2str(org, "");
	}

	// Object안에 데이터가 숫자있때 nullString으로 변환 ==> NumberFormatException 방지
	// Object안에 데이터가 String일때 nullString으로 변환?
	// Object안에 데이터가 null일때 nullString으로 변환 ==> NullPorinterException 방지
	public static String null2str(Object org) {
		if (org != null && org instanceof java.math.BigDecimal) {
			return CommonUtil.null2str((java.math.BigDecimal) org, "");
		} else {
			return CommonUtil.null2str((String) org, "");
		}
	}

	// 숫자라면 그대로 리턴 null이라면 가져온 매개변수 String리턴
	public static String null2str(java.math.BigDecimal org, String converted) {
		if (org == null)
			return converted;
		else
			return org.toString();
	}

	// null이나 nullString이라면 nullString으로 변환
	public static String null2str(java.math.BigDecimal org) {
		return CommonUtil.null2str(org, "");
	}

	// 제조일자
	public static String toDateStr(String dateStr) {
		if (dateStr == null)
			return "";
		else if (dateStr.length() != 8)
			return dateStr;
		else
			return dateStr.substring(0, 4) + "/" + dateStr.substring(4, 6) + "/" + dateStr.substring(6, 8);
	}

	// 가져온 date의 DateFormat을 바꿔준다
	public static String toDateStr(Timestamp date) {
		if (date == null)
			return "";
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format(new Date(date.getTime()));
		}
	}

	// 주민등록번호
	public static String toSsnStr(String ssnStr) {
		if (ssnStr == null)
			return "";
		else if (ssnStr.length() != 13)
			return ssnStr;
		else
			return ssnStr.substring(0, 6) + "-" + ssnStr.substring(6, 13);
	}

	//String 1234567 => 1,234,567
	public static String toAmountStr(String amountStr) {
		String returnValue = "";
		if (amountStr == null)
			return returnValue;
		else {
			int strLength = amountStr.length();

			if (strLength <= 3) {
				return amountStr;
			}else {
				String s1 = "";
				String s2 = "";
				
				for (int i = strLength - 1; i >= 0; i--) {
					s1 += amountStr.charAt(i);
				}

				for (int i = strLength - 1; i >= 0; i--) {
					s2 += s1.charAt(i);
					if (i % 3 == 0 && i != 0)
						s2 += ",";
				}
			
				return s2;
				
			}//end of if (strLength <= 3)
		}
	}

	//Object 1234567 => 1,234,567
	public static String toAmountStr(java.math.BigDecimal amount) {
		if (amount == null) {
			return "";
		} else {
			return toAmountStr(amount.toString());
		}
	}
}
