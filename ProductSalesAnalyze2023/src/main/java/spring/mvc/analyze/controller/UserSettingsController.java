package spring.mvc.analyze.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.mvc.analyze.dao.UserDao;
import spring.mvc.analyze.dao.UserDaoResposity;
import spring.mvc.analyze.dto.UserDto;
import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.mvc.analyze.dao.LevelDao;


@Controller
@RequestMapping("/user")
public class UserSettingsController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	LevelDao levelDao;
	
	@GetMapping("/mantain")
	public String permissionSettings(Model model) {
		List<User> userList = userDao.findAllUsers();
		model.addAttribute("userList", userList);
		return "analyze/permissionSettings";
	}
	
	@GetMapping("/getAllUsers")
	@ResponseBody
	public List<User> getAllUser(Model model) {
		List<User> userList = userDao.findAllUsers();
		model.addAttribute("userList", userList);
		System.out.println(userList);
		return userList;
	}
	
	// 新增員工
	@PostMapping("/mantain")
	@ResponseBody
	public List<User> addUser(@RequestBody User newUser) {
	    // 根據UserDto中的數據進行相應的業務邏輯處理
	    // 例如，新增使用者到數據庫
		//Level level = levelDao.findLevelById(newUser.getLevelId()).orElse(null);
		//newUser.setLevel(level);
	    userDao.addUser(newUser);

	    // 處理完成後，返回更新後的使用者列表
	    return userDao.findAllUsers();
	}
	
	@GetMapping("/mantain/{userId}")
	@ResponseBody
	public UserDto editUser(@PathVariable("userId") Integer userId, Model model) {

	    // 從DB中獲取原始使用者數據
	    Optional<User> userOpt = userDao.findUserById(userId);
	    User user = userOpt.get();
	    
	    UserDto userDto = convertToDto(user);
	    //model.addAttribute("user", userDto);
	    
	    return userDto;
	    
	}

	// 使用PUT方法進行更新
	@PutMapping("/mantain/{userId}")
	@ResponseBody
	public List<User> updateUser(@PathVariable("userId") Integer userId, @RequestBody User uptUser, Model model) {
		// 根據UserDto中的數據進行相應的業務邏輯處理
	    // 例如，更新使用者信息到數據庫
		userDao.updateUserLevelIdAndName(userId, uptUser.getLevelId(), uptUser.getUsername());
		//System.out.println(userDao.findAllUsers());
		// 處理完成後，重定向到使用者列表頁面或其他頁面
	    return userDao.findAllUsers();
	}

	// 刪除員工
	@DeleteMapping("/mantain/{userId}")
	public String deleteUser(@PathVariable("userId") Integer userId) {
		int rowcount = userDao.removeProductById(userId);
		//System.out.println("delete User rowcount =" + rowcount);
		 
		return "redirect:/mvc/user/mantain/"; 
	}
	
	
	private UserDto convertToDto(User user) {
	    UserDto userDto = new UserDto();
	    Level level = new Level();
	    
	    userDto.setUserId(user.getUserId());
	    userDto.setUsername(user.getUsername());
	    userDto.setName(user.getUsername());
	    userDto.setPassword(user.getPassword());
	    userDto.setLevelId(user.getLevelId());
	    userDto.setLevel(user.getLevel());
	    userDto.setLevelName(user.getLevel().getLevelName());
	    // 其他屬性的轉換
	    return userDto;
	}
	
}
