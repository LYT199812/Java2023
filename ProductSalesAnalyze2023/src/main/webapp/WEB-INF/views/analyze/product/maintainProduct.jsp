<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--  產品表格 -->
<div class="container mt-3 mb-5">
  <h2>產品表格</h2>
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
        <!-- 
          <a href="${pageContext.request.contextPath}/mvc/product/editProduct/${product.productId}" class="btn btn-primary" onclick="edit('${product.productId}')" role="button" data-bs-toggle="button">編輯</a>
           -->
          <a href="${ pageContext.request.contextPath }/mvc/product/editProduct2/${product.productId}">
          <button class="btn btn-primary" id="editButton" >編輯</button>
          </a>
          
          <button class="btn btn-danger">刪除</button>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<script>
    // 請確保在這裡定義了名為 edit 的函式
    function edit(productId) {
        // 执行相应的编辑操作，可以使用 productId 参数
        console.log('Edit function called with productId:', productId);
    }
</script>


<!-- 編輯 Modal -->
<!-- 
<c:forEach items="${ products }" var="product">
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editModalLabel">編輯${product.productId}資訊</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
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
         -->
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
		 
     
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
        <button type="button" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div>
-->

<%@include file="/WEB-INF/views/analyze/footer.jsp" %>