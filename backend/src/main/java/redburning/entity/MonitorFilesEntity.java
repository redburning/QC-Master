package redburning.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import redburning.entity.converter.StringListConverter;

@Data
@Entity
@Table(name = "monitor_files")
public class MonitorFilesEntity {

	@Id
	private String id;
	
	@Convert(converter = StringListConverter.class)
	private List<String> files;
	
	public MonitorFilesEntity() {}
	
	public MonitorFilesEntity(String id, String file) {
		this.id = id;
		this.files = new ArrayList<>();
		this.files.add(file);
	}
	
}