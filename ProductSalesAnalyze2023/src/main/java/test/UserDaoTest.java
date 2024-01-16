package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.UserDaoResposity;
import spring.mvc.analyze.entity.Level;
import spring.mvc.analyze.entity.User;

public class UserDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		UserDaoResposity userDaoResposity = ctx.getBean("userDaoResposity", UserDaoResposity.class);
		
//		User user1 = userDaoResposity.findUserById(103).get();
//		System.out.println(new Gson().toJson(user1));

//		User user2 = userDaoResposity.findUserByUsername("Sean").get();
//		System.out.println(new Gson().toJson(user2));
		
		User user99 = new User();
		user99.setUsername("Antia");
		user99.setPassword("123");
		user99.setLevel(Level.builder().levelId(1).build());
		int rowcount = userDaoResposity.save(user99);
		System.out.println(rowcount);
		System.out.println(user99.getUserId());
	}

}
