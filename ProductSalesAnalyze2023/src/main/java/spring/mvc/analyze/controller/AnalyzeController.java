package spring.mvc.analyze.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import spring.mvc.analyze.model.dao.AnalyzeDao;
import spring.mvc.analyze.model.entity.SalesData;
import spring.mvc.analyze.model.entity.User;


@Controller
@RequestMapping("/analyze")
public class AnalyzeController {
	
	@Autowired
	private AnalyzeDao dao;
	
	
	//登入首頁
	/* 這裡的return路徑設定會與springmvc-servlet.xml的設定有關；
	 * <!-- 配置 view 渲染器位置 -->
		<bean id="internalResourceViewResolver"
			  class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		  <property name="prefix" value="/WEB-INF/views/" />
		  <property name="suffix" value=".jsp" />
		</bean>
	 */
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
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/main"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	// ecWebsite momo
	@GetMapping("/ecWebsite/momo")
	public String ecWebsiteMomo(Model model) {
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/ecWebsite/momo"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
		
	// ecWebsite momo2
	@GetMapping("/ecWebsite/momo2")
	public String ecWebsiteMomo2(Model model) {
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/ecWebsite/momo2"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	//---------------------------------------------------------------------------------------------------
	// POI EXCEL 檔案匯入
	// 3.17版本 (為了可以支援commons-io 2.6的版本)
	/*
	@PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("uploadFile") MultipartFile uploadFile, Model model) {
        if (!uploadFile.isEmpty()) {
            try (InputStream inputStream = uploadFile.getInputStream()) {
                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

                // 遍歷每一行
                for (Row row : sheet) {
                    // 遍歷每一列
                    for (Cell cell : row) {
                        // 根據單元格類型進行相應的處理
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            // 其他類型的處理可以根據需要添加
                            default:
                                System.out.print("Unsupported Cell Type\t");
                        }
                    }
                    System.out.println(); // 換行
                }

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
	
}
