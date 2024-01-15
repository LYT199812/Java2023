package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;

public interface StockDao {

	public List<Stock> findAllStocks();
	
	public List<Stock> findStockByProductId2(String productId);
	
	Optional<Stock> findStockByProductId(String productId);
	
	int addStock (Stock stock);
	
}
