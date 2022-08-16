<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>회원정보수정</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="../javascript/calendar.js">
</script>

<script type="text/javascript">

function fncUpdateProduct(){
	//Form 유효성 검증
 	var name = document.detailForm.prodName.value;
	var detail = document.detailForm.prodDetail.value;
	var manuDate = document.detailForm.manuDate.value;
	var amount = document.detailForm.amount.value;
	var price = document.detailForm.price.value;
	//alert(name);
	//alert(detail);
	//alert(manuDate);
	//alert(amount);
	//alert(price);

	if(name == null || name.length<1){
		alert("상품명은 반드시 입력하여야 합니다.");
		return;
	}
	if(detail == null || detail.length<1){
		alert("상품상세정보는 반드시 입력하여야 합니다.");
		return;
	}
	if(manuDate == null || manuDate.length<1){
		alert("제조일자는 반드시 입력하셔야 합니다.");
		return;
	}
	if(amount == null || amount.length<1){
		alert("수량은 반드시 입력하셔야 합니다.");
		return;
	}
	if(price == null || price.length<1){
		alert("가격은 반드시 입력하셔야 합니다.");
		return;
	}
		
	document.detailForm.action='/product/updateProduct';
	document.detailForm.submit();
}

</script>

<script type="text/javascript">
var cnt = ${ count };
function fn_addFile(){
	alert(cnt);
	if(cnt == 5){
		document.getElementById('limit').innerText = '5개까지 업로드 가능합니다.';
	}else{
		cnt++;
		var input1 = document.createElement('input');
		var br1 = document.createElement('br');
		input1.setAttribute("type", "file");
		input1.setAttribute("name", "uploadfile");
		input1.setAttribute("value", "value"+cnt);
		input1.setAttribute("class", "ct_input_g");
		input1.setAttribute("style", "width: 200px; height: 19px");
		input1.setAttribute("maxLength", "13");
		document.getElementById('createInput').appendChild(br1);
		document.getElementById('createInput').appendChild(input1);
	}
}

function fn_removeFile(){
	if(cnt == 0){
		document.getElementById('limit').innerText = ' ';
	}else{
		cnt--;
		document.getElementById('limit').innerText = ' ';
		document.getElementById('createInput').lastChild.remove();
		document.getElementById('createInput').lastChild.remove();
	}
}
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post" enctype="multipart/form-data">

<input type="hidden" name="prodNo" value="${ productVO.prodNo }"/>
<input type="hidden" name="fileName" value="${ productVO.fileName }"/>

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품수정</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">
						<input 	type="text" name="prodName" disabled="disabled" class="ct_input_g" 
										style="width: 100px; height: 19px" maxLength="20" value="${ productVO.prodName }">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품상세정보 <img	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="prodDetail" value="${ productVO.prodDetail }" class="ct_input_g" 
						style="width: 100px; height: 19px" maxLength="10"	minLength="6">
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			제조일자 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" readonly="readonly" name="manuDate" value="${ productVO.manuDate }" 
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="10" minLength="6">&nbsp;
						<img 	src="../images/ct_icon_date.gif" width="15" height="15" 
									onclick="show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value)" />
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			수량 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="amount" value="${ productVO.amount }"
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="50"/>&nbsp;개
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			가격 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="price" value="${ productVO.price }"
						class="ct_input_g" style="width: 100px; height: 19px" maxLength="50"/>&nbsp;원
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품이미지</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01" id="createInput">
			<input type="button" value="파일추가" onclick="fn_addFile()">
			<input type="button" value="파일삭제" onclick="fn_removeFile()">
			<b id="limit"></b>
		</td>
			<!-- 테이블 시작 -->
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">
					<b id="fileName1">
						<c:if test="${ uploadVO.fileName[0].fileName1 != null }">
							<img src="/images/uploadFiles/${ uploadVO.fileName[0].fileName1 }"/><br/>
							<input type="file" name="uploadfile" value="파일수정" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13">
							${ uploadVO.fileName[0].fileName1 }<br/>
						</c:if>
					</b>
					<b id="fileName2">
						<c:if test="${ uploadVO.fileName[0].fileName2 != null }">
							<img src="/images/uploadFiles/${ uploadVO.fileName[0].fileName2 }"/><br/>
							<input type="file" name="uploadfile" value="파일수정" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13">
							${ uploadVO.fileName[0].fileName2 }<br/>
						</c:if>
					</b>
					<b id="fileName3">
						<c:if test="${ uploadVO.fileName[0].fileName3 != null }">
							<img src="/images/uploadFiles/${ uploadVO.fileName[0].fileName3 }"/><br/>
							<input type="file" name="uploadfile" value="파일수정" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13">
							${ uploadVO.fileName[0].fileName3 }<br/>
						</c:if>
					</b>
					<b id="fileName4">
						<c:if test="${ uploadVO.fileName[0].fileName4 != null }">
							<img src="/images/uploadFiles/${ uploadVO.fileName[0].fileName4 }"/><br/>
							<input type="file" name="uploadfile" value="파일수정" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13">
							${ uploadVO.fileName[0].fileName4 }<br/>
						</c:if>
					</b>
					<b id="fileName5">
						<c:if test="${ uploadVO.fileName[0].fileName5 != null }">
							<img src="/images/uploadFiles/${ uploadVO.fileName[0].fileName5 }"/><br/>
							<input type="file" name="uploadfile" value="파일수정" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13">
							${ uploadVO.fileName[0].fileName5 }<br/>
						</c:if>
					</b>
					</td>
				</tr>
			</table>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
						<a href="javascript:fncUpdateProduct();">수정</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					<td width="30"></td>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif"width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<a href="javascript:history.go(-1)">취소</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>

</body>
</html>