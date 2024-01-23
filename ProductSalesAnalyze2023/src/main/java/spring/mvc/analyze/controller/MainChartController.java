package spring.mvc.analyze.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.SalesDataDao;
import spring.mvc.analyze.dto.SalesDataDto;
import spring.mvc.analyze.entity.Ecommerce;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductBrand;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.Stock;

@Controller
@RequestMapping("/analyze/chart")
public class MainChartController {

	@Autowired
	SalesDataDao salesDataDao;
	
	@Autowired
	ProductDao productDao;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping("/main")
	public String eccommerce(Model model) {
		
		// 從DB來的原始數據
		List<SalesData> salesDatasOr = salesDataDao.findAllSalesDatas();
		
		//平台月銷售趨勢
		// 使用Map來存儲分組後的數據:同平台、同銷售日期(月份)
		List<SalesData> salesDatas = salesDataDao.findAllSalesDatas();
		Map<String, Map<String, List<SalesData>>> groupedData = salesDatas.stream()
                .collect(Collectors.groupingBy(s -> s.getEcommerce().getName(),
                        Collectors.groupingBy(s -> {
                            String date = sdf.format(s.getEcSalesDate());
                            return date.substring(0, 7);  // 只保留年月部分
                        })));
        
		// 從分析過後的 analyze sales data， 轉成 sales data dto，將分組過後的資料，加總銷售量&銷售額
        List<SalesDataDto> salesDataByEcDateDtos = new ArrayList<>();
        int number = 1;

        for (Map.Entry<String, Map<String, List<SalesData>>> entry : groupedData.entrySet()) {
            String ecommerce = entry.getKey();
            Map<String, List<SalesData>> monthlyData = entry.getValue();

            for (Map.Entry<String, List<SalesData>> monthEntry : monthlyData.entrySet()) {
                String month = monthEntry.getKey();
                List<SalesData> monthlySalesData = monthEntry.getValue();

                int totalSales = monthlySalesData.stream().mapToInt(SalesData::getEcSalesPrice).sum();
                int totalSalesQuantity = monthlySalesData.stream().mapToInt(SalesData::getEcSalesQty).sum();

                SalesDataDto salesDataDto = new SalesDataDto();
                salesDataDto.setNumber(number++);
                salesDataDto.setEcommerce(ecommerce);
                salesDataDto.setMonth(month);
                salesDataDto.setSales(totalSalesQuantity);
                salesDataDto.setSalesFigures(totalSales);

                salesDataByEcDateDtos.add(salesDataDto);
            }
        }
		
		//---------------------------------------------------------------------------------------------------------
    	//先篩選當月月份
        List<SalesData> curMonthForEc = salesDataDao.findAllSalesDatas();
        curMonthForEc = setCurrentMonthDatas(curMonthForEc);
		
		// 計算當月總銷售金額
		List<SalesData> salesDataCalSalesFigures = salesDataDao.findAllSalesDatas();
		salesDataCalSalesFigures = setCurrentMonthDatas(salesDataCalSalesFigures);
		
		setCurrentMonthDatas(salesDataCalSalesFigures);
        int totalSalesFigures = salesDataCalSalesFigures.stream()
                .mapToInt(SalesData::getEcSalesPrice)
                .sum();
		
        // 計算當月訂單數
        int orderSaleCount = 0;
        int orderRefundCount = 0;

        for (SalesData salesData : salesDataCalSalesFigures) {
            if ("退貨".equals(salesData.getEcSalesStatus())) {
                orderRefundCount += 1;
            } else {
                orderSaleCount += 1;
            }
        }
        int orderCount = orderSaleCount - orderRefundCount;
        
        // 計算當月平均客單
        double AOV = totalSalesFigures / orderCount;
		
		// 相同平台 合併 銷售數量 + 銷售額
		List<SalesData> analyzeSalesDatas = new ArrayList<SalesData>();         // 處理後的數據 (邏輯：相同平台 合併 銷售數量 + 銷售額)	
		List<String> ecNames = curMonthForEc.stream().map(s -> s.getEcommerce().getName()).distinct().collect(Collectors.toList());
		for(String ecName: ecNames) {
			List<SalesData> salesEcList = curMonthForEc.stream().filter(s->s.getEcommerce().getName().equals(ecName)).collect(Collectors.toList());
			int qty = salesEcList.stream().mapToInt(s-> s.getEcSalesQty()).sum();
			int price =  salesEcList.stream().mapToInt(s-> s.getEcSalesPrice()).sum();
			SalesData salesEcData = salesEcList.get(0);
			salesEcData.setEcSalesQty(qty);
			salesEcData.setEcSalesPrice(price);
			analyzeSalesDatas.add(salesEcData);
		}
		
		//------------------------------------------------------------------------------------
		// 品牌月銷售趨勢
		// 使用Map來存儲分組後的數據:同品牌、同銷售日期(月份)
		List<SalesData> salesDatasMapByBrandAndDate = salesDataDao.findAllSalesDatas();
		Map<String, Map<String, List<SalesData>>> groupedDataByBrandAndDate = salesDatasMapByBrandAndDate.stream()
                .collect(Collectors.groupingBy(s -> {
		                	Product product = s.getProduct();
		                    if (product != null) {
		                        ProductBrand brand = product.getProductBrand();
		                        if (brand != null) {
		                            return brand.getName();
		                        }
		                    }
		                    return "UnknownBrand";
		                },
                		LinkedHashMap::new,
                		Collectors.groupingBy(
                                s -> {
                                    String date = sdf.format(s.getEcSalesDate());
                                    return date.substring(0, 7);  // 只保留年月部分
                                }
                        )
                ));
        
		// 從分析過後的 analyze sales data， 轉成 sales data dto，將分組過後的資料，加總銷售量&銷售額
        List<SalesDataDto> salesDataByBrandAndDateDtos = new ArrayList<>();
        int numberBrand = 1;

        for (Map.Entry<String, Map<String, List<SalesData>>> entry : groupedDataByBrandAndDate.entrySet()) {
            String brand = entry.getKey();
            Map<String, List<SalesData>> monthlyData = entry.getValue();

            for (Map.Entry<String, List<SalesData>> monthEntry : monthlyData.entrySet()) {
                String month = monthEntry.getKey();
                List<SalesData> monthlySalesData = monthEntry.getValue();

                int totalSales = monthlySalesData.stream().mapToInt(SalesData::getEcSalesPrice).sum();
                int totalSalesQuantity = monthlySalesData.stream().mapToInt(SalesData::getEcSalesQty).sum();

                SalesDataDto salesDataDto = new SalesDataDto();
                salesDataDto.setNumber(numberBrand++);
                salesDataDto.setBrand(brand);
                salesDataDto.setMonth(month);
                salesDataDto.setSales(totalSalesQuantity);
                salesDataDto.setSalesFigures(totalSales);

                salesDataByBrandAndDateDtos.add(salesDataDto);
            }
        } 
		
		 
        // 相同品牌 合併 銷售數量 + 銷售額
        // 先建立篩選當月資料
        List<SalesData> curMonthDatasForBrand = salesDataDao.findAllSalesDatas();
        curMonthDatasForBrand = setCurrentMonthDatas(curMonthDatasForBrand);
        
 		List<SalesData> analyzeBrandSalesDatas = new ArrayList<SalesData>();         // 處理後的數據 (邏輯：相同品牌 合併 銷售數量 + 銷售額)	
 		List<Integer> productBrandIds = curMonthDatasForBrand.stream().map(s -> s.getProduct().getProductBrandId()).distinct().collect(Collectors.toList());
 		for(Integer productBrandId : productBrandIds) {
 			List<SalesData> salesList = curMonthDatasForBrand.stream().filter(s->s.getProduct().getProductBrandId().equals(productBrandId)).collect(Collectors.toList());
 			int qty = salesList.stream().mapToInt(s-> s.getEcSalesQty()).sum();
 			int price =  salesList.stream().mapToInt(s-> s.getEcSalesPrice()).sum();
 			SalesData salesData = salesList.get(0);
 			salesData.setEcSalesQty(qty);
 			salesData.setEcSalesPrice(price);
 			analyzeBrandSalesDatas.add(salesData);
 		}
		
 		//------------------------------------------------------------------------------------
		// 分類月銷售趨勢
		// 使用Map來存儲分組後的數據:同分類、同銷售日期(月份)
		List<SalesData> salesDatasMapByTypeAndDate = salesDataDao.findAllSalesDatas();
		Map<String, Map<String, List<SalesData>>> groupedDataByTypeAndDate = salesDatasMapByTypeAndDate.stream()
                .collect(Collectors.groupingBy(s -> {
		                	Product product = s.getProduct();
		                    if (product != null) {
		                        ProductType type = product.getProductType();
		                        if (type != null) {
		                            return type.getName();
		                        }
		                    }
		                    return "UnknownType";
		                },
                		LinkedHashMap::new,
                		Collectors.groupingBy(
                                s -> {
                                    String date = sdf.format(s.getEcSalesDate());
                                    return date.substring(0, 7);  // 只保留年月部分
                                }
                        )
                ));
        
		// 從分析過後的 analyze sales data， 轉成 sales data dto，將分組過後的資料，加總銷售量&銷售額
        List<SalesDataDto> salesDataByTypeAndDateDtos = new ArrayList<>();
        int numberType = 1;

        for (Map.Entry<String, Map<String, List<SalesData>>> entry : groupedDataByTypeAndDate.entrySet()) {
            String type = entry.getKey();
            Map<String, List<SalesData>> monthlyData = entry.getValue();

            for (Map.Entry<String, List<SalesData>> monthEntry : monthlyData.entrySet()) {
                String month = monthEntry.getKey();
                List<SalesData> monthlySalesData = monthEntry.getValue();

                int totalSales = monthlySalesData.stream().mapToInt(SalesData::getEcSalesPrice).sum();
                int totalSalesQuantity = monthlySalesData.stream().mapToInt(SalesData::getEcSalesQty).sum();

                SalesDataDto salesDataDto = new SalesDataDto();
                salesDataDto.setNumber(numberType++);
                salesDataDto.setProductDepartment(type);
                salesDataDto.setMonth(month);
                salesDataDto.setSales(totalSalesQuantity);
                salesDataDto.setSalesFigures(totalSales);

                salesDataByTypeAndDateDtos.add(salesDataDto);
            }
        } 
 		
        
        // 相同中分類 合併 銷售數量 + 銷售額
        // 先建立篩選當月資料
        List<SalesData> curMonthDatasForSubType = salesDataDao.findAllSalesDatas();
        curMonthDatasForSubType = setCurrentMonthDatas(curMonthDatasForSubType);
        
 		List<SalesData> analyzeSubTypeSalesDatas = new ArrayList<SalesData>();         // 處理後的數據 (邏輯：相同品牌 合併 銷售數量 + 銷售額)	
 		List<Integer> productSubTypeIds = curMonthDatasForSubType.stream().map(s -> s.getProduct().getProductSubTypeId()).distinct().collect(Collectors.toList());
 		for(Integer productSubTypeId : productSubTypeIds) {
 			List<SalesData> salesList = curMonthDatasForSubType.stream().filter(s->s.getProduct().getProductSubTypeId().equals(productSubTypeId)).collect(Collectors.toList());
 			int qty = salesList.stream().mapToInt(s-> s.getEcSalesQty()).sum();
 			int price =  salesList.stream().mapToInt(s-> s.getEcSalesPrice()).sum();
 			SalesData salesData = salesList.get(0);
 			salesData.setEcSalesQty(qty);
 			salesData.setEcSalesPrice(price);
 			analyzeSubTypeSalesDatas.add(salesData);
 		}
 		        
 		        
		
		//----------------------------------------------------------------------------
		// 從將當月、平台合併 分析過後的 analyze sales data， 轉成 sales data dto
		List<SalesDataDto> salesDataByEcDtos = new ArrayList<SalesDataDto>();
		setSalesDataDto(analyzeSalesDatas,salesDataByEcDtos);		
		// 最原始資料轉成 sales data dto
		List<SalesDataDto> salesDataDtos = new ArrayList<SalesDataDto>();
		setSalesDataDto(salesDatasOr,salesDataDtos);
		// 從將當月、品牌合併 分析過後的 analyze sales data， 轉成 sales data dto
		List<SalesDataDto> salesDataByBrandDtos = new ArrayList<SalesDataDto>();
		setSalesDataDto(analyzeBrandSalesDatas,salesDataByBrandDtos);
		// 從將當月、中分類合併 分析過後的 analyze sales data， 轉成 sales data dto
		List<SalesDataDto> salesDataBySubTypeDtos = new ArrayList<SalesDataDto>();
		setSalesDataDto(analyzeSubTypeSalesDatas,salesDataBySubTypeDtos);
		
		
		
		model.addAttribute("salesDataByEcDateForCharts", new Gson().toJson(salesDataByEcDateDtos)); // 合併當日取月、平台資訊
		model.addAttribute("salesDataByEcForCharts", new Gson().toJson(salesDataByEcDtos)); // 合併當月、平台資訊
		model.addAttribute("salesDataForCharts", new Gson().toJson(salesDataDtos)); // 最原始資料
		model.addAttribute("totalSalesFigures", new Gson().toJson(totalSalesFigures)); // 當月總銷售金額
		model.addAttribute("orderCount", new Gson().toJson(orderCount)); // 當月總訂單
		model.addAttribute("AOV", new Gson().toJson(AOV)); // 當月均客單
		model.addAttribute("salesDataByBrandAndDateForCharts", new Gson().toJson(salesDataByBrandAndDateDtos)); // 合併當日取月、品牌資訊
		model.addAttribute("salesDataByBrandForCharts", new Gson().toJson(salesDataByBrandDtos)); // 合併當月、品牌資訊
		model.addAttribute("salesDataByTypeAndDateForCharts", new Gson().toJson(salesDataByTypeAndDateDtos)); // 合併當日取月、分類資訊
		model.addAttribute("salesDataBySubTypeForCharts", new Gson().toJson(salesDataBySubTypeDtos)); // 合併當月、中分類資訊
		
		
		return "/analyze/main";
	}
	
