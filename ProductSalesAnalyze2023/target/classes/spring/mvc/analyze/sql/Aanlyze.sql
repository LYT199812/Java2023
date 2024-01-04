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
-- private String productCodeMomo;
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
    productCodeMomo varchar(50) unique not null primary key,
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

-- 設立預設值
insert into user (userId, username, password, level) values
(101, 'John', 'pass123', 1),
(102, 'Sean', 'pass456', 2),
(103, 'Amy', 'pass789', 3)