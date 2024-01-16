package spring.mvc.analyze.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.ProductTypeDao;
import spring.mvc.analyze.dao.SalesDataDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.dto.SalesDataDto;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.Stock;

@Controller
@RequestMapping("/eccommerce")
public class EccommerceController {

	@Autowired
	SalesDataDao salesDataDao;
	
	@Autowired
	ProductDao productDao;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 		salesDataDtos.add(
				SalesDataDto.builder()
				.number(1)
				.productId("A101")
				.productName("好用鍋鏟")
				.brand("AAA")
				.productDepartment("餐廚")
				.productType("鍋鏟")
				.productEAN("12345678")
				.ecQuantity(20)
				.quantity(50)
				.sales(3)
				.salesFigures(5000)
				.date("2023-12-03")
				.yoy(50)
				.build()
		    );
		    
	//		List<SalesData> analyzeSalesDatas = new ArrayList<SalesData>();         // 處理後的數據 (邏輯：相同型號 合併 銷售數量 + 銷售額)	
			// 相同型號 合併 銷售數量 + 銷售額
	//		List<String> productIds = salesDatas.stream().map(SalesData::getProductId).distinct().collect(Collectors.toList());
	//		for(String productId: productIds) {
	//			List<SalesData> salesList = salesDatas.stream().filter(s->s.getProductId().equals(productId)).collect(Collectors.toList());
	//			int qty = salesList.stream().mapToInt(s-> s.getEcSalesQty()).sum();
	//			int price =  salesList.stream().mapToInt(s-> s.getEcSalesPrice()).sum();
	//			SalesData salesData = salesList.get(0);
	//			salesData.setEcSalesQty(qty);
	//			salesData.setEcSalesPrice(price);
	//			analyzeSalesDatas.add(salesData);
	//		}
	 * @param ecId
	 * @param model
	 * @return
	 */
	@GetMapping("/{ecId}")
	public String eccommerce(@PathVariable("ecId") Integer ecId,Model model) {
		
		// 從DB來的原始數據
		List<SalesData> salesDatas = salesDataDao.findAllSalesDataByEcId(ecId);

		// 從分析過後的 analyze sales data， 轉成 sales data dto
		List<SalesDataDto> salesDataDtos = new ArrayList<SalesDataDto>();
		for (int i=0;i<salesDatas.size();i++) {
			
			SalesData salesData = salesDatas.get(i);
			Product product = productDao.findProductById(salesData.getProductId()).get();
			ProductType productType = product.getProductType();
			ProductSubType productSubType = product.getProductSubType();
			Stock stock = product.getInventory()
					             .stream()
					             .filter(s-> s.getEcId().equals(ecId))
					             .findFirst()
					             .get();
			
			SalesDataDto salesDataDto = new SalesDataDto();
			salesDataDto.setNumber((i+1));
			salesDataDto.setProductId(salesData.getProductId());
			salesDataDto.setProductName(product.getProductName());
			salesDataDto.setBrand(product.getProductBrand());
			salesDataDto.setProductDepartment(productType.getName());
			salesDataDto.setProductType(productSubType.getName());
			salesDataDto.setProductEAN(product.getProductBarcode());
			salesDataDto.setEcQuantity(stock.getEcProductQty());
			salesDataDto.setQuantity(stock.getProductQty());
			salesDataDto.setSales(salesData.getEcSalesQty());
			salesDataDto.setSalesFigures(salesData.getEcSalesPrice());
			salesDataDto.setDate(sdf.format(salesData.getEcSalesDate()));
			salesDataDto.setYoy(50); // 之後要算，先寫死
			salesDataDtos.add(salesDataDto);
		}

		model.addAttribute("salesData", new Gson().toJson(salesDataDtos));
		
		return "/analyze/ecWebsite/eccommerce";
	}
}
