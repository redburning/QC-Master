package redburning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(generator = "definitionGenerator")
	@GenericGenerator(name = "definitionGenerator", strategy = "uuid")
	private String id;
	
	@Column(unique = true)
	private String username;
	private String password;
	
}
