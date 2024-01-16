package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.SalesData;

public interface SalesDataDao {

	List<SalesData> findAllSalesDatas();
	
	Optional<SalesData> findSalesDataByProductId(String productId);
	
	List<SalesData> findAllSalesDataByEcId(Integer ecId);
	
}
