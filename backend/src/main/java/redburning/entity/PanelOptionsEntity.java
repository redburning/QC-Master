package redburning.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "panel_options")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PanelOptionsEntity {
	
	@Id
	private String id;
	
	private String options;
	
}