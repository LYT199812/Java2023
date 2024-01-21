// 初始化表格 salesTable
function initTable(data) {
    const tableBody = document.getElementById('salesTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // 清空表格

	// 相同型號 合併 銷售數量 + 銷售額
	let productIds = [...new Set(data.map(s => s.productId))];
	let analyzeSalesDatas = [];
	for (let productId of productIds) {
	    let salesList = data.filter(s => s.productId === productId);
	    let qty = salesList.reduce((sum, s) => sum + s.sales, 0);
	    let price = salesList.reduce((sum, s) => sum + s.salesFigures, 0);
	    let salesData = { ...salesList[0] }; 
	    salesData.sales = qty;
	    salesData.salesFigures = price;
	    analyzeSalesDatas.push(salesData);
	}
    //console.log(analyzeSalesDatas);

    // 將資料插入表格
    analyzeSalesDatas.forEach(item => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = item.number;
        row.insertCell(1).textContent = item.productId;
        row.insertCell(2).textContent = item.productName;
        row.insertCell(3).textContent = item.brand;
        row.insertCell(4).textContent = item.productDepartment;
        row.insertCell(5).textContent = item.productType;
        row.insertCell(6).textContent = item.productEAN;
        row.insertCell(7).textContent = item.ecQuantity;
        row.insertCell(8).textContent = item.quantity;
        row.insertCell(9).textContent = item.sales;
        row.insertCell(10).textContent = item.salesFigures;
        //row.insertCell(11).textContent = item.date;
        const dateCell = row.insertCell(11);
	    dateCell.textContent = item.date;
	    dateCell.style.display = 'none'; // 隱藏 date 欄位
	    
        row.insertCell(12).textContent = item.yoy;
        // 插入其他欄位...
    });
    
    // 處理總計
    // 總庫存
    let quantitys = 0;
    analyzeSalesDatas.forEach(item => {
		quantitys += item.quantity;
	});
	document.getElementById('totalQty').innerText = quantitys;
	//總銷量
	let salesQuantitys = 0;
    analyzeSalesDatas.forEach(item => {
		salesQuantitys += item.sales;
	});
	document.getElementById('totalSalesQty').innerText = salesQuantitys;
	//總銷售
	let salesFigures = 0;
    analyzeSalesDatas.forEach(item => {
		salesFigures += item.salesFigures;
	});
	document.getElementById('totalSalesFigures').innerText = salesFigures;
}

//---------------------------------------------------------------------------------------
    // 初始化表格 brandSalesTable
	function initBrandTable(data) {
	    const tableBody = document.getElementById('brandSalesTable').getElementsByTagName('tbody')[0];
	    tableBody.innerHTML = ''; // 清空表格
	
	let brandGroups = {};
	
	// 確保 data 是陣列
    if (Array.isArray(data)) {
        // 相同品牌、同館別、同分類 合併 銷售數量 + 銷售額 + 平台庫存 + 總庫存
        
        data.forEach(sale => {
            let key = `${sale.brand}_${sale.productDepartment}_${sale.productType}`;
            if (!brandGroups[key]) {
                brandGroups[key] = {
                    sales: 0,
                    salesFigures: 0,
                    //ecQuantity: 0,
                    //quantity: 0
                };
            }
            brandGroups[key].sales += sale.sales;
            brandGroups[key].salesFigures += sale.salesFigures;
            //brandGroups[key].ecQuantity += sale.ecQuantity;
            //brandGroups[key].quantity += sale.quantity;
        });

        // 將資料插入表格
        Object.keys(brandGroups).forEach(key => {
            const row = tableBody.insertRow();
            const [brand, department, type] = key.split('_');

            row.insertCell(0).textContent = ''; // 暫時不處理 number
            row.insertCell(1).textContent = brand;
            row.insertCell(2).textContent = department;
            row.insertCell(3).textContent = type;
            //row.insertCell(4).textContent = '';
            //row.insertCell(5).textContent = '';
            row.insertCell(4).textContent = brandGroups[key].sales;
            row.insertCell(5).textContent = brandGroups[key].salesFigures;
            // 插入其他欄位...

            // 處理 date
            const dateCell = row.insertCell(6);
            dateCell.textContent = ''; // 暫時不處理 date
            dateCell.style.display = 'none'; // 隱藏 date 欄位

            // 處理 yoy
            row.insertCell(7).textContent = ''; // 暫時不處理 yoy
            // 插入其他欄位...
        });
    } else {
        console.error("Data is not an array.");
    }
	
	// 處理總計
    // 總銷量
    let salesQuantitys = 0;
    Object.values(brandGroups).forEach(item => {
        salesQuantitys += item.sales;
    });
    document.getElementById('brandTotalSalesQty').innerText = salesQuantitys;

    // 總銷售
    let salesFigures = 0;
    Object.values(brandGroups).forEach(item => {
        salesFigures += item.salesFigures;
    });
    document.getElementById('brandTotalSalesFigures').innerText = salesFigures;

	
	/*
		// 相同品牌 合併 銷售數量 + 銷售額 + 平台庫存 + 總庫存
	    let brandGroups = {};
	    data.forEach(sale => {
	        let brand = sale.brand;
	        if (!brandGroups[brand]) {
	            brandGroups[brand] = {
	                sales: 0,
	                salesFigures: 0,
	                ecQuantity: 0,
	                quantity: 0
	            };
	        }
	        brandGroups[brand].sales += sale.sales;
	        brandGroups[brand].salesFigures += sale.salesFigures;
	        brandGroups[brand].ecQuantity += sale.ecQuantity;
	        brandGroups[brand].quantity += sale.quantity;
	    });
	    //console.log(analyzeSalesDatas);
	
	    // 將資料插入表格
	    Object.keys(brandGroups).forEach(brand => {
        const row = tableBody.insertRow();
        row.insertCell(0).textContent = ''; // 暫時不處理 number
        row.insertCell(1).textContent = brand;
        row.insertCell(2).textContent = ''; // 暫時不處理 productDepartment
        row.insertCell(3).textContent = ''; // 暫時不處理 productType
        row.insertCell(4).textContent = brandGroups[brand].ecQuantity;
        row.insertCell(5).textContent = brandGroups[brand].quantity;
        row.insertCell(6).textContent = brandGroups[brand].sales;
        row.insertCell(7).textContent = brandGroups[brand].salesFigures;
        // 插入其他欄位...

        // 處理 date
        const dateCell = row.insertCell(8);
        dateCell.textContent = ''; // 暫時不處理 date
        dateCell.style.display = 'none'; // 隱藏 date 欄位

        // 處理 yoy
        row.insertCell(9).textContent = ''; // 暫時不處理 yoy
        // 插入其他欄位...
    });
    */
    } 
    
