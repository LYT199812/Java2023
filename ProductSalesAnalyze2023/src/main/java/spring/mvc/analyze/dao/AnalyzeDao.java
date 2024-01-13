package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.User;

public interface AnalyzeDao {

//	使用者-User:
//	1. 查詢所有使用者(多筆)
	List<User> findAllUsers();
	
//	2. 新增使用者
	void addUser(User user);
	
//	3. 修改密碼
	Boolean updateUserPassword(Integer userId, String newPassword);
	
//	4. 根據使用者名稱查找使用者(登入用-單筆)
	Optional<User> findUserByUsername(String username);
	
//	5. 根據使用者ID查找使用者(單筆)
	Optional<User> findUserById(Integer userId);
	
	// 將匯入的 excel 寫入 MySQL
	void saveExcelData(List<SalesData> dataList);
	
//	商品-Product
//	1. 查詢所有商品(多筆)
	List<Product> findAllProducts();
	// 根據 isLaunch 狀態取得商品資料
	List<Product> findAllProducts(Boolean isLaunch);
	
//	2. 根據產品ID來查找商品(單筆)
	Optional<Product> findProductById(Integer productId);
	
//	3. 新增商品
	void addProduct(Product product);
	
//	4. 變更商品上架狀態
	Boolean updateProductLaunch(Integer productId, Boolean isLaunch);
	 
//  5. 批次新增商品
	void addProductExcelData(List<Product> dataList);
	
}
