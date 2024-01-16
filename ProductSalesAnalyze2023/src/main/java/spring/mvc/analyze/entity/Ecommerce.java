package spring.mvc.analyze.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ecommerce {

	private Integer id;
	private String name;
	private String website;
	
}
