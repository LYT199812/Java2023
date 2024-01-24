package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;

public interface StockDao {

	public List<Stock> findAllStocks();
	
	public List<Stock> findStockByProductId(String productId);
	
	public Optional<Stock> findStockByProductIdAndEcId(String productId, Integer ecId);
	
	public int addStock (Stock stock);
	
	public void addStockByExcel(List<Stock> stockList);
	
	public int updateStock (Stock stock);
	
	public int removeStockByIdAndEcId(String productId, Integer ecId);
	
}
