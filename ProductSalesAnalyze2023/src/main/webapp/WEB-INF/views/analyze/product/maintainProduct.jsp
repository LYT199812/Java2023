<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  產品表格 -->
<div class="container mt-5">
  <h2>產品表格</h2>
 ${product.inventory}
  <table class="table">
    <thead>
      <tr>
      	<th>產品型號</th>
        <th>產品名稱</th>
        <th>價格</th>
        <th>條碼</th>
        <th>品牌</th>
        <th>大分類</th>
        <th>中分類</th>
        <th>上架平台</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="product" items="${products}">
      <tr>
  		<td><c:out value="${product.productId}" /></td>
        <td><c:out value="${product.productName}" /></td>
        <td><c:out value="${product.productPrice}" /></td>
        <td><c:out value="${product.productBarcode}" /></td>
        <td><c:out value="${product.productBrand}" /></td>
        <td>
        <c:out value="${product.productType.name}" />
        </td>
        <td><c:out value="${product.productSubType.name}" /></td>
        <!-- 
        <td><img src="<c:url value='/images/${product.productImg}' />" alt="產品圖片"></td>
         -->
        <td>
        <c:forEach var="stock" items="${product.inventory}">
		   <c:out value="${stock.ecommerce.name}" />
		</c:forEach>
		           
        <!-- 
          <c:forEach var="stock" items="${product.inventory}">
          <c:set var="ecId" value="${stock.ecId}" />
		  <c:set var="ecommerce" value="${ecommerceDaoResposity.findEcById(ecId)}" />
		  <c:out value="${ecommerce.name}" />
         </c:forEach>
          -->
        </td>
        <td>
          <button class="btn btn-primary" id="editButton" onclick="edit('${product.productId}')" data-bs-toggle="modal" data-bs-target="#editModal">編輯
          	<a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/product/addProduct">商品新增</a>
          </button>
          <button class="btn btn-danger">刪除</button>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<!-- 編輯 Modal -->
<c:forEach items="${ products }" var="product">
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editModalLabel">編輯${product.productId}資訊</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <!-- 編輯欄位 -->
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
        </c:forEach>
        <!-- 
        <div>
        	<label for="productDescription" class="form-label">上架平台</label>
			<div class="form-check">
			  <input class="form-check-input" type="checkbox" value="" id="defaultCheck1">
			  <label class="form-check-label" for="defaultCheck1">
			    Momo
			  </label>
			</div>
			<div class="form-check">
			  <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
			  <label class="form-check-label" for="defaultCheck2">
			    PChome
			  </label>
			</div>
			<div class="form-check">
			  <input class="form-check-input" type="checkbox" value="" id="defaultCheck2">
			  <label class="form-check-label" for="defaultCheck2">
			    蝦皮
			  </label>
			</div>
		</div>
		 -->
        <!-- 其他欄位... -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
        <button type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
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
    
    
 	// 假設這是你的編輯按鈕，點擊時觸發編輯模態視窗
    document.getElementById('editButton').addEventListener('click', function() {
        // 假設product是後端傳遞到前端的Product物件
        var product = /* 從後端取得的Product物件 */;
        
        // 將取得的product資料填入模態視窗中的欄位
        document.getElementById('productName').value = product.productName;
        document.getElementById('productPrice').value = product.productPrice;
        document.getElementById('productBarcode').value = product.productBarcode;
        document.getElementById('productBrand').value = product.productBrand;
        document.getElementById('productTypeId').value = product.productTypeId;
        document.getElementById('productSubTypeId').value = product.productSubTypeId;
        document.getElementById('productDesc').value = product.productDesc;
        document.getElementById('productQty').value = product.productQty;

        // 根據product的inventory填入上架平台的庫存
        for (var i = 0; i < product.inventory.length; i++) {
            var stock = product.inventory[i];
            document.getElementById('defaultCheck' + stock.ecId).checked = true;
            document.getElementById('defaultCheck' + stock.ecId + '-inventory').value = stock.ecProductQty;
        }

        // 開啟模態視窗
        var myModal = new bootstrap.Modal(document.getElementById('editModal'));
        myModal.show();
    });

    // 當模態視窗關閉時，清空模態中的所有欄位值
    $('#editModal').on('hidden.bs.modal', function () {
        // 清空欄位值
        $('#editModal input').val('');
        $('#editModal textarea').val('');
        $('#editModal input[type="checkbox"]').prop('checked', false);
    });

    
  </script>

<%@include file="/WEB-INF/views/analyze/footer.jsp" %>