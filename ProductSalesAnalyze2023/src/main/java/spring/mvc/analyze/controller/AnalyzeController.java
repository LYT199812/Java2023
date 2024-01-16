package spring.mvc.analyze.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import spring.mvc.analyze.dao.AnalyzeDao;
import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.ProductType;
import spring.mvc.analyze.entity.SalesData;
import spring.mvc.analyze.entity.User;


@Controller
@RequestMapping("/analyze")
public class AnalyzeController {
	
	@Autowired
	private AnalyzeDao dao;
	
	// 驗證碼
	@GetMapping("/getcode")
	@ResponseBody
	private void getCodeImage(HttpSession session, HttpServletResponse response) throws IOException {
		// 產生一個驗證碼 code
		Random random = new Random();
		//String code = String.format("%04d", random.nextInt(10000)); //0~9999
		String code1 = String.format("%c", (char)(random.nextInt(122-65+1) + 65)); //random生成英文字元
		String code2 = String.format("%c", (char)(random.nextInt(122-65+1) + 65)); //random生成英文字元
		String code3 = String.format("%c", (char)(random.nextInt(122-65+1) + 65)); //random生成英文字元
		String code4 = String.format("%c", (char)(random.nextInt(122-65+1) + 65)); //random生成英文字元
		// 字元轉字串
		String code = code1+code2+code3+code4;
		// 將 code 存放到 session 變數中
		session.setAttribute("code", code);
		
		// Java 2D圖像 產生圖檔
		// 1. 建立圖像暫存區
		BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_INT_BGR);
		// 2. 建立畫布
		Graphics g = img.getGraphics();
		// 3. 設定顏色
		g.setColor(Color.YELLOW);
		// 4. 塗滿背景
		g.fillRect(0, 0, 80, 30);
		// 5. 設定顏色(字的)
		g.setColor(Color.BLACK);
		// 6. 設定字型
		g.setFont(new Font("新細明體", Font.PLAIN, 30)); //Font.PLAIN細體、Font.BOLD粗體
		// 7. 繪字串
		g.drawString(code, 10, 23); // code, x, y chatAt(0)
		// 8. 干擾線
		g.setColor(Color.RED);
		for(int i=0; i<10; i++) {
			int x1 = random.nextInt(80);
			int y1 = random.nextInt(30);
			int x2 = random.nextInt(80);
			int y2 = random.nextInt(30);
			g.drawLine(x1, y1, x2, y2);
		}
		
