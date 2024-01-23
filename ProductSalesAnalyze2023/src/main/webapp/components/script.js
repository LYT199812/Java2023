// 初始化表格 salesTable
let analyzeSalesDatas = [];  // 將 analyzeSalesDatas 放在這裡宣告
function initTable(data) {
    const tableBody = document.getElementById('salesTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // 清空表格

	// 相同型號 合併 銷售數量 + 銷售額
	let productIds = [...new Set(data.map(s => s.productId))];
	analyzeSalesDatas = [];
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
    /*
    // 初始化表格 brandSalesTable (無品牌版本)
	let brandGroups = {};
	function initBrandTable(data) {
	    const tableBody = document.getElementById('brandSalesTable').getElementsByTagName('tbody')[0];
	    tableBody.innerHTML = ''; // 清空表格
	
	brandGroups = {};
	
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

    }*/
    
    // 初始化表格 brandSalesTable
let brandGroups = {};
function initBrandTable(data) {
    const tableBody = document.getElementById('brandSalesTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // 清空表格

    brandGroups = {};

    // 確保 data 是陣列
    if (Array.isArray(data)) {
        // 相同品牌、同館別、同分類 合併 銷售數量 + 銷售額 + 平台庫存 + 總庫存

        data.forEach(sale => {
            let key = `${sale.brand}_${sale.productDepartment}_${sale.productType}_${sale.yoy}`;
            if (!brandGroups[key]) {
                brandGroups[key] = {
                    brand: sale.brand, // 新增品牌資訊
                    department: sale.productDepartment, // 新增部門資訊
                    type: sale.productType, // 新增類型資訊
                    sales: 0,
                    salesFigures: 0,
                    yoy: sale.yoy,
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
            const [brand, department, type, yoy] = key.split('_');

            row.insertCell(0).textContent = ''; // 暫時不處理 number
            row.insertCell(1).textContent = brandGroups[key].brand; // 顯示品牌資訊
            row.insertCell(2).textContent = brandGroups[key].department; // 顯示部門資訊
            row.insertCell(3).textContent = brandGroups[key].type; // 顯示類型資訊
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
            row.insertCell(7).textContent = brandGroups[key].yoy; // 暫時不處理 yoy
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
    const filteredData = analyzeSalesDatas.filter(item => {
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
    
    console.log("Filtered Data:", filteredData); // 新增這行除錯語句
    console.log("brandGroups:", Object.values(brandGroups));
    return filteredData || []; // 確保返回一個陣列
}



/*
// 匯出excel用
function filterData() {
    const keyword = document.getElementById('keyword').value.toLowerCase();
    const departmentSelect = document.getElementById('departmentSelect');
    const typeSelect = document.getElementById('typeSelect');
    const brandSelect = document.getElementById('brandSelect');
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

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

    console.log("Filtered Data:", filteredData); // 新增這行除錯語句

    return filteredData || []; // 確保返回一個陣列
}
*/



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
// 報表匯出
function exportFilteredDataToExcel() {
	console.log("exportFilteredDataToExcel function called");
    // 獲取篩選後的資料
    const filteredData = filterData();
    console.log("filteredData:", filteredData);
    console.log("brandGroups:", Object.values(brandGroups));

    // 檢查是否有符合條件的資料
    if (filteredData && filteredData.length > 0) {
        // 創建一個新的工作簿
        var workbook = XLSX.utils.book_new();

		// 根據篩選模式選擇初始化函式
	    if (document.getElementById('modelSelect').value === '商品') {
	        initTable(filteredData);
	    } else if (document.getElementById('modelSelect').value === '品牌') {
	        initBrandTable(filteredData);
	    }
	    
	    // 設定匯出的資料，這裡假設你的商品模式的資料結構包含加總金額
    	const exportData = document.getElementById('modelSelect').value === '商品' ? 
    	filteredData.concat([productTotal])
    	: Object.values(brandGroups).concat([brandTotal]);


        // 將篩選後的資料轉換成工作表
        var worksheet = XLSX.utils.json_to_sheet(exportData);

        // 將工作表添加到工作簿
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Filtered Data');

        // 將工作簿轉換成二進制數據（Blob）
        //var blobData = XLSX.write(workbook, { bookType: 'xlsx', type: 'blob', mimeType: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

		// 將工作簿轉換成二進制數據（Blob）
	    var blobData = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
	
	    // 將 Blob 資料轉換成 Blob
	    var blob = new Blob([blobData], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

        // 創建一個下載連結
        var link = document.createElement('a');
        link.href = URL.createObjectURL(new Blob([blobData], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' }));


        // 設定下載的檔名
        link.download = 'FilteredData.xlsx';

        // 將連結添加到頁面中並觸發點擊事件
        document.body.appendChild(link);
        link.click();

        // 清理
        document.body.removeChild(link);
    } else {
        console.error("No data to export or filteredData is not an array.");
    }
}

// 計算加總
function calculateTotal(data) {
    let total = {
        sales: 0,
        salesFigures: 0,
        // 其他加總項目...
    };

    data.forEach(item => {
        total.sales += item.sales;
        total.salesFigures += item.salesFigures;
        // 其他加總項目...
    });

    return total;
}

// 獲取篩選後的資料
const filteredData = filterData();

// 計算加總
const productTotal = calculateTotal(filteredData);
const brandTotal = calculateTotal(Object.values(brandGroups));



