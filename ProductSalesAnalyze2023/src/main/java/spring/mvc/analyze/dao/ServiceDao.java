package spring.mvc.analyze.dao;

import java.util.List;

import spring.mvc.analyze.entity.Service;

public interface ServiceDao {

	List<Service> findSevicesByUserId(Integer userId);
}
