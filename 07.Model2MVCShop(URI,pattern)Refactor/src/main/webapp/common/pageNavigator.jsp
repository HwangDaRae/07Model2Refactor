<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<%-- <c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetUserList('${ resultPage.currentPage-1}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetUserList('${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetUserList('${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if> --%>
	
	
	<c:if test="${ resultPage.beginUnitPage != 1 }">
		<a href="javascript:fncGetList('1')">처음으로&nbsp;</a>
	</c:if>
	<c:if test="${ resultPage.beginUnitPage != 1 }">
		<a href="javascript:fncGetList('${ resultPage.beginUnitPage-1 }')">이전&nbsp;</a>
	</c:if>
	<c:forEach var="i" begin="0" end="${ resultPage.pageUnit-1 }" step="1">
		<c:if test="${ resultPage.beginUnitPage+i <= resultPage.endUnitPage }">
			<a href="javascript:fncGetList('${ resultPage.beginUnitPage+i }')">${ resultPage.beginUnitPage+i }</a>
		</c:if>
	</c:forEach>
	<c:if test="${ resultPage.endUnitPage != resultPage.maxPage }">
		<a href="javascript:fncGetList('${ resultPage.endUnitPage+1 }')">&nbsp;다음</a>
	</c:if>
	<c:if test="${ resultPage.endUnitPage != resultPage.maxPage }">
		<a href="javascript:fncGetList('${ resultPage.maxPage }')">&nbsp;끝으로</a>
	</c:if>
