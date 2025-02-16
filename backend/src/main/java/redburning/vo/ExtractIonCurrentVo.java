package redburning.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import redburning.entity.spectrum.EICEntity;
import redburning.util.BlobUtil;

@Data
public class ExtractIonCurrentVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String landmark;
	
	private List<Double> rt;
	
	private List<Double> intensity;
	
	public ExtractIonCurrentVo(EICEntity entity) {
		this.landmark = entity.getLandmark();
		this.rt = BlobUtil.convertBytesToList(entity.getRt());
		this.intensity = BlobUtil.convertBytesToList(entity.getIntensity());
	}
}
