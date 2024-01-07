package spring.mvc.analyze.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.model.entity.Product;
import spring.mvc.analyze.model.entity.SalesData;
import spring.mvc.analyze.model.entity.User;

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
		String sql = "select userId, username, password, level from user";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
	}

	@Override
	public void addUser(User user) {
		String sql = "insert into user(username, password, level) value (?, ?, ?)";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getLevel());
		
	}

	@Override
	public Boolean updateUserPassword(Integer userId, String newPassword) {
		String sql = "update user set password = ? where userId = ?";
		int rowcount = jdbcTemplate.update(sql, newPassword, userId);
		return rowcount > 0;
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		String sql  = "select userId, username, password, level from User where username = ?";
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
		String sql = "select userId, username, password, level from user where userId = ?";
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
                    ps.setString(1, salesData.getOrderNumber());
                    ps.setString(2, salesData.getProductCodeMomo());
                    ps.setString(3, salesData.getProductName());
                    ps.setString(4, salesData.getProductId());
                    ps.setString(5, salesData.getProductDepartment());
                    ps.setString(6, salesData.getProductType());
                    ps.setString(7, salesData.getWarehouse());
                    ps.setInt(8, salesData.getSales());
                    ps.setInt(9, salesData.getPrice());
                    ps.setString(10, salesData.getSalesDate());
                    ps.setString(11, salesData.getSalesOrReturn());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean updateProductLaunch(Integer productId, Boolean isLaunch) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
