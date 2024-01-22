<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="/WEB-INF/views/analyze/ecWebsite/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	body {
		background-color: #F0F0F0
	}
</style>

<style>
        body {
            background-color: #F0F0F0
        }
    </style>
    
    <style>
        body {
            background-color: #F0F0F0
        }
    </style>
    
    <div class="w-75 bg-white mt-3" style="margin: 0 auto; border: 1px; padding: 15px;">
        <ul class="nav nav-tabs justify-content-center" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home" type="button" role="tab" aria-controls="home" aria-selected="true">新增員工</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">員工設定</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="contact-tab" data-bs-toggle="tab" data-bs-target="#contact" type="button" role="tab" aria-controls="contact" aria-selected="false">身分設定</button>
            </li>
        </ul>
        <div class="tab-content bg-white mt-3" id="myTabContent">
            <!-- 新增員工 -->
            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                <form id="addEmployeeForm">
                    <div class="mb-3">
                        <label for="username" class="form-label">員工姓名：</label>
                        <input type="text" class="form-control" id="username" name="username">
                        <label for="userPassword" class="form-label">登入密碼：</label>
                        <input type="text" class="form-control" id="password" name="password">
                    </div>
                    <div class="mb-3">
                        <label for="levelId" class="form-label">職務權限：</label>
                        <select class="form-select" id="levelId" name="levelId">
                        <c:forEach var="user" items="${ userList }">
                            <option value="${user.levelId }">${user.level.levelName } </option>
                            <!-- 
                            <option value="1">分析人員</option>
                            <option value="2">商品管理人員</option>
                             -->
                        </c:forEach>
                        </select>
                    </div>
                    <button type="button" class="btn btn-primary" onclick="addEmployee()">新增員工</button>
                </form>
            </div>
    
            <!-- 員工設定 -->
            <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                <h2 class="mt-4">員工權限列表</h2>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">員工ID</th>
                            <th scope="col">員工姓名</th>
                            <th scope="col">職務權限</th>
                            <th scope="col">操作</th>
                        </tr>
                    </thead>
                    <tbody id="userListTable">
                    
                        <tr>
				            <td>101</td>
				            <td>John</td>
				            <td>主管</td>
				            <td>
				                <button class="btn btn-warning btn-sm" onclick="editEmployee(${user.userId})">編輯</button>
				                <button class="btn btn-danger btn-sm" onclick="deleteEmployee(${user.userId})">刪除</button>
				            </td>
				        </tr>
                    
                    </tbody>
                </table>
            </div>
    
            <!-- 身分設定 -->
            <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">...</div>
        </div>
    </div>
    
    
    <!-- 編輯員工的 Modal 彈出視窗 -->
    <div class="modal fade" id="editEmployeeModal" tabindex="-1" aria-labelledby="editEmployeeModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editEmployeeModalLabel">編輯員工</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editEmployeeForm">
                        <div class="mb-3">
                            <label for="editUserName" class="form-label">員工姓名：</label>
                            <input type="text" class="form-control" id="editUserName" name="editUserName">
                        </div>
                        <div class="mb-3">
                            <label for="editUserLevel" class="form-label">權限角色：</label>
                            <select class="form-select" id="editUserLevel" name="editUserLevel">
                                <option value="3">主管</option>
                                <option value="1">分析人員</option>
                                <option value="2">管理人員</option>
                            </select>
                        </div>
                        <input type="hidden" id="editUserId" name="editUserId">
                        <button type="button" class="btn btn-primary btn-update-employee" onclick="updateEmployee(${user.userId})">更新員工</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    
    <script>
        function addEmployee() {
        	var username = $("#username").val();
            var password = $("#password").val();
            var levelId = $("#levelId").val();

            var userData = {
                username: username,
                password: password,
                levelId: levelId
            };

            // 使用Ajax發送請求到後端新增員工
            $.ajax({
                type: "POST", // 使用POST方法表示新增
                contentType: "application/json",
                url: "/ProductSalesAnalyze2023/mvc/user/mantain", // 修改為你的後端控制器的URL
                data: JSON.stringify(userData),
                success: function (response) {
                	
                	// 新增成功後的處理，例如關閉Modal等
                    console.log("新增員工成功", response);
                    // 重新載入員工列表
                    loadEmployeeList();
                    // 清空新增員工表單
                    clearAddEmployeeForm();
                },
                error: function (error) {
                    console.error("新增員工失敗", error);
                    // 處理新增失敗的情況
                }
            });
        }
    
        function editEmployee(userId) {
            // 使用Ajax發送請求到後端獲取員工資訊
            $.ajax({
                type: "GET",
                url: "/ProductSalesAnalyze2023/mvc/user/mantain/" + userId, // 根据你的后端路由来修改URL
                success: function (user) {
                	console.log(user);
                    // 將員工資訊填入編輯表單
                    $("#editUserId").val(user.userId);
                    $("#editUserName").val(user.username);
                    $("#editUserLevel").val(user.levelId);

                    // 切換按鈕為"更新員工"
                    $(".btn-update-employee").show();

                    // 彈出編輯員工的 Modal
                    $("#editEmployeeModal").modal("show");
                },
                error: function (error) {
                    console.error("獲取員工信息失敗", error);
                    // 處理獲取失敗的情況
                }
            });
        }

    
        function deleteEmployee(userId) {
        	// 使用Ajax發送DELETE請求到後端刪除員工
            $.ajax({
                type: "DELETE",
                url: "/ProductSalesAnalyze2023/mvc/user/mantain/" + userId, 
                success: function (response) {
                    console.log(response);
                    // 刪除成功後的處理，例如刷新列表等
                    loadEmployeeList();
                },
                error: function (error) {
                    console.error("刪除員工失敗", error);
                    // 處理刪除失敗的情況
                }
            });
        }
    
        
        function updateEmployee(userId) {
            var userId = $("#editUserId").val();
            var username = $("#editUserName").val();
            var levelId = $("#editUserLevel").val();
    
            if (userId && username && levelId) {
                var userData = new Object();
                userData.userId = userId;
                userData.username = username;
                userData.levelId = levelId;
                console.log(JSON.stringify(userData));
                // 使用Ajax發送請求到後端更新員工
                $.ajax({
                    type: "PUT", // 使用PUT方法表示更新
                    contentType: "application/json",
                    url: "/ProductSalesAnalyze2023/mvc/user/mantain/" + userId, // 修改為你的後端控制器的URL
                    data: JSON.stringify(userData),
                    success: function (users) {
                    	console.log(users);
                        // 更新成功後的處理，例如關閉Modal等
                        // ...
                        $("#editEmployeeModal").modal("hide");
                        loadEmployeeList(); // 重新載入員工列表
                    },
                    error: function (error) {
                        console.error("更新員工失敗", error);
                        // 處理更新失敗的情況
                    }
                });
            } else {
                alert("請填寫完整員工資訊");
            }
        }
    
        function clearAddEmployeeForm() {
            $("#username").val("");
            $("#password").val("");
            $("#levelId").val("");
        }
    	
        function loadEmployeeList() {
            // 使用Ajax發送請求到後端重新載入員工列表
            $.ajax({
                type: "GET",
                url: "/ProductSalesAnalyze2023/mvc/user/getAllUsers",  // 根据你的后端路由来修改URL
                //headers: {
                //    'Accept': 'application/json' // 期望的回應內容是 JSON
                //},
                success: function (response) {
                    // 清空员工列表
                	console.log(response);
                    //$("#userListTable").empty();
                    $("#userListTable").html("");
                    console.log($("#userListTable").html());

                    // 遍历响应中的员工数据，并添加到列表中
                    for (var i = 0; i < response.length; i++) {
                        var user = response[i];
                        $("#userListTable").append(`<tr><td>${user.userId}</td><td>${user.username}</td><td>${user.level.levelName}</td><td><button class="btn btn-warning btn-sm" onclick="editEmployee(${user.userId})">編輯</button><button class="btn btn-danger btn-sm" onclick="deleteEmployee(${user.userId})">刪除</button></td></tr>`);
                    }
                },
                error: function (error) {
                    console.error("加载员工列表失败", error);
                    // 处理加载失败的情况
                }
            });
        }
    
        // 初次載入頁面時載入員工列表
        loadEmployeeList();
    </script>

<%@include file="/WEB-INF/views/analyze/footer.jsp" %>