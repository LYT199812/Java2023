package spring.mvc.analyze.dto;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.mvc.analyze.entity.Ecommerce;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceStockEdit {

	String productId;
	
	Boolean isLaunch;
	
	Ecommerce ecommerce;
	
	Integer stockQty;
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
