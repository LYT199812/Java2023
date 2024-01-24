<%@page import="spring.mvc.analyze.entity.Product"%>
<%@page import="spring.mvc.analyze.entity.Stock"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%

	Product product = (Product)request.getAttribute("product");

%>
<style>
	body {
		background-color: #F0F0F0
	}
</style>

<%-- ${ product } --%>

<%-- ${ ecommerceStockEdits } --%>

${ errorMessage }


<div class="container mt-4">
  <h2 class="mb-4">編輯 ${product.productId} 資訊</h2>
	<form class="needs-validation" id="editProductForm" enctype="application/x-www-form-urlencoded"
	      method="post" 
	      action="${ pageContext.request.contextPath }/mvc/analyze/product/updateProduct" >
	   <div class="mb-3">
	   		<label for="productQty" class="form-label">產品總庫存</label>
		    <input type="number" class="form-control" id="productQty" value="${product.productQty}">
	   </div>
	   <c:forEach items="${ ecommerceStockEdits }" var="ecommerceStockEdit">
			<div class="mb-3">
			    <label for="productDescription" class="form-label">上架平台</label>
				<div class="form-check">
					<input class="form-check-input" 
					       type="checkbox" 
					       value="${ ecommerceStockEdit.isLaunch}" 
					       ${ ecommerceStockEdit.isLaunch ? 'checked' : ''}
					       onchange="changeEcCommerce(event.target.checked, ${ecommerceStockEdit.ecommerce.id })" > 
					<input id="isLaunch${ecommerceStockEdit.ecommerce.id }" 
					       type="hidden" 
					       name="isLaunch_${ecommerceStockEdit.ecommerce.id }" 
					       value="${ ecommerceStockEdit.isLaunch}" >
					<label class="form-check-label"	for="defaultCheck1"> ${ecommerceStockEdit.ecommerce.name } </label> 
					<input id="ec${ecommerceStockEdit.ecommerce.id }" 
					       type="number" 
					       name="stockQty_${ecommerceStockEdit.ecommerce.id }" 
					       class="form-control inventory-field" 
					       placeholder="庫存" 
					       value="${ecommerceStockEdit.stockQty }">
				</div>
			</div>
		</c:forEach>
		<div class="mb-3">
			<label for="productName" class="form-label">產品名稱</label> 
			<input type="text" class="form-control"  id="productName" value="${product.productName}">
		</div>
		<div class="mb-3">
			<label for="productPrice" class="form-label">產品價格</label>
			<input type="number" class="form-control"  id="productPrice" value="${product.productPrice}">
		</div>
		<div class="mb-3">
			<label for="productBarcode" class="form-label">產品條碼</label>
			<input type="text" class="form-control"  id="productBarcode" value="${product.productBarcode}">
		</div>
		<div class="mb-3">
			<label for="productBrand" class="form-label">產品品牌</label> 
			<input type="text" class="form-control" id="productBrand" value="${product.productBrand.name}">
		</div>
		<div class="mb-3">
			<label for="productTypeId" class="form-label">產品大分類</label> 
			<input type="text" class="form-control" id="productTypeId" value="${product.productType.name}">
		</div>
		<div class="mb-3">
			<label for="productSubTypeId" class="form-label">產品中分類</label> 
			<input type="text" class="form-control" id="productSubTypeId" value="${product.productSubType.name}">
		</div>
		<div class="mb-3">
			<label for="productDescription" class="form-label">商品描述</label>
			<textarea class="form-control" id="productDesc" name="productDesc"
				rows="3" placeholder="輸入商品描述">${product.productDesc}</textarea>
		</div>
		<input type="hidden" name="productId" value="${ product.productId }">
		<div class="mb-5 mt-3">
			<button type="submit" class="btn btn-primary">修改</button>
		</div>
	</form>
</div>

<%!
	private Stock getStockByEcId(java.util.List<Stock> stocks, int ecId) {
		return stocks.stream().filter(s-> s.getEcId().equals(ecId)).findFirst().orElse(new Stock());
	}
%>

<script>

	function changeEcCommerce(checked, ecId) {
		if(checked) {
			$('#isLaunch'+ecId).val(true);
			if($('#ec'+ecId).val() == 0) {
				$('#ec'+ecId).val(1);
			}
		} else {
			$('#ec'+ecId).val(0);
			$('#isLaunch'+ecId).val(false);
		}
	}

	function updateInventory(checkbox) {
	    var inventoryField = document.getElementById(checkbox.id + "-inventory");
	    inventoryField.disabled = !checkbox.checked;
	}

</script>

<%@include file="/WEB-INF/views/analyze/footer.jsp"%>