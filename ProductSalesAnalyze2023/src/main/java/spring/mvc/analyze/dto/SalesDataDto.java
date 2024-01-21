package spring.mvc.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDataDto {

	Integer number;
	String productId;
	String productName;
	String brand;
	String productDepartment;
	String productType;
	String productEAN;
	Integer ecQuantity;
	Integer quantity;
	Integer sales;
	Integer salesFigures;
	String date;
	Integer yoy;
	String month;
	
	String ecommerce;
}
