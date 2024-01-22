package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Product;
import spring.mvc.analyze.entity.User;

public interface UserDao {

	List<User> findAllUsers();
	
	Optional<User> findUserById(Integer userId);
	
	Optional<User> findUserByUsername(String username);
	
	int addUser(User user);
	
	int updateUserLevelIdAndName(Integer userId, Integer levelId, String name);
	
	int removeProductById(Integer userId);
}
