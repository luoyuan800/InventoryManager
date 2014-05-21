<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品入库</title>
<script type="text/javascript">
	function check(id){
		var storageQuantity = document.getElementById(id);
		if (isNaN(storageQuantity.value)){
			alert("数据非法！");
			storageQuantity.focus();
		}
		else if (parseInt(storageQuantity.value) < 0){
			alert("数据非法！");
			storageQuantity.focus();
		}
	}
	function changePage(num){
		var pageIndex = document.getElementById("pageIndex");
		pageIndex.value = parseInt(pageIndex.value) + parseInt(num);
		document.forms[0].submit();
	}
	function storage(){
		var action = document.getElementById("action");
		action.value = "commit";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form action="ProductStorageServlet" method="post">
		<input type="hidden" id="action" name="action" value="list"/>
		<input type="hidden" id="pageIndex" name="pageIndex" value="${requestScope.pageIndex }"/>
		<input type="hidden" name="pageSize" value="3"/>
		<table border="1px">
			<thead>
				<tr>
					<th>产品名称</th>
					<th>产品编号</th>
					<th>当前库存</th>
					<th>入库数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.products }" var="p">
					<tr>
						<td>${p.name }</td>
						<td>${p.code }</td>
						<td>${p.inventory.quantity }</td>
						<td>
							<input type="hidden" name="id" value="${p.id }"/>
							<input type="text" id="storageQuantity${p.id }" name="storageQuantity" onblur="check('storageQuantity${p.id}')"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a href="javascript:void(0);" onclick="changePage(-1);">上一页</a>
		${requestScope.pageIndex }
		<a href="javascript:void(0);" onclick="changePage(1);">下一页</a><br/>
		<input type="button" value="全部入库" onclick="storage();"/>
	</form>
</body>
</html>