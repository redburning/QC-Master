package redburning.engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import redburning.dto.Landmark;
import redburning.entity.spectrum.BPCEntity;
import redburning.entity.spectrum.EICEntity;
import redburning.entity.spectrum.LandmarkDataEntity;
import redburning.entity.spectrum.TICEntity;
import redburning.service.spectrum.BPCService;
import redburning.service.spectrum.EICService;
import redburning.service.spectrum.LandmarkDataService;
import redburning.service.spectrum.TICService;
import redburning.util.BlobUtil;
import redburning.util.DataIntegration;
import redburning.util.DataSampling;
import redburning.util.DataUtil;
import redburning.vo.BasePeakCurrentVo;
import redburning.vo.ExtractIonCurrentVo;
import redburning.vo.LandmarkDataVo;
import redburning.vo.TotalIonCurrentVo;
import umich.ms.datatypes.scan.IScan;
import umich.ms.fileio.exceptions.FileParsingException;
import umich.ms.fileio.filetypes.mzxml.MZXMLFile;
import umich.ms.fileio.filetypes.mzxml.MZXMLIndex;

@Component
public class MzXmlDataParser {

	@Autowired
	private BPCService bpcService;
	
	@Autowired
	private TICService ticService;
	
	@Autowired
	private EICService eicService;
	
	@Autowired
	private LandmarkDataService landmarkDataService;
	
	private String taskId;
	
	private List<Landmark> landmarks;
	
	private SseEmitter emitter;
	
	private List<String> sampleList = new ArrayList<>();;
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public void setLandmarks(List<Landmark> landmarks) {
		this.landmarks = landmarks;
	}
	
	public void setEmitter(SseEmitter emitter) {
		this.emitter = emitter;
	}
	
	public void setSampleList(List<String> sampleList) {
		this.sampleList = sampleList;
	}
	
