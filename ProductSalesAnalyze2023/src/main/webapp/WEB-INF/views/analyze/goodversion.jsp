<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>銷售分析</title>
</head>

<body>
	<div class="d-flex bd-highlight px-5">
		<div class="p-2 flex-grow-1 bd-highlight">
			<h2 class="d-flex">MOMO Sales Analysis</h2>
		</div>
		<form id="excelForm" method="post" action="../upload"
			enctype="multipart/form-data">
			<div class="p-2 bd-highlight">
				<div class="input-group d-flex">
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
			<button class="btn btn-outline-secondary  d-flex" type="submit"
				onclick="window.location.href='./download?filename=456.docx'">
				匯出<i class="bi bi-download"></i>
			</button>
		</div>

	</div>


	<div class="container mt-3">
		<!-- 篩選條件 -->
		<div class="row mb-3 d-flex align-items-start">
			<div class="col-md-1">
				<select class="form-select" name='name' id="modelSelect"
					aria-label="Floating label select example">
					<option value="" selected>模式</option>
					<option value="商品">商品</option>
					<option value="品牌">品牌</option>
					<option value="分類">分類</option>
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
					<option value="鍋鏟">鍋鏟</option>
					<option value="桿麵棍">桿麵棍</option>
					<option value="頸枕">頸枕</option>
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
				<input type="date" class="form-control" id="startDate"> <label
					for="startDate">開始日期</label>
			</div>
			<div class="form-floating col-md-2">
				<input type="date" class="form-control" id="endDate"> <label
					for="endDate">結束日期</label>
			</div>
			<div class="col-md-2 ">
				<button class="btn btn-primary me-2" onclick="filterData()">篩選</button>
				<button class="btn btn-secondary " onclick="clearFilters()">清空</button>
			</div>
		</div>

		<!-- 銷售資料表格 -->
		<table class="table" id="salesTable">
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
					<th>銷售日期</th>
					<th>YOY</th>
					<!-- 其他欄位... -->
				</tr>
			</thead>
			<!-- 表格內容 -->
			<tbody>
				<!-- 資料將在篩選時插入這裡 -->
			</tbody>
		</table>
	</div>


    <h5 id="totalQty" class="text-center text-danger"></h5>

	<script>
		const salesData = ${salesData};
	</script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/components/script.js"></script>	
</body>

</html>