//-----------------------------------------------------------------------------------------------
// 創建一個折線圖：平台月銷售量趨勢
/*
// 將原始資料，透過電商平台設置資料list
var moData = [];
var pcData = [];
var spData = [];

salesData.forEach(function(entry) {
    if (entry.ecommerce === "Momo") {
        moData.push(entry.salesFigures);
    } else if (entry.ecommerce === "PChome") {
        pcData.push(entry.salesFigures);
    } else if (entry.ecommerce === "蝦皮") {
        spData.push(entry.salesFigures);
    }
});

// 提取唯一的月份
var uniqueMonths = Array.from(new Set(salesData.map(entry => entry.month)));
*/

// 提取唯一的月份並排序
var uniqueMonths = Array.from(new Set(salesData.map(entry => entry.month))).sort();

// 初始化各電商的銷售資料
var moData = Array(uniqueMonths.length).fill(0);
var pcData = Array(uniqueMonths.length).fill(0);
var spData = Array(uniqueMonths.length).fill(0);

// 將資料填入對應的月份位置
salesData.forEach(function(entry) {
    var monthIndex = uniqueMonths.indexOf(entry.month);
    if (entry.ecommerce === "Momo") {
        moData[monthIndex] += entry.sales;
    } else if (entry.ecommerce === "PChome") {
        pcData[monthIndex] += entry.sales;
    } else if (entry.ecommerce === "蝦皮") {
        spData[monthIndex] += entry.sales;
    }
});

