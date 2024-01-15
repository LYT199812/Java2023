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

public class StockDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProductDaoResposity productDaoResposity = ctx.getBean("productDaoResposity", ProductDaoResposity.class);
		StockDaoResposity stockDaoResposity = ctx.getBean("stockDaoResposity", StockDaoResposity.class);
		
		// 測試查詢功能
//		List<Stock> stocks = stockDaoResposity.findStockByProductIdAndEcId("A101", 1);
//		System.out.println(new Gson().toJson(stocks));
		
		// 測試新增功能
		Stock stock = new Stock();
//		stock.setProductId("C101");
//		stock.setEcId(3);
//		stock.setProductQty(40);
//		stock.setEcProductQty(10);
//		int rowcount2 = stockDaoResposity.addStock(stock);		
//		System.out.println(new Gson().toJson(rowcount2));
		
		// 測試修改功能
//		stock.setProductId("A101");
//		stock.setEcId(2);
//		stock.setProductQty(1);
//		stock.setEcProductQty(0);
//        int updateRowCount = stockDaoResposity.updateStock(stock);
//        System.out.println("Update Product Rows: " + updateRowCount);
		
		
        // 刪除產品
        String productIdToDelete = "A101";
        Integer ecIdToDelete = 2;

        // 呼叫刪除方法
        int rowcount = stockDaoResposity.removeStockByIdAndEcId(productIdToDelete, ecIdToDelete);
//      int product = productDaoResposity.removeProductById("A101");

        // 檢查受影響的行數，判斷刪除是否成功
        if (rowcount > 0) {
            System.out.println("Stock deleted successfully.");
        } else {
            System.out.println("Stock deletion failed. Product with ID " + productIdToDelete + ecIdToDelete +" not found.");
        }
        
        
       
    }
}
