package test;


import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.ProductDaoResposity;
import spring.mvc.analyze.dao.StockDaoResposity;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

public class ProductDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProductDaoResposity productDaoResposity = ctx.getBean("productDaoResposity", ProductDaoResposity.class);
		StockDaoResposity stockDaoResposity = ctx.getBean("stockDaoResposity", StockDaoResposity.class);
		
		// 測試查詢功能
//		List<Product> product1 = productDaoResposity.findAllProducts();
//		Product product1 = productDaoResposity.findProductById("A101").get();
//		Product product1 = productDaoResposity.findProductByProductname("好用鍋鏟").get();
//		System.out.println(new Gson().toJson(product1));
		
		// 測試新增功能
//		Product product = new Product();
//		product.setProductId("C101");
//		product.setProductName("好用鍋鏟");
//		product.setProductPrice(300);
//		product.setProductBarcode("78945632");
//		product.setProductBrand("CCC");
//		product.setProductType(ProductType.builder().id(1).build());
//		product.setProductSubType(ProductSubType.builder().id(1).build());
//		product.setIsLaunch(false);
//		Stock stock = new Stock();
//		stock.setProductId("C101");
//		stock.setEcId(3);
//		stock.setProductQty(40);
//		stock.setEcProductQty(10);
//		int rowcount1 = productDaoResposity.addProduct(productC);
//		int rowcount2 = stockDaoResposity.addStock(stock);		
//		System.out.println(new Gson().toJson(rowcount1 + rowcount2));
//		System.out.println(new Gson().toJson(rowcount2));
		
		// 測試修改功能
//		product.setProductId("A101");
//		product.setProductName("好用鍋鏟");
//		product.setProductPrice(800);
//		product.setProductBarcode("");
//		product.setProductBrand("AAA");
//		product.setProductTypeId(1);
//		product.setProductSubTypeId(1);
//		product.setProductImg(null);
//		product.setProductDesc(null);
//        product.setIsLaunch(false);
//        int updateRowCount = productDaoResposity.updateProduct(product);
//        System.out.println("Update Product Rows: " + updateRowCount);
		

        // 刪除產品
        String productIdToDelete = "C101";

        // 呼叫刪除方法
        int rowcount = productDaoResposity.removeProductById(productIdToDelete);
//      int product = productDaoResposity.removeProductById("A101");

        // 檢查受影響的行數，判斷刪除是否成功
        if (rowcount > 0) {
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product deletion failed. Product with ID " + productIdToDelete + " not found.");
        }
        
        
       
    }
}
