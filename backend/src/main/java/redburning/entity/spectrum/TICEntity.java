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
@Table(name = "spectrum_total_ion_current")
public class TICEntity {

	@Id
	@GeneratedValue(generator = "TICIdGenerator")
	@GenericGenerator(name = "TICIdGenerator", strategy = "uuid")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	private String sample;
	
	@Column(name = "blob_rt")
	private byte[] blobRt;
	
	@Column(name = "blob_total_ion_current")
	private byte[] blobTotalIonCurrent;
	
	public TICEntity() {}
	
	public TICEntity(String id, String sample, byte[] blobRt, byte[] blobTotalIonCurrent) {
		this.taskId = id;
		this.sample = sample;
		this.blobRt = blobRt;
		this.blobTotalIonCurrent = blobTotalIonCurrent;
	}
	
}
