package spring.mvc.analyze.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.mvc.analyze.entity.ProductBrand;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;

@Repository
public class ProductBrandDaoResposity implements ProductBrandDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ProductBrand> findAllProductBrands() {
		String sql = "select id, name from productBrand";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductBrand.class));
	}
	@Override
	public Optional<ProductBrand> findProductBrandById(Integer productBrandId) {
		String sql = "select pd.id, pd.name from productbrand pd where pd.id=?";
		try {
			ProductBrand productBrand = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ProductBrand.class), productBrandId);
			return Optional.ofNullable(productBrand);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}
	@Override
	public int addProductBrand(ProductBrand productBrand) {
		String sql = "insert into productbrand(name) value (?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowcount = jdbcTemplate.update(conn -> {
        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	pstmt.setString(1, productBrand.getName());
        	return pstmt;
        }, keyHolder);
        if(rowcount > 0) {
        	int id = keyHolder.getKey().intValue(); // 得到 id
        	productBrand.setId(id);
        }
	
		return rowcount ;
	}
	
	@Override
	public int updateProductBrand(ProductBrand productBrand) {
		String sql = "UPDATE productbrand SET name=? WHERE id=?";
		return jdbcTemplate.update(sql, productBrand.getName(), productBrand.getId());
	}
	
	@Override
	public int removeProductBrandById(Integer id) {
		String sql = "delete from productbrand where id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
