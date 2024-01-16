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
import spring.mvc.analyze.dao.ProductTypeDaoResposity;
import spring.mvc.analyze.dao.StockDaoResposity;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

public class ProductTypeDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProductTypeDaoResposity productTypeDaoResposity = ctx.getBean("productTypeDaoResposity", ProductTypeDaoResposity.class);
		
		
		// 測試新增功能
//		ProductType productType = new ProductType();
//		ProductSubType productSubType = new ProductSubType();
//		productType.setName("烘焙");
//		productSubType.setName("桿麵棍");
		
//		int rowcount1 = productTypeDaoResposity.addProductType(productType);		
//		int rowcount2 = productTypeDaoResposity.addProductSubType(productSubType);		
//		System.out.println(rowcount2);
//		System.out.println(productSubType.getId());
		
		
		// 測試修改功能
		ProductType productType = new ProductType();
		ProductSubType productSubType = new ProductSubType();
		productType.setId(1);
		productType.setName("餐廚");
		productSubType.setId(1);
		productSubType.setName("鍋鏟");
		int rowcount1 = productTypeDaoResposity.updateProductType(productType);
		int rowcount2 = productTypeDaoResposity.updateProductSubType(productSubType);
		System.out.println(rowcount1 + rowcount2);
		
		
		
		/*
        // 刪除產品
		Integer productTypeIdToDelete = 4;
        Integer productSubTypeIdToDelete = 4;

        // 呼叫刪除方法
        int rowcount1 = productTypeDaoResposity.removeProductTypeById(productTypeIdToDelete);
        int rowcount2 = productTypeDaoResposity.removeProductSubTypeById(productSubTypeIdToDelete);

        // 檢查受影響的行數，判斷刪除是否成功
        if (rowcount1 > 0) {
            System.out.println("productType deleted successfully.");
        } else {
            System.out.println("productType deletion failed. Product with ID " + productTypeIdToDelete +" not found.");
        }
        // 檢查受影響的行數，判斷刪除是否成功
        if (rowcount2 > 0) {
            System.out.println("productSubType deleted successfully.");
        } else {
            System.out.println("productSubType deletion failed. Product with ID " + productSubTypeIdToDelete +" not found.");
        }
        */
        
       
    }
}
