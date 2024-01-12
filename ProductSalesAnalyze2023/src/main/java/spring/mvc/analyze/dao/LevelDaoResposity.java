package spring.mvc.analyze.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Level;

@Repository
public class LevelDaoResposity implements LevelDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Optional<Level> findLevelByUserId(Integer userId) {
		String sql = "select l.levelId, l.levelName from user u, level l where u.levelId = l.levelId and userId = ?";
		try {
			Level level = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Level.class), userId);
			return Optional.ofNullable(level);
		} catch (Exception e) {
			e.printStackTrace(); // 可以看console的錯誤
			return Optional.empty();
		}
	}

}
