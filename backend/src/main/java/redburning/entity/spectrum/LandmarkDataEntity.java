package redburning.entity.spectrum;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import redburning.entity.converter.StringListConverter;


@Data
@Entity
@Table(name = "spectrum_landmark_data")
public class LandmarkDataEntity {

	@Id
	@GeneratedValue(generator = "landmarkDataIdGenerator")
	@GenericGenerator(name = "landmarkDataIdGenerator", strategy = "uuid")
	private String id;
	
	@Column(name = "task_id")
	private String taskId;
	
	@Column(name = "landmark_name")
	private String landmarkName;
	
	@Column(name = "sample_list")
	@Convert(converter = StringListConverter.class)
	private List<String> sampleList;
	
	@Column(name = "blob_rt")
	private byte[] rt;
	
	@Column(name = "blob_intensity")
	private byte[] intensity;
	
	@Column(name = "blob_rel_mz_error")
	private byte[] relMzError;
	
	@Column(name = "blob_abs_mz_error")
	private byte[] absMzError;
	
	@Column(name = "blob_peak_area")
	private byte[] peakArea;
	
	public LandmarkDataEntity() {}
	
	public LandmarkDataEntity(String taskId, String landmarkName, List<String> sampleList, byte[] rt,
			byte[] intensity, byte[] relMzError, byte[] absMzError, byte[] peakArea) {
		this.taskId = taskId;
		this.landmarkName = landmarkName;
		this.sampleList = sampleList;
		this.rt = rt;
		this.intensity = intensity;
		this.relMzError = relMzError;
		this.absMzError = absMzError;
		this.peakArea = peakArea;
	}
}