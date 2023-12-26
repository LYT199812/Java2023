package spring.mvc.analyze.model.entity;

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
public class User {
	
	private Integer userId; // 使用者代號
	private String username; // 使用者名稱
	private String password; // 使用者密碼
	private Integer level; // 使用者權限
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", level=" + level
				+ "]";
	}
	
}
