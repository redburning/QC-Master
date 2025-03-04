package redburning.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "landmark")
public class LandmarkEntity {

	@Id
	@GeneratedValue(generator = "definitionGenerator")
	@GenericGenerator(name = "definitionGenerator", strategy = "uuid")
	private String id;
	
	private String name;
	private String rt;
	private String mz;
	private String polarity;
	
}