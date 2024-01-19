<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sp" uri="http://www.springframework.org/tags/form" %>

${ product }
<div class="container mt-4">
  <h2 class="mb-4">編輯 ${product.productId} 資訊</h2>
  <sp:form class="needs-validation" method="post" modelAttribute="product" action="../updateProduct" enctype="multipart/form-data">
    <div class="mb-3">
      <label for="productName" class="form-label">產品名稱</label>
      <sp:input type="text" class="form-control" path="productName" />
    </div>
    <div class="mb-3">
      <label for="productPrice" class="form-label">產品價格</label>
      <sp:input type="number" class="form-control" path="productPrice" />
    </div>
    <div class="mb-3">
      <label for="productBarcode" class="form-label">產品條碼</label>
      <sp:input type="text" class="form-control" path="productBarcode" />
    </div>
    <div class="mb-3">
      <label for="productBrand" class="form-label">產品品牌</label>
      <sp:input type="text" class="form-control" path="productBrand" />
    </div>
    <div class="mb-3">
      <label for="productTypeId" class="form-label">產品大分類</label>
      <sp:select class="form-control" path="productTypeId"
      											 items="${ productTypes }"
												 itemLabel="name"
												 itemValue="id" />
    </div>
    <div class="mb-3">
      <label for="productSubTypeId" class="form-label">產品中分類</label>
      <sp:select class="form-control" path="productSubTypeId"
      											 items="${ productSubTypes }"
												 itemLabel="name"
												 itemValue="id" />
    </div>
    <div class="mb-3">
      <label for="productDescription" class="form-label">商品描述</label>
      <sp:textarea class="form-control" path="productDesc" rows="3" placeholder="輸入商品描述" />
    </div>
    <div class="mb-3">
      <label for="productQty" class="form-label">產品總庫存</label>
      <sp:input type="number" class="form-control" path="productQty" />
    </div>
    <div class="mb-3">
    <label for="ecommerce" class="form-check-label">上架的平台</label></ p>
    <sp:checkboxes path="inventory" 
							items="${ ecommerces }"
							itemLabel="name"
							itemValue="id" />
	</div>
	
	<div class="mb-3">
      <label for="productBrand" class="form-label">平台庫存</label>
      <sp:input type="number" class="form-control" path="inventory" 
													items="${ stocks }"
													itemLabel="ecProductQty"
													itemValue="ecProductQty" />
    </div>
    
	<input type="hidden" name="_method" value="PUT" />
    <div class="mb-5 mt-3">
      <button type="submit" class="btn btn-primary">${ submitBtnName }</button>
    </div>
  </sp:form>
</div>


<!-- 
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
 -->











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