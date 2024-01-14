package spring.mvc.analyze.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * productId, 
 * ecId, 
 * productQty, 
 * ecProductQty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

	private String productId; 
	private Integer ecId; 
	private Integer productQty; 
	private Integer ecProductQty;
	
}
