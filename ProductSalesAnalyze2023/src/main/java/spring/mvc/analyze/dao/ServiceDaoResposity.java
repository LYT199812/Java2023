package spring.mvc.analyze.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.analyze.entity.Service;

@Repository
public class ServiceDaoResposity implements ServiceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Service> findSevicesByUserId(Integer userId) {
		String sql = "select s.serviceId, s.serviceName, s.serviceUrl from user u, levelrefservice ls, level l, service s where u.levelId = ls.levelId and ls.levelId = l.levelId and ls.serviceId = s.serviceId and userId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Service.class),userId);
	}

}
