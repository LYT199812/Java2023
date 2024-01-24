package spring.mvc.analyze.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.net.SMTPAppender;
import spring.mvc.analyze.dao.EcommerceDao;
import spring.mvc.analyze.dao.ProductBrandDao;
import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.ProductTypeDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.dto.EcommerceStockEdit;
import spring.mvc.analyze.entity.Ecommerce;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductBrand;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;
import spring.mvc.analyze.exception.StockQtyInquientException;
import spring.mvc.analyze.service.ProductServiceImpl;
import spring.mvc.analyze.service.StockServiceImpl;



@Controller
@RequestMapping("/analyze/product")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private StockDao stockDao;
	
	@Autowired 
	EcommerceDao ecommerceDao;
	
	@Autowired
	ProductTypeDao productTypeDao;
	
	@Autowired
	ProductBrandDao productBrandDao;
	
	@Autowired
	StockServiceImpl stockServiceImpl;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	// product addProduct
	@GetMapping("/addProduct")
	public String addProduct(Model model) {
		return "analyze/product/addProduct";
	}
	// product maintainProduct
	@GetMapping("/maintainProduct")
	public String maintainProduct(Model model) {
		List<Product> productList = productDao.findAllProducts();
        model.addAttribute("products", productList);
		return "analyze/product/maintainProduct";
	}
	
	/**
	 * http://localhost:8080/ProductSalesAnalyze2023/mvc/analyze/product/editProduct2/A108?errorMessage=errorTest
	 * @param productId
	 * @param errorMessage
	 * @param model
	 * @return
	 */
	// product editProduct (取的預設資料 2)
	@GetMapping("/editProduct2/{productId}")
	public String editProduct(
			@PathVariable("productId") String productId,
			@RequestParam(name = "errorMessage", required = false, defaultValue = "") String errorMessage,
			Model model) {
		
		Optional<Product> productOpt = productServiceImpl.findProductById(productId);
		if(productOpt.isPresent()) {
			
			Product product = productOpt.get();
			
			// 處理平台上架的標記
	        for (Stock stock : product.getInventory()) {
	            model.addAttribute("platform_" + stock.getEcId() + "_isOnSale", true);
	        }
	        model.addAttribute("product", product);
	        
	        // 取得所有平台-產品-庫存
	        List<EcommerceStockEdit> ecommerceStockEdits = productServiceImpl.getEcommerceStockEdits(product);
	        model.addAttribute("ecommerceStockEdits", ecommerceStockEdits);
		} else {
			errorMessage = "無此 Product";
		}
	
        model.addAttribute("errorMessage", errorMessage);
		return "analyze/product/editProduct2";
	}
	
	
	// 新增商品
	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute Product product,  
							 HttpSession session) {
		productDao.addProduct(product);
		return "analyze/product/addProduct";
	}
	
	/**
	 * {
	 *   productDesc=, 
	 *   isLaunch_1=true, stockQty_1=10, 
	 *   isLaunch_2=true, stockQty_2=18, 
	 *   isLaunch_3=true, stockQty_3=10, 
	 *   productId=A101
	 * }
	 * @param body
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	// 修改商品 (1)
	@PostMapping(value = "/updateProduct") // 修改 Product
	public String updateProduct(
			@RequestParam Map<String,Object> formData,
			Model model) {
		try {
			stockServiceImpl.updateProduct(formData);
		} catch (StockQtyInquientException e) {
			String urlInfoString = String.format("%s?errorMessage=%s", formData.get("productId"),URLEncoder.encode(e.getMessage()));
			return "redirect:/mvc/analyze/product/editProduct2/"+urlInfoString;
		}
		return "redirect:/mvc/analyze/product/maintainProduct";
	}
	

	// 首頁基礎資料
	private void addBasicModel(Model model) {
		List<Product> products = productDao.findAllProducts();
		List<ProductType> productTypes =productTypeDao.findAllProductTypes();
		List<ProductSubType> productSubTypes =productTypeDao.findAllProductSubTypes();
		List<ProductBrand> productBrands = productBrandDao.findAllProductBrands();
		List<Stock> stocks = stockDao.findAllStocks();
		List<Ecommerce> ecommerces = ecommerceDao.findAllEcommerces();
		
		model.addAttribute("products", products); // 將產品資料傳給 jsp
		model.addAttribute("productTypes", productTypes); // 將大分類資料傳給 jsp
		model.addAttribute("productSubTypes", productSubTypes); // 將中分類資料傳給 jsp
		model.addAttribute("productBrands", productBrands); // 將品牌資料傳給 jsp
		model.addAttribute("stocks", stocks); // 取得目前最新 stocks 資料
		model.addAttribute("ecommerces", ecommerces); // 取得目前最新 ecommerces 資料
	}
	
	
	//----------------------------------------------------------------------------------------
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
        product.setProductBrandId(getCellValueAsInt(row.getCell(4)));
        product.setProductTypeId(getCellValueAsInt(row.getCell(5)));
        product.setProductSubTypeId(getCellValueAsInt(row.getCell(6)));
        product.setProductImg(getCellValueAsString(row.getCell(7)));
        product.setProductDesc(getCellValueAsString(row.getCell(8)));
        product.setProductQty(getCellValueAsInt(row.getCell(9)));
        product.setIsLaunch(getCellValueAsBoolean(row.getCell(10)));

        return product;
    }

    private List<Stock> createStockFromRow(Row row, String productId) {
        
    	List<Stock> stocks = new ArrayList<>();

        // Assuming that ecId_1, ecId_2, ecId_3 are in consecutive columns starting from the 11th column
        for (int i = 1; i <= 3; i++) {
            Stock stock = new Stock();
            stock.setProductId(productId);
            stock.setEcId(getCellValueAsInt(row.getCell(10 + i)));
            stock.setEcProductQty(getCellValueAsInt(row.getCell(13 + i)));

            stocks.add(stock);
        }
        return stocks;
    }

    private String getCellValueAsString(Cell cell) {
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private int getCellValueAsInt(Cell cell) {
        
    	if (cell == null) {
            return 1; 
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
                    return 0; 
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
  
    
    
}
