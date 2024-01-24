<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Layout</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
		<link rel="stylesheet" href="<%=getServletContext().getContextPath() %>/css/style.css">
	 
	<style type="text/css">
		.navbar {
			background-color: #4F9D9D;
		}
	</style>
	 
	</head>
	<body>
	
		<nav class="navbar navbar-expand-lg navbar-light px-5">
	  <div class="container-fluid px-5">
	    <a class="navbar-brand text-light" href="${ pageContext.request.contextPath }/mvc/analyze/chart/main">MyAnalyze</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	      	
	      	<li class="nav-item dropdown" id="platformDropdown">
	          <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            平台
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	            <li><a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/1">MOMO</a></li>
	            <li><a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/2"">PChome</a></li>
	            <li><a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/3">蝦皮</a></li>
	            <li><hr class="dropdown-divider"></li>
	            <li><a class="dropdown-item" href="#">ALL</a></li>
	          </ul>
        	</li>

			<!-- 	      
	        <li class="nav-item">
	          <a class="nav-link active text-light" aria-current="page" href="${ pageContext.request.contextPath }/mvc/analyze/maintainStock">庫存</a>
	        </li>
	         -->
	         
	        <li class="nav-item dropdown" id="productDropdown">
	          <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            商品
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	            <li><a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/analyze/product/addProduct">商品新增</a></li>
	            <li><a class="dropdown-item" href="${ pageContext.request.contextPath }/mvc/analyze/product/maintainProduct">商品管理</a></li>
	          </ul>
        	</li>
        	
	        <li class="nav-item" id="userDropdown">
	          <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/user/mantain">權限設定</a>
	        </li>
	      </ul>
	    </div>
	    <i class="bi bi-person-circle text-light h5 pe-4 mb-0 d-none d-md-block">${user.username}</i>
	    <i class="bi bi-box-arrow-right text-light ml-3" role="button" onclick="window.location.href='${ pageContext.request.contextPath }/mvc/analyze/logout'">登出</i>
	  </div>
	</nav>
	
	
<script>
	document.addEventListener('DOMContentLoaded', function() {
	    // 使用 EL 獲取後端傳遞的權限信息
	    var userPermissions = ${userLevelId};  // 注意這裡的 EL 表達式
	
	    // 獲取平台的 dropdown 元素
	    var platformDropdown = document.getElementById('platformDropdown');
	    var productDropdown = document.getElementById('productDropdown');
	    var userDropdown = document.getElementById('userDropdown');
	
	    // 如果使用者有權限1，將平台的 dropdown 改為並列的 navbar 選項
	    if (userPermissions === 1) {
	        // 清空原本的 dropdown
	        platformDropdown.innerHTML = '';
		    productDropdown.style.display = 'none';
		    userDropdown.style.display = 'none';
	
	        // 新增並列的 navbar 選項
	        platformDropdown.innerHTML += `
	        	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		            <li class="nav-item">
		                <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/1">MOMO</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/2">PChome</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/3">蝦皮</a>
		            </li>
		            <li class="nav-item">
		                <a class="nav-link text-light" href="#">ALL</a>
		            </li>
	            </div>
	        `;
	    }	else if (userPermissions === 2) {
	    	// 清空原本的 dropdown
	        productDropdown.innerHTML = '';
	        platformDropdown.style.display = 'none';
		    userDropdown.style.display = 'none';
		    
		 	// 新增並列的 navbar 選項
	        productDropdown.innerHTML += `
	        	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		        	<li class="nav-item">
		        		<a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/product/addProduct">商品新增</a>
	        		</li>
		            <li class="nav-item">
		            	<a class="nav-link text-light" href="${ pageContext.request.contextPath }/mvc/analyze/product/maintainProduct">商品管理</a>
	            	</li>
	            </div>
	            `;
		    
	    } 
	});
</script>
	
	