//----------------------------------------------------------------------------------   
//變換表格模式
function switchTableMode(mode) {
    // 隱藏所有表格
    document.getElementById('salesTable').style.display = 'none';
    document.getElementById('brandSalesTable').style.display = 'none';

    // 根據模式顯示相應表格
    if (mode === '商品') {
        document.getElementById('salesTable').style.display = 'table';
        // 初始化商品表格
        initTable(salesData);
    } else if (mode === '品牌') {
        document.getElementById('brandSalesTable').style.display = 'table';
       // 初始化品牌表格，確保 data 是陣列
        if (Array.isArray(salesData)) {
            initBrandTable(salesData);
        } else {
            console.error("Data is not an array.");
        }
    }
}

// 在頁面載入完成後，綁定事件處理函數
document.addEventListener('DOMContentLoaded', function () {
    // 預設顯示商品表格
    switchTableMode('商品');
    
    // 獲取選擇框元素
    var modeSelect = document.getElementById('modelSelect');

    // 監聽選擇框的變化事件
    modeSelect.addEventListener('change', function () {
        // 獲取選擇的值
        var selectedMode = modeSelect.value;

        // 調用切換表格模式的函數
        switchTableMode(selectedMode);
    });
});

//-----------------------------------------------------------------------------
// 根據篩選條件更新表格
/*
function filterData() {
    //const modelSelect = document.getElementById('modelSelect');
    const keyword = document.getElementById('keyword').value.toLowerCase();
    const departmentSelect = document.getElementById('departmentSelect');
    const typeSelect = document.getElementById('typeSelect');
    const brandSelect = document.getElementById('brandSelect');
    // const selectedValue = modelSelect.options[modelSelect.selectedIndex].value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // 使用filter函數篩選資料
    const filteredData = salesData.filter(item => {
        //const modelSelectMatch = !modelSelect || item.product.toLowerCase().includes(modelSelect.value.toLowerCase());
        const keywordMatch = !keyword || item.productId.toLowerCase().includes(keyword) || item.productName.toLowerCase().includes(keyword) || item.productEAN.toLowerCase().includes(keyword);
        const departmentSelectMatch = !departmentSelect || item.productDepartment.toLowerCase().includes(departmentSelect.value.toLowerCase());
        const typeSelectMatch = !typeSelect || item.productType.toLowerCase().includes(typeSelect.value.toLowerCase());
        const brandSelectMatch = !brandSelect || item.brand.toLowerCase().includes(brandSelect.value.toLowerCase());
        const dateInRange = (!startDate || item.date >= startDate) && (!endDate || item.date <= endDate);
        // const keywordMatchSelect1 = !modelSelect || item.product.toLowerCase().includes(selectedValue.toLowerCase());
        // const keywordMatch = !keyword1 || item.product.toLowerCase().includes(keyword1);
        //const keywordMatch2 = !keyword || item.code.toString().includes(keyword); //數字的寫法
        return dateInRange && keywordMatch && departmentSelectMatch && typeSelectMatch && brandSelectMatch;
    });

    // 更新表格
    initTable(filteredData);
}

function clearFilters() {
    //document.getElementById('modelSelect').selectedIndex = 0;
    document.getElementById('departmentSelect').selectedIndex = 0;
    document.getElementById('typeSelect').selectedIndex = 0;
    document.getElementById('brandSelect').selectedIndex = 0;
    document.getElementById('keyword').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    initTable(salesData);

}*/

