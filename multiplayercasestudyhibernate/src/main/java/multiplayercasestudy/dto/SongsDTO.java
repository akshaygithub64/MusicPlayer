package multiplayercasestudy.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SongsDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;
	private double duration;
	private String movie;
	private String singer;
	
	public String toString() {
		return " [id= " + id + ", name= " + name + ", duration= " + duration + ", movie= " + movie + ", singer= "
				+ singer + "]";
	}
}
