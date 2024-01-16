package spring.mvc.analyze.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesData {

	private Integer trxId;
	private String ecOrderNumber;
	private String productId;
	private String ecProductCode;
	private String ecProductType;
    private String ecProductSubType;
    private String ecWarehouse;
    private Integer ecSalesQty;
    private Integer ecSalesPrice;
    private Date ecSalesDate;
    private String ecSalesStatus;
    private Date createTime;
    
    private Ecommerce ecommerce;
    private Product product;
    
}
