package spring.mvc.analyze.entity;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ecommerce {

	private Integer id;
	private String name;
	private String website;
	
	@Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ecommerce ecommerce = (Ecommerce) obj;
        return Objects.equals(id, ecommerce.id);
    }
	
}
