package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;

@Repository
public class StockDaoResposity implements StockDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Stock> findAllStocks() {
		String sql = "SELECT productId, ecId, productQty, ecProductQty FROM stock";
//		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
//				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
//				+ "FROM product p "
//				+ "LEFT JOIN stock s ON p.productId = s.productId;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class));
//		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public List<Stock> findStockByProductId2(String productId) {
		String sql = "SELECT productId, ecId, productQty, ecProductQty FROM stock where productId=?";
//		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
//				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
//				+ "FROM product p "
//				+ "LEFT JOIN stock s ON p.productId = s.productId;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), productId);
//		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public Optional<Stock> findStockByProductId(String productId) {
		String sql = "SELECT productId, ecId, productQty, ecProductQty FROM stock where productId=?";
		try {
			Stock stock = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Stock.class), productId);
			return Optional.ofNullable(stock);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public int addStock(Stock stock) {
		String stockSql = "INSERT INTO stock(productId, ecId, productQty, ecProductQty) VALUES (?, ?, ?, ?)";
		int rowcount = jdbcTemplate.update(stockSql, stock.getProductId(), stock.getEcId(), stock.getProductQty(), stock.getEcProductQty());
		return rowcount;
	}
	
	
	
}
