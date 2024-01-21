package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.ProductBrand;

public interface ProductBrandDao {

	List<ProductBrand> findAllProductBrands();
	
	Optional<ProductBrand> findProductBrandById(Integer productBrandId);
	
	public int addProductBrand (ProductBrand productBrand);
	
	public int updateProductBrand (ProductBrand productBrand);
	
	public int removeProductBrandById (Integer id);
	
	
}
