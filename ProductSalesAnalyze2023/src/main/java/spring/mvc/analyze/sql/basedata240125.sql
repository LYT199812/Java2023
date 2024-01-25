-- 1. 設立 level 預設值
insert into level (levelName) values ('分析人員'),('管理人員'),('主管');

-- 2. 設立 service 預設值
insert into service (serviceName, serviceUrl) 
values ('首頁', '/mvc/analyze/main'),
('電商', '/mvc/analyze/ecWebsite/eccommerce'),
('新增商品', '/mvc/analyze/product/addProduct'),
('管理商品', '/mvc/analyze/product/maintainProduct'),
('編輯商品', '/mvc/analyze/product/editProduct2'),
('權限管理', '/mvc/analyze/permissionSettings');

-- 3. 設立 levelRefService 預設值
insert into levelRefService (levelId, serviceId) values (1, 1),(1, 2),(2, 1),(2, 3),(2, 4),(2, 5),(3, 1),(3, 2),(3, 3),(3, 4),(3, 5),(3, 6);

-- 4. 設立 User 預設值
insert into user (username, password, levelId) values ('John', 'pass123', 1),('Sean', 'pass456', 2),('Amy', 'pass789', 3);

-- -------------------------------------------------------------------------------------------------------------------------------
-- 5. 設立 productType 預設值
insert into productType (name) values ('餐廚'), ('烘焙'), ('旅遊');

-- 6. 設立 productSubType 預設值
insert into productSubType (name) values ('平底鍋'),('鍋鏟'),('主廚刀'),('餅乾模'),('麵粉篩')
,('烘焙紙'),('頸枕'),('收納袋'),('背包');

-- 7. 設立 productBrand 預設值
insert into productBrand (name) values ('AAA'), ('BBB'), ('CCC'), ('DDD'), ('EEE');

-- 8. 設立 product 預設值
insert into product (productId, productName, productPrice, productBarcode, productBrandId, productTypeId, productSubTypeId, productImg, productDesc, productQty, isLaunch) 
values
('A101','好用平底鍋',599,'21437294',1,1,1,'A101.jpg','好用的產品，增添生活美好',100,1),
('A102','好用鍋鏟',149,'26946769',1,1,2,'A102.jpg','好用的產品，增添生活美好',90,1),
('A103','好用主廚刀',599,'28523164',1,1,3,'A103.jpg','好用的產品，增添生活美好',80,1),
('A204','好用餅乾模',129,'11213124',1,2,4,'A204.jpg','好用的產品，增添生活美好',70,1),
('B101','不沾平底鍋',799,'13466829',2,1,1,'B101.jpg','好用的產品，增添生活美好',60,1),
('B102','不沾鍋鏟',149,'17866941',2,1,2,'B102.jpg','好用的產品，增添生活美好',50,1),
('B204','不沾餅乾模',299,'21159381',2,2,4,'B204.jpg','好用的產品，增添生活美好',100,1),
('B206','不沾烘焙紙',199,'24770882',2,2,6,'B206.jpg','好用的產品，增添生活美好',70,1),
('C101','歐森平底鍋',699,'51918166',3,1,1,'C101.jpg','好用的產品，增添生活美好',20,1),
('C102','歐森鍋鏟',149,'56844016',3,1,2,'C102.jpg','好用的產品，增添生活美好',50,1),
('C307','歐森頸枕',699,'59121276',3,3,7,'C307.jpg','好用的產品，增添生活美好',50,1),
('C308','歐森收納袋',250,'60379149',3,3,8,'C308.jpg','好用的產品，增添生活美好',30,1),
('C309','歐森後背包',999,'65008787',3,3,9,'C309.jpg','好用的產品，增添生活美好',20,1);


-- 9. 設立 ecommerce 預設值
insert into ecommerce (name, website) 
values ('Momo', 'https://www.momoshop.com.tw/main/Main.jsp'), 
('PChome', 'https://24h.pchome.com.tw/sign/ce?gclid=EAIaIQobChMI1NX3-N7UgwMVw80WBR0VnQ5PEAAYASAAEgLVPvD_BwE'), 
('蝦皮', 'https://shopee.tw/');

-- 10. 設立 stcok 預設值
insert into stock (productId, ecId, ecProductQty) values ('A101',1,20),
('A101',2,30),('A101',3,10),('A102',1,40),('A102',2,20),('A102',3,20),
('A103',1,10),('A103',2,15),('A103',3,20),('A204',1,20),('A204',2,30),
('A204',3,15),('B101',1,10),('B101',2,10),('B101',3,10),('B102',1,20),
('B102',2,15),('B102',3,10),('B204',1,20),('B204',2,20),('B204',3,30),
('B206',1,20),('B206',2,15),('B206',3,20),('C101',1,10),('C101',2,5),
('C101',3,3),('C102',1,20),('C102',2,10),('C102',3,10),('C307',1,20),
('C307',2,10),('C307',3,10),('C308',1,15),('C308',2,5),('C308',3,5),
('C309',1,10),('C309',2,5),('C309',3,5);

