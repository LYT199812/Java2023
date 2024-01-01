// 假設你有一個名為salesData的陣列，內含銷售資料物件
const salesData = [
    { number: 1, productId: 'A101', productName: '好用鍋鏟', brand: 'AAA', productDepartment: '餐廚', productType: '鍋鏟', 
    productEAN: '12345678' , ecQuantity: 20 , quantity: 50 , sales: 3 , salesFigures: 5000, date: '2023-12-03', yoy: 50 },

    { number: 2, productId: 'B101', productName: '好用桿麵棍', brand: 'BBB', productDepartment: '烘焙', productType: '桿麵棍', 
    productEAN: '12348765' , ecQuantity: 20 , quantity: 50 , sales: 3 , salesFigures: 5000, date: '2023-08-13', yoy: 50 },

    { number: 3, productId: 'B201', productName: '好用擠花袋', brand: 'BBB', productDepartment: '烘焙', productType: '擠花袋', 
    productEAN: '87654321' , ecQuantity: 20 , quantity: 50 , sales: 3 , salesFigures: 5000, date: '2023-05-10', yoy: 50 },
    
    // 其他資料...
    // 其他資料...
    // 其他資料...
    // 其他資料...
];

// 初始化表格
function initTable(data) {
    const tableBody = document.getElementById('salesTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = ''; // 清空表格

    // 將資料插入表格
    data.forEach(item => {
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
        row.insertCell(11).textContent = item.date;
        row.insertCell(12).textContent = item.yoy;
        // 插入其他欄位...
    });
}

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
        //const modelSelectMatch = !modelSelect || item.product.toLowerCase().includes(modelSelect.value.toLowerCase());
        const keywordMatch = !keyword || item.productId.toLowerCase().includes(keyword) || item.productName.toLowerCase().includes(keyword) || item.productEAN.toLowerCase().includes(keyword);
        const departmentSelectMatch = !departmentSelect || item.productDepartment.toLowerCase().includes(departmentSelect.value.toLowerCase());
        const typeSelectMatch = !typeSelect || item.productType.toLowerCase().includes(typeSelect.value.toLowerCase());
        const brandSelectMatch = !typeSelect || item.brand.toLowerCase().includes(brandSelect.value.toLowerCase());
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
    document.getElementById('modelSelect').selectedIndex = 0;
    document.getElementById('departmentSelect').selectedIndex = 0;
    document.getElementById('typeSelect').selectedIndex = 0;
    document.getElementById('brandSelect').selectedIndex = 0;
    document.getElementById('keyword').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    initTable(salesData);

}


// 初始載入時顯示所有資料
initTable(salesData);

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

