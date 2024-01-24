package spring.mvc.analyze.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	private StockDao stockDaoResposity;
	
	@Autowired
	private ProductTypeDao productTypeDaoResposity;
	
	@Autowired
	private ProductBrandDao productBrandDaoResposity;
	
	RowMapper<Product> rowMapper = (ResultSet rs, int rowNum) -> {
		Product product = new Product();
		product.setProductId(rs.getString("productId"));
		product.setProductName(rs.getString("productName"));
		product.setProductPrice(rs.getInt("productPrice"));
		product.setProductBrandId(rs.getInt("productBrandId"));
		product.setProductBarcode(rs.getString("productBarcode"));
		product.setProductTypeId(rs.getInt("productTypeId"));
		product.setProductType(productTypeDaoResposity.findProductTypeById(rs.getInt("productTypeId")).get());
		product.setProductSubTypeId(rs.getInt("productSubTypeId"));
		product.setProductSubType(productTypeDaoResposity.findProductSubTypeById(rs.getInt("productSubTypeId")).get());
		product.setProductBrand(productBrandDaoResposity.findProductBrandById(rs.getInt("productBrandId")).get());
		product.setProductImg(rs.getString("productImg"));
		product.setProductDesc(rs.getString("productDesc"));
		product.setProductQty(rs.getInt("productQty"));
		product.setIsLaunch(rs.getBoolean("isLaunch"));
		product.setInventory(stockDaoResposity.findStockByProductId(rs.getString("productId")));
		//product.setProductQty(rs.getInt("productQty"));
		//user.setMenu(serviceDaoResposity.findSevicesByUserId(rs.getInt("userId")));
		return product;
	};
	
	@Override
	public List<Product> findAllProducts() {
		String sql = "select productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch from product";
//		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
//				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
//				+ "FROM product p "
//				+ "LEFT JOIN stock s ON p.productId = s.productId;";
		//return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Optional<Product> findProductById(String productId) {
		String sql = "select productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch from product where productId = ?";
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
		String sql = "select productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch from product where productName = ?";
		try {
			Product product = jdbcTemplate.queryForObject(sql,rowMapper, productName);
			return Optional.ofNullable(product);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public int addProduct (Product product) {
		String sql = "insert into product(productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rowcount =  jdbcTemplate.update(sql, 
				product.getProductId(), product.getProductName(), product.getProductPrice(), product.getProductBarcode(), product.getProductBrandId(), 
				product.getProductTypeId(), product.getProductSubTypeId(), product.getProductImg(), product.getProductDesc(), product.getProductQty(), product.getIsLaunch());
	
		return rowcount ;
	}

	@Override
	public void addProductByExcel(List<Product> productList) {
		String sql = "insert into product(productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch) "
				+ "value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		jdbcTemplate.batchUpdate(sql, productList, productList.size(),
          (ps, product) -> {
      		  ps.setString(1, product.getProductId());
              ps.setString(2, product.getProductName());
              ps.setInt(3, product.getProductPrice());
              ps.setString(4, product.getProductBarcode());
              ps.setInt(5, product.getProductBrandId());
              ps.setInt(6, product.getProductTypeId());
              ps.setInt(7, product.getProductSubTypeId());
              ps.setString(8, product.getProductImg());
              ps.setString(9, product.getProductDesc());
              ps.setInt(10, product.getProductQty());
              ps.setBoolean(11, product.getIsLaunch());
          });
		
	}

	@Override
	public int updateProductById(Product product) {
		String sql = "UPDATE product " +
                "SET productName=?, productPrice=?, productBarcode=?, " +
                "productBrandId=?, productTypeId=?, productSubTypeId=?, " +
                "productImg=?, productDesc=?, productQty=?, isLaunch=? " +
                "WHERE productId=?";

	   // 使用 update 方法執行更新，並回傳受影響的列數
	   return jdbcTemplate.update(sql,
	           product.getProductName(),
	           product.getProductPrice(),
	           product.getProductBarcode(),
	           product.getProductBrandId(),
	           product.getProductTypeId(),
	           product.getProductSubTypeId(),
	           product.getProductImg(),
	           product.getProductDesc(),
	           product.getProductQty(),
	           product.getIsLaunch(),
	           product.getProductId());
	}

	@Override
	public int removeProductById(String productId) {
		String sql = "delete from product where productId = ?";
		return jdbcTemplate.update(sql, productId);
	}

	
}
