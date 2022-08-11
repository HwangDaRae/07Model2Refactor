<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="/css/admin.css" type="text/css">
<title>Insert title here</title>
<script type="text/javascript" src="../javascript/calendar.js">
</script>
<script type="text/javascript">
function purchaseFinish(){
	confirm('결제완료 하시겠습니까?');
	document.detailForm.submit();
}
</script>
</head>

<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="/css/admin.css" type="text/css">
</head>

<body bgcolor="#ffffff" text="#000000">
	<div style="width: 98%; margin-left: 10px;">
		<form name="detailForm" action="/purchase/addCartPurchase" method="post">
			<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="15" height="37"><img src="/images/ct_ttl_img01.gif" width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">${ sessionScope.user.role } 배송내역</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif" width="12" height="37" /></td>
				</tr>
			</table>


			<table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
				<tr>
					<td colspan="11">
						총 <%-- ${ fn:length(list) } --%> ${ count } 개의 상품선택&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="결제완료" onclick="purchaseFinish()">
					</td>

				</tr>
				<tr>
					<td class="ct_list_b" width="100">상품이미지</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품정보</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품정보</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">수량</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">가격</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
					<!-- list시작 -->
					<c:set var="size" value="${ fn:length(purList) }"/>
					<c:if test="${ fn:length(purList) > 0 }">
						<c:forEach var="i" begin="0" end="${ size-1 }" step="1">
						
							<input type="text" name="productNo" value="${ purList[i].purchaseProd.prodNo }">
							<input type="text" name="amountArr" value="${ purList[i].amount }">
							
							<tr class="ct_list_pop" id="divDataId">
								<td align="center"><img height="250" width="250" src="/images/uploadFiles/${ purList[i].purchaseProd.fileName }"/></td>
								<td></td>
								<td align="left">${ purList[i].purchaseProd.prodName }</td>
								<td></td>
								<td align="left">${ purList[i].purchaseProd.prodDetail }</td>
								<td></td>
								<td align="left">${ purList[i].amount }개</td>
								<td></td>
								<td align="left">${ purList[i].purchaseProd.price } * ${ purList[i].amount } = ${ purList[i].purchaseProd.price * purList[i].amount }</td>
							</tr>
							<tr>
								<td colspan="11" bgcolor="D6D7D6" height="1"></td>
							</tr>
						</c:forEach>
					</c:if>
				<tr>
					<td width="104" class="ct_write">구매방법</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<select name="paymentOption" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20">
							<option value="1" selected="selected">현금구매</option>
							<option value="2">신용구매</option>
						</select>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">구매자이름</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<input type="text" name="receiverName" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" />
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">구매자연락처</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<input 	type="text" name="receiverPhone" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" />
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">구매자주소</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<input 	type="text" name="divyAddr" class="ct_input_g" 
										style="width: 100px; height: 19px" maxLength="20" />
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">구매요청사항</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<input type="text" name="divyRequest" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20" />
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">배송희망일자</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td width="200" class="ct_write01">
						<input type="text" name="divyDate" class="ct_input_g"
							style="width: 100px; height: 19px" maxLength="20"/>
							<img src="../images/ct_icon_date.gif" width="15" height="15"
							onclick="show_calendar('document.detailForm.divyDate', document.detailForm.divyDate.value)"/>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
				<tr>
					<td align="left"><input type="button" value="결제완료" onclick="purchaseFinish()"></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>