package redburning.entity.spectrum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "spectrum_extract_ion_current")
public class EICEntity {

	@Id
	@GeneratedValue(generator = "EICIdGenerator")
	@GenericGenerator(name = "EICIdGenerator", strategy = "uuid")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	private String landmark;
	
	@Column(name = "blob_rt")
	private byte[] rt;
	
	@Column(name = "blob_intensity")
	private byte[] intensity;
	
	public EICEntity() {}
	
	public EICEntity(String taskId, String landmark, byte[] rt, byte[] intensity) {
		this.taskId = taskId;
		this.landmark = landmark;
		this.rt = rt;
		this.intensity = intensity;
	}
	
}
