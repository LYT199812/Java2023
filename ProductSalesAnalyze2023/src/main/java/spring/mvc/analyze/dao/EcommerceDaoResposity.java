package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Ecommerce;

@Repository
public class EcommerceDaoResposity implements EcommerceDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ecommerce> findAllEcommerces() {
		String sql = "select id, name, website from ecommerce";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ecommerce.class));
	}

	@Override
	public Optional<Ecommerce> findEcById(Integer id) {
		String sql = "select id, name, website from ecommerce where id=?";
		try {
			Ecommerce ecommerce = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Ecommerce.class), id);
			return Optional.ofNullable(ecommerce);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

}
