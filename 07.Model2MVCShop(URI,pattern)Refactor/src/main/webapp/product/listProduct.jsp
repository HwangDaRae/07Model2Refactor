<%@page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();
}
function fncGetProductList() {
	document.detailForm.searchCondition.value = document.detailForm.searchCondition.value;
	//document.detailForm.searchKeyword.value = document.detailForm.searchKeyword.value;
	document.forms[0].elements[2].value = document.forms[0].elements[2].value;
   	document.detailForm.submit();
}
function fncGetSortList(priceSort) {
	document.detailForm.priceSort.value = priceSort;
   	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct" method="post">
<!-- hidden name : menu, currentPage, priceSort -->
<input type="hidden" name="menu" value="${ menu }">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01"> ${ sessionScope.user.role } ${ menu } 
						<c:if test="${ menu == 'manage' }">
							상품관리
						</c:if>
						<c:if test="${ menu != 'manage' }">
							상품 목록조회
						</c:if>					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ (searchVO.searchCondition == '0')?"selected":"" } >상품번호</option>
				<option value="1" ${ (searchVO.searchCondition == '1')?"selected":"" } >상품명</option>
				<option value="2" ${ (searchVO.searchCondition == '2')?"selected":"" } >상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value="${ searchVO.searchKeyword }" class="ct_input_g" style="width:200px; height:19px" />
		</td>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 ${ resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage } 페이지
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:fncGetSortList('asc')">낮은 가격순</a>&nbsp;/&nbsp;<a href="javascript:fncGetSortList('desc')">높은 가격순</a>
		<input type="hidden" name="priceSort" value="${ searchVO.priceSort }">
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var="size" value="${ fn:length(list) }" />

	<c:if test="${ !empty sessionScope.user && sessionScope.user.role == 'admin' }">
		<c:forEach var="i" begin="0" end="${ size-1 }" step="1">
			<tr class="ct_list_pop">
				<td align="center">${ size-i }</td>
				<td></td>
					<td align="left">
						<!-- 판매코드가 0이 아니면 상품수정 불가 -->
						<%-- <a href="/updateProductView.do?prodNo=${ list[i].prodNo }&menu=${ menu }">${ list[i].prodName }</a> --%>
						<a href="/product/updateProductView/${ list[i].prodNo }/${ menu }">${ list[i].prodName }</a>
					</td>				
				<td></td>
				<td align="left">${ list[i].price }</td>
				<td></td>
				<td align="left">${ list[i].regDate }</td>
				<td></td>
				<td align="left">
					<c:if test="${ fn:trim(list[i].proTranCode) == '0' }">
						판매중
					</c:if>
					<c:if test="${ fn:trim(list[i].proTranCode) == '1' }">
						구매완료
						<c:if test="${ menu == 'manage' }">
							<%-- -<a href="/updateTranCodeByProd.do?prodNo=${ list[i].prodNo }&currentPage=${ resultPage.currentPage }&tranCode=2&menu=${ menu }">배송하기</a> --%>
							-<a href="/purchase/updateTranCodeByProd?prodNo=${ list[i].prodNo }&currentPage=${ resultPage.currentPage }&tranCode=2&menu=${ menu }">배송하기</a>
						</c:if>
					</c:if>
					<c:if test="${ fn:trim(list[i].proTranCode) == '2' }">
						배송중
					</c:if>
					<c:if test="${ fn:trim(list[i].proTranCode) == '3' }">
						배송완료
					</c:if>									
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</c:forEach>
	</c:if>
	<!-- 회원, 비회원 -->
	<c:if test="${ sessionScope.user.role != 'admin' }">
		<c:forEach var="i" begin="0" end="${ listSize-1 }" step="1">
			<tr class="ct_list_pop">
				<td align="center">${ listSize-i }</td>
				<td></td>
					<td align="left">
						<c:if test="${ fn:trim(list[i].proTranCode) == '0' }">
							<%-- <a href="/getProduct.do?prodNo=${ list[i].prodNo }&menu=${ menu }">${ list[i].prodName }</a> --%>
							<a href="/product/getProduct/${ list[i].prodNo }/${ menu }">${ list[i].prodName }</a>
						</c:if>
						<c:if test="${ fn:trim(list[i].proTranCode) != '0' }">
							${ list[i].prodName }
						</c:if>
					</td>
				
				<td></td>
				<td align="left">${ list[i].price }</td>
				<td></td>
				<td align="left">${ list[i].regDate }</td>
				<td></td>
				<td align="left">
					<c:if test="${ fn:trim(list[i].proTranCode) == '0' }">
						판매중
					</c:if>
					<c:if test="${ fn:trim(list[i].proTranCode) != '0' }">
						재고없음
					</c:if>
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</c:forEach>
	</c:if>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" name="currentPage" id="currentPage" value="0"/>
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
