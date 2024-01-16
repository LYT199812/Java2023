<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>

<head>
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['table']});
      google.charts.setOnLoadCallback(drawTable);

      function drawTable() {
        var data = new google.visualization.DataTable();
        data.addColumn('number', '型號');
        data.addColumn('string', '品名');
        data.addColumn('string', '品牌');
        data.addColumn('string', '分類');
        data.addColumn('string', '條碼');
        data.addColumn('number', '總庫存');
        data.addColumn('number', '平台庫存');
        data.addColumn('number', '銷售量');
        data.addColumn('number', '銷售額');
        data.addColumn('number', 'YOY');
        data.addRows([
          [101,'鍋鏟', 'A','廚房用品', '012345678', 5, 5,5,{v: 10000, f: '$10,000'}, {v: 128, f: '128%'},],
          [102,'鍋子', 'B','廚房用品', '012345678', 6,5, 5, {v: 10000, f: '$10,000'},{v: 100, f: '100%'},],
          [103,'盤子', 'C','廚房用品', '012345678', 10,5, 5,{v: 10000, f: '$10,000'},{v: 30, f: '30%'},],
          [104,'湯勺', 'D','廚房用品', '012345678', 2, 5,5, {v: 10000, f: '$10,000'},{v: 5, f: '-5%'},]
        ]);

        var table = new google.visualization.Table(document.getElementById('table_div'));

        table.draw(data, {showRowNumber: true, width: '100%', height: '100%'});
      }
    </script>
    
    <style type="text/css">
    	.serch-form-control{
			border-radius:0 0.25rem 0.25rem 0;
		}
		.serch-button-control{
			border-radius:0.25rem 0 0 0.25rem;
		}
		.serch-form-floating{
			width: 100%;
		}
    </style>
    
    <script type="text/javascript">
	    
    	function changeType(){
    		var selectBox = document.getElementById("selectType");
    	    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    	    console.log(selectedValue);
	    }
    
    </script>
    
</head>

<body>
	

<div class="d-flex bd-highlight">
  <div class="p-2 flex-grow-1 bd-highlight"><h2 class="d-flex">MOMO Sales Analysis</h2></div>
  
  <div class="p-2 bd-highlight">
  	<div class="input-group d-flex">
	  <input type="file" class="form-control" id="inputGroupFile04" aria-describedby="inputGroupFileAddon04" aria-label="Upload">
	  <button class="btn btn-outline-secondary" type="button" id="inputGroupFileAddon04"><i class="bi bi-upload"></i></button>
	</div>
  </div>
  
  <div class="p-2 bd-highlight">
  	<button class="btn btn-outline-secondary  d-flex" type="submit" onclick="window.location.href='./download?filename=456.docx'">匯出<i class="bi bi-download"></i></button>
  </div>
  
</div>

<div class="row g-6 my-2 mx-1" >
	
	    <div class="form-floating d-flex mt-0  col-sm-2">
	      <select class="form-select d-flex" id="floatingSelectGrid" aria-label="Floating label select example">
	        <option selected>請選擇</option>
	        <option value="1">商品</option>
	        <option value="2">品牌</option>
	        <option value="3">分類</option>
	      </select>
	      <label for="floatingSelectGrid">模式</label>
	    </div>
	  
   <div class=" d-flex  mt-0  col-sm-2"> 
  
      <button class="btn btn-outline-secondary serch-button-control" type="button" id="button-addon1"><i class="bi bi-search"></i></button>
   	  <div class="form-floating serch-form-floating">  
      <input type="text" class="form-control serch-form-control" id="floatingInputGrid" placeholder="" aria-label="Example text with button addon" aria-describedby="button-addon1" value="">
      <label for="floatingInputGrid">型號/品名/條碼</label>
    </div>
    
  </div>
  
    <div class="form-floating d-flex mt-0  col-sm-2">
      <select class="form-select" id="selectType" aria-label="Floating label select example" 
              onchange="changeType()">
        <option selected>請選擇</option>
        <option value="1">廚房用品</option>
        <option value="2">烘焙用品</option>
        <option value="3">旅遊用品</option>
      </select>
      <label for="floatingSelectGrid">分類</label>
    </div>
  
	
 
    <div class="form-floating d-flex mt-0  col-sm-2">
      <select class="form-select" id="floatingSelectGrid" aria-label="Floating label select example">
        <option selected>請選擇</option>
        <option value="1">One</option>
        <option value="2">Two</option>
        <option value="3">Three</option>
      </select>
      <label for="floatingSelectGrid">品牌</label>
    </div>
  
    <div class="form-floating d-flex mt-0  col-sm-2">
      <input type="date" class="form-control" id="floatingInputGrid" placeholder="name@example.com" value="mdo@example.com">
      <label for="floatingInputGrid">銷售起始日</label>
    </div>
  
    <div class="form-floating d-flex mt-0  col-sm-2">
      <input type="date" class="form-control" id="floatingInputGrid" placeholder="name@example.com" value="mdo@example.com">
      <label for="floatingInputGrid">銷售結束日</label>
    </div>
  
 
</div>
	
<div id="table_div"></div>



</body>


<%@include file="/WEB-INF/views/analyze/footer.jsp" %>