const ctxByEcLineChart = document.getElementById('salesByEcLineChart').getContext('2d');
const ecLineLabels = uniqueMonths;
//const salesValues = salesData.map(item => item.salesFigures);
const datactxByEcLineChart = {
  labels: ecLineLabels,
  datasets: [
	  {
	    label: 'Momo',
	    data: moData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(221, 160, 221)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(221, 160, 221, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
  		},
	  {
		label: 'pchome',
	    data: pcData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(30, 144, 255)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(30, 144, 255, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  },
  	{
		label: '蝦皮',
	    data: spData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(255, 222, 173)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(255, 222, 173, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  }
  ]
};

const configctxByEcLineChart = {
  type: 'line', // 设置图表类型
  data: datactxByEcLineChart,  // 设置数据集
	  options: {
	    scales: {
	      y: {
	       beginAtZero: true // 设置 y 轴从 0 开始
	      }
	   }
	  },
};
const myChart = new Chart(ctxByEcLineChart, configctxByEcLineChart);

//------------------------------------------------------------------------------
// 創建一個圓餅圖：平台當月銷售額占比

const ctxByEcPieChart = document.getElementById('salesByEcPieChart').getContext('2d');
const ecPieLabels = salesDataByEc.map(item => item.ecommerce);
const salesByECValues = salesDataByEc.map(item => item.salesFigures);
const dataByEcPieChart = {
  labels: ecPieLabels,
  datasets: [
	  {
	    label: '電商佔比',
	    data: salesByECValues,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgba(221, 160, 221, 0.8)',
	      'rgba(30, 144, 255, 0.8)',
	      'rgba(255, 222, 173, 0.8)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgb(221, 160, 221)',
	      'rgb(30, 144, 255)',
	      'rgb(255, 222, 173)',
	    ],
	    borderWidth: 1     // 设置线条宽度
  		},
  ]
};

const configByEcPieChart = {
  type: 'pie', // 设置图表类型
  data: dataByEcPieChart,  // 设置数据集
  options: {
    scales: {
      y: {
        beginAtZero: true // 设置 y 轴从 0 开始
      }
    }
  },
};
const ecChart = new Chart(ctxByEcPieChart, configByEcPieChart);

//------------------------------------------------------------------------
// 品牌銷售額趨勢分析圖
// 提取唯一的月份並排序
var uniqueMonths2 = Array.from(new Set(salesDataByBrandAndDate.map(entry => entry.month))).sort();

// 初始化各品牌的銷售資料
var aaData = Array(uniqueMonths2.length).fill(0);
var bbData = Array(uniqueMonths2.length).fill(0);
var ccData = Array(uniqueMonths2.length).fill(0);

// 將資料填入對應的月份位置
salesDataByBrandAndDate.forEach(function(entry) {
    var monthIndex2 = uniqueMonths2.indexOf(entry.month);
    if (entry.brand === "AAA") {
        aaData[monthIndex2] += entry.sales;
    } else if (entry.brand === "BBB") {
        bbData[monthIndex2] += entry.sales;
    } else if (entry.brand === "CCC") {
        ccData[monthIndex2] += entry.sales;
    }
});

const ctxByBrandLineChart = document.getElementById('salesByBrandLineChart').getContext('2d');
const brandLineLabels = uniqueMonths;
//const salesValues = salesData.map(item => item.salesFigures);
const datactxByBrandLineChart = {
  labels: brandLineLabels,
  datasets: [
	  {
	    label: 'AAA',
	    data: aaData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(0, 71, 125)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(0, 71, 125, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
  		},
	  {
		label: 'BBB',
	    data: bbData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(255, 191, 0)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(255, 191, 0, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  },
  	{
		label: 'CCC',
	    data: ccData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(204, 119, 34)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(204, 119, 34, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  }
  ]
};

const configctxByBrandLineChartt = {
  type: 'line', // 设置图表类型
  data: datactxByBrandLineChart,  // 设置数据集
	  options: {
	    scales: {
	      y: {
	       beginAtZero: true // 设置 y 轴从 0 开始
	      }
	   }
	  },
};
const myBrandChart = new Chart(ctxByBrandLineChart, configctxByBrandLineChartt);

//------------------------------------------------------------------------------
// 創建一個圓餅圖：品牌當月銷售額占比
const ctxByBrandPieChart = document.getElementById('salesByBrandPieChart').getContext('2d');
const brandPieLabels = salesDataByBrand.map(item => item.brand);
const salesByBrandValues = salesDataByBrand.map(item => item.salesFigures);
const dataByBrandPieChart = {
  labels: brandPieLabels,
  datasets: [
	  {
	    label: '品牌佔比',
	    data: salesByBrandValues,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgba(0, 71, 125, 0.8)',
	      'rgba(255, 191, 0, 0.8)',
	      'rgba(204, 119, 34, 0.8)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgb(0, 71, 125)',
	      'rgb(255, 191, 0)',
	      'rgb(204, 119, 34)',
	    ],
	    borderWidth: 1     // 设置线条宽度
  		},
  ]
};

const configByBrandPieChart = {
  type: 'pie', // 设置图表类型
  data: dataByBrandPieChart,  // 设置数据集
  options: {
    scales: {
      y: {
        beginAtZero: true // 设置 y 轴从 0 开始
      }
    }
  },
};
const brandChart = new Chart(ctxByBrandPieChart, configByBrandPieChart);

//---------------------------------------------------------------------------------
// 分類銷售額趨勢圖
// 提取唯一的月份並排序
var uniqueMonths3 = Array.from(new Set(salesDataByTypeAndDate.map(entry => entry.month))).sort();

// 初始化各電商的銷售資料
var kcData = Array(uniqueMonths3.length).fill(0);
var baData = Array(uniqueMonths3.length).fill(0);
var trData = Array(uniqueMonths3.length).fill(0);

// 將資料填入對應的月份位置
salesDataByTypeAndDate.forEach(function(entry) {
    var monthIndex = uniqueMonths3.indexOf(entry.month);
    if (entry.productDepartment === "餐廚") {
        kcData[monthIndex] += entry.salesFigures;
    } else if (entry.productDepartment === "烘焙") {
        baData[monthIndex] += entry.salesFigures;
    } else if (entry.productDepartment === "旅遊") {
        trData[monthIndex] += entry.salesFigures;
    }
});

const ctxByTypeLineChart = document.getElementById('salesByTypeLineChart').getContext('2d');
const typeLineLabels = uniqueMonths3;
//const salesValues = salesData.map(item => item.salesFigures);
const datactxByTypeLineChart = {
  labels: typeLineLabels,
  datasets: [
	  {
	    label: '餐廚',
	    data: kcData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(107, 160, 127)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(107, 160, 127, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
  		},
	  {
		label: '烘焙',
	    data: baData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(139, 127, 181)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(139, 127, 181, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  },
  	{
		label: '旅遊',
	    data: trData,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgb(164, 129, 27)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgba(164, 129, 27, 0.8)',
	    ],
	    borderWidth: 4     // 设置线条宽度
	  }
  ]
};

const configctxByTypeLineChart = {
  type: 'line', // 设置图表类型
  data: datactxByTypeLineChart,  // 设置数据集
	  options: {
	    scales: {
	      y: {
	       beginAtZero: true // 设置 y 轴从 0 开始
	      }
	   }
	  },
};
const myTypeChart = new Chart(ctxByTypeLineChart, configctxByTypeLineChart);

//------------------------------------------------------------------------------
// 創建一個長條圖：中分類當月銷售額占比
const ctxBySubTtypeBarChart = document.getElementById('salesBySubTypeBarChart').getContext('2d');
const subTypeBarLabels = salesDataBySubType.map(item => item.productType);
const salesBySubTypeValues = salesDataBySubType.map(item => item.salesFigures);
const dataBySubTypeBarChart = {
  labels: subTypeBarLabels,
  datasets: [
	  {
	    label: '中分類銷售額',
	    data: salesBySubTypeValues,
	    backgroundColor: [      // 设置每个柱形图的背景颜色
	      'rgba(181, 205, 73, 0.8)',
	      'rgba(106, 174, 53, 0.8)',
	      'rgba(0, 136, 117, 0.8)',
	      'rgba(0, 137, 143, 0.8)',
	      'rgba(0, 144, 192, 0.8)',
	      'rgba(78, 59, 132, 0.8)',
	      'rgba(119, 104, 174, 0.8)',
	      'rgba(174, 165, 198, 0.8)',
	      'rgba(101, 60, 131, 0.8)',
	      'rgba(157, 107, 168, 0.8)',
	      'rgba(251, 208, 0, 0.8)',
	      'rgba(252, 175, 0, 0.8)',
	      'rgba(202, 110, 37, 0.8)',
	      'rgba(245, 206, 96, 0.8)',
	      'rgba(245, 222, 0, 0.8)',
	    ],
	    borderColor: [     //设置每个柱形图边框线条颜色
	      'rgb(181, 205, 73)',
	      'rgb(106, 174, 53)',
	      'rgb(0, 136, 117)',
	      'rgb(0, 137, 143)',
	      'rgb(0, 144, 192)',
	      'rgb(78, 59, 132)',
	      'rgb(119, 104, 174)',
	      'rgb(174, 165, 198)',
	      'rgb(101, 60, 131)',
	      'rgb(157, 107, 168)',
	      'rgb(251, 208, 0)',
	      'rgb(252, 175, 0)',
	      'rgb(202, 110, 37)',
	      'rgb(245, 206, 96)',
	      'rgb(245, 222, 0)',
	    ],
	    borderWidth: 1     // 设置线条宽度
  		},
  ]
};

const configBySubTypeBarChart = {
  type: 'bar', // 设置图表类型
  data: dataBySubTypeBarChart,  // 设置数据集
  options: {
    scales: {
      y: {
        beginAtZero: true // 设置 y 轴从 0 开始
      }
    }
  },
};
const subTypeChart = new Chart(ctxBySubTtypeBarChart, configBySubTypeBarChart);



/*
function createBarChart() {
    const ctx = document.getElementById('salesBarChart').getContext('2d');
    const productLabels = salesData.map(item => item.productName);
    const salesValues = salesData.map(item => item.sales);

    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: productLabels,
            datasets: [{
                label: '銷售量',
                data: salesValues,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
			
			responsive: true, // 禁用自適應
        	maintainAspectRatio: true // 禁用維持寬高比
            //scales: {
              //  y: {
              //      beginAtZero: true
              //  }
           // }
        }
    });
}

window.addEventListener('resize', function () {
    myChart.resize(); // myChart 是你的 Chart 實例
});


//調用創建圖表的函數： 當頁面載入完成時，調用剛剛創建的圖表函數
document.addEventListener('DOMContentLoaded', function () {
    createBarChart();
});
*/

