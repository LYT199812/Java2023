package spring.mvc.analyze.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.google.gson.Gson;

import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.ProductTypeDao;
import spring.mvc.analyze.dao.SalesDataDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.dto.SalesDataDto;
import spring.mvc.analyze.entity.Ecommerce;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductBrand;
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
			salesDataDto.setYoy(50); // 之後要算，先寫死
			salesDataDtos.add(salesDataDto);
		}

		model.addAttribute("salesData", new Gson().toJson(salesDataDtos));
		
		return "/analyze/ecWebsite/eccommerce";
	}
	
	// 大量匯入銷售原始資料
	@PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("source") String source, @RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request, Model model) {
		
		//------設定source----------------------------------------------
		// 根據 source 確定對應的 ecId
	    int ecId;
	    switch (source) {
	        case "momo":
	            ecId = 1;
	            break;
	        case "pchome":
	            ecId = 2;
	            break;
	        case "shopee":
	            ecId = 3;
	            break;
	        default:
	            // 預設為 0 或其他處理方式
	            ecId = 0;
	            break;
	    }
	    
		if (!uploadFile.isEmpty()) {
            try (InputStream inputStream = uploadFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

                List<SalesData> dataList = new ArrayList<>();
                
                
                // 遍歷每一行
                int rowIndex = 0;  // 用於追蹤行索引
                for (Row row : sheet) {
                	// 跳過第一行（表頭）
                    if (rowIndex == 0) {
                        rowIndex++;
                        continue;}
                	
                	SalesData salesData = new SalesData();
                	Product product = new Product();
                	salesData.setEcommerce(Ecommerce.builder().id(ecId).build());
                    
                	// 遍歷每一列
                    for (Cell cell : row) {
                    	int columnIndex = cell.getColumnIndex();
                        // 根據單元格的索引將資料映射到對應的屬性
                        switch (columnIndex) {
                            case 0:
                            	//強制設定單元格cell類型
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcOrderNumber(cell.getStringCellValue());
                                break;
                            case 1:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductCode(cell.getStringCellValue());
                                break;
                            case 2:
                            	cell.setCellType(CellType.STRING);
                            	product.setProductName(cell.getStringCellValue());
                                break;
                            case 3:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductId(cell.getStringCellValue());
                                break;
                            case 4:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductType(cell.getStringCellValue());
                                break;
                            case 5:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductSubType(cell.getStringCellValue());
                                break;
                            case 6:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcWarehouse(cell.getStringCellValue());
                                break;
                            case 7:
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setEcSalesQty((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                        salesData.setEcSalesQty(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setEcSalesQty(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setSales((int)(cell.getNumericCellValue()));
                                //break;
                            case 8:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setEcSalesPrice((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String priceStringValue = cell.getStringCellValue().trim();
                                    try {
                                        salesData.setEcSalesPrice(Integer.parseInt(priceStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setEcSalesPrice(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setPrice((int)(cell.getNumericCellValue()));
                                //break;
                            case 9:
                            	if (DateUtil.isCellDateFormatted(cell)) {
                                    salesData.setEcSalesDate(cell.getDateCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesDateStringValue = cell.getStringCellValue().trim();
                                    System.out.println("Trying to parse date: " + salesDateStringValue);
                                    // 根據實際情況進行日期解析，這裡僅提供示例
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        Date salesDate = dateFormat.parse(salesDateStringValue);
                                        salesData.setEcSalesDate(salesDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        // 處理日期解析失敗的情況
                                    }
                                }
                                break;
								
                            case 10:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcSalesStatus(cell.getStringCellValue());
                                break;                               
                            // 忽略不需要的列
                            default:
                            	break;
                        }
                    }
                    dataList.add(salesData);
                }
                // 將 SalesData 物件保存到資料庫
                salesDataDao.addSalesDataByExcel(dataList);

                return "File uploaded and processed successfully.";
            } catch (IOException ex) {
                ex.printStackTrace();
                return "Error processing the file.";
            }
        } else {
            return "File is empty.";
        }
    }
	
	
	
	/*
	@PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("uploadFile") MultipartFile uploadFile, Model model) {
        if (!uploadFile.isEmpty()) {
            try (InputStream inputStream = uploadFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

                List<SalesData> dataList = new ArrayList<>();
                
                // 遍歷每一行
                int rowIndex = 0;  // 用於追蹤行索引
                for (Row row : sheet) {
                	// 跳過第一行（表頭）
                    if (rowIndex == 0) {
                        rowIndex++;
                        continue;}
                	
                	SalesData salesData = new SalesData();
                    
                	// 遍歷每一列
                    for (Cell cell : row) {
                    	int columnIndex = cell.getColumnIndex();
                        // 根據單元格的索引將資料映射到對應的屬性
                        switch (columnIndex) {
                            case 0:
                            	//強制設定單元格cell類型
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcOrderNumber(cell.getStringCellValue());
                                break;
                            case 1:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductCode(cell.getStringCellValue());
                                break;
                            case 2:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductId(cell.getStringCellValue());
                                break;
                            case 3:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductType(cell.getStringCellValue());
                                break;
                            case 4:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcProductSubType(cell.getStringCellValue());
                                break;
                            case 5:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcWarehouse(cell.getStringCellValue());
                                break;
                            case 6:
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setEcSalesQty((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                        salesData.setEcSalesQty(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setEcSalesQty(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setSales((int)(cell.getNumericCellValue()));
                                //break;
                            case 7:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setEcSalesPrice((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String priceStringValue = cell.getStringCellValue().trim();
                                    try {
                                        salesData.setEcSalesPrice(Integer.parseInt(priceStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setEcSalesPrice(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setPrice((int)(cell.getNumericCellValue()));
                                //break;
                            case 8:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    salesData.setEcSalesDate(cell.getDateCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesDateStringValue = cell.getStringCellValue().trim();
                                    // 根據實際情況進行日期解析，這裡僅提供示例
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        Date salesDate = dateFormat.parse(salesDateStringValue);
                                        salesData.setEcSalesDate(salesDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        // 處理日期解析失敗的情況
                                    }
                                }
                                break;

                            case 9:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setEcSalesStatus(cell.getStringCellValue());
                                break;                               
                            // 忽略不需要的列
                            default:
                            	break;
                        }
                    }
                    dataList.add(salesData);
                }
                // 將 SalesData 物件保存到資料庫
                salesDataDao.addSalesDataByExcel(dataList);

                return "File uploaded and processed successfully.";
            } catch (IOException ex) {
                ex.printStackTrace();
                return "Error processing the file.";
            }
        } else {
            return "File is empty.";
        }
    }
	*/
	
}
