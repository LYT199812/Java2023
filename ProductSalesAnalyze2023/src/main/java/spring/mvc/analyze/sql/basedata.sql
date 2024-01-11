<<<<<<< HEAD
-- 設立 User 預設值
select * from user;
insert into user (username, password, level) values ('John', 'pass123', 1),('Sean', 'pass456', 2),('Amy', 'pass789', 3);

-- 設立 productCategory 預設值
insert into productCategory (name) values ('餐廚'), ('傢俱'), ('旅遊');

-- 設立 productType 預設值
insert into productType (name) values ('鍋鏟'), ('平底鍋'), ('主廚刀');

-- 設立 product 預設值
insert into product (productId, productName, price, barcode, brand, productCategory, productType, isLaunch, qty) 
values ('A101', '好用鍋鏟', 550, '12345678', 'AAA', 1, 1, 0, 50), ('B101', '好用平底鍋', 1200, '22345678', 'BBB', 1, 2, 0, 50);

-- 設立 ecommerce 預設值
insert into ecommerce (name, website) 
values ('Momo', 'https://www.momoshop.com.tw/main/Main.jsp'), 
('PChome', 'https://24h.pchome.com.tw/sign/ce?gclid=EAIaIQobChMI1NX3-N7UgwMVw80WBR0VnQ5PEAAYASAAEgLVPvD_BwE'), 
('蝦皮', 'https://shopee.tw/');

-- 設立 stcok 預設值
insert into stock (product_id, ec_id, qty, ec_qty) values ('A101', 1, 50, 10), ('B101', 1, 50, 8);

-- 設立 salesdata 預設值
insert into salesdata (ec_id, ec_orderNumber, ec_productCode, productId, productName, ec_productCategory, ec_productType, ec_warehouse, ec_salesQty, ec_salesPrice, ec_salesDate, ec_salesStatus) 
values (1, 'BD-2023123012345-01', '12345-01', 'A101', '好用鍋鏟', '刀具砧板配件', '鍋鏟', '轉單', 2, 550, 20231201, '銷售'),
(1, 'BD-2023123012345-02', '12345-02', 'B101', '好用平底鍋', '鍋具', '平底鍋', '寄倉', 1, 1000, 20231202, '銷售')
=======
-- 新增 User
select * from user;
insert into user (username, password, level) values ('John', 'pass123', 1),('Sean', 'pass456', 2),('Amy', 'pass789', 3);

-- 新增 product
>>>>>>> branch 'main' of https://github.com/LYTING12/Java2023.git
