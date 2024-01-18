package spring.mvc.analyze.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.Stock;

@Repository
public class StockDaoResposity implements StockDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EcommerceDaoResposity ecommerceDaoResposity;
	
	RowMapper<Stock> rowMapper = (ResultSet rs, int rowNum) -> {
		Stock stock = new Stock();
		stock.setEcId(rs.getInt("ecId"));
		stock.setEcProductQty(rs.getInt("ecProductQty"));
		stock.setEcommerce(ecommerceDaoResposity.findEcById(rs.getInt("ecId")).get());
		return stock;
		};
	
	@Override
	public List<Stock> findAllStocks() {
		String sql = "SELECT productId, ecId, ecProductQty FROM stock";
//		String sql = "SELECT p.productId, p.productName, p.productPrice, p.productBarcode, p.productBrand, "
//				+ "p.productTypeId, p.productSubTypeId, p.productImg, p.productDesc, p.isLaunch, s.productQty "
//				+ "FROM product p "
//				+ "LEFT JOIN stock s ON p.productId = s.productId;";
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class));
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public List<Stock> findStockByProductId(String productId) {
		String sql = "SELECT productId, ecId, ecProductQty FROM stock where productId=?";
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stock.class), productId);
		return jdbcTemplate.query(sql, rowMapper,productId);
	}
	
	@Override
	public Optional<Stock> findStockByProductIdAndEcId(String productId, Integer ecId) {
		String sql = "SELECT productId, ecId, ecProductQty FROM stock where productId=? and ecId=?";
		try {
			Stock stock = jdbcTemplate.queryForObject(sql, rowMapper, productId, ecId);
			return Optional.ofNullable(stock);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}
	
	@Override
	public int addStock(Stock stock) {
		String stockSql = "INSERT INTO stock(productId, ecId, ecProductQty) VALUES (?, ?, ?)";
		int rowcount = jdbcTemplate.update(stockSql, stock.getProductId(), stock.getEcId(), stock.getEcProductQty());
		return rowcount;
	}

	@Override
	public void addStockByExcel(List<Stock> stockList) {
		String sql = "insert into stock(productId, ecId, ecProductQty) VALUES (?, ?, ?)";
	
		jdbcTemplate.batchUpdate(sql, stockList, stockList.size(),
          (ps, stock) -> {
      		  ps.setString(1, stock.getProductId());
              ps.setInt(2, stock.getEcId());
              ps.setInt(3, stock.getEcProductQty());
          });
		
	}
	
	
	@Override
	public int updateStock(Stock stock) {
		String sql = "UPDATE stock " +
                "SET ecProductQty=? " +
                "WHERE productId=? and ecId=?";

	   // 使用 update 方法執行更新，並回傳受影響的列數
	   return jdbcTemplate.update(sql,
			   stock.getEcProductQty(),
			   stock.getProductId(),
			   stock.getEcId());        
	}

	@Override
	public int removeStockByIdAndEcId(String productId, Integer ecId) {
		String sql = "delete from stock where productId = ? and ecId=? ";
		return jdbcTemplate.update(sql, productId, ecId);
	}
		
}
