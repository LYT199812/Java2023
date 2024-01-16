package spring.mvc.analyze.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.User;

@Repository
public class AnalyzeDaoMySQL implements AnalyzeDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//-----------------------------------------------------------------------
	//user
	@Override
	public List<User> findAllUsers() {
		String sql = "select userId, username, password, levelId from user";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public void addUser(User user) {
		String sql = "insert into user(username, password, levelId) value (?, ?, ?)";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getLevel().getLevelId());
		
	}

	@Override
	public Boolean updateUserPassword(Integer userId, String newPassword) {
		String sql = "update user set password = ? where userId = ?";
		int rowcount = jdbcTemplate.update(sql, newPassword, userId);
		return rowcount > 0;
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		String sql  = "select userId, username, password, levelId from User where username = ?";
		// 若找不到會發生例外，所以看要不要寫try catch，這裡有寫
		try {
			User user =  jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
			return Optional.ofNullable(user);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserById(Integer userId) {
		String sql = "select userId, username, password, levelId from user where userId = ?";
		try {
			User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
			return Optional.ofNullable(user);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	//------------------------------------------------------------------------
	//匯入excel
	@Override
	public void saveExcelData(List<SalesData> dataList) {
		String sql = "insert into salesdata(orderNumber, productCodeMomo, productName, productId, productDepartment, productType, warehouse, sales, price, salesDate, salesOrReturn) "
				   + "value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		
		
		jdbcTemplate.batchUpdate(sql, dataList, dataList.size(),
                (ps, salesData) -> {
                	java.util.Date utilDate = salesData.getEcSalesDate();
            		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    ps.setString(1, salesData.getEcOrderNumber());
                    ps.setString(2, salesData.getEcProductCode());
                    ps.setString(3, salesData.getProduct().getProductName());
                    ps.setString(4, salesData.getProduct().getProductId());
                    ps.setString(5, salesData.getEcProductType());
                    ps.setString(6, salesData.getEcProductSubType());
                    ps.setString(7, salesData.getEcWarehouse());
                    ps.setInt(8, salesData.getEcSalesQty());
                    ps.setInt(9, salesData.getEcSalesPrice());
                    ps.setDate(10, sqlDate);
                    ps.setString(11, salesData.getEcSalesStatus());
                });
	}
	
	//---------------------------------------------------------------------------
	//product
	@Override
	public List<Product> findAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findAllProducts(Boolean isLaunch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> findProductById(Integer productId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void addProduct(Product product) {
		String sql = "insert into product(productId, productName, productPrice, productBarcode, brproductBrandand, productTypeId, productSubTypeId, productImg, productDesc, isLaunch, productQty) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, product.getProductId(), product.getProductName(), product.getProductPrice(), product.getProductBarcode(), product.getProductBrand(), product.getProductType().getId(), product.getProductSubType().getId(), product.getProductImg(), product.getProductDesc(), product.getIsLaunch());
		
	}

	@Override
	public Boolean updateProductLaunch(Integer productId, Boolean isLaunch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProductExcelData(List<Product> dataList) {
		String sql = "insert into product(productId, productName, price, barcode, brand, productDepartment, productType, isLaunch) value(?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate.batchUpdate(sql, dataList, dataList.size(),
             (ps, product) -> {
                 ps.setString(1, product.getProductId());
                 ps.setString(2, product.getProductName());
                 ps.setInt(3, product.getProductPrice());
                 ps.setString(4, product.getProductBarcode());
                 ps.setString(5, product.getProductBrand());
                 ps.setInt(6, product.getProductType().getId());
                 ps.setInt(7, product.getProductSubType().getId());
                 ps.setBoolean(8, product.getIsLaunch());
             });
		
	}
	
}
