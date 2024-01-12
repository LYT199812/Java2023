package spring.mvc.analyze.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

	private Integer serviceId;
	
	private String serviceName;
	
	private String serviceUrl;
}
