<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登入頁面</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
		
	<style>
    	body {
            font-family: Arial, sans-serif;
            background-color: #F0F0F0;
            margin: 0;
            padding: 0;
        }
    	form {
	    	background-color: #C4E1E1;
            padding: 20px;
            padding-top: 40px;
            padding-bottom: 10px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 500px;
            height: auto;
    	}
    	.error-text {
		    color: red;
		  }
    </style>
		
	</head>
	<body>
	
		<div class="d-flex justify-content-center align-items-center vh-100">
		<form class="needs-validation" method="post" action="./login" novalidate>
		  <h4 class="text-center">MyAnalyze</h4>
		  <div class="mt-3">
		    <label for="username" class="form-label">帳號</label>
		    <div class="input-group has-validation">
		      <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
		      <input type="text" class="form-control" id="username" name="username" placeholder="請輸入帳號" value="John"  required>
		      <div class="invalid-feedback">
		        請輸入帳號!
		      </div>
		    </div>
		  </div>
		  
		  <div class="mt-3">
		    <label for="password" class="form-label">密碼</label>
		    <div class="input-group has-validation">
		      <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
		      <input type="password" class="form-control" id="password" name="password" placeholder="請輸入密碼" value="pass123" required>
		      <div class="invalid-feedback">
		        請輸入密碼!
		      </div>
		    </div>
		  </div>
		 
		  
		  <div class="mt-3 ">
		    <label for="verificationCode">驗證碼</label>
		    <div class="d-flex">
			    <input type="text" class="form-control" id="code" name="code" placeholder="請輸入驗證碼" required>
			    <img src="./getcode" alt="驗證碼" valign="middle" class="d-flex">
			</div>
		  </div>
		  <span class="error-text">${ loginMessage }</span> 
		  
		  <div class="d-flex justify-content-center mt-3">
		    <button class="btn btn-primary" type="submit">登入</button>
		  </div>
		   
		   
		</form>
		</div> 
		
	</body>
</html>

<script type="text/javascript">
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
  'use strict'

  // Fetch all the forms we want to apply custom Bootstrap validation styles to
  var forms = document.querySelectorAll('.needs-validation')

  // Loop over them and prevent submission
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()
</script>
