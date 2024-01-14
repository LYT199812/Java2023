package spring.mvc.analyze.dao;

import java.util.Optional;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Stock;

public interface StockDao {

	Optional<Stock> findStockByProductId(String productId);
	
	
}
