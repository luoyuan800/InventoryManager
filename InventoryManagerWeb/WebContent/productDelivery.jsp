<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品出库</title>
<script type="text/javascript">
	function changePage(num){
		var pageIndex = document.getElementById("pageIndex");
		pageIndex.value = parseInt(pageIndex.value) + parseInt(num);
		document.forms[0].submit();
	}
	function check(id){
		var currentQuantity = document.getElementById("currentQuantity" + id);
		var deliveryQuantity = document.getElementById("deliveryQuantity" + id);
		if (isNaN(deliveryQuantity.value) || (parseInt(currentQuantity.value) < parseInt(deliveryQuantity.value))){
			alert("数据非法！");
			deliveryQuantity.focus();
		}
	}
	function delivery(){
		var action = document.getElementById("action");
		action.value = "commit";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="ProductDeliveryServlet" method="post">
		<input type="hidden" id="action" name="action" value="list"/>
		<input type="hidden" id="pageIndex" name="pageIndex" value="${requestScope.pagination.currentPage }"/>
		<input type="hidden" name="pageSize" value="3"/>
		<table border="1px">
			<thead>
				<tr>
					<td>产品名称</td>
					<td>产品编号</td>
					<td>当前库存</td>
					<td>出库数量</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.products }" var="p">
					<tr>
						<td>${p.name }</td>
						<td>${p.code }</td>
						<td>
							<input type="text" id="currentQuantity${p.id }" value="${p.inventory.quantity }" readonly="readonly"/>
						</td>
						<td>
							<input type="hidden" name="id" value="${p.id }"/>
							<input type="text" id="deliveryQuantity${p.id }" name="deliveryQuantity" onblur="check(${p.id});"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${requestScope.pagination.currentPage <= 1 }">上一页</c:if>
		<c:if test="${requestScope.pagination.currentPage >1 }">
			<a href="javascript:void(0);" onclick="changePage(-1);">上一页</a>
		</c:if>
		${requestScope.pagination.currentPage }
		<c:if test="${requestScope.pagination.currentPage == requestScope.pagination.totalPages }">下一页</c:if>
		<c:if test="${requestScope.pagination.currentPage < requestScope.pagination.totalPages }">
			<a href="javascript:void(0);" onclick="changePage(1);">下一页</a>
		</c:if>
		<br/>
		<input type="button" value="确认出库" onclick="delivery();"/>
	</form>
</body>
</html>