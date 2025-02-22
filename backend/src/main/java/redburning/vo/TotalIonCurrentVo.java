package redburning.vo;

import java.util.List;

import lombok.Data;
import redburning.entity.spectrum.TICEntity;
import redburning.util.BlobUtil;
import redburning.util.DataSampling;

@Data
public class TotalIonCurrentVo {

	private String sample;
	
	private List<Double> rt;
	
	private List<Double> tic;
	
	public TotalIonCurrentVo(TICEntity entity) {
		this.sample = entity.getSample();
		this.rt = BlobUtil.convertBytesToList(entity.getBlobRt());
		this.tic = BlobUtil.convertBytesToList(entity.getBlobTotalIonCurrent());
	}
	
	public TotalIonCurrentVo(TICEntity entity, double samplingPercent) {
		this.sample = entity.getSample();
		this.rt = DataSampling.sampling(
				BlobUtil.convertBytesToList(entity.getBlobRt()), 
				samplingPercent);
		this.tic = DataSampling.sampling(
				BlobUtil.convertBytesToList(entity.getBlobTotalIonCurrent()), 
				samplingPercent);
	}
	
}
