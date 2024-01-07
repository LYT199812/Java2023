<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/product/header.jsp" %>

<div class="container mt-4">
    <h2 class="mb-4">新增商品</h2>
    <form enctype="multipart/form-data">
        <div class="mb-3">
            <label for="productName" class="form-label">商品名稱</label>
            <input type="text" class="form-control" id="productName" name="productName" placeholder="輸入商品名稱">
        </div>
        <div class="mb-3">
            <label for="productPrice" class="form-label">商品價格</label>
            <input type="number" class="form-control" id="productPrice" name="productPrice" placeholder="輸入商品價格">
        </div>
        <div class="mb-3">
            <label for="productDescription" class="form-label">商品描述</label>
            <textarea class="form-control" id="productDescription" name="productDescription" rows="3" placeholder="輸入商品描述"></textarea>
        </div>
        
        <label for="productImage" class="form-label">上傳圖片</label>
        <div class="row mb-3" id="updatebox">
	        <label for="file" class="col-sm-4">
	            <div class="panel updatepanel">
	                <div class="remove-icon bi bi-x-lg"></div>
	                <input type="file" id="file" class="file-input"  hidden=""/>
	                <div class="addbox">
		                <span class="bi bi-plus">
	                	</span>
                	</div>
		            <img class="updateimg img-responsive" style="width: inherit; height: 210px; display: none;">
	            </div>
	        </label>
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
	
</style>
	
<!-- 自定义js -->
<script>
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