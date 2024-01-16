package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Ecommerce;

public interface EcommerceDao {

	List<Ecommerce> findAllEcommerces();
	
	Optional<Ecommerce> findEcById(Integer id);
	
}
