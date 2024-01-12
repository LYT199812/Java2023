package spring.mvc.analyze.entity;


public class SalesData {

	private String orderNumber;
	private String productCodeMO;
    private String productName;
    private String productId;
    private String productDepartment;
    private String productType;
    private String warehouse;
    private Integer sales;
    private Integer price;
    private String salesDate;
    private String salesOrReturn;
    
    public SalesData() {
    	
    }
    
	public SalesData(String orderNumber, String productCodeMO, String productName, String productId,
			String productDepartment, String productType, String warehouse, Integer sales, Integer price,
			String salesOrReturn, String salesDate) {
		this.orderNumber = orderNumber;
		this.productCodeMO = productCodeMO;
		this.productName = productName;
		this.productId = productId;
		this.productDepartment = productDepartment;
		this.productType = productType;
		this.warehouse = warehouse;
		this.sales = sales;
		this.price = price;
		this.salesOrReturn = salesOrReturn;
		this.salesDate = salesDate;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getProductCodeMomo() {
		return productCodeMO;
	}
	public void setProductCodeMomo(String productCodeMomo) {
		this.productCodeMO = productCodeMomo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDepartment() {
		return productDepartment;
	}
	public void setProductDepartment(String productDepartment) {
		this.productDepartment = productDepartment;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getSalesOrReturn() {
		return salesOrReturn;
	}
	public void setSalesOrReturn(String salesOrReturn) {
		this.salesOrReturn = salesOrReturn;
	}
	public String getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	
	@Override
	public String toString() {
		return "SalesData [orderNumber=" + orderNumber + ", productCodeMomo=" + productCodeMO + ", productName="
				+ productName + ", productId=" + productId + ", productDepartment=" + productDepartment
				+ ", productType=" + productType + ", warehouse=" + warehouse + ", sales=" + sales + ", price=" + price
				+ ", salesOrReturn=" + salesOrReturn + ", salesDate=" + salesDate + "]";
	}
	
}
