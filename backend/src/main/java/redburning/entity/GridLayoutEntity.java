package redburning.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "grid_layout")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GridLayoutEntity {

	@Id
	@GeneratedValue(generator = "definitionGenerator")
	@GenericGenerator(name = "definitionGenerator", strategy = "uuid")
	private String id;
	
	private String name;
	private String layout;
	
}