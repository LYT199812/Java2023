<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Layout</title>
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	    <link rel="stylesheet" href="/ProductSalesAnalyze2023/css/style.css">
		<link rel="stylesheet" href="<%=getServletContext().getContextPath() %>/css/style.css">
	 
	<style type="text/css">
		.navbar {
			background-color: #4F9D9D;
		}
	</style>
	 
	</head>
	<body>
	
		<nav class="navbar navbar-expand-lg navbar-light ">
	  <div class="container-fluid">
	    <a class="navbar-brand text-light" href=".\main.jsp">MyAnalyze</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	      	
	      	<li class="nav-item dropdown">
	          <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            平台
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	            <li><a class="dropdown-item" href=".\ecWebsite\momo2.jsp">MOMO</a></li>
	            <li><a class="dropdown-item" href="#">PChome</a></li>
	            <li><a class="dropdown-item" href="#">蝦皮</a></li>
	            <li><hr class="dropdown-divider"></li>
	            <li><a class="dropdown-item" href="#">ALL</a></li>
	          </ul>
        	</li>
	      
	        <li class="nav-item">
	          <a class="nav-link active text-light" aria-current="page" href="./">庫存</a>
	        </li>
	        
	        <li class="nav-item dropdown">
	          <a class="nav-link dropdown-toggle text-light" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            商品
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	            <li><a class="dropdown-item" href=".\product\addProduct.jsp">商品新增</a></li>
	            <li><a class="dropdown-item" href=".\product\maintainProduct.jsp">商品管理</a></li>
	          </ul>
        	</li>
        	
	        <li class="nav-item">
	          <a class="nav-link text-light" href=".\permissionSettings">權限設定</a>
	        </li>
	      </ul>
	    </div>
	    <i class="bi bi-person-circle text-light h5 pe-4 mb-0 d-none d-md-block">${user.username}</i>
	    <i class="bi bi-box-arrow-right text-light ml-3" role="button" onclick="window.location.href='./logout'">登出</i>
	  </div>
	</nav>
	
	</body>
</html>