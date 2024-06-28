<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>銷售分析</title>
<style type="text/css">

	.canvas {
	    max-width: 100%;
	    height: auto;
    }
    
    body {
		background-color: #F0F0F0
	}

</style>


</head>
<!-- 
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
 -->
<script src="https://cdn.staticfile.org/Chart.js/3.9.1/chart.js"></script>
<!-- SheetJS library -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.16.9/xlsx.full.min.js"></script>

<body>
	<div class="d-flex bd-highlight px-5">
		<div class="p-2 flex-grow-1 bd-highlight">
			<h2 class="d-flex" id="salesAnalysisTitle"> ${pageTitle}</h2>
		</div>
		<form id="excelForm" method="post" action="${ pageContext.request.contextPath }/mvc/analyze/eccommerce/upload"
			enctype="multipart/form-data">
			<div class="p-2 bd-highlight">
				<div class="input-group d-flex">
				 <!-- 动态设置 ecId 值 -->
	            <input type="hidden" id="ecId" name="ecId" value="${ecId}">
				<!-- 
				 <input type="hidden" name="originalUri" value="${request.getRequestURI()}" />
				  -->
				 <input type="hidden" name="source" value="momo">
					<input type="file" class="form-control" id="uploadFile"
						name="uploadFile" accept=".xlsx" required
						aria-describedby="inputGroupFileAddon04" aria-label="Upload">
					<button class="btn btn-outline-secondary" type="submit"
						id="inputGroupFileAddon04" name="inputGroupFileAddon04">
						<i class="bi bi-upload"></i>
					</button>
				</div>
			</div>
		</form>
		<div class="p-2 bd-highlight">
			<button class="btn btn-outline-secondary  d-flex" type="button"
				onclick="exportFilteredDataToExcel()">
				匯出<i class="bi bi-download"></i>
			</button>
		</div>

	</div>
	
	<div class="d-flex justify-content-end me-5 pe-5">
		<!-- 批次新增商品，匯入成功or失敗訊息 -->
		<c:if test="${not empty successMessage}">
		       <span style="color: red;"> ${successMessage} </span>
		</c:if>
		<c:if test="${not empty errorMessage}">
		      <span style="color: red;"> ${errorMessage} </span>
		</c:if>
	</div>
	
	<div class="container mt-3 mb-5 px-0">
		<!-- 篩選條件 -->
		<div class="row mb-3 d-flex align-items-start">
			<div class="col-md-1">
				<select class="form-select" name='name' id="modelSelect"
					aria-label="Floating label select example">
					<option value="商品" selected>模式</option>
					<option value="商品">商品</option>
					<option value="品牌">品牌</option>
				</select>
			</div>
			<div class="col-md-2">
				<input type="text" class="form-control" id="keyword"
					placeholder="🔍型號/品名/條碼">
			</div>
			<div class="col-md-1">
				<select class="form-select" name='name' id="departmentSelect">
					<option value="" selected>館別</option>
					<option value="餐廚">餐廚</option>
					<option value="烘焙">烘焙</option>
					<option value="旅遊">旅遊</option>
				</select>
			</div>
			<div class="col-md-1">
				<select class="form-select" name='name' id="typeSelect">
					<option value="" selected>分類</option>
					<option value="平底鍋">平底鍋</option>
					<option value="鍋鏟">鍋鏟</option>
					<option value="主廚刀">主廚刀</option>
					<option value="餅乾模">餅乾模</option>
					<option value="麵粉篩">麵粉篩</option>
					<option value="烘焙紙">烘焙紙</option>
					<option value="頸枕">頸枕</option>
					<option value="收納袋">收納袋</option>
					<option value="背包">背包</option>
				</select>
			</div>
			<div class="col-md-1">
				<select class="form-select" name='name' id="brandSelect">
					<option value="" selected>品牌</option>
					<option value="AAA">AAA</option>
					<option value="BBB">BBB</option>
					<option value="CCC">CCC</option>
				</select>
			</div>
			<div class="form-floating col-md-2">
				<input type="date" class="form-control" id="startDate"> 
				<label class="ms-2" for="startDate">開始日期</label>
			</div>
			<div class="form-floating col-md-2">
				<input type="date" class="form-control" id="endDate"> 
				<label class="ms-2" for="endDate">結束日期</label>
			</div>
			<div class="col-md-2 ">
				<button class="btn btn-primary me-2" onclick="filterData()">篩選</button>
				<button class="btn btn-secondary " onclick="clearFilters()">清空</button>
			</div>
		</div>


		<!-- 產品銷售資料表格 -->
		<table class="table mb-3" id="salesTable">
			<!-- 表格標頭 -->
			<thead>
				<tr>
					<th></th>
					<th>型號</th>
					<th>品名</th>
					<th>品牌</th>
					<th>產品館別</th>
					<th>產品分類</th>
					<th>條碼</th>
					<th>平台庫存</th>
					<th>總庫存</th>
					<th>銷售量</th>
					<th>銷售額</th>
					<!-- 其他欄位... -->
				</tr>
			</thead>
			<!-- 表格內容 -->
			<tbody>
				<!-- 資料將在篩選時插入這裡 -->
			</tbody>
			<!-- 表格總計 -->
		    <tfoot>
		        <tr>
		            <td colspan="9">總計：</td>
		            <td id="totalSalesQty" class="text-danger">></td>
		            <td id="totalSalesFigures" class="text-danger">></td>
		             <!-- 如果有其他欄位，調整 colspan 的值 -->
		        </tr>
		    </tfoot>
		</table>
		
		<!-- 品牌銷售資料表格 -->
		<table class="table mb-3" id="brandSalesTable">
			<!-- 表格標頭 -->
			<thead>
				<tr>
					<th></th>
					<th>品牌</th>
					<th>產品館別</th>
					<th>產品分類</th>
					<th>銷售量</th>
					<th>銷售額</th>
					<!-- 其他欄位... -->
				</tr>
			</thead>
			<!-- 表格內容 -->
			<tbody>
				<!-- 資料將在篩選時插入這裡 -->
			</tbody>
			<!-- 表格總計 -->
		    <tfoot>
		        <tr>
		            <td colspan="4">總計：</td>
		            <td id="brandTotalSalesQty" class="text-danger">></td>
		            <td id="brandTotalSalesFigures" class="text-danger">></td>
		             <!-- 如果有其他欄位，調整 colspan 的值 -->
		        </tr>
		    </tfoot>
		</table>
		
	</div>
	
	<script>
		const salesData = ${salesData};
	</script>

	<script>
    // 使用 JavaScript 動態設置 action 属性
    document.getElementById("excelForm").action += "/" + document.getElementById("ecId").value;
	</script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/components/script.js"></script>	

	

</body>



</html>