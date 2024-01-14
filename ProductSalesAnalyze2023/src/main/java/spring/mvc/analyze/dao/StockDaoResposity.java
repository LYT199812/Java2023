package spring.mvc.analyze.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Stock;

@Repository
public class StockDaoResposity implements StockDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Stock> findStockByProductId(String productId) {
		String sql = "SELECT p.productId, s.productQty FROM product p JOIN stock s ON p.productId = s.productId where p.productId=?";
		try {
			Stock stock = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Stock.class), productId);
			return Optional.ofNullable(stock);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}
	
	
	
}
