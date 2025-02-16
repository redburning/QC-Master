package redburning.vo;

import java.util.List;

import lombok.Data;
import redburning.entity.spectrum.TICEntity;
import redburning.util.BlobUtil;

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
	
}