	//---------------------------------------------------------------------------------------
	// 篩選當月銷售資料
	public List<SalesData> setCurrentMonthDatas (List<SalesData> salesDatasOnlyCurrDatas) {
		
		salesDatasOnlyCurrDatas = salesDataDao.findAllSalesDatas();
		LocalDate currentDate = LocalDate.now();
		salesDatasOnlyCurrDatas = salesDatasOnlyCurrDatas.stream()
		        .filter(s -> {
		            Instant instant = ((java.sql.Date) s.getEcSalesDate()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
		            LocalDate saleDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		            return saleDate.getMonth() == currentDate.getMonth();
		        })
		        .collect(Collectors.toList());
		
		return salesDatasOnlyCurrDatas;
		
	}
	
	//---------------------------------------------------------------------------------------
	// 將幾組要渲染前端的資料，裝進 salesDataDto
	//                                       分析處理過後的銷售資料                 要放進dto傳到前端的容器        
	public void setSalesDataDto( List<SalesData> analyzeSalesDatas, List<SalesDataDto> salesDataByEcDtos) {
		
		for (int i=0;i<analyzeSalesDatas.size();i++) {
					
					SalesData salesData = analyzeSalesDatas.get(i);
					Product product = productDao.findProductById(salesData.getProductId()).get();
					ProductType productType = product.getProductType();
					ProductSubType productSubType = product.getProductSubType();
					ProductBrand productBrand = product.getProductBrand();
					Stock stock = product.getInventory()
							             .stream()
							             .findFirst()
							             .get();
					
					SalesDataDto salesDataDto = new SalesDataDto();
					salesDataDto.setNumber((i+1));
					salesDataDto.setProductId(salesData.getProductId());
					salesDataDto.setProductName(product.getProductName());
					salesDataDto.setBrand(productBrand.getName());
					salesDataDto.setProductDepartment(productType.getName());
					salesDataDto.setProductType(productSubType.getName());
					salesDataDto.setProductEAN(product.getProductBarcode());
					salesDataDto.setEcQuantity(stock.getEcProductQty());
					salesDataDto.setQuantity(product.getProductQty());
					salesDataDto.setSales(salesData.getEcSalesQty());
					salesDataDto.setSalesFigures(salesData.getEcSalesPrice());
					salesDataDto.setDate(sdf.format(salesData.getEcSalesDate()));
					salesDataDto.setEcommerce(salesData.getEcommerce().getName());
					salesDataDto.setYoy(50);
					salesDataByEcDtos.add(salesDataDto);// 之後要算，先寫死
				}
		
	}
	
}
