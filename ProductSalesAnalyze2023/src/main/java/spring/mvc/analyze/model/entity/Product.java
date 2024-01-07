package spring.mvc.analyze.model.entity;

public class Product {

	private String productId;
	private String productName;
	private Integer price;
	private String barcode;
	private String brand;
	private String productDepartment;
	private String productType;
	private String productCodeMO;
	private String productCodePC;
	private String productCodeSP;
	private Boolean isLaunch;
	
	public Product() {
		
	}
	
	public Product(String productId, String productName, Integer price, String barcode, String brand,
			String productDepartment, String productType, String productCodeMO, String productCodePC,
			String productCodeSP, Boolean isLaunch) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.barcode = barcode;
		this.brand = brand;
		this.productDepartment = productDepartment;
		this.productType = productType;
		this.productCodeMO = productCodeMO;
		this.productCodePC = productCodePC;
		this.productCodeSP = productCodeSP;
		this.isLaunch = isLaunch;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	public String getProductCodeMO() {
		return productCodeMO;
	}
	public void setProductCodeMO(String productCodeMO) {
		this.productCodeMO = productCodeMO;
	}
	public String getProductCodePC() {
		return productCodePC;
	}
	public void setProductCodePC(String productCodePC) {
		this.productCodePC = productCodePC;
	}
	public String getProductCodeSP() {
		return productCodeSP;
	}
	public void setProductCodeSP(String productCodeSP) {
		this.productCodeSP = productCodeSP;
	}
	public Boolean getIsLaunch() {
		return isLaunch;
	}
	public void setIsLaunch(Boolean isLaunch) {
		this.isLaunch = isLaunch;
	}
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", price=" + price + ", barcode="
				+ barcode + ", brand=" + brand + ", productDepartment=" + productDepartment + ", productType="
				+ productType + ", productCodeMO=" + productCodeMO + ", productCodePC=" + productCodePC
				+ ", productCodeSP=" + productCodeSP + ", isLaunch=" + isLaunch + "]";
	}
	
	
}
