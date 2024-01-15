package spring.mvc.analyze.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

@Repository
public class ProductDaoResposity implements ProductDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProductTypeDaoResposity productTypeDaoResposity;
	
	RowMapper<Product> rowMapper = (ResultSet rs, int rowNum) -> {
		Product product = new Product();
		product.setProductId(rs.getString("productId"));
		product.setProductName(rs.getString("productName"));
		product.setProductPrice(rs.getInt("productPrice"));
		product.setProductBarcode(rs.getString("productBarcode"));
		product.setProductType(productTypeDaoResposity.findProductTypeById(rs.getInt("productTypeId")).get());
		product.setProductSubType(productTypeDaoResposity.findProductSubTypeById(rs.getInt("productSubTypeId")).get());
		product.setProductImg(rs.getString("productImg"));
		product.setProductDesc(rs.getString("productDesc"));
		product.setIsLaunch(rs.getBoolean("isLaunch"));
		product.setProductQty(rs.getInt("productQty"));
		//product.setProductQty(stockDaoResposity.findStockByProductId(rs.getString("productId")).orElse(null));
		//user.setMenu(serviceDaoResposity.findSevicesByUserId(rs.getInt("userId")));
		return product;
	};
	
	@Override
	public List<Product> findAllProducts() {
//		String sql = "select productId, productName, productPrice, productBarcode, productBrand, productTypeId, productSubTypeId, productImg, productDesc, isLaunch from product";
		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
				+ "FROM product p "
				+ "LEFT JOIN stock s ON p.productId = s.productId;";
		//return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Optional<Product> findProductById(String productId) {
		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
				+ "FROM product p "
				+ "LEFT JOIN stock s ON p.productId = s.productId "
				+ "where P.productId=?;";
		try {
			Product product = jdbcTemplate.queryForObject(sql,rowMapper, productId);
			return Optional.ofNullable(product);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public Optional<Product> findProductByProductname(String productName) {
		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
				+ "FROM product p "
				+ "LEFT JOIN stock s ON p.productId = s.productId "
				+ "where P.productName=?;";
		try {
			Product product = jdbcTemplate.queryForObject(sql,rowMapper, productName);
			return Optional.ofNullable(product);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public int save(Product product, Integer productQty) {
		String sql = "insert into product(productId, productName, productPrice, productBarcode, productBrand, productTypeId, productSubTypeId, productImg, productDesc, isLaunch) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowcount1 =  jdbcTemplate.update(sql, 
				product.getProductId(), product.getProductName(), product.getProductPrice(), product.getProductBarcode(), product.getProductBrand(), 
				product.getProductType().getId(), product.getProductSubType().getId(), product.getProductImg(), product.getProductDesc(), product.getIsLaunch());
	
		String stockSql = "INSERT INTO stock(productId, productQty) VALUES (?, ?)";
		int rowcount2 = jdbcTemplate.update(stockSql, product.getProductId(), productQty);
		return rowcount1 + rowcount2;
	}

	@Override
	public void saveProductExcelData(List<Product> productList) {
		// TODO Auto-generated method stub
		
	}

}
