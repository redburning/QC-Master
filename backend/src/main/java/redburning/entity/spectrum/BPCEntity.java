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
@Table(name = "spectrum_base_peak_current")
public class BPCEntity {

	@Id
	@GeneratedValue(generator = "BPCIdGenerator")
	@GenericGenerator(name = "BPCIdGenerator", strategy = "uuid")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	private String sample;
	
	@Column(name = "blob_rt")
	private byte[] blobRt;
	
	@Column(name = "blob_base_peak_intensity")
	private byte[] blobBasePeakIntensity;
	
	public BPCEntity() {}
	
	public BPCEntity(String taskId, String sample, byte[] blobRt, byte[] blobBasePeakIntensity) {
		this.taskId = taskId;
		this.sample = sample;
		this.blobRt = blobRt;
		this.blobBasePeakIntensity = blobBasePeakIntensity;
	}
}
