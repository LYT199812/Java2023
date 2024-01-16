package spring.mvc.analyze.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	
	@Override
	public int addProductType(ProductType productType) {
		String sql = "insert into producttype(name) value (?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowcount = jdbcTemplate.update(conn -> {
        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	pstmt.setString(1, productType.getName());
        	return pstmt;
        }, keyHolder);
        if(rowcount > 0) {
        	int id = keyHolder.getKey().intValue(); // 得到 id
        	productType.setId(id);
        }
	
		return rowcount ;
	}

	@Override
	public int addProductSubType(ProductSubType productSubType) {
		String sql = "insert into productsubtype(name) value (?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowcount = jdbcTemplate.update(conn -> {
        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	pstmt.setString(1, productSubType.getName());
        	return pstmt;
        }, keyHolder);
        if(rowcount > 0) {
        	int id = keyHolder.getKey().intValue(); // 得到 id
        	productSubType.setId(id);
        }
	
		return rowcount ;
	}

	@Override
	public int updateProductType(ProductType productType) {
		String sql = "UPDATE producttype SET name=? WHERE id=?";
		return jdbcTemplate.update(sql, productType.getName(), productType.getId());
	}

	@Override
	public int updateProductSubType(ProductSubType productSubType) {
		String sql = "UPDATE productsubtype SET name=? WHERE id=?";
		return jdbcTemplate.update(sql, productSubType.getName(), productSubType.getId());
	}
	
	@Override
	public int removeProductTypeById(Integer id) {
		String sql = "delete from producttype where id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int removeProductSubTypeById(Integer id) {
		String sql = "delete from productsubtype where id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
