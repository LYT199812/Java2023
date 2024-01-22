package spring.mvc.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.mvc.analyze.entity.Level;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private Integer userId;
	private String username;
	private String name;
    private String password;
    private int levelId;
    
    private Level level;
    private String levelName;
	
}
