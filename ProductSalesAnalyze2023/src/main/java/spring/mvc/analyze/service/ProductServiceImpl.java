package spring.mvc.analyze.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.mvc.analyze.dao.EcommerceDao;
import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dto.EcommerceStockEdit;
import spring.mvc.analyze.entity.Ecommerce;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.Stock;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductDao productDao;
	
	@Autowired 
	EcommerceDao ecommerceDao;
	
	public Optional<Product> findProductById(String productId) {
		return productDao.findProductById(productId);
	}
	
	public List<EcommerceStockEdit> getEcommerceStockEdits(Product product) {
		List<EcommerceStockEdit> ecommerceStockEdits = new ArrayList<>();
        List<Ecommerce> ecommerces = ecommerceDao.findAllEcommerces();
        ecommerces.forEach(ecommerce-> {
        	EcommerceStockEdit ecommerceStockEdit = new EcommerceStockEdit();
        	ecommerceStockEdit.setProductId(product.getProductId());
        	ecommerceStockEdit.setStockQty(0);
        	ecommerceStockEdit.setIsLaunch(false);
        	ecommerceStockEdit.setEcommerce(ecommerce);
        	
        	Optional<Stock> stockOpt = product.getInventory().stream().filter(s -> s.getEcId().equals(ecommerce.getId())).findFirst();
        	if(stockOpt.isPresent()) {
        		int qty = stockOpt.get().getEcProductQty();
        		ecommerceStockEdit.setStockQty(qty);
        		ecommerceStockEdit.setIsLaunch(qty == 0 ? false: true);
        	}
        	ecommerceStockEdits.add(ecommerceStockEdit);
        });
        return ecommerceStockEdits;
	}
}
