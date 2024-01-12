package spring.mvc.analyze.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
2. 使用者
level: 1(分析人員-匯入檔案查看銷售表), 2(管理人員-管理庫存+上架商品人員), 3(主管-2+3+可以更改權限設定)
+--------+----------+----------+-------+
| userId | username | password | level |
+--------+----------+----------+-------+
|  101   |   John   | pass123  |   1   |
|  102   |   Sean   | pass456  |   2   |
|  103   |   Amy    | pass789  |   3   |
+--------+----------+----------+-------+
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	Integer userId;
	
	String username;
	
	String password;
	
	Level level;
	
	List<Service> menu;

}
