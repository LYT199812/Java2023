package spring.mvc.analyze.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Service;
import spring.mvc.analyze.entity.User;

@Repository
public class UserDaoResposity implements UserDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceDaoResposity serviceDaoResposity;
	
	@Autowired
	private LevelDaoResposity levelDaoResposity;
	
	
	RowMapper<User> rowMapper = (ResultSet rs, int rowNum) -> {
		User user = new User();
		user.setUserId(rs.getInt("userId"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setLevel(levelDaoResposity.findLevelByUserId(rs.getInt("userId")).get());
		List<Service> menu = serviceDaoResposity.findSevicesByUserId(rs.getInt("userId"));
		user.setMenu(menu);
		return user;
	};
	
	@Override
	public List<User> findAllUsers() {
		String sql = "select userId, username, password, levelId from user";
		//return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Optional<User> findUserById(Integer userId) {
		String sql = "select userId, username, password, levelId from user where userId=?";
		try {
			User user = jdbcTemplate.queryForObject(sql,rowMapper, userId);
			return Optional.ofNullable(user);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

}
