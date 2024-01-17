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
    
    <form method="post" action="./addProduct" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="productId" class="form-label">商品ID</label>
            <input type="text" class="form-control" id="productId" name="productId" placeholder="輸入商品ID">
        </div>
        <div class="mb-3">
            <label for="productName" class="form-label">商品名稱</label>
            <input type="text" class="form-control" id="productName" name="productName" placeholder="輸入商品名稱">
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">商品價格</label>
            <input type="number" class="form-control" id="price" name="price" placeholder="輸入商品價格">
        </div>
        <div class="mb-3">
            <label for="barcode" class="form-label">商品條碼</label>
            <input type="text" class="form-control" id="barcode" name="barcode" placeholder="輸入商品條碼">
        </div>
        <div class="mb-3">
            <label for="brand" class="form-label">品牌</label>
            <input type="text" class="form-control" id="brand" name="brand" placeholder="輸入商品品牌">
        </div>
        <div class="mb-3">
            <label for="productPrice" class="form-label">館別</label>
            <input type="text" class="form-control" id="productDepartment" name="productDepartment" placeholder="輸入商品館別">
        </div>
        <div class="mb-3">
            <label for="productPrice" class="form-label">分類</label>
            <input type="text" class="form-control" id="productType" name="productType" placeholder="輸入商品分類">
        </div>
        <div class="mb-3">
            <label for="productDescription" class="form-label">商品描述</label>
            <textarea class="form-control" id="productDescription" name="productDescription" rows="3" placeholder="輸入商品描述"></textarea>
        </div>
        
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
        
        <div class="mb-3">
            <label for="productLaunch" class="form-label">是否立即上架</label>
            <div required>
	            <input type="radio" id="isLaunch" name="isLaunch" value="yes" >
	            <label for="productisLaunch">是</label>
	            <input type="radio" id="isLaunch" name="isLaunch" value="no">
	        	<label for="productnoLaunch">否</label>
        	</div>
        </div>
        
        <button type="submit" class="btn btn-primary mb-5">新增商品</button>
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
	
<!-- 自定义js -->
<script>
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
</script>


<%@include file="/WEB-INF/views/analyze/footer.jsp" %>