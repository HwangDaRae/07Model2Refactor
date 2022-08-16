<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
function count(type) {
	var resultNumber = document.getElementById('result');
	var productAmount = document.getElementById('productAmount').value;
	var number = resultNumber.innerText;
	
	if( type == 'plus' ){
		if( parseInt(number) < parseInt(productAmount )){
			number = parseInt(number) +1;
		}else if( parseInt(number) == parseInt(productAmount) ){
			document.getElementById('limit').innerText = '더이상 구매하실 수 없습니다';
			return;
		}
	}else if( type == 'minus' ){
		number = parseInt(number) -1;
		if( number == 0 ){
			number = 1;
		}else{
			document.getElementById('limit').innerText = '';
		}
	}else{
		alert('혹시');
	}

	document.getElementById('amount').value = number;
	resultNumber.innerText = number;
}
function cartSubmit() {
	if(confirm('장바구니에 담으시겠습니까?')){
		document.detailForm.submit();
	}
}

function addPurchase(){
	document.detailForm.action = "/purchase/addPurchaseView";
	document.detailForm.submit();
}
</script>

<%--
Product vo = (Product)request.getAttribute("productVO");
System.out.println(vo);
//상품번호를 쿠키에 담는다
String history = "";

Cookie[] cookies = request.getCookies();

if(cookies != null && cookies.length > 0) {
	for (int i = 0; i < cookies.length; i++) {
		if(cookies[i].getName().equals("prodInfoCookie")) {
			history = URLDecoder.decode(cookies[i].getValue()) + "," + vo.getProdNo();
		}
	}			
}

if(history.isEmpty()) {
	history = vo.getProdNo()+"";
}

Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(history));
cookie.setMaxAge(24*60*60);
response.addCookie(cookie);
--%>

<html>
<head>
<meta charset="EUC-KR">

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<title>Insert title here</title>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post" action="/cart/addCart">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"	width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품상세조회</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"  width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">${ productVO.prodNo }</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품명 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ productVO.prodName }</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			상품이미지 <img 	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26">
						<img src="/images/uploadFiles/${ productVO.fileName }"/>
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
			상품상세정보 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ productVO.prodDetail }</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">제조일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ productVO.manuDate }</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">가격</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ productVO.price }</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">등록일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${ productVO.regDate }</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">구매수량</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="button" value="-" onclick='count("minus")'>
			<b id="result">1</b>
			<input type="hidden" id="amount" name="amount" value="1">
			<input type="hidden" id="productAmount" name="productAmount" value="${ productVO.amount }">
			<input type="hidden" name="prod_no" value="${ productVO.prodNo }">
			<input type="button" value="+" onclick='count("plus")'>
			<b id="limit"></b>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>



<table width="100%" border="0" cellspacing="0" style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">

		<table border="0" cellspacing="0">
			<tr>
			
				<c:if test="${ sessionScope.user.userId == 'non-member' }">userId == non-member</c:if>
				<c:if test="${ sessionScope.user.userId != 'non-member' }">userId != non-member</c:if>
				<c:if test="${ sessionScope.user.role == 'user' }">role == user</c:if>
				<c:if test="${ sessionScope.user.role != 'user' }">role != user</c:if>
				
				<c:if test="${ sessionScope.user.userId == 'non-member' || sessionScope.user.role == 'user' }">
			
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<input type="button" value="장바구니 담기" onclick="cartSubmit()">
				</td>
				<td width="14" height="23">
					<img src="/images/ct_btnbg03.gif" width="14" height="23">
				</td>
				<td width="30"></td>
				
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<input type="button" value="구매" onclick="addPurchase()">
						<input type="button" value="">
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
					<td width="30"></td>
				</c:if>
				
				<td width="17" height="23">
					<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
				</td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<a href="javascript:history.go(-1)">이전</a>
				</td>
				<td width="14" height="23">
					<img src="/images/ct_btnbg03.gif" width="14" height="23">
				</td>
			</tr>
		</table>

		</td>
	</tr>
</table>
</form>

</body>
</html>