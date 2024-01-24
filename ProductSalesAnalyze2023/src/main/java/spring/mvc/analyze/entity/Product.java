package spring.mvc.analyze.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private String productId;
	private String productName;
	private Integer productPrice;
	private String productBarcode;
	private Integer productBrandId;
	private Integer productTypeId;
	private Integer productSubTypeId ;
	private String productImg;
	private String productDesc;
	private Integer productQty;
	private Boolean isLaunch;
	
	//private Integer productQty;
	private List<Stock> inventory;
	
	private ProductBrand productBrand;
	private ProductType productType;
	private ProductSubType productSubType ;
	
}
