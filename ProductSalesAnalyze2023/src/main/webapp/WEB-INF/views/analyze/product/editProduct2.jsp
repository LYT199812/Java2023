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

<div class="container mt-4">
	<div class="d-flex justify-content-between">
	  <h2 class="mb-4">編輯 ${product.productId} 資訊</h2>
	  <div class="mb-5 ">
		 <button type="submit" class="btn btn-primary">修改</button>
	  </div>
	</div>
  
	<form class="needs-validation" id="editProductForm" enctype="application/x-www-form-urlencoded"
	      method="post" 
	      action="${ pageContext.request.contextPath }/mvc/analyze/product/updateProduct" >
	   <div class="mb-3">
	   		<label for="productQty" class="form-label">產品總庫存</label>
		    <input type="number" class="form-control" id="productQty" name="productQty" value="${product.productQty}">
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
			<input type="text" class="form-control"  id="productName" name="productName" value="${product.productName}">
		</div>
		<div class="mb-3">
			<label for="productPrice" class="form-label">產品價格</label>
			<input type="number" class="form-control"  id="productPrice" name="productPrice" value="${product.productPrice}">
		</div>
		<div class="mb-3">
			<label for="productBarcode" class="form-label">產品條碼</label>
			<input type="text" class="form-control"  id="productBarcode" name="productBarcode" value="${product.productBarcode}">
		</div>
		<div class="mb-3">
			<label for="productBrandId" class="form-label">產品品牌</label> 
			<select class="form-select" id="productBrandId" name="productBrandId">
            	<c:forEach var="productBrand" items="${ productBrands }">
                <c:set var="selected" value="${productBrand.id eq product.productBrandId}"/> <!-- Check if it's the product's type -->
            	<option value="${productBrand.id}" ${productBrand.id eq product.productBrandId ? 'selected' : ''}>${productBrand.name}</option>
                </c:forEach>
            </select>
		</div>
		<div class="mb-3">
			<label for="productTypeId" class="form-label">產品大分類</label>
			<select class="form-select" id="productTypeId" name="productTypeId">
            	<c:forEach var="productType" items="${ productTypes }">
                <c:set var="selected" value="${productType.id eq product.productTypeId}"/> <!-- Check if it's the product's type -->
            	<option value="${productType.id}" ${productType.id eq product.productTypeId ? 'selected' : ''}>${productType.name}</option>
                </c:forEach>
            </select>
		</div>
		<div class="mb-3">
			<label for="productSubTypeId" class="form-label">產品中分類</label> 
			<select class="form-select" id="productSubTypeId" name="productSubTypeId">
            	<c:forEach var="productSubType" items="${ productSubTypes }">
                <c:set var="selected" value="${productSubType.id eq product.productSubTypeId}"/> <!-- Check if it's the product's type -->
            	<option value="${productSubType.id}" ${productSubType.id eq product.productSubTypeId ? 'selected' : ''}>${productSubType.name}</option>
                </c:forEach>
            </select>
		</div>
		<div class="mb-5">
			<label for="productDescription" class="form-label">商品描述</label>
			<textarea class="form-control" id="productDesc" name="productDesc"
				rows="3" placeholder="輸入商品描述">${product.productDesc}</textarea>
		</div>
		<input type="hidden" name="productId" value="${ product.productId }">
		
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