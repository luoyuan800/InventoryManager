<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加产品</title>
<script type="text/javascript">
	function check(){
		var name = document.getElementById("name");
		if (name.value.length == 0){
			alert("产品名称不能为空！");
			name.focus();
			return false;
		}
		var code = document.getElementById("code");
		if (code.value.length == 0){
			alert("产品编号不能为空！");
			code.focus();
			return false;
		}
		var minStock = document.getElementById("minStock");
		if (minStock.value.length == 0 || isNaN(minStock.value)){
			alert("最小库存为空或为非数字！");
			minStock.focus();
			return false;
		}
		
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form method="post" <c:if test="${requestScope.product == null }">action="ProductServlet?action=add"</c:if>
						<c:if test="${requestScope.product != null }">action="ProductServlet?action=update&pid=${product.id}"</c:if>>
		产品名称：<input type="text" id="name" name="name" value="${product.name }"/><br>
		产品编号：<input type="text" id="code" name="code" value="${product.code }"/><br>
		最小库存：<input type="text" id="minStock" name="minStock" value="${product.minStock }"/><br>
		<input type="button" value="提交" onclick="check()"/>
	</form>
</body>
</html>