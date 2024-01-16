package test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.SalesData;

public class SecondVersion {

	public static void main(String[] args) {
		// POI EXCEL 檔案匯入
		/* 4.2.5版本，單純讀取資料到eclipse
		@PostMapping("upload")
		@ResponseBody
		public String handleFileUpload(@RequestParam("uploadFile") MultipartFile uploadFile) {
	        if (!uploadFile.isEmpty()) {
	            try (InputStream inputStream = uploadFile.getInputStream()) {
	                Workbook workbook = WorkbookFactory.create(inputStream);
	                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

	                // 遍歷每一行
	                for (Row row : sheet) {
	                    // 遍歷每一列
	                    for (Cell cell : row) {
	                        // 根據單元格類型進行相應的處理
	                        switch (cell.getCellType()) {
	                            case STRING:
	                                System.out.print(cell.getStringCellValue() + "\t");
	                                break;
	                            case NUMERIC:
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
	            } catch (IOException | EncryptedDocumentException ex) {
	                ex.printStackTrace();
	                return "Error processing the file.";
	            }
	        } else {
	            return "File is empty.";
	        }
	    }
		*/

		
		// 單純讀取資料到eclipse 3.17版本 (為了可以支援commons-io 2.6的版本)
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
		
		
		/*
		 * 寫入進SQL，不使用LIST而是單筆單筆寫入
		@PostMapping("/upload")
	    @ResponseBody
	    public String handleFileUpload(@RequestParam("uploadFile") MultipartFile uploadFile, Model model) {
	        if (!uploadFile.isEmpty()) {
	            try (InputStream inputStream = uploadFile.getInputStream()) {
	                Workbook workbook = new XSSFWorkbook(inputStream); // 使用XSSFWorkbook處理xlsx格式的Excel

	                Sheet sheet = workbook.getSheetAt(0); // 假設只有一個工作表，根據實際情況調整索引

	                List<SalesData> dataList = new ArrayList<>();
	                
	                // 遍歷每一行
	                for (Row row : sheet) {
	                	SalesData salesData = new SalesData();
	                    // 遍歷每一列
	                    for (Cell cell : row) {
	                    	int columnIndex = cell.getColumnIndex();
	                    	
	                        // 根據單元格的索引將資料映射到對應的屬性
	                        switch (columnIndex) {
	                            case 0:
	                            	salesData.setOrderNumber(cell.getStringCellValue());
	                                break;
	                            case 1:
	                            	salesData.setProductCodeMomo(cell.getStringCellValue());
	                                break;
	                            case 2:
	                            	salesData.setProductName(cell.getStringCellValue());
	                                break;    
	                            case 3:
	                            	salesData.setProductId(cell.getStringCellValue());
	                                break;
	                            case 4:
	                            	salesData.setProductDepartment(cell.getStringCellValue());
	                                break;
	                            case 5:
	                            	salesData.setProductType(cell.getStringCellValue());
	                                break;
	                            case 6:
	                            	salesData.setWarehouse(cell.getStringCellValue());
	                                break;
	                            case 7:
	                            	salesData.setSales((int)(cell.getNumericCellValue()));
	                                break;
	                            case 8:
	                            	salesData.setPrice((int)(cell.getNumericCellValue()));
	                                break;
	                            case 9:
	                            	salesData.setSalesDate(cell.getStringCellValue());
	                                break;
	                            case 10:
	                            	salesData.setSalesOrReturn(cell.getStringCellValue());
	                                break;                               
	                            // 忽略不需要的列
	                            default:
	                            	break;
	                        }
	                    }
	                    // 將 SalesData 物件保存到資料庫
	                    dao.saveExcelData(null);
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
		
		
		// 使用動態生成sql語法，目的是為了自由選擇要修改的 product 屬性
		/*
		@Override
		public int updateProduct(Product product) {
		    Map<String, Object> updateMap = new HashMap<>();
		    updateMap.put("productName", product.getProductName());
		    updateMap.put("productPrice", product.getProductPrice());
		    updateMap.put("productBrand", product.getProductBrand());

		    // 可以為 null 的屬性
		    Arrays.asList("productBarcode", "productTypeId", "productSubTypeId", "productImg", "productDesc")
		            .forEach(field -> {
		                Object value = getProductFieldValue(product, field);
		                if (value != null) {
		                    updateMap.put(field, value);
		                }
		            });

		    updateMap.put("isLaunch", product.getIsLaunch());

		    String sql = buildUpdateSql("product", updateMap.keySet(), "productId");

		    // 將 Map 的值轉為 Object[] 以便傳遞給 update 方法
		    Object[] params = updateMap.values().toArray();
		    // 在 Object[] 末尾添加 productId 的值
		    Object[] fullParams = Arrays.copyOf(params, params.length + 1);
		    fullParams[params.length] = product.getProductId();
		    System.out.println(sql);
		    return jdbcTemplate.update(sql, fullParams);
		}

		private Object getProductFieldValue(Product product, String fieldName) {
		    try {
		        Field field = Product.class.getDeclaredField(fieldName);
		        field.setAccessible(true);
		        return field.get(product);
		    } catch (NoSuchFieldException | IllegalAccessException e) {
		        e.printStackTrace();
		        return null;
		    }
		}

		private String buildDynamicUpdateSql(String tableName, Set<String> fields, String idField, Product product) {
		    List<String> filteredFields = fields.stream()
		            .filter(field -> !Objects.isNull(getProductFieldValue(product, field)))
		            .collect(Collectors.toList());

		    String setClause = filteredFields.stream()
		            .map(field -> field + " = ?")
		            .collect(Collectors.joining(", "));

		    // 將 Map 的值轉為 Object[] 以便傳遞給 update 方法
		    Object[] params = filteredFields.stream()
		            .map(field -> getProductFieldValue(product, field))
		            .toArray();

		    // 在 Object[] 末尾添加 productId 的值
		    Object[] fullParams = Arrays.copyOf(params, params.length + 1);
		    fullParams[params.length] = product.getProductId();

		    return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause, idField);
		}


		private String buildUpdateSql(String tableName, Set<String> fields, String idField) {
		    String setClause = fields.stream().map(field -> field + " = ?").collect(Collectors.joining(", "));
		    return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause, idField);
		}
		*/
		
	}

}
