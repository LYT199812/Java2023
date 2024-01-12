package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import spring.mvc.analyze.dao.UserDaoResposity;
import spring.mvc.analyze.entity.User;

public class UserDaoTest {

	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/WEB-INF/springmvc-servlet.xml");
		
		UserDaoResposity userDaoResposity = ctx.getBean("userDaoResposity", UserDaoResposity.class);
		
		User user1 = userDaoResposity.findUserById(101).get();
		System.out.println(new Gson().toJson(user1));

		User user2 = userDaoResposity.findUserById(102).get();
		System.out.println(new Gson().toJson(user2));
		
	}

}
