package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

public interface ProductDao {

	List<Product> findAllProducts();
	
	Optional<Product> findProductById(String productId);
	
	Optional<Product> findProductByProductname(String productName);
	
	int addProduct (Product product);
	
	void addProductByExcel(List<Product> productList);
	
	public int updateProduct(Product product);
	
	public int removeProductById(String productId);
	
	
}