		// 設定回應類型
		response.setContentType("image/png");
		// 將影像串流回寫給 client
		ImageIO.write(img, "PNG", response.getOutputStream());	
	}
	
	
	//登入首頁
	@GetMapping(value = {"/login", "/", "/login/"})
	public String loginPage(HttpSession session) {
		return "analyze/login"; // 完整路徑：/WEB-INF/views/analyze/login.jsp
	}
	/*
	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpSession session, Model model) {
	    // 通过 HttpServletRequest 对象获取参数
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    // 打印请求参数
	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);

	    // 其他登录逻辑...
	}
	*/
	
	// 前台登入處理
	@PostMapping("/login")
	public String login(@RequestParam(value ="username") String username, 
						@RequestParam(value = "password") String password, 
						HttpSession session, Model model) {
		// 比對驗證碼
//			if (!code.equals(session.getAttribute("code")+"")) {//session.getAttribute("code")原本是物件，加 +"" java會自動拆裝箱轉成字串的來比對
//				session.invalidate(); // session 過期失效
//				model.addAttribute("loginMessage", "驗證碼錯誤");
//				return "group_buy/login";
//			}
		
		// 根據 username 查找 user 物件
		Optional<User> userOpt = dao.findUserByUsername(username);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			// 比對 password
			if (user.getPassword().equals(password)) {
				session.setAttribute("user", user); // 將 user 物件放入到 session 變數中
				// 這邊重導的路徑是看下面@RequestMapping("/frontend/main")設定，因為是client發起(外部路徑)，會重新再經過controller，再重新發起session；
				// 資訊安全的一部分，讓client不知道我們實際內部的檔案路徑
				return "redirect:/mvc/analyze/main"; //login ok，導前台首頁
			}else {
				session.invalidate(); // session 過期失效
				model.addAttribute("loginMessage", "密碼錯誤");
				return "analyze/login";
			}
		}else {
			session.invalidate(); // session 過期失效
			model.addAttribute("loginMessage", "無此使用者");
			return "analyze/login"; // 自己渲染給jsp，再呈現給前端
		}
	}
	
	//登出
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/mvc/analyze/login"; //為了不要重新帶東西渲染給jsp，直接讓瀏覽器重新發送請求
	}
 
	//---------------------------------------------------------------------------------------------
		
	// 首頁
	@GetMapping("/main")
	public String main(Model model) {
		return "analyze/main"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	// product addProduct
	@GetMapping("/product/addProduct")
	public String addProduct(Model model) {
		return "analyze/product/addProduct"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	// product maintainProduct
	@GetMapping("/product/maintainProduct")
	public String maintainProduct(Model model) {
		return "analyze/product/maintainProduct"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
		
	// ecWebsite momo2
	@GetMapping("/ecWebsite/momo2")
	public String ecWebsiteMomo2(Model model) {
		return "analyze/ecWebsite/momo2"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	// 權限設定
	@GetMapping("/permissionSettings")
	public String permissionSettings(Model model) {
		return "analyze/permissionSettings"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	//---------------------------------------------------------------------------------------------------
	// 新增商品
	@PostMapping("/product/addProduct")
	public String addProduct(@ModelAttribute Product product,  
							 HttpSession session) {
			dao.addProduct(product);
			return "analyze/product/addProduct";
	}
	
	
	// POI 批次新增商品 EXCEL 檔案匯入
	@PostMapping("/addProductUpload")
    @ResponseBody
    public String addProductFileUpload(@RequestParam("addProductUploadFile") MultipartFile addProductUploadFile, Model model) {
        if (!addProductUploadFile.isEmpty()) {
            try (InputStream inputStream = addProductUploadFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

                List<Product> dataList = new ArrayList<>();
                
                // 遍歷每一行
                int rowIndex = 0;  // 用於追蹤行索引
                for (Row row : sheet) {
                	// 跳過第一行（表頭）
                    if (rowIndex == 0) {
                        rowIndex++;
                        continue;}
                	
                	Product product = new Product();
                    
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
                    dataList.add(product);
                }
                // 將 SalesData 物件保存到資料庫
                dao.addProductExcelData(dataList);

                return "File uploaded and processed successfully.";
            } catch (IOException ex) {
                ex.printStackTrace();
                return "Error processing the file.";
            }
        } else {
            return "File is empty.";
        }
    }
	
	
	
	//---------------------------------------------------------------------------------------------------
	// POI EXCEL 檔案匯入
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
                            	salesData.setOrderNumber(cell.getStringCellValue());
                                break;
                            case 1:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductCodeMomo(cell.getStringCellValue());
                                break;
                            case 2:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductName(cell.getStringCellValue());
                                break;    
                            case 3:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductId(cell.getStringCellValue());
                                break;
                            case 4:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductDepartment(cell.getStringCellValue());
                                break;
                            case 5:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setProductType(cell.getStringCellValue());
                                break;
                            case 6:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setWarehouse(cell.getStringCellValue());
                                break;
                            case 7:
                            	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setSales((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String salesStringValue = cell.getStringCellValue().trim();;
                                    try {
                                        salesData.setSales(Integer.parseInt(salesStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setSales(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setSales((int)(cell.getNumericCellValue()));
                                //break;
                            case 8:
                                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    salesData.setPrice((int) cell.getNumericCellValue());
                                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                    String priceStringValue = cell.getStringCellValue().trim();
                                    try {
                                        salesData.setPrice(Integer.parseInt(priceStringValue));
                                    } catch (NumberFormatException e) {
                                        // 處理轉換失敗的情況，例如文字不是合法的整數格式
                                        salesData.setPrice(0); // 或者設定為預設值
                                    }
                                }
                                break;
                            	
                            	//salesData.setPrice((int)(cell.getNumericCellValue()));
                                //break;
                            case 9:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setSalesDate(cell.getStringCellValue());
                                break;
                            case 10:
                            	cell.setCellType(CellType.STRING);
                            	salesData.setSalesOrReturn(cell.getStringCellValue());
                                break;                               
                            // 忽略不需要的列
                            default:
                            	break;
                        }
                    }
                    dataList.add(salesData);
                }
                // 將 SalesData 物件保存到資料庫
                dao.saveExcelData(dataList);

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
