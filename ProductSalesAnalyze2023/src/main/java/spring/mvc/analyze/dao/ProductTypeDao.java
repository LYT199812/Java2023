package spring.mvc.analyze.dao;

import java.util.Optional;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;

public interface ProductTypeDao {

	Optional<ProductType> findProductTypeById(Integer productTypeId);
	
	Optional<ProductSubType> findProductSubTypeById(Integer productSubTypeId);
	
}
