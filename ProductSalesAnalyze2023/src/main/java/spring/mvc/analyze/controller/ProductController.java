package spring.mvc.analyze.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;



@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private StockDao stockDao;
	
	// product addProduct
	@GetMapping("/addProduct")
	public String addProduct(Model model) {
		return "analyze/product/addProduct";
	}
	
	// POI 批次新增商品 EXCEL 檔案匯入
	@PostMapping("/addProductUpload")
    @ResponseBody
    public String addProductFileUpload(@RequestParam("addProductUploadFile") MultipartFile addProductUploadFile, Model model) {
        
		try (InputStream inputStream = addProductUploadFile.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }

                Product product = createProductFromRow(row);
                List<Stock> stock = createStockFromRow(row, product.getProductId());

                productDao.addProduct(product);
                stockDao.addStockByExcel(stock);

                rowIndex++;
            }

            return "Excel data imported successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error importing Excel data.";
        }
    }

    private Product createProductFromRow(Row row) {
        Product product = new Product();
        product.setProductId(getCellValueAsString(row.getCell(0)));
        product.setProductName(getCellValueAsString(row.getCell(1)));
        product.setProductPrice(getCellValueAsInt(row.getCell(2)));
        product.setProductBarcode(getCellValueAsString(row.getCell(3)));
        product.setProductBrand(getCellValueAsString(row.getCell(4)));
        product.setProductTypeId(getCellValueAsInt(row.getCell(5)));
        product.setProductSubTypeId(getCellValueAsInt(row.getCell(6)));
        product.setProductImg(getCellValueAsString(row.getCell(7)));
        product.setProductDesc(getCellValueAsString(row.getCell(8)));
        product.setIsLaunch(getCellValueAsBoolean(row.getCell(9)));

        return product;
    }

    private List<Stock> createStockFromRow(Row row, String productId) {
        
    	List<Stock> stocks = new ArrayList<>();

        // Assuming that ecId_1, ecId_2, ecId_3 are in consecutive columns starting from the 11th column
        for (int i = 1; i <= 3; i++) {
            Stock stock = new Stock();
            stock.setProductId(productId);
            stock.setEcId(getCellValueAsInt(row.getCell(9 + i)));
            stock.setEcProductQty(getCellValueAsInt(row.getCell(12 + i)));
            stock.setProductQty(getCellValueAsInt(row.getCell(16)));

            stocks.add(stock);
        }
        return stocks;
    	
    	/*
    	Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setEcId(getCellValueAsInt(row.getCell(10)));
        stock.setEcId(getCellValueAsInt(row.getCell(11)));
        stock.setEcId(getCellValueAsInt(row.getCell(12)));
        stock.setEcProductQty(getCellValueAsInt(row.getCell(13)));
        stock.setEcProductQty(getCellValueAsInt(row.getCell(14)));
        stock.setEcProductQty(getCellValueAsInt(row.getCell(15)));
        stock.setProductQty(getCellValueAsInt(row.getCell(16)));

        return stock;
        */
    }

    private String getCellValueAsString(Cell cell) {
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private int getCellValueAsInt(Cell cell) {
        
    	if (cell == null) {
            return 1; // 或者你想要的默認值
        }

        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                return (int) cell.getNumericCellValue();
            case Cell.CELL_TYPE_STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    // 轉換失敗處理邏輯
                    return 0; // 或者你想要的默認值
                }
            default:
                return 0;} // 或者你想要的默認值
    	
    	/*
    	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String stringValue = cell.getStringCellValue().trim();
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
        */
    }
		
    // 新增一個用於處理 Boolean 型別的方法
    private Boolean getCellValueAsBoolean(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String stringValue = cell.getStringCellValue().trim();
            return Boolean.parseBoolean(stringValue);
        }
        return false; // 或者設定為預設值
    }
    
		
		/*
		if (!addProductUploadFile.isEmpty()) {
            try (InputStream inputStream = addProductUploadFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

                List<Product> productDataList = new ArrayList<>();
                List<Stock> StockDataList = new ArrayList<>();
                
                // 遍歷每一行
                int rowIndex = 0;  // 用於追蹤行索引
                for (Row row : sheet) {
                	// 跳過第一行（表頭）
                    if (rowIndex == 0) {
                        rowIndex++;
                        continue;}
                	
                	Product product = new Product();
                	Stock stock = new Stock();
                    
                	// 遍歷每一列
                    for (Cell cell : row) {
                    	int columnIndex = cell.getColumnIndex();
                        // 根據單元格的索引將資料映射到對應的屬性
                        switch (columnIndex) {
                            case 0:
                            	//強制設定單元格cell類型
                            	cell.setCellType(CellType.STRING);
                            	product.setProductId(cell.getStringCellValue());
                                break;
                            case 1:
                            	cell.setCellType(CellType.STRING);
                            	product.setProductName(cell.getStringCellValue());
                                break;
                            case 2:
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            		product.setProductPrice((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                    	product.setProductPrice(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                    	product.setProductPrice(0); // 或者設定為預設值
                                    }
                                }
                                break;   
                            case 3:
                            	cell.setCellType(CellType.STRING);
                            	product.setProductBarcode(cell.getStringCellValue());
                                break;
                            case 4:
                            	cell.setCellType(CellType.STRING);
                            	product.setProductBrand(cell.getStringCellValue());
                                break;
                            case 5:            
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            		product.setProductType(ProductType.builder().id((int) cell.getNumericCellValue()).build());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                    	product.setProductTypeId(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                    	product.setProductTypeId(0); // 或者設定為預設值
                                    }
                                }
                                break;   
                            case 6:
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            		product.setProductSubTypeId((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                    	product.setProductSubTypeId(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                    	product.setProductSubTypeId(0); // 或者設定為預設值
                                    }
                                }
                                break;   
                            case 7:
                            	cell.setCellType(CellType.BOOLEAN);
                            	product.setIsLaunch(cell.getBooleanCellValue());
                                break;                               
                            // 忽略不需要的列
                            default:
                            	break;
                        }
                    }
                    productDataList.add(product);
                    StockDataList.add(stock);                    
                }
                // 將 SalesData 物件保存到資料庫
                productDao.addProductByExcel(productDataList);
                stockDao.addStockByExcel(StockDataList);

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
