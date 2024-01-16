package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

public class SalesDataDaoResposity implements SalesDataDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SalesData> findAllSalesDatas() {
		String sql = "SELECT trxId, ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus, createTime FROM salesData";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalesData.class));
	}

	@Override
	public Optional<SalesData> findSalesDataByProductId(String productId) {
		String sql = "SELECT trxId, ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus, createTime FROM salesData where productId=?";
		try {
			SalesData salesData = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(SalesData.class), productId);
			return Optional.ofNullable(salesData);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

}