-- 11.設立 salesdata 預設值
insert into salesdata (ecId, productId, ecOrderNumber, ecProductCode, ecProductType, ecProductSubType, ecWarehouse, ecSalesQty, ecSalesPrice, ecSalesDate, ecSalesStatus) 
values 
(1,'A101','MO-20230101-01','0LIFH','餐廚','平底鍋','寄倉',5,2995,20230101,'銷售'),
(1,'A102','MO-20230205-01','1XEY0','餐廚','鍋鏟','寄倉',2,298,20230205,'銷售'),
(1,'A204','MO-20230415-01','5JQII','烘焙','餅乾模','寄倉',4,516,20230415,'銷售'),
(1,'B101','MO-20230520-01','7M98F','餐廚','平底鍋','寄倉',7,5593,20230520,'銷售'),
(1,'B206','MO-20230810-01','D0SLX','烘焙','烘焙紙','寄倉',6,1194,20230810,'銷售'),
(2,'A101','PC-20230101-01','0WUMH','餐廚','平底鍋','寄倉',8,4792,20230101,'銷售'),
(2,'A102','PC-20230610-01','2TQ5A','餐廚','鍋鏟','寄倉',9,1341,20230610,'銷售'),
(2,'A103','PC-20231005-01','4KL36','餐廚','主廚刀','寄倉',5,2995,20231005,'銷售'),
(2,'A103','PC-20231110-01','4KL36','餐廚','主廚刀','寄倉',11,6589,20231110,'銷售'),
(2,'A204','PC-20240120-01','60OCV','烘焙','餅乾模','寄倉',2,258,20240120,'銷售'),
(2,'B102','PC-20230730-01','A5JO2','餐廚','鍋鏟','寄倉',10,1490,20230730,'銷售'),
(2,'B204','PC-20230915-01','BU9BQ','烘焙','餅乾模','寄倉',2,598,20230915,'銷售'),
(2,'B206','PC-20230415-01','DJODS','烘焙','烘焙紙','寄倉',6,1194,20230415,'銷售'),
(2,'C102','PC-20230310-01','TNILK','餐廚','鍋鏟','寄倉',2,298,20230310,'銷售'),
(2,'C309','PC-20230520-01','Z3ND4','旅遊','背包','寄倉',7,6993,20230520,'銷售'),
(2,'C309','PC-20240115-01','Z3ND4','旅遊','背包','寄倉',5,4995,20240115,'銷售'),
(3,'A102','SP-20240120-01','3L3US','餐廚','鍋鏟','寄倉',5,745,20240120,'銷售'),
(3,'A103','SP-20230310-01','53W1Z','餐廚','主廚刀','寄倉',20,11980,20230310,'銷售'),
(3,'A204','SP-20231220-01','6TLEV','烘焙','餅乾模','寄倉',3,387,20231220,'銷售'),
(3,'B102','SP-20230810-01','AJ8K4','餐廚','鍋鏟','寄倉',1,149,20230810,'銷售'),
(3,'B204','SP-20231005-01','C26ZT','烘焙','餅乾模','寄倉',5,1495,20231005,'銷售'),
(3,'B206','SP-20230915-01','DTQ6B','烘焙','烘焙紙','寄倉',1,199,20230915,'銷售'),
(3,'C102','SP-20230520-01','UJNKQ','餐廚','鍋鏟','寄倉',5,745,20230520,'銷售'),
(3,'C307','SP-20230415-01','W3K5Q','旅遊','頸枕','寄倉',8,5592,20230415,'銷售'),
(3,'C308','SP-20230610-01','XXPQJ','旅遊','收納袋','寄倉',3,750,20230610,'銷售');

-- 依照平台，取得商品銷售資訊原始數據(待定)
-- select d.trxId,p.productId,p.productName,p.productBrand,pt.name,pst.name,p.productBarcode, s.ecProductQty, s.productQty, d.ecSalesQty,d.ecSalesPrice
-- from product p, salesdata d, ecommerce e , stock s, producttype pt, productsubtype pst
-- where p.productId = d.productId and d.ecId = e.id
-- and s.ecId = e.id and s.productId = p.productId
-- and pt.id = p.productTypeId
-- and pst.id = productSubTypeId
-- and e.name = 'Momo';