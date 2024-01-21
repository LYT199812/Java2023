package spring.mvc.analyze.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import spring.mvc.analyze.dao.EcommerceDao;
import spring.mvc.analyze.dao.ProductBrandDao;
import spring.mvc.analyze.dao.ProductDao;
import spring.mvc.analyze.dao.ProductTypeDao;
import spring.mvc.analyze.dao.StockDao;
import spring.mvc.analyze.entity.Ecommerce;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductBrand;
import spring.mvc.analyze.entity.ProductSubType;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.Stock;



@Controller
@RequestMapping("/product")
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
	/*
	// product editProduct (取的預設資料 1)
	@GetMapping("/editProduct/{productId}")
	public String getProduct(@PathVariable("productId") String productId, Model model) {
		
		addBasicModel(model);
		
		Optional<Product> productOpt = productDao.findProductById(productId);
		Product product = productOpt.get();
		
		model.addAttribute("product", product);
		model.addAttribute("submitBtnName", "修改");
		model.addAttribute("_method", "PUT");
		
		// 根據商品和平台ID查詢庫存資料
	    List<Stock> stockList = new ArrayList<>();
	    Stock stock = product.getInventory()
	             .stream()
	             .filter(s-> s.getProductId().equals(productId))
	             .findFirst()
	             .get();

	    model.addAttribute("stockList", stockList);
		
		
		return "analyze/product/editProduct";
	}
	*/
	
	
	// product editProduct (取的預設資料 2)
	@GetMapping("/editProduct2/{productId}")
	public String editProduct(@PathVariable("productId") String productId,Model model) {
		Optional<Product> productOpt = productDao.findProductById(productId);
		Product product = productOpt.get();
		// 處理平台上架的標記
        for (Stock stock : product.getInventory()) {
            model.addAttribute("platform_" + stock.getEcId() + "_isOnSale", true);
        }

//		// 取得所有可能的平台，這裡假設你有一個方法可以取得所有平台的列表
//		List<Ecommerce> ecommerces = product.getInventory().stream()
//			    .map(Stock::getEcommerce)
//			    .distinct()
//			    .collect(Collectors.toList()); 
//
//	    // 將所有平台設置到model中
//	    model.addAttribute("ecommerce", ecommerces);
//		
//	    //創建一個 Map 以保存庫存，以 ecId 為 key
//	    Map<Integer, Stock> stockMap = product.getInventory()
//	            .stream()
//	            .filter(stock -> stockDao.findStockByProductIdAndEcId(productId, stock.getEcId()).isPresent())
//	            .collect(Collectors.toMap(Stock::getEcId, Function.identity()));
//        
        
//        List<Stock> stockOpt = product.getInventory()
//        	    .stream()
//        	    .filter(stock -> stockDao.findStockByProductIdAndEcId(productId, stock.getEcId()).isPresent())
//        	    .collect(Collectors.toList());

        model.addAttribute("product", product);
//        model.addAttribute("stockMap", stockMap);
		return "analyze/product/editProduct2";
	}
	
	
	// 新增商品
	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute Product product,  
							 HttpSession session) {
		productDao.addProduct(product);
		return "analyze/product/addProduct";
	}
	
	// 修改商品 (1)
	@PostMapping("/updateProduct") // 修改 Product
	public String updateProduct(
			@RequestParam("eccommerce") int[] checkedList,
			@RequestParam("eccommerceQty1") int eccommerceQty1,
			@RequestParam("eccommerceQty2") int eccommerceQty2,
			@RequestParam("eccommerceQty3") int eccommerceQty3,
			@RequestParam("productId") String productId, Model model) {
		
		
		
		//int rowcount = productDao.updateProductById(product);
		//int rowcount2 = stockDao.updateStock(stock);
		//System.out.println("update User&stock rowcount = " + rowcount + rowcount2);
		return "analyze/product/maintainProduct";
	}
	
	/*
	// 修改商品 (2)
	@PostMapping("/updateProduct/{productId}")
    public String updateProduct(@PathVariable String productId, 
    							@RequestParam String productName,  
    							@RequestParam Integer productPrice,  
    							@RequestParam String productBarcode,  
    							@RequestParam String productBrand,  
    							@RequestParam Integer productTypeId,  
    							@RequestParam Integer productSubTypeId,  
    							@RequestParam String productDesc,  
    							@RequestParam Integer productQty,  
    							@RequestParam Integer ecProductQty,  
    							
    							@ModelAttribute Product product, 
    							@ModelAttribute Stock stock, Model model) {
		// 使用 productId 來辨識要更新的商品
//	    product.setProductId(productId);
		int rowCount = productDao.updateProductById(product);
		int rowCount2 = stockDao.updateStock(stock);

        if (rowCount > 0 && rowCount2>0) {
            // 產品更新成功
            return "redirect:/product/maintainProduct";
        } else {
            // 處理更新失敗的情況
            model.addAttribute("error", "無法更新產品。");
            return "analyze/product/editProduct2";
        }
    }
	*/
	
	
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
