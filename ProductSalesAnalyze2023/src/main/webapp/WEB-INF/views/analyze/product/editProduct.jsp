<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

${ product }
<div class="container mt-4">
  <h2 class="mb-4">編輯 ${product.productId} 資訊</h2>
	<form class="needs-validation" method="post" id="editProductForm" action="./editProduct/${product.productId}" enctype="multipart/form-data">
		<div class="mb-3">
			<label for="productName" class="form-label">產品名稱</label> 
			<input type="text" class="form-control" id="productName" value="${product.productName}">
		</div>
		<div class="mb-3">
			<label for="productPrice" class="form-label">產品價格</label>
			<input type="number" class="form-control" id="productPrice" value="${product.productPrice}">
		</div>
		<div class="mb-3">
			<label for="productBarcode" class="form-label">產品條碼</label>
			<input type="text" class="form-control" id="productBarcode" value="${product.productBarcode}">
		</div>
		<div class="mb-3">
			<label for="productBrand" class="form-label">產品品牌</label> 
			<input type="text" class="form-control" id="productBrand" value="${product.productBrand}">
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
		<div class="mb-3">
			<label for="productQty" class="form-label">產品總庫存</label>
		        <input type="number" class="form-control" id="productQty" value="${product.productQty}">
		</div>
	
		<div class="mb-3">
		    <label for="productDescription" class="form-label">上架平台</label>
		    <c:forEach var="ecommerce" items="${ecommerce}">
		        <div class="form-check">
		            <c:set var="ecId" value="${ecommerce.id}" />
		            <c:set var="stockMap" value="${request.getAttribute('stockMap')}" />
		            <c:set var="ecProductQty" value="${stockMap[ecId]?.ecProductQty}" />
		            
		            <input class="form-check-input" type="checkbox" value="${ecId}" id="defaultCheck${ecId}" ${ecProductQty ne null && ecProductQty ne '' ? 'checked' : ''}>
		            <label class="form-check-label" for="defaultCheck${ecId}">
		                <c:out value="${ecommerce.name}"/>
		            </label>
		            <input type="number" class="form-control inventory-field" id="defaultCheck${ecId}-inventory" placeholder="庫存" value="${ecProductQty}">
		        </div>
		    </c:forEach>
		</div>
			<!-- 
			<div class="form-check">
				<input class="form-check-input" type="checkbox" value="1" id="defaultCheck1" ${platform_1_isOnSale ? 'checked' : ''}> 
				<label class="form-check-label"	for="defaultCheck1"> Momo </label> 
				<input type="number" class="form-control inventory-field" id="defaultCheck1-inventory" placeholder="庫存" value="${product.inventory[0].ecProductQty}">
			</div>
			 
		</div>
		<div class="form-check">
		    <input class="form-check-input" type="checkbox" value="2" id="defaultCheck2" ${platform_2_isOnSale ? 'checked' : ''}> 
		 	<label class="form-check-label"	for="defaultCheck2"> PChome </label>
			<input type="number" class="form-control inventory-field" id="defaultCheck2-inventory" placeholder="庫存" value="${product.inventory[1].ecProductQty}">
		</div>
		<div class="form-check">
			<input class="form-check-input" type="checkbox" value="3" id="defaultCheck3" ${platform_3_isOnSale ? 'checked' : ''}> 
			<label class="form-check-label"	for="defaultCheck3"> 蝦皮 </label> 
			<input type="number" class="form-control inventory-field" id="defaultCheck3-inventory" placeholder="庫存" value="${product.inventory[2].ecProductQty}">
		</div>
		-->
		<div class="mb-5 mt-3">
			<button type="submit" class="btn btn-primary">修改</button>
		</div>
	</form>
</div>

<script>
	$(document).ready(function() {
		$('input[type="checkbox"]').change(function() {
			var checkboxId = $(this).attr('id');
			var inventoryFieldId = checkboxId + '-inventory';

			if ($(this).prop('checked')) {
				$('#' + inventoryFieldId).show();
			} else {
				$('#' + inventoryFieldId).hide();
			}
		});
	});
	
	function updateInventory(checkbox) {
	    var inventoryField = document.getElementById(checkbox.id + "-inventory");
	    inventoryField.disabled = !checkbox.checked;
	}

</script>












<!--  
        <div class="mb-3">
          <label for="productName" class="form-label">產品名稱</label>
          <input type="text" class="form-control" id="productName">
        </div>
        <div class="mb-3">
          <label for="productPrice" class="form-label">產品價格</label>
          <input type="number" class="form-control" id="productPrice">
        </div>
        <div class="mb-3">
          <label for="productBarcode" class="form-label">產品條碼</label>
          <input type="text" class="form-control" id="productBarcode">
        </div>
        <div class="mb-3">
          <label for="productBrand" class="form-label">產品品牌</label>
          <input type="text" class="form-control" id="productBrand">
        </div>
        <div class="mb-3">
          <label for="productTypeId" class="form-label">產品館別</label>
          <input type="number" class="form-control" id="productTypeId">
        </div>
        <div class="mb-3">
          <label for="productSubTypeId" class="form-label">產品分類</label>
          <input type="number" class="form-control" id="productSubTypeId">
        </div>
        <div class="mb-3">
            <label for="productDescription" class="form-label">商品描述</label>
            <textarea class="form-control" id="productDesc" name="productDesc" rows="3" placeholder="輸入商品描述"></textarea>
        </div>
        <div class="mb-3">
          <label for="productQty" class="form-label">產品總庫存</label>
          <input type="number" class="form-control" id="productQty">
        </div>
        <div>
		  <label for="productDescription" class="form-label">上架平台</label>
		  <div class="form-check">
		    <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
		    <label class="form-check-label" for="defaultCheck1">
		      Momo
		    </label>
		    <input type="text" class="form-control inventory-field" id="defaultCheck1-inventory" placeholder="庫存">
		  </div>
		  <div class="form-check">
		    <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
		    <label class="form-check-label" for="defaultCheck2">
		      PChome
		    </label>
		    <input type="text" class="form-control inventory-field" id="defaultCheck2-inventory" placeholder="庫存">
		  </div>
		  <div class="form-check">
		    <input class="form-check-input" type="checkbox" value="" id="defaultCheck3">
		    <label class="form-check-label" for="defaultCheck3">
		      蝦皮
		    </label>
		    <input type="text" class="form-control inventory-field" id="defaultCheck3-inventory" placeholder="庫存">
		  </div>
		</div>
 		-->




<%@include file="/WEB-INF/views/analyze/footer.jsp"%>