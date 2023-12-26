drop table if exists user;
-- 創建user資料表
create table if not exists user(
	userId int auto_increment primary key,
    username varchar(50) unique not null,
    password varchar(20) not null,
    level int not null
); 

-- 設立預設值
insert into user (userId, username, password, level) values
(101, 'John', 'pass123', 1),
(102, 'Sean', 'pass456', 2),
(103, 'Amy', 'pass789', 3)