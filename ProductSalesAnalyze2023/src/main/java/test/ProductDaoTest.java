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
		
//		List<Product> product1 = productDaoResposity.findAllProducts();
//		Product product1 = productDaoResposity.findProductById("A101").get();
//		Product product1 = productDaoResposity.findProductByProductname("好用鍋鏟").get();
//		System.out.println(new Gson().toJson(product1));
		
		Product productC = new Product();
		productC.setProductId("C101");
		productC.setProductName("好用鍋鏟");
		productC.setProductPrice(300);
		productC.setProductBarcode("78945632");
		productC.setProductBrand("CCC");
		productC.setProductType(ProductType.builder().id(1).build());
		productC.setProductSubType(ProductSubType.builder().id(1).build());
		productC.setIsLaunch(false);
		Stock stock = new Stock();
		stock.setProductId("C101");
		stock.setEcId(3);
		stock.setProductQty(40);
		stock.setEcProductQty(10);
		int rowcount1 = productDaoResposity.addProduct(productC);
		int rowcount2 = stockDaoResposity.addStock(stock);		
		System.out.println(new Gson().toJson(rowcount1 + rowcount2));
		System.out.println(new Gson().toJson(rowcount2));

		
	}

}
