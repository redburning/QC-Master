package redburning.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import redburning.entity.spectrum.BPCEntity;
import redburning.util.BlobUtil;
import redburning.util.DataSampling;

@Data
public class BasePeakCurrentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sample;
	
	private List<Double> rt;
	
	private List<Double> intensity;
	
	public BasePeakCurrentVo(BPCEntity entity) {
		this.sample = entity.getSample();
		this.rt = BlobUtil.convertBytesToList(entity.getBlobRt());
		this.intensity = BlobUtil.convertBytesToList(entity.getBlobBasePeakIntensity());
	}
	
	public BasePeakCurrentVo(BPCEntity entity, double samplingPercent) {
		this.sample = entity.getSample();
		this.rt = DataSampling.sampling(
				BlobUtil.convertBytesToList(entity.getBlobRt()), 
				samplingPercent);
		this.intensity = DataSampling.sampling(
				BlobUtil.convertBytesToList(entity.getBlobBasePeakIntensity()), 
				samplingPercent);
	}
	
}
