package redburning.vo;

import lombok.Data;
import redburning.entity.ProjectEntity;

@Data
public class ProjectVo {

	private String id;
	
	private String name;
	
	private String monitorDir;
	
	private String status;
	
	private String createTime;
	
	private int sampleNumber;
	
	public ProjectVo() {}
	
	public ProjectVo(ProjectEntity projectEntity) {
		this.id = projectEntity.getId();
		this.name = projectEntity.getName();
		this.monitorDir = projectEntity.getMonitorDir();
		this.status = projectEntity.getStatus();
		this.createTime = projectEntity.getCreateTime();
	}
	
}
