package spring.mvc.analyze.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.mvc.analyze.model.dao.AnalyzeDao;
import spring.mvc.analyze.model.entity.User;

@Controller
@RequestMapping("/")
public class AnalyzeController {
	
	@Autowired
	private AnalyzeDao dao;
	
	// 前台登入處理
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, 
						@RequestParam("password") String password, 
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
				return "redirect:/mvc/main"; //login ok，導前台首頁
			}else {
				session.invalidate(); // session 過期失效
				model.addAttribute("loginMessage", "密碼錯誤");
				return "group_buy/login";
			}
		}else {
			session.invalidate(); // session 過期失效
			model.addAttribute("loginMessage", "無此使用者");
			return "group_buy/login"; // 自己渲染給jsp，再呈現給前端
		}
	}
	
	
	
}
