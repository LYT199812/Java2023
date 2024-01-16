package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.entity.User;

@Repository
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

	@Override
	public List<SalesData> findAllSalesDataByEcId(Integer ecId) {
		String sql = "SELECT trxId, ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus, createTime FROM salesData where ecId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SalesData.class),ecId);
	}

	@Override
	public void addSalesDataByExcel(List<SalesData> salesDataList) {
		String sql = "insert into salesdata(ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus) "
				   + "value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		jdbcTemplate.batchUpdate(sql, salesDataList, salesDataList.size(),
             (ps, salesData) -> {
             	java.util.Date utilDate = salesData.getEcSalesDate();
         		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
         		 ps.setInt(1, salesData.getEcommerce().getId());
         		 ps.setString(2, salesData.getProduct().getProductId());
                 ps.setString(3, salesData.getEcOrderNumber());
                 ps.setString(4, salesData.getEcProductCode());
                 ps.setString(5, salesData.getEcProductType());
                 ps.setString(6, salesData.getEcProductSubType());
                 ps.setString(7, salesData.getEcWarehouse());
                 ps.setInt(8, salesData.getEcSalesQty());
                 ps.setInt(9, salesData.getEcSalesPrice());
                 ps.setDate(10, sqlDate);
                 ps.setString(11, salesData.getEcSalesStatus());
             });
		
	}

}
