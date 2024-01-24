package spring.mvc.analyze.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.exception.StockQtyInquientException;

@Service
public class StockServiceImpl {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = StockQtyInquientException.class)
	public void updateProduct(Map<String,Object> formData) throws StockQtyInquientException {

		Set<String> formKeySet = formData.keySet();
		
		Set<String> stockQtySet = formKeySet.stream().filter(key-> key.startsWith("stockQty_")).collect(Collectors.toSet());
		
		String productId = String.valueOf(formData.get("productId"));
		
		// 更新庫存 by stockQtySet
		stockQtySet.stream().forEach(stockQty-> {
			String[] split = stockQty.split("_");
			if(split.length==2) {
				Integer ecId = Integer.valueOf(split[1]);
				Integer qty = Integer.valueOf(formData.get(stockQty).toString());
				
				Optional<Stock> stockOpt = stockDao.findStockByProductIdAndEcId(productId, ecId);
				if(stockOpt.isPresent()) {
					Stock stock = stockOpt.get();
					stock.setEcProductQty(qty);
					stockDao.updateStock(stock);
				} else {
					Stock stock = new Stock();
					stock.setEcId(ecId);
					stock.setProductId(productId);
					stock.setEcProductQty(qty);
					stockDao.addStock(stock);
				}
			}
		});
		
		// 檢查目前產品數量>=所有庫存的數量，否則拋出例外
		Product product = productDao.findProductById(productId).get();
		List<Stock> stocks = stockDao.findStockByProductId(productId);
		int stockQtySum = stocks.stream().mapToInt(Stock::getEcProductQty).sum();
		if(stockQtySum > product.getProductQty()) {
			throw new StockQtyInquientException(String.format("庫存不足，目前庫存數量為%s", product.getProductQty()));
		}
	}
}
