<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>库存管理</title>
<script type="text/javascript">
	function buttonClick(action){
		document.forms[0].action = action;
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form method="post" action="ProductServlet">
		<input type="button" value="产品列表" onclick="buttonClick('ProductServlet?action=list&pageIndex=1&pageSize=3')"/>
		<input type="button" value="产品入库" onclick="buttonClick('ProductStorageServlet?action=list&pageIndex=1&pageSize=3')"/>
		<input type="button" value="产品出库" onclick="buttonClick('ProductDeliveryServlet?action=list&pageIndex=1&pageSize=3')"/>
		<input type="button" value="库存盘点" onclick="buttonClick('InventoryCheckingServlet?action=list&pageIndex=1&pageSize=3')"/>
	</form>
</body>
</html>