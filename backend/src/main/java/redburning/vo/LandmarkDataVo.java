package redburning.vo;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;
import redburning.entity.spectrum.LandmarkDataEntity;
import redburning.util.BlobUtil;

@Data
public class LandmarkDataVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String landmark;
	
	private List<String> sample;
	
	private List<Double> rt;
	
	private List<Double> intensity;
	
	private List<Double> relMzError;
	
	private List<Double> absMzError;
	
	private List<Double> peakArea;
	
	private List<Integer> index;
	
	public LandmarkDataVo(LandmarkDataEntity entity) {
		this.landmark = entity.getLandmarkName();
		this.sample = entity.getSampleList();
		this.rt = BlobUtil.convertBytesToList(entity.getRt());
		this.intensity = BlobUtil.convertBytesToList(entity.getIntensity());
		this.relMzError = BlobUtil.convertBytesToList(entity.getRelMzError());
		this.absMzError = BlobUtil.convertBytesToList(entity.getAbsMzError());
		this.peakArea = BlobUtil.convertBytesToList(entity.getPeakArea());
		this.index = IntStream.rangeClosed(1, this.sample.size())
				.boxed().collect(Collectors.toList());
	}
}
