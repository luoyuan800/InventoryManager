<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品列表</title>
<script type="text/javascript">
	function changePage(num){
		var pageIndex = document.getElementById("pageIndex");
		pageIndex.value = parseInt(pageIndex.value) + parseInt(num);
		document.forms[0].submit();
	}
	function editProduct(id){
		document.forms[0].action = "ProductServlet?pid=" + id;
		document.getElementById("action").value = "edit";
		document.forms[0].submit();
	}
	function removeProduct(id){
		document.forms[0].action = "ProductServlet?pid=" + id;
		document.getElementById("action").value = "remove";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form method="post" action="ProductServlet">
		<input type="hidden" id="action" name="action" value="list"/>
		<input type="hidden" id="pageIndex" name="pageIndex" value="${pageIndex }"/>
		<input type="hidden" id="pageSize" name="pageSize" value="3"/>
		<table border="1px">
			<thead>
				<tr>
					<td>产品名称</td>
					<td>产品编号</td>
					<td>最小库存</td>
					<td>当前库存量</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${products }" var="p">
					<tr>
						<td>${ p.name}</td>
						<td>${p.code }</td>
						<td>${p.minStock }</td>
						<td>${p.inventory.quantity }</td>
						<td>&nbsp;
							<a href="ProductServlet?action=edit&pid=${p.id }" style="text-decoration: none;">
								<img src="image/btn_edit.png" style="border-color: #FFFFFF;" />
							</a>
							&nbsp;&nbsp;
							<a href="ProductServlet?action=remove&pid=${p.id }" style="text-decoration: none;">
								<img src="image/btn_delete.png" style="border-color: #FFFFFF;"/>
							</a>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="button" value="上一页" onclick="changePage(-1)"/>
		${pageIndex }
		<input type="button" value="下一页" onclick="changePage(1)"/><br>
		<a href="addProduct.jsp">添加产品</a>
	</form>
</body>
</html>