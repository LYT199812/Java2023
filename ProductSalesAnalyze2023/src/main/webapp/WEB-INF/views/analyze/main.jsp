<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="https://cdn.staticfile.org/Chart.js/3.9.1/chart.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<style>
    	body {
            font-family: Arial, sans-serif;
            background-color: #F0F0F0;
            margin: 0;
            padding: 0;
        }
    	.chartBox {
	    	background-color: white;
            padding: 10px;
            padding-top: 10px;
            padding-bottom: 10px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 500px;
            height: auto;
    	}
    	h1{
    		color: #0093D6
    	}
    </style>

<div class="container mt-4 mb-5">
    <div class="row">
	    <div class="col chartBox me-4">
			<h2>總銷售額</h2>
			<h1>${ totalSalesFigures }</h1>			
		</div>
	    <div class="col chartBox me-4">
	    	<h2>總訂單數</h2>
	    	<h1>${ orderCount }</h1>
	    </div>
	    <div class="col chartBox me-4">
	    	<h2>平均客單</h2>
	    	<h1>${ AOV }</h1>
	    </div>
	    <div class="col chartBox"><h2>月增率</h2></div>
   </div>
   <div class="row mt-4">
	    <div class="col chartBox me-4">
	    	<div class="d-flex ms-2 mt-3 mb-5">
	    		<h3><i class="bi bi-graph-up"></i> 各平台月銷售量趨勢</h3>
	    	</div>
	      	<canvas class="mt-2" id="salesByEcLineChart" width="400" height="200"></canvas>
	    </div>
	    <div class="col chartBox">
	      	<div class="d-flex ms-2 my-3">
	    		<h3><i class="bi bi-pie-chart"></i> 各平台當月銷售額</h3>
	    	</div>
	    	<div class="px-5 mx-5 pb-5">
	      		<canvas id="salesByEcPieChart" width="50" height="25"></canvas>
	      	</div>
	    </div>
  </div>
   <div class="row mt-4">
	    <div class="col chartBox chartBox me-4">
	      	<div class="d-flex ms-2 mt-3 mb-5">
	    		<h3><i class="bi bi-graph-up"></i> 各品牌月銷售量趨勢</h3>
	    	</div>
	      	<canvas class="mt-2" id="salesByBrandLineChart" width="400" height="200"></canvas>
	    </div>
	    <div class="col chartBox">
	      	<div class="d-flex ms-2 my-3">
	    		<h3><i class="bi bi-pie-chart"></i> 各品牌當月銷售額</h3>
	    	</div>
	    	<div class="px-5 mx-5 pb-5">
	      		<canvas id="salesByBrandPieChart" width="50" height="25"></canvas>
	      	</div>
	    </div>
  </div>
   <div class="row mt-4">
   		<div class="col chartBox chartBox me-4">
	    	<div class="d-flex ms-2 mt-3 mb-3">
	    		<h3><i class="bi bi-graph-up"></i> 各大分類月銷售量趨勢</h3>
	    	</div>
	      	<canvas class="mt-2" id="salesByTypeLineChart" width="400" height="200"></canvas>
	    </div>
	    <div class="col chartBox">
	     	<div class="d-flex ms-2 my-3">
	    		<h3><i class="bi bi-bar-chart-line"></i> 各中分類當月銷售額</h3>
	    	</div>
	    	<div class="mt-2 px-3">
	      		<canvas id="salesBySubTypeBarChart" width="50" height="25"></canvas>
	      	</div>
	    </div>
  </div>
</div>   



<script>
		const salesData = ${salesDataByEcDateForCharts};
		const salesDataByEc = ${salesDataByEcForCharts};
		const salesDataByBrandAndDate = ${salesDataByBrandAndDateForCharts};
		const salesDataByBrand = ${salesDataByBrandForCharts};
		const salesDataByTypeAndDate = ${salesDataByTypeAndDateForCharts};
		const salesDataBySubType = ${salesDataBySubTypeForCharts};
</script>

<script type="text/javascript" src="<%=request.getContextPath()%>/components/scriptchart.js"></script>	


<%@include file="/WEB-INF/views/analyze/footer.jsp" %>