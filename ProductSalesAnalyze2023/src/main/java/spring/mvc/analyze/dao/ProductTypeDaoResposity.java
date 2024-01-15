package spring.mvc.analyze.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;

@Repository
public class ProductTypeDaoResposity implements ProductTypeDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<ProductType> findProductTypeById(Integer productTypeId) {
		String sql = "select pt.id, pt.name from producttype pt where pt.id=?";
		try {
			ProductType productType = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ProductType.class), productTypeId);
			return Optional.ofNullable(productType);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

	@Override
	public Optional<ProductSubType> findProductSubTypeById(Integer productSubTypeId) {
		String sql = "select pst.id, pst.name from productsubtype pst where pst.id=?";
		try {
			ProductSubType productSubType = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ProductSubType.class), productSubTypeId);
			return Optional.ofNullable(productSubType);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

}