// 根據篩選條件更新表格
function filterData() {
    //const modelSelect = document.getElementById('modelSelect');
    const keyword = document.getElementById('keyword').value.toLowerCase();
    const departmentSelect = document.getElementById('departmentSelect');
    const typeSelect = document.getElementById('typeSelect');
    const brandSelect = document.getElementById('brandSelect');
    // const selectedValue = modelSelect.options[modelSelect.selectedIndex].value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    // 使用filter函數篩選資料
    const filteredData = salesData.filter(item => {
        const isProductMode = document.getElementById('modelSelect').value === '商品';
        const keywordMatch = isProductMode ? (
            !keyword || item.productId.toLowerCase().includes(keyword) || item.productName.toLowerCase().includes(keyword) || item.productEAN.toLowerCase().includes(keyword)
        ) : true;

        const departmentSelectMatch = !departmentSelect || item.productDepartment.toLowerCase().includes(departmentSelect.value.toLowerCase());
        const typeSelectMatch = !typeSelect || item.productType.toLowerCase().includes(typeSelect.value.toLowerCase());
        const brandSelectMatch = !brandSelect || item.brand.toLowerCase().includes(brandSelect.value.toLowerCase());
        const dateInRange = (!startDate || item.date >= startDate) && (!endDate || item.date <= endDate);

        return dateInRange && keywordMatch && departmentSelectMatch && typeSelectMatch && brandSelectMatch;
    });

    // 更新表格
    if (document.getElementById('modelSelect').value === '商品') {
        initTable(filteredData);
    } else if (document.getElementById('modelSelect').value === '品牌') {
        initBrandTable(filteredData);
    }
}

function clearFilters() {
    //document.getElementById('modelSelect').selectedIndex = 0;
    document.getElementById('departmentSelect').selectedIndex = 0;
    document.getElementById('typeSelect').selectedIndex = 0;
    document.getElementById('brandSelect').selectedIndex = 0;
    document.getElementById('keyword').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    
    // 清空表格
    if (document.getElementById('modelSelect').value === '商品') {
        initTable(salesData);
    } else if (document.getElementById('modelSelect').value === '品牌') {
        initBrandTable(salesData);
    }
}

// 初始載入時顯示所有資料
initTable(salesData);

//-----------------------------------------------------------------------------------------------
//圖表測試
// 創建一個柱狀圖

const ctx = document.getElementById('salesBarChart').getContext('2d');
const productLabels = salesData.map(item => item.productName);
const salesValues = salesData.map(item => item.sales);
const data = {
  labels: productLabels,
  datasets: [{
    label: '分類銷售量',
    data: salesValues,
    backgroundColor: [      // 设置每个柱形图的背景颜色
      'rgba(255, 99, 132, 0.2)',
      'rgba(255, 159, 64, 0.2)',
      'rgba(255, 205, 86, 0.2)',
      'rgba(75, 192, 192, 0.2)',
      'rgba(54, 162, 235, 0.2)',
      'rgba(153, 102, 255, 0.2)',
      'rgba(201, 203, 207, 0.2)'
    ],
    borderColor: [     //设置每个柱形图边框线条颜色
      'rgb(255, 99, 132)',
      'rgb(255, 159, 64)',
      'rgb(255, 205, 86)',
      'rgb(75, 192, 192)',
      'rgb(54, 162, 235)',
      'rgb(153, 102, 255)',
      'rgb(201, 203, 207)'
    ],
    borderWidth: 1     // 设置线条宽度
  }]
};
const config = {
  type: 'bar', // 设置图表类型
  data: data,  // 设置数据集
  options: {
    scales: {
      y: {
        beginAtZero: true // 设置 y 轴从 0 开始
      }
    }
  },
};
const myChart = new Chart(ctx, config);








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



//-----------------------------------------------------------------------------------------
// POI 
/*
document.getElementById('inputGroupFileAddon04').addEventListener('click', function () {
    var fileInput = document.getElementById('uploadFile');
    var file = fileInput.files[0];

    if (file) {
        var formData = new FormData();
        formData.append('uploadFile', uploadFile);

        fetch('../upload', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(result => {
            alert(result);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    } else {
        alert('Please choose a file.');
    }
});
*/

/*
//POI2
document.getElementById('excelForm').addEventListener('submit', function (e) {
    e.preventDefault();
    var form = e.target;
    var formData = new FormData(form);

    fetch(form.action, {
        method: form.method,
        body: formData
    })
    .then(response => response.text())
    .then(result => {
        alert(result); // 顯示從後端接收到的訊息
    })
    .catch(error => {
        console.error('Error:', error);
    });
});
*/
