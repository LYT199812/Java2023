package spring.mvc.analyze.dao;

import java.util.Optional;

import spring.mvc.analyze.entity.Level;

public interface LevelDao {

	Optional<Level> findLevelByUserId(Integer uesrId);
}
