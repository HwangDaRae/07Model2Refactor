<%@ page contentType="text/html; charset=euc-kr" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/purchase/updatePurchaseView/0" method="post">

다음과 같이 구매가 되었습니다.

<c:forEach var="i" begin="0" end="${ fn:length(prodList) -1 }" step="1">
<table border=1>
	<tr class="ct_list_pop" id="divDataId">
		<td align="center"><img height="250" width="250" src="/images/uploadFiles/${ prodList[i].fileName }"/></td>
		<td></td>
		<td align="left">${ prodList[i].prodName }</td>
		<td></td>
		<td align="left">${ prodList[i].prodDetail }</td>
		<td></td>
		<td align="left">${ prodList[i].price }개</td>
		<td></td>
		<td align="left">${ list[i].amount }개</td>
		<td></td>
		<td align="left">${ prodList[i].price * list[i].amount }원</td>
	</tr>
</table>
</c:forEach>
<table border=1>
	<tr>
		<td>주문번호를 꼭 기억하세요</td>
		<td>${ list[0].tranId }<%-- ${ purchaseVO.purchaseProd.prodNo } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<c:if test="${ list[0].buyer.userId == 'non-member' }">
		<td>비회원<%-- ${ purchaseVO.buyer.userId } --%></td>
		</c:if>
		<c:if test="${ list[0].buyer.userId != 'non-member' }">
		<td>${ list[0].buyer.userId }<%-- ${ purchaseVO.buyer.userId } --%></td>
		</c:if>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>${ (fn:trim(list[0].paymentOption) == '1')?"현금구매":"신용구매" }<%-- ${ (purchaseVO.paymentOption == '1')?"현금구매":"신용구매" } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${ list[0].receiverName }<%-- ${ purchaseVO.receiverName } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${ list[0].receiverPhone }<%-- ${ purchaseVO.receiverPhone } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${ list[0].divyAddr }<%-- ${ purchaseVO.divyAddr } --%></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${ list[0].divyRequest }<%-- ${ purchaseVO.divyRequest } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${ list[0].divyDate }<%-- ${ purchaseVO.divyDate } --%></td>
		<td></td>
	</tr>
	<tr>
		<td>총 가격</td>
		<td>${ list[0].totalPrice }원<%-- ${ purchaseVO.totalPrice } --%></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>