	public void parseSampleName(String filePath) {
		String sampleName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length() - 6);
		sampleList.add(sampleName);
		try {
			emitter.send(SseEmitter.event().name("update-sample").data(sampleName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseBasePeakCurrent(String filePath) throws FileParsingException {
		MZXMLFile source = new MZXMLFile(filePath);
		MZXMLIndex index = source.fetchIndex();
		List<Double> rtTrace = new ArrayList<>();
		List<Double> basePeakIntensityTrace = new ArrayList<>();
		for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
			IScan scan = source.parseScan(scanNumRaw, true);
			rtTrace.add(scan.getRt());
			basePeakIntensityTrace.add(scan.getBasePeakIntensity());
		}
		source.close();
		
		DataSampling.lttbSampling(rtTrace, basePeakIntensityTrace, 800);
		
		String sampleName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length() - 6);
		BPCEntity bpcEntity = new BPCEntity(taskId, 
				sampleName, 
				BlobUtil.convertListToBytes(rtTrace), 
				BlobUtil.convertListToBytes(basePeakIntensityTrace));
		bpcService.save(bpcEntity);
		try {
			emitter.send(SseEmitter.event().name("update-bpc").data(new BasePeakCurrentVo(bpcEntity)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseTotalIonCurrent(String filePath) throws FileParsingException {
		MZXMLFile source = new MZXMLFile(filePath);
		MZXMLIndex index = source.fetchIndex();
		List<Double> rtTrace = new ArrayList<>();
		List<Double> totalIonCurrentTrace = new ArrayList<>();
		for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
			IScan scan = source.parseScan(scanNumRaw, true);
			rtTrace.add(scan.getRt());
			totalIonCurrentTrace.add(scan.getTic());
		}
		source.close();
		
		DataSampling.lttbSampling(rtTrace, totalIonCurrentTrace, 800);
		
		String sampleName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length() - 6);		
		TICEntity ticEntity = new TICEntity(taskId,
				sampleName,
				BlobUtil.convertListToBytes(rtTrace),
				BlobUtil.convertListToBytes(totalIonCurrentTrace));
		ticService.save(ticEntity);
		try {
			emitter.send(SseEmitter.event().name("update-tic").data(new TotalIonCurrentVo(ticEntity)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseExtractIonCurrent(String filePath) throws FileParsingException {
		MZXMLFile source = new MZXMLFile(filePath);
		MZXMLIndex index = source.fetchIndex();
		
		List<ExtractIonCurrentVo> extractIonCurrentVoList = new ArrayList<>();
		for (int l = 0; l < landmarks.size(); l++) {
			String landmarkName = landmarks.get(l).getName();
			double rtMin = landmarks.get(l).getRtMin();
			double rtMax = landmarks.get(l).getRtMax();
			double mzMin = landmarks.get(l).getMzMin();
			double mzMax = landmarks.get(l).getMzMax();
			// extract ion current
			List<Double> extractLandmarkRt = new ArrayList<>();
			List<Double> extractLandmarkIntensity = new ArrayList<>();
			
			for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
				IScan scan = source.parseScan(scanNumRaw, true);
				// rt单位为分钟
				double rt = scan.getRt();
				
				for (int s = 0; s < scan.getSpectrum().getMZs().length; s++) {
					double mz = scan.getSpectrum().getMZs()[s];
					double intensity = scan.getSpectrum().getIntensities()[s];
					// 找到landmark对应的所有rt+intensity
					if (rt >= rtMin && rt <= rtMax && mz >= mzMin && mz <= mzMax) {
						extractLandmarkRt.add(rt);
						extractLandmarkIntensity.add(intensity);
					}
				}
			}
			// 根据保留时间排序
			DataUtil.sortListAccordingtoWeight(extractLandmarkIntensity, extractLandmarkRt);
			
			EICEntity eicEntity = eicService.getByTaskIdAndLandmark(taskId, landmarkName);
			if (eicEntity == null) {
				eicEntity = new EICEntity(taskId, landmarkName, 
						BlobUtil.convertListToBytes(extractLandmarkRt),
						BlobUtil.convertListToBytes(extractLandmarkIntensity));
			} else {
				List<Double> rtList = BlobUtil.convertBytesToList(eicEntity.getRt());
				rtList.add(null);			// 补充null以使该landmark的多个sample trace断开
				rtList.addAll(extractLandmarkRt);
				
				List<Double> intensityList = BlobUtil.convertBytesToList(eicEntity.getIntensity());
				intensityList.add(null);	// 补充null以使该landmark的多个sample trace断开
				intensityList.addAll(extractLandmarkIntensity);
				
				eicEntity.setRt(BlobUtil.convertListToBytes(rtList));
				eicEntity.setIntensity(BlobUtil.convertListToBytes(intensityList));
			}
			eicService.save(eicEntity);
			
			extractIonCurrentVoList.add(new ExtractIonCurrentVo(eicEntity));
		}
		source.close();
		
		try {
			emitter.send(SseEmitter.event().name("update-eic").data(extractIonCurrentVoList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractLandmarks(String filePath) throws FileParsingException {
		MZXMLFile source = new MZXMLFile(filePath);
		MZXMLIndex index = source.fetchIndex();
		
		List<LandmarkDataVo> landmarkDataVoList = new ArrayList<>();
		for (int l = 0; l < landmarks.size(); l++) {
			double rtMin = landmarks.get(l).getRtMin();
			double rtMax = landmarks.get(l).getRtMax();
			double mzVal = landmarks.get(l).getMzVal();
			double mzMin = landmarks.get(l).getMzMin();
			double mzMax = landmarks.get(l).getMzMax();
			String landmarkName = landmarks.get(l).getName();
			
			double eligibleRt = 0.0;
			double eligibleIntensity = 0.0;
			double eligibleRelMzError = 0.0;
			double eligibleAbsMzError = 0.0;
			
			List<Double> extractLandmarkRt = new ArrayList<>();
			List<Double> extractLandmarkIntensity = new ArrayList<>();
			
			boolean findLandmark = false;
			for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
				IScan scan = source.parseScan(scanNumRaw, true);
				// rt单位为分钟
				double rt = scan.getRt();
				for (int s = 0; s < scan.getSpectrum().getMZs().length; s++) {
					double mz = scan.getSpectrum().getMZs()[s];
					double intensity = scan.getSpectrum().getIntensities()[s];
					if (rt >= rtMin && rt <= rtMax && mz >= mzMin && mz <= mzMax) {
						// 如果是第一次找到符合条件的，直接保存
						if (!findLandmark) {
							eligibleRt = rt;
							eligibleIntensity = intensity;
							eligibleRelMzError = (mz - mzVal) * 1000;
							eligibleAbsMzError = Math.abs(mz - mzVal) / mzVal * 1e6;
						}
						// 如果已经找到符合条件的，则与之比较intensity，保留强度更大的
						else {
							if (Double.compare(intensity, eligibleIntensity) > 0) {
								eligibleRt = rt;
								eligibleIntensity = intensity;
								eligibleRelMzError = (mz - mzVal) * 1000;
								eligibleAbsMzError = Math.abs(mz - mzVal) / mzVal * 1e6;
							}
						}
						findLandmark = true;
						
						// 保留所有符合条件的数据，计算PeakArea积分
						if (rt >= rtMin && rt <= rtMax && mz >= mzMin && mz <= mzMax) {
							extractLandmarkRt.add(rt);
							extractLandmarkIntensity.add(intensity);
						}
					}
				}
			}
			
			double peakArea = DataIntegration.doIntegrate(extractLandmarkRt, extractLandmarkIntensity);
			
			LandmarkDataEntity landmarkDataEntity = updateLandmarkData(landmarkName, 
					eligibleRt, 
					eligibleIntensity, 
					eligibleRelMzError, 
					eligibleAbsMzError,
					peakArea);
			
			landmarkDataVoList.add(new LandmarkDataVo(landmarkDataEntity));
		}
		source.close();
		
		try {
			emitter.send(SseEmitter.event().name("update-landmark-data").data(landmarkDataVoList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private LandmarkDataEntity updateLandmarkData(String landmarkName, double rt, double intensity, double relMzError,
			double absMzError, double peakArea) {
		LandmarkDataEntity landmarkDataEntity = landmarkDataService.getByTaskIdAndLandmarkName(taskId, landmarkName);
		if (landmarkDataEntity == null) {
			List<Double> rtList = new ArrayList<>();
			rtList.add(rt);
			List<Double> intensityList = new ArrayList<>();
			intensityList.add(intensity);
			List<Double> relMzErrorList = new ArrayList<>();
			relMzErrorList.add(relMzError);
			List<Double> absMzErrorList = new ArrayList<>();
			absMzErrorList.add(absMzError);
			List<Double> peakAreaList = new ArrayList<>();
			peakAreaList.add(peakArea);

			landmarkDataEntity = new LandmarkDataEntity(taskId, landmarkName, sampleList,
					BlobUtil.convertListToBytes(rtList), 
					BlobUtil.convertListToBytes(intensityList),
					BlobUtil.convertListToBytes(relMzErrorList), 
					BlobUtil.convertListToBytes(absMzErrorList),
					BlobUtil.convertListToBytes(peakAreaList));
		} else {
			List<Double> rtList = BlobUtil.convertBytesToList(landmarkDataEntity.getRt());
			rtList.add(rt);
			List<Double> intensityList = BlobUtil.convertBytesToList(landmarkDataEntity.getIntensity());
			intensityList.add(intensity);
			List<Double> relMzErrorList = BlobUtil.convertBytesToList(landmarkDataEntity.getRelMzError());
			relMzErrorList.add(relMzError);
			List<Double> absMzErrorList = BlobUtil.convertBytesToList(landmarkDataEntity.getAbsMzError());
			absMzErrorList.add(absMzError);
			List<Double> peakAreaList = BlobUtil.convertBytesToList(landmarkDataEntity.getPeakArea());
			peakAreaList.add(peakArea);
			landmarkDataEntity.setRt(BlobUtil.convertListToBytes(rtList));
			landmarkDataEntity.setIntensity(BlobUtil.convertListToBytes(intensityList));
			landmarkDataEntity.setRelMzError(BlobUtil.convertListToBytes(relMzErrorList));
			landmarkDataEntity.setAbsMzError(BlobUtil.convertListToBytes(absMzErrorList));
			landmarkDataEntity.setPeakArea(BlobUtil.convertListToBytes(peakAreaList));
			landmarkDataEntity.setSampleList(sampleList);
		}
		landmarkDataService.save(landmarkDataEntity);
		return landmarkDataEntity;
	}
	
}
