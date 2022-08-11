<%@page import="com.model2.mvc.service.domain.User"%>
<%@ page contentType="text/html; charset=euc-kr" %>

<%
	User vo=(User)session.getAttribute("user");

	String role=" ";

	if(vo != null) {
		role=vo.getRole();
	}
%>

<html>
<head>
<title>Model2 MVC Shop</title>

<link href="/css/left.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
function history(){
	popWin = window.open("/history.jsp","popWin","left=300, top=200, width=300, height=200, marginwidth=0, marginheight=0, scrollbars=no, scrolling=no, menubar=no, resizable=no");
}
</script>
</head>

<body background="/images/left/imgLeftBg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table width="159" border="0" cellspacing="0" cellpadding="0">

<!--menu 01 line-->
<tr>
<td valign="top"> 
	<table  border="0" cellspacing="0" cellpadding="0" width="159" >
		<tr>
		<%
			if(vo != null){
		%>
		<tr>
		<td class="Depth03">
			<a href="/user/getUser?userId=<%=vo.getUserId() %>" target="rightFrame">개인정보조회</a>
		</td>
		</tr>
		<%
			}
		%>
		<%
			if(vo != null){
				if(role.equals("admin")){
		%>
		<tr>
		<td class="Depth03" >
			<a href="/user/listUser" target="rightFrame">회원정보조회</a>
		</td>
		</tr>
		<%
				}
			}	
		%>
		<tr>
			<td class="DepthEnd">&nbsp;</td>
		</tr>
	</table>
</td>
</tr>

	<%
		if(role.equals("admin")){
	%>
<!--menu 02 line-->
<tr>
<td valign="top"> 
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
			<td class="Depth03">
				<!-- <a href="../product/addProductView.jsp;" target="rightFrame">판매상품등록</a> -->
				<a href="/product/addProductView" target="rightFrame">판매상품등록</a>
			</td>
		</tr>
		<td class="Depth03">
				<a href="/product/listProduct/manage" target="rightFrame">판매상품관리</a>
			</td>
		</tr>
		<tr>
			<td class="DepthEnd">&nbsp;</td>
		</tr>
	</table>
</td>
</tr>
	<%
				}
	%>

<!--menu 03 line-->
<tr>
<td valign="top">
	<table  border="0" cellspacing="0" cellpadding="0" width="159">
		<tr>
			<td class="Depth03"><!-- /menu=search -->
				<a href="/product/listProduct/search" target="rightFrame">상 품 검 색</a>
				<!-- <a href="/user/login" target="rightFrame">상 품 검 색</a> -->
			</td>
		</tr>
		<%
			if(vo != null){
				if(role.equals("user")){
		%>
		<tr>
			<td class="Depth03">
				<a href="/purchase/listPurchase" target="rightFrame">구매이력조회</a><br/>
			</td>
		</tr>
		<%
				}
			}
		%>
		<tr>
			<td class="Depth03">
				<a href="/cart/listCart" target="rightFrame">장바구니</a><br/>
			</td>
		</tr>
		<tr>
			<td class="Depth03">
				<!-- <a href="../purchase/nonMemberPurchase.jsp" target="rightFrame">비회원주문조회</a><br/> -->
				<a href="/purchase/nonMemberPurchase" target="rightFrame">비회원주문조회</a><br/>
			</td>
		</tr>
		<tr>
		<td class="DepthEnd">&nbsp;</td>
		</tr>
		<tr>
			<td class="Depth03">
				<a href="javascript:history()">최근 본 상품</a>
			</td>
		</tr>
	</table>
</td>
</tr>

</table>
</body>
</html>