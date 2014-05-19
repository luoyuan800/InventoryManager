<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品盘点</title>
<script type="text/javascript">
	function changePage(num){
		/*名为pageIndex的表单元素，注意是元素，而不是值*/
		var pageIndex = document.getElementById("pageIndex");
		pageIndex.value = parseInt(pageIndex.value) + parseInt(num);
		document.forms[0].submit();
	}
	function calculateDifference(id){
		/*代表当前库存数量的表单元素，注意是表单元素，而不是值。*/
		var currentQuantity = document.getElementById("quantity" + id);
		/*代表盘点的库存数量的表单元素，注意是表单元素，而不是值。*/
		var realQuantity = document.getElementById("checkingQuantity" + id);
		if (isNaN(realQuantity.value)){
			alert("盘点数量不是数值类型！");
			realQuantity.focus();
			return;
		}
		/*代表差值的表单元素，注意是表单元素，而不是值。*/
		var difference = document.getElementById("difference" + id);
		if (realQuantity.value == ""){
			difference.value = "";
		}else {
			difference.value = parseInt(currentQuantity.value) - parseInt(realQuantity.value);
		}
		
	}
	function commit(){
		/*名为action的表单元素，注意是元素，而不是值。*/
		var action = document.getElementById("action");
		action.value = "commit";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<form method="post" action="InventoryCheckingServlet">
		<input type="hidden" id="action" name="action" value="list"/>
		<input type="hidden" id="pageIndex" name="pageIndex" value="${requestScope.pageIndex }"/>
		<input type="hidden" name="pageSize" value="3"/>
		<table border="1px">
			<thead>
				<tr>
					<td>产品名称</td>
					<td>产品编号</td>
					<td>当前库存</td>
					<td>盘点数量</td>
					<td>差值</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${requestScope.products }">
					<tr>
						<td>${p.name }</td>
						<td>${p.code }</td>
						<td>${p.inventory.quantity }</td>
						<td>
							<input type="hidden" name="id" value="${p.id }"/>
							<input type="hidden" id="quantity${p.id }" value="${p.inventory.quantity }"/>
							<input type="text" id="checkingQuantity${p.id }" onblur="calculateDifference(${p.id})"/>
						</td>
						<td>
							<input type="text" id="difference${p.id }" name="difference" readonly="readonly"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="button" value="上一页" onclick="changePage(-1)"/>
		${requestScope.pageIndex }
		<input type="button" value="下一页" onclick="changePage(1)"/><br/>
		<input type="button" value="提交盘点结果" onclick="commit()"/>
	</form>
</body>
</html>