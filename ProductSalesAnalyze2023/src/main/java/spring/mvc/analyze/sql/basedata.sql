-- 1. 設立 level 預設值
insert into level (levelName) values ('分析人員'),('管理人員'),('主管');

-- 2. 設立 service 預設值
insert into service (serviceName, serviceUrl) 
values ('首頁', '/mvc/analyze/main'),
('Momo', '/mvc/analyze/ecWebsite/momo2'),
('商品', '/mvc/analyze/product/addProduct'),
('庫存', '/mvc/analyze/productQty'),
('權限管理', '/mvc/analyze/product/permissionSettings');

-- 3. 設立 levelRefService 預設值
insert into levelRefService (levelId, serviceId) values (1, 1),(1, 2),(2, 1),(2, 3),(2, 4),(3, 1),(3, 2),(3, 3),(3, 4),(3, 5);

-- 4. 設立 User 預設值
insert into user (username, password, levelId) values ('John', 'pass123', 1),('Sean', 'pass456', 2),('Amy', 'pass789', 3);

-- -------------------------------------------------------------------------------------------------------------------------------
-- 5. 設立 productType 預設值
insert into productType (name) values ('餐廚'), ('烘焙'), ('傢俱'), ('旅遊');

-- 6. 設立 productSubType 預設值
insert into productSubType (name) values ('鍋鏟'), ('餅乾模'), ('平底鍋'), ('主廚刀'),
('醬料刷'),	('量杯'), ('電子秤'), ('量匙'), ('隔熱手套'), 
('派烤盤'), ('蛋糕烤盤'), ('可麗露模'), ('烘焙紙'), ('計時器');

-- 7. 設立 product 預設值
insert into product (productId, productName, productPrice, productBarcode, productBrand, productTypeId, productSubTypeId, productImg, productDesc, isLaunch) 
values
('A101', '好用鍋鏟', 550, '12345678', 'AAA', 1, 1, '','', 0), ('B101', '好用平底鍋', 1200, '22345678', 'BBB', 1, 2, '','', 0), 
('C101', '好用平底鍋', 1200, '22345689', 'CCC', 1, 2, '','', 0), ('A102', '好用平底鍋', 550, '12345678', 'AAA', 1, 1, '','', 0),
('A103', '好用湯杓', 550, '12345678', 'AAA', 1, 1, '','', 0), ('A104', '好用主廚刀', 550, '12345678', 'AAA', 1, 1, '','', 0),
('A105', '好用料理剪', 550, '12345678', 'AAA', 1, 1, '','', 0), ('A106', '好用砧板', 550, '12345678', 'AAA', 1, 1, '','', 0),
('A107', '好用鍋蓋', 550, '12345678', 'AAA', 1, 1, '','', 0), ('A108', '好用湯鍋', 550, '12345678', 'AAA', 1, 1, '','', 0),
('B102', '超好用平底鍋', 550, '12345678', 'BBB', 1, 1, '','', 0), ('B103', '好用湯杓', 550, '12345678', 'BBB', 1, 1, '','', 0);

-- 8. 設立 ecommerce 預設值
insert into ecommerce (name, website) 
values ('Momo', 'https://www.momoshop.com.tw/main/Main.jsp'), 
('PChome', 'https://24h.pchome.com.tw/sign/ce?gclid=EAIaIQobChMI1NX3-N7UgwMVw80WBR0VnQ5PEAAYASAAEgLVPvD_BwE'), 
('蝦皮', 'https://shopee.tw/');

-- 9. 設立 stcok 預設值
insert into stock (productId, ecId, productQty, ecProductQty) values ('A101', 1, 50, 10), ('B101', 1, 50, 8), ('A101', 2, 50, 18), 
('A102', 1, 50, 10), ('A103', 1, 50, 10), ('A104', 1, 50, 10), ('A105', 1, 50, 10), ('A106', 1, 50, 10), ('A107', 1, 50, 10), ('A108', 1, 50, 10),
('B102', 1, 50, 10), ('B103', 1, 50, 10), ('C101', 1, 50, 10);

-- 10.設立 salesdata 預設值
insert into salesdata (ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus) 
values 
(1, 'A101', 'BD-2023123012345-01', '12345-01', '刀具砧板配件', '鍋鏟', '轉單', 2, 550, 20231201, '銷售'),
(1, 'B101', 'BD-2023123012345-02', '12345-02', '鍋具', '平底鍋', '寄倉', 1, 1000, 20231202, '銷售'),
(1, 'A101', 'BD-2023123012345-03', '12345-01', '刀具砧板配件', '鍋鏟', '轉單', 2, 550, 20230909, '銷售');

-- 依照平台，取得商品銷售資訊原始數據(待定)
select d.trxId,p.productId,p.productName,p.productBrand,pt.name,pst.name,p.productBarcode, s.ecProductQty, s.productQty, d.ecSalesQty,d.ecSalesPrice
from product p, salesdata d, ecommerce e , stock s, producttype pt, productsubtype pst
where p.productId = d.productId and d.ecId = e.id
and s.ecId = e.id and s.productId = p.productId
and pt.id = p.productTypeId
and pst.id = productSubTypeId
and e.name = 'Momo';