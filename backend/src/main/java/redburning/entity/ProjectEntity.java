package redburning.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "project")
public class ProjectEntity {

	@Id
	@GeneratedValue(generator = "projectIdGenerator")
	@GenericGenerator(name = "projectIdGenerator", strategy = "uuid")
	private String id;
	
	private String name;
	
	@Column(name = "monitor_dir")
	private String monitorDir;
	
	private String status;
	
	@Column(name = "create_time")
	private String createTime;
	
	public ProjectEntity() {}
	
	public ProjectEntity(String name, String monitor, String status, long createTime) {
		this(monitor, status, createTime);
		this.name = name;
	}
	
	public ProjectEntity(String monitorDir, String status, long createTime) {
		this.monitorDir = monitorDir;
		this.status = status;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.createTime = sdf.format(new Date(createTime));
	}
	
}