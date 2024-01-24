<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>

<div class="container mt-4">
	<div class="d-flex justify-content-between">
    <h2 class="mb-4">商品新增</h2>
    <form id="addProductExcelForm" method="post" action="./addProductUpload" enctype="multipart/form-data">
	  <input type="file" class="form-control" id="addProductUploadFile" name="addProductUploadFile" accept=".xlsx" required hidden="">
	  <button type="button" class="btn btn-primary" onclick="document.getElementById('addProductUploadFile').click();">
	  	<span id="fileNameContainer">
            <i class="bi bi-plus-circle"></i> 批次新增商品
        </span>
	  </button>
	  <button class="btn btn-outline-secondary" type="submit" id="uploadButton" style="display: none;">檔案上傳</button>
	 </form>
    </div>
    
    <form class="needs-validation" method="post" id="addProduct" action="./addProduct" enctype="multipart/form-data">
        
        <div class="mb-3">
            <label for="productId" class="form-label">商品ID</label>
            <div class="input-group has-validation">
		        <input type="text" class="form-control" id="productId" name="productId" placeholder="輸入商品ID" required>
		        <div class="invalid-feedback">
				     請輸入商品ID!
		        </div>
	        </div>
       	</div>
        <div class="mb-3">
	        <label for="productName" class="form-label">商品名稱</label>
	        <div class="input-group has-validation">
	            <input type="text" class="form-control" id="productName" name="productName" placeholder="輸入商品名稱" required>
	        	<div class="invalid-feedback">
					 請輸入商品名稱!
			    </div>
	        </div>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">商品價格</label>
            <div class="input-group has-validation">
	            <input type="number" class="form-control" id="productPrice" name="productPrice" placeholder="輸入商品價格" required>
	        	<div class="invalid-feedback">
					 請輸入商品價格!
				</div>
	        </div>
        </div>
        <div class="mb-3">
            <label for="barcode" class="form-label">商品條碼</label>
            <input type="text" class="form-control" id="productBarcode" name="productBarcode" placeholder="輸入商品條碼">
        </div>
        <div class="mb-3">
            <label for="brand" class="form-label">品牌</label>
	        <div class="input-group has-validation">
	            <input type="text" class="form-control" id="productBrand" name="productBrand" placeholder="輸入商品品牌" required>
		        <div class="invalid-feedback">
					 請輸入商品品牌!
				</div>
		   	</div>
        </div>
        <div class="mb-3">
            <label for="productPrice" class="form-label">大分類</label>
            <div class="input-group has-validation">
	            <input type="text" class="form-control" id="productTypeId" name="productTypeId" placeholder="輸入商品館別" required>
	        	<div class="invalid-feedback">
					請輸入商品大分類!
				</div>
		   	</div>
        </div>
        <div class="mb-3">
            <label for="productPrice" class="form-label">中分類</label>
            <div class="input-group has-validation">
	            <input type="text" class="form-control" id="productSubTypeId" name="productSubTypeId" placeholder="輸入商品分類" required>
	        	<div class="invalid-feedback">
						請輸入商品中分類!
				</div>
		   	</div>
        </div>
        <div class="mb-3">
            <label for="productDescription" class="form-label">商品描述</label>
            <textarea class="form-control" id="productDesc" name="productDesc" rows="3" placeholder="輸入商品描述"></textarea>
        </div>
        
        <!-- 
        <label for="productImage" class="form-label">商品圖片</label>
        <div class="row mb-3" id="updatebox">
	        <label for="file" class="col-sm-4">
	            <div class="panel updatepanel">
	                <div class="remove-icon bi bi-x-lg"></div>
	                <input type="file" id="file" class="file-input"  hidden=""/>
	                <div class="addbox">
		                <span class="bi bi-plus-lg">
	                		上傳圖片
	                	</span>
                	</div>
		            <img class="updateimg img-responsive" style="width: inherit; height: 210px; display: none;">
	            </div>
	        </label>
    	</div>
    	 -->
    	
        <div class="mb-3">
            <label for="productLaunch" class="form-label">是否立即上架</label>
            <div id="radio-error-message" class="error-message" style="display: none; color: red;">
		      請至少選擇一個選項
		    </div>
            <div>
	            <input class="radio-button-input" type="radio" id="isLaunch" name="isLaunch" value="true" required>
	            <label class="radio-button-label" for="productisLaunch">是</label>
	            <input class="radio-button-input" type="radio" id="isLaunch" name="isLaunch" value="false" required>
	        	<label class="radio-button-label" for="productnoLaunch">否</label>
        	</div>
        </div>
        
        <div class="mb-3">
          <label for="productLaunch" class="form-label">有上架的電商平台</label>
          <div id="error-message" class="error-message" style="display: none; color: red;">
	      請至少選擇一個選項
	      </div>
          <div>
			  <input class="check-box-input" type="checkbox" id="ecId" value="1">
			  <label class="form-check-label" for="inlineCheckbox1">Momo</label>
			  <input class="check-box-input" type="checkbox" id="ecId" value="2">
			  <label class="form-check-label" for="inlineCheckbox2">PChome</label>
			  <input class="check-box-input" type="checkbox" id="ecId" value="3">
			  <label class="form-check-label" for="inlineCheckbox3">蝦皮</label>
		  </div>
		</div>
		
        
        <button type="submit" class="btn btn-primary mb-5" onclick="validateForm();validateRadio();">新增商品</button>
    </form>
</div>

<style>
	/*	
	.updatepanel {
	    position: relative;
	    border: 1px solid #ccc;
		text-align: center;
   }

       .remove-icon {
           position: absolute;
           top: 5px;
           right: 5px;
           cursor: pointer;
           color: red;
           display: none;
       }

	.panel {
		height: 250px;
		margin-bottom: 0;
		margin-top: 10px;
	}
	.updatepanel .addbox {
		vertical-align: middle;
		height: 250px;
		line-height: 250px;
	}
	*/
	
	body {
		background-color: #F0F0F0
	}
	
	.error-radio {
	  box-shadow: 0 0 5px red; /* 使用紅色的陰影 */
	  border: 2px solid red;  /* 使用紅色的邊框，調整邊框寬度 */
	  border-radius: 4px; /* 可以添加圓角效果，視需求而定 */
	  transition: box-shadow 0.3s ease, border 0.3s ease; /* 陰影和邊框變化的過渡效果 */
	}
	
	.error-checkbox {
	  box-shadow: 0 0 5px red; /* 使用紅色的陰影 */
	  border: 2px solid red;  /* 使用紅色的邊框 */
	  transition: box-shadow 0.3s ease; /* 陰影變化的過渡效果 */
	}
	
	.panel {
    height: 250px;
    margin-bottom: 0;
    margin-top: 10px;
	}
	
	.updatepanel {
	    position: relative;
	    border: 1px solid #ccc;
		text-align: center;
	}
	
	.updatepanel .addbox {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 250px;
	    line-height: 250px;
	}
	
	.remove-icon {
	    position: absolute;
	    top: 5px;
	    right: 5px;
	    cursor: pointer;
	    color: red;
	    display: none;
	}
	
	bi-x-circle{
		color:red !important;
	}
	
</style>
	
<!-- 自定義js -->
<script type="text/javascript">
	// 批次新增商品
	document.getElementById('addProductUploadFile').addEventListener('change', function () {
	    var fileName = this.files[0].name;
	    var fileNameContainer = document.getElementById('fileNameContainer');
	    var uploadButton = document.getElementById('uploadButton');
	    
	    //fileNameContainer.innerHTML = `<span>${fileName}</span><i class="bi bi-x" onclick="resetUpload()"></i>`;
	    document.getElementById('fileNameContainer').innerText = fileName;
	    fileNameContainer.insertAdjacentHTML('beforeend', '&nbsp;<i class="bi bi-x-circle" onclick="resetUpload()"></i>');
	
	    if (fileName !== '') {
            uploadButton.style.display = 'inline';
        }
	    
	    
	});
	
	function resetUpload() {
	    var fileNameContainer = document.getElementById('fileNameContainer');
	    var addProductUploadFile = document.getElementById('addProductUploadFile');
	
	    // Clear the file input
	    addProductUploadFile.value = '';
	
	    // Reset the button content
	    //fileNameContainer.innerHTML = `<i class="bi bi-plus-circle"></i> 批次新增商品`;
	    document.getElementById('fileNameContainer').innerText = '批次新增商品';
	    fileNameContainer.insertAdjacentHTML('afterbegin', '<i class="bi bi-plus-circle"></i>&nbsp;');
	    uploadButton.style.display = 'none';
	}
	
	//-----------------------------------------------------------------------------
	// 上傳圖片

    $(document).ready(function () {
        $(".file-input").on("change", function () {
            addImage(this);
        });

        $(document).on("click", ".remove-icon", function () {
            removeImage($(this).siblings(".updateimg"));
        });
    });

    function addImage(input) {
        var file = input.files[0];
        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var $panel = $(input).closest('.updatepanel');
                $panel.find('.updateimg').attr('src', e.target.result).show();
                $panel.find('.remove-icon').show();
                $panel.find('.addbox').hide(); // 隱藏 + 號
            };
            reader.readAsDataURL(file);
        }
    }

    function removeImage(img) {
        var $panel = $(img).closest('.updatepanel');
        $panel.find('.updateimg').attr('src', '').hide();
        $panel.find('.remove-icon').hide();
        $panel.find('.addbox').show(); // 顯示 + 號
        // Optionally, you can clear the file input
        $panel.find('.file-input').val('');
    }
    
 	//  表單驗證
 	
 	// radio-button 驗證
	function validateRadio() {
	  var radioButtons = document.getElementsByName('isLaunch');
	  var isChecked = false;
	
	  for (var i = 0; i < radioButtons.length; i++) {
	    if (radioButtons[i].checked) {
	      isChecked = true;
	      break;
	    }
	  }
	
	  var errorMessage = document.getElementById('radio-error-message');
	  if (!isChecked) {
	    errorMessage.style.display = 'block'; // 顯示錯誤消息
	
	    // 遍歷所有 radio 按鈕，添加紅色光暈樣式
	    for (var i = 0; i < radioButtons.length; i++) {
	      radioButtons[i].classList.add('error-radio');
	    }
	  } else {
	    errorMessage.style.display = 'none'; // 隱藏錯誤消息
	
	    // 移除所有 radio 按鈕的紅色光暈樣式
	    for (var i = 0; i < radioButtons.length; i++) {
	      radioButtons[i].classList.remove('error-radio');
	    }
	  }

	  return isChecked;
	}
 	
	// check-box 驗證
	function validateForm() {
	  var checkboxes = document.getElementsByClassName('check-box-input');
	  var isChecked = false;
	
	  for (var i = 0; i < checkboxes.length; i++) {
	    if (checkboxes[i].checked) {
	      isChecked = true;
	      break;
	    }
	  }
      
      var errorMessage = document.getElementById('error-message');
      if (!isChecked) {
        errorMessage.style.display = 'block'; // 顯示錯誤消息
        // 遍歷所有checkbox，添加紅色光暈樣式
        for (var i = 0; i < checkboxes.length; i++) {
          checkboxes[i].classList.add('error-checkbox');
        }
        
        event.preventDefault(); // 防止表單提交
        
      } else {
        errorMessage.style.display = 'none'; // 隱藏錯誤消息
        // 移除所有checkbox的紅色光暈樣式
        for (var i = 0; i < checkboxes.length; i++) {
          checkboxes[i].classList.remove('error-checkbox');
        }
      }
      
   	// 使用 Bootstrap 的驗證
      var form = document.querySelector('.needs-validation');
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }

      form.classList.add('was-validated');
      
      
    }
 	
	// 添加事件監聽器，當 radio-button 被選擇時，隱藏錯誤消息
	document.addEventListener('change', function(event) {
	  var target = event.target;
	  if (target.type === 'radio' && target.classList.contains('radio-button-input')) {
	    var errorMessage = document.getElementById('radio-error-message');
	    errorMessage.style.display = 'none';
	    
	    var radioButtons = document.getElementsByClassName('radio-button-input');
	    for (var i = 0; i < radioButtons.length; i++) {
	    	radioButtons[i].classList.remove('error-radio');
	    }
	  }
	});
	// 添加事件監聽器，當 checkbox 被選擇時，隱藏錯誤消息
	document.addEventListener('change', function(event) {
	  var target = event.target;
	  if (target.type === 'checkbox' && target.classList.contains('check-box-input')) {
	    var errorMessage = document.getElementById('error-message');
	    errorMessage.style.display = 'none';
	    
	    var checkboxes = document.getElementsByClassName('check-box-input');
	    for (var i = 0; i < checkboxes.length; i++) {
	      checkboxes[i].classList.remove('error-checkbox');
	    }
	  }
	});
	
    
</script>


<%@include file="/WEB-INF/views/analyze/footer.jsp" %>