package spring.mvc.analyze.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.mvc.analyze.model.dao.AnalyzeDao;
import spring.mvc.analyze.model.entity.User;


@Controller
@RequestMapping("/analyze")
public class AnalyzeController {
	
	@Autowired
	private AnalyzeDao dao;
	
	
	//登入首頁
	/* 這裡的return路徑設定會與springmvc-servlet.xml的設定有關；
	 * <!-- 配置 view 渲染器位置 -->
		<bean id="internalResourceViewResolver"
			  class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		  <property name="prefix" value="/WEB-INF/views/" />
		  <property name="suffix" value=".jsp" />
		</bean>
	 */
	@GetMapping(value = {"/login", "/", "/login/"})
	public String loginPage(HttpSession session) {
		return "analyze/login"; // 完整路徑：/WEB-INF/views/analyze/login.jsp
	}
	/*
	@PostMapping("/login")
	public void login(HttpServletRequest request, HttpSession session, Model model) {
	    // 通过 HttpServletRequest 对象获取参数
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    // 打印请求参数
	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);

	    // 其他登录逻辑...
	}
	*/
	
	// 前台登入處理
	@PostMapping("/login")
	public String login(@RequestParam(value ="username") String username, 
						@RequestParam(value = "password") String password, 
						HttpSession session, Model model) {
		// 比對驗證碼
//			if (!code.equals(session.getAttribute("code")+"")) {//session.getAttribute("code")原本是物件，加 +"" java會自動拆裝箱轉成字串的來比對
//				session.invalidate(); // session 過期失效
//				model.addAttribute("loginMessage", "驗證碼錯誤");
//				return "group_buy/login";
//			}
		
		// 根據 username 查找 user 物件
		Optional<User> userOpt = dao.findUserByUsername(username);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			// 比對 password
			if (user.getPassword().equals(password)) {
				session.setAttribute("user", user); // 將 user 物件放入到 session 變數中
				// 這邊重導的路徑是看下面@RequestMapping("/frontend/main")設定，因為是client發起(外部路徑)，會重新再經過controller，再重新發起session；
				// 資訊安全的一部分，讓client不知道我們實際內部的檔案路徑
				return "redirect:/mvc/analyze/main"; //login ok，導前台首頁
			}else {
				session.invalidate(); // session 過期失效
				model.addAttribute("loginMessage", "密碼錯誤");
				return "analyze/login";
			}
		}else {
			session.invalidate(); // session 過期失效
			model.addAttribute("loginMessage", "無此使用者");
			return "analyze/login"; // 自己渲染給jsp，再呈現給前端
		}
	}
	
	//登出
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/mvc/analyze/login"; //為了不要重新帶東西渲染給jsp，直接讓瀏覽器重新發送請求
	}
 
	//---------------------------------------------------------------------------------------------
		
	// 首頁
	@GetMapping("/main")
	public String main(Model model) {
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/main"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
	// ecWebsite momo
	@GetMapping("/ecWebsite/momo")
	public String ecWebsiteMomo(Model model) {
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/ecWebsite/momo"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
		
	// ecWebsite momo2
	@GetMapping("/ecWebsite/momo2")
	public String ecWebsiteMomo2(Model model) {
		/*
		// 過濾出只有上架的商品
		List<Product> products = dao.findAllProducts(true);
		model.addAttribute("products", products);
		*/
		return "analyze/ecWebsite/momo2"; // 這邊的路徑是實際上檔案位於的位置(內部路徑)
	}
	
}