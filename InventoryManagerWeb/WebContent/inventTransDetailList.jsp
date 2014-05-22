<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>事务详情</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<td align="center">日期</td>
				<td align="center">类型</td>
				<td align="center">数量</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.transDetails }" var="t">
				<tr>
					<td>${t.inventoryTransaction.date }&nbsp;&nbsp;</td>
					<td>${t.inventoryTransaction.type }&nbsp;&nbsp;</td>
					<td>${t.quantity }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>