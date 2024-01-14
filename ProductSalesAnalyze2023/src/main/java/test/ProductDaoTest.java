package test;


import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.ProductDaoResposity;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

public class ProductDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		ProductDaoResposity productDaoResposity = ctx.getBean("productDaoResposity", ProductDaoResposity.class);
		
		List<Product> product1 = productDaoResposity.findAllProducts();
//		Product product1 = productDaoResposity.findProductById("B101").get();
//		Product product1 = productDaoResposity.findProductByProductname("好用鍋鏟").get();
		System.out.println(new Gson().toJson(product1));
		
//		Product productC = new Product();
//		Stock stock = new Stock();
//		productC.setProductId("C101");
//		productC.setProductName("好用鍋鏟");
//		productC.setProductPrice(300);
//		productC.setProductBarcode("78945632");
//		productC.setProductBrand("CCC");
//		productC.setProductType(ProductType.builder().id(1).build());
//		productC.setProductSubType(ProductSubType.builder().id(1).build());
//		productC.setIsLaunch(false);
//		stock.setProductQty(100);
//		int rowcount = productDaoResposity.save(productC, 100);
//		System.out.println(new Gson().toJson(rowcount));

	}

}
