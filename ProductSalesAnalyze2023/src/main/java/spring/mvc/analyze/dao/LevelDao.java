package spring.mvc.analyze.dao;

import java.util.List;
import java.util.Optional;

import spring.mvc.analyze.entity.Level;

public interface LevelDao {

	List<Level> findAllLevels();
	
	Optional<Level> findLevelById(Integer levelId);
}
