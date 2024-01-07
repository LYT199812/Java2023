drop table if exists user;
-- 創建user資料表
create table if not exists user(
	userId int auto_increment primary key,
    username varchar(50) unique not null,
    password varchar(20) not null,
    level int not null
); 

drop table if exists salesdata;
-- 創建 salesdata 表格
-- private String orderNumber;
-- private String productCodeMO;
--     private String productName;
--     private String productId;
--     private String productDepartment;
--     private String productType;
--     private String warehouse;
--     private Integer sales;
--     private Integer price;
--     private String salesDate;
--     private String salesOrReturn;
create table if not exists salesdata(
	orderNumber varchar(100) unique not null,
    productCodeMO varchar(50) unique not null primary key,
    productName varchar(50) not null,
    productId varchar(50) unique not null,
    productDepartment varchar(50),
    productType varchar(50),
    warehouse varchar(50) not null,
    sales int not null,
    price int not null,
    salesDate varchar(50) not null,
    salesOrReturn varchar(50) not null
); 

drop table if exists product;
-- 創建 product 表格
-- private String productId;
-- 	private String productName;
-- 	private Integer price;
-- 	private String barcode;
-- 	private String brand;
-- 	private String productDepartment;
-- 	private String productType;
-- 	private String productCodeMO;
-- 	private String productCodePC;
-- 	private String productCodeSP;
-- 	private Boolean isLaunch;
create table if not exists product(
	productId varchar(20) unique not null primary key,
    productName varchar(50) not null,
    price int not null,
    barcode varchar(20),
    brand varchar(50),
    productDepartment varchar(50),
    productType varchar(50),
    productCodeMO varchar(20),
    productCodePC varchar(20),
    productCodeSP varchar(20),
    isLaunch varchar(20)
);

-------------------------------------------------------------------------------------
-- 設立預設值
insert into user (userId, username, password, level) values
(101, 'John', 'pass123', 1),
(102, 'Sean', 'pass456', 2),
(103, 'Amy', 'pass789', 3)