package redburning.engine.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import redburning.Application;
import redburning.dto.Landmark;
import redburning.dto.TaskDTO;
import redburning.util.FileUtil;
import umich.ms.datatypes.scan.IScan;
import umich.ms.fileio.filetypes.mzxml.MZXMLFile;
import umich.ms.fileio.filetypes.mzxml.MZXMLIndex;

@Slf4j
public class Task implements ITask {

	private String pwizHomePath = Application.home() + File.separator + "assets" + File.separator + "proteowizard"
			+ File.separator;
	private String resultPath = null;
	
	// task settings
	private String rawDataDir;
	private String outputFormat;
	private List<Landmark> landmarks;
	
	// task meta info
	private int id;
	private volatile TaskState state;
	private volatile String progress;
	
	// task result
	private List<String> monitorFiles = new ArrayList<>();
	private List<String> rawDataFiles = new ArrayList<>();
	private List<String> processedFileOfIonCurrent = new ArrayList<>();
	private List<String> processedFileOfLandmarks = new ArrayList<>();
	// base peak current: sample - [[rt-1, rt-2, ..., rt-n], [base peak intensity 1, base peak intensity 2, ..., base peak intensity n]]
	private JSONArray basePeakCurrentList = new JSONArray();
	// total ion current: sample - [[rt-1, rt-2, ..., rt-n], [total ion current 1, total ion current 2, ..., total ion current n]]
	private JSONArray totalIonCurrentList = new JSONArray();
	// extract ion current: landmark - [[rt-1, rt-2, ..., rt-n], [intensity 1, intensity 2, ..., intensity n]]
	private Map<String, List<List<List<Double>>>> extractIonCurrentMap = new LinkedHashMap<>();
	// 保留时间图: landmark - [rt1, rt2, rt3, rt4, rt5]
	private Map<String, List<Double>> landmarkRtMap = new LinkedHashMap<>();
	// Peak Intensity: landmark - [intensity1, intensity-2, intensity-3, intensity-4, intensity-5]
	private Map<String, List<Double>> landmarkIntensityMap = new LinkedHashMap<>();
	// relative m/z error
	private Map<String, List<Double>> landmarkRelMzErrorMap = new LinkedHashMap<>();
	// absolute m/z error
	private Map<String, List<Double>> landmarkAbsMzErrorMap = new LinkedHashMap<>();
	
	public Task(int id, TaskDTO options) {
		this.id = id;
		this.rawDataDir = options.getRawDataDir();
		this.outputFormat = options.getOutputFormat();
		this.landmarks = options.getLandmarks();
		
		this.resultPath = Application.home() + File.separator + "output" + File.separator + String.valueOf(id);
		FileUtil.deleteDir(resultPath);
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public TaskState state() {
		return state;
	}
	
	@Override
	public String getMonitorDir() {
		return rawDataDir;
	}
	
	private Map<String, JSONArray> transpose(Map<String, List<Double>> dataMap, String sample) {
		Map<String, JSONArray> result = new HashMap<>();
		if (!dataMap.isEmpty()) {
			String firstKey = dataMap.keySet().iterator().next();
			int landmarkNum = dataMap.get(firstKey).size();
			for (int i = 0; i < landmarkNum; i++) {
				JSONArray landmarkTrace = new JSONArray();
				double index = 1;
				int count = 0;
				for (String sampleName : dataMap.keySet()) {
					if (sample.toLowerCase().contains("all")) {
						JSONObject sampleObject = new JSONObject();
						sampleObject.put("sample", sampleName);
						sampleObject.put("value", dataMap.get(sampleName).get(i));
						sampleObject.put("index", index++);
						landmarkTrace.add(sampleObject);
					} else {
						int lastn = Integer.valueOf(sample);
						if (count >= dataMap.keySet().size() - lastn) {
							JSONObject sampleObject = new JSONObject();
							sampleObject.put("sample", sampleName);
							sampleObject.put("value", dataMap.get(sampleName).get(i));
							sampleObject.put("index", index++);
							landmarkTrace.add(sampleObject);
						}
					}
					count++;
				}
				result.put(landmarks.get(i).getName(), landmarkTrace);
			}
		}
		return result;
	}
	
	private Map<String, List<List<Double>>> transpose2(Map<String, List<List<List<Double>>>> dataMap) {
		Map<String, List<List<Double>>> result = new HashMap<>();
		if (!dataMap.isEmpty()) {
			String firstKey = dataMap.keySet().iterator().next();
			int size = dataMap.get(firstKey).size();
			for (int i = 0; i < size; i++) {
				List<List<Double>> traceList = new ArrayList<>();
				List<Double> trace0 = new ArrayList<>();
				List<Double> trace1 = new ArrayList<>();
				for (String key : dataMap.keySet()) {
					List<Double> values0 = dataMap.get(key).get(i).get(0);
					List<Double> values1 = dataMap.get(key).get(i).get(1);
					sortListAccordingtoWeight(values1, values0);
					if (!trace0.isEmpty() && !trace1.isEmpty()) {
						trace0.add(null);
						trace1.add(null);
					}
					trace0.addAll(values0);
					trace1.addAll(values1);
				}
				traceList.add(trace0);
				traceList.add(trace1);
				result.put(landmarks.get(i).getName(), traceList);
			}
		}
		return result;
	}
	
	
	private Map<String, List<List<Double>>> transpose3(Map<String, List<List<List<Double>>>> dataMap) {
		Map<String, List<List<Double>>> result = new HashMap<>();
		if (!dataMap.isEmpty()) {
			String firstKey = dataMap.keySet().iterator().next();
			int size = dataMap.get(firstKey).size();
			// landmark
			for (int i = 0; i < size; i++) {
				List<Double> areaList = new ArrayList<>();
				List<Double> sequenceList = new ArrayList<>();
				double seq = 1;
				// sample
				for (String key : dataMap.keySet()) {
					List<Double> values0 = dataMap.get(key).get(i).get(0);
					List<Double> values1 = dataMap.get(key).get(i).get(1);
					sortListAccordingtoWeight(values1, values0);
					// 计算积分
					double integration = 0.0;
					for (int k = 1; k < values0.size(); k++) {
						double area = (values1.get(k) + values1.get(k - 1)) * (values0.get(k) - values0.get(k - 1)) / 2;
						integration += area;
					}
					areaList.add(integration);
					sequenceList.add(seq++);
				}
				List<List<Double>> traceList = new ArrayList<>();
				traceList.add(sequenceList);
				traceList.add(areaList);
				result.put(landmarks.get(i).getName(), traceList);
			}
		}
		return result;
	}
	
	
	public static <E extends Object, W extends Comparable<W>> void sortListAccordingtoWeight(List<E> list, List<W> weight) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size() - i - 1; j++) {
				if (weight.get(j).compareTo(weight.get(j + 1)) > 0) {
					W w = weight.get(j);
					weight.set(j, weight.get(j + 1));
					weight.set(j + 1, w);
					E o = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, o);
				}
			}
		}
	}
	
	
	@Override
	public Map<String, Object> fetchIonCurrent(String sample) {
		Map<String, Object> result = new HashMap<>();
		try {
			List<String> outputFiles = FileUtil.listDir(resultPath, ".mzXML");
			List<String> newFoundData = new ArrayList<>();
			for (String file : outputFiles) {
				if (!processedFileOfIonCurrent.contains(file) && (System.currentTimeMillis() - FileUtil.getFileLastModifiedTime(file)) > 5000) {
					newFoundData.add(file);
					break;
				}
			}
			
			for (int i = 0; i < newFoundData.size(); i++) {
				String outputFile = newFoundData.get(i);
				Path path = Paths.get(outputFile);
				MZXMLFile source = new MZXMLFile(path.toString());
				MZXMLIndex index = source.fetchIndex();
				List<Double> rtTrace = new ArrayList<>();
				List<Double> basePeakIntensityTrace = new ArrayList<>();
				List<Double> totalIonCurrentTrace = new ArrayList<>();
				for (Integer scanNumRaw : index.getMapByRawNum().keySet()) {
					IScan scan = source.parseScan(scanNumRaw, true);
					rtTrace.add(scan.getRt());
					basePeakIntensityTrace.add(scan.getBasePeakIntensity());
					totalIonCurrentTrace.add(scan.getTic());
				}
				List<List<Double>> basePeakCurrent = new ArrayList<>();
				basePeakCurrent.add(rtTrace);
				basePeakCurrent.add(basePeakIntensityTrace);
				JSONObject bpcObject = new JSONObject();
				String sampleName = outputFile.substring(outputFile.lastIndexOf(File.separator) + 1, outputFile.length() - 6);
				bpcObject.put("name", sampleName);
				bpcObject.put("data", basePeakCurrent);
				monitorFiles.add(rawDataDir + File.separator + sampleName + ".raw");
				basePeakCurrentList.add(bpcObject);
				
				List<List<Double>> totalIonCurrent = new ArrayList<>();
				totalIonCurrent.add(rtTrace);
				totalIonCurrent.add(totalIonCurrentTrace);
				JSONObject ticObject = new JSONObject();
				ticObject.put("name", sampleName);
				ticObject.put("data", totalIonCurrent);
				totalIonCurrentList.add(ticObject);
				
				source.close();
				processedFileOfIonCurrent.add(outputFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sample.toLowerCase().contains("all")) {
			result.put("basePeakCurrent", basePeakCurrentList);
			result.put("totalIonCurrent", totalIonCurrentList);
			result.put("monitorFiles", monitorFiles);
		} else {
			int lastn = Integer.valueOf(sample);
			int fromIndex = basePeakCurrentList.size() - lastn >= 0 ? basePeakCurrentList.size() - lastn : 0;
			int toIndex = basePeakCurrentList.size() - 1;
			result.put("basePeakCurrent", basePeakCurrentList.subList(fromIndex, toIndex));
			result.put("totalIonCurrent", totalIonCurrentList.subList(fromIndex, toIndex));
			result.put("monitorFiles", monitorFiles.subList(fromIndex, toIndex));
		}
		
		return result;
	}
	
	
	@Override
	public Map<String, Object> fetchLandmarks(String landmark, String sample) {
		Map<String, Object> result = new HashMap<>();
		try {
			List<String> outputFiles = FileUtil.listDir(resultPath, ".mzXML");
			List<String> newFoundData = new ArrayList<>();
			for (String file : outputFiles) {
				if (!processedFileOfLandmarks.contains(file) && (System.currentTimeMillis() - FileUtil.getFileLastModifiedTime(file)) > 5000) {
					newFoundData.add(file);
					break;
				}
			}
			
			for (int i = 0; i < newFoundData.size(); i++) {
				String outputFile = newFoundData.get(i);
				Path path = Paths.get(outputFile);
				MZXMLFile source = new MZXMLFile(path.toString());
				MZXMLIndex index = source.fetchIndex();
				// landmark rt
				List<Double> landmarkRtList = new ArrayList<>();
				// landmark intensity
				List<Double> landmarkIntensityList = new ArrayList<>();
				// landmark relative m/z error
				List<Double> landmarkRelMzErrorList = new ArrayList<>();
				// landmark absolute m/z error
				List<Double> landmarkAbsMzErrorList = new ArrayList<>();
				
				List<List<List<Double>>> extractRtIntensityList = new ArrayList<>();
				for (int l = 0; l < landmarks.size(); l++) {
					boolean findLandmark = false;
					double rtMin = landmarks.get(l).getRtMin();
					double rtMax = landmarks.get(l).getRtMax();
					double mzVal = landmarks.get(l).getMzVal();
					double mzMin = landmarks.get(l).getMzMin();
					double mzMax = landmarks.get(l).getMzMax();
					
					// extract ion current
					List<Double> extractLandmarkRt = new ArrayList<>();
					List<Double> extractLandmarkIntensity = new ArrayList<>();
					List<List<Double>> extractLandmarkRtIntensityList = new ArrayList<>();
					
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
									landmarkRtList.add(rt);
									landmarkIntensityList.add(intensity);
									double relMzError = (mz - mzVal) * 1000;
									landmarkRelMzErrorList.add(relMzError);
									double absMzError = Math.abs(mz - mzVal) / mzVal * 1e6;
									landmarkAbsMzErrorList.add(absMzError);
								}
								// 如果已经找打符合条件的，则与之比较intensity，保留强度更大的
								else {
									int lastIndex = landmarkIntensityList.size() - 1;
									if (Double.compare(intensity, landmarkIntensityList.get(lastIndex)) > 0) {
										landmarkRtList.set(lastIndex, rt);
										landmarkIntensityList.set(lastIndex, intensity);
										double relMzError = (mz - mzVal) * 1000;
										landmarkRelMzErrorList.set(lastIndex, relMzError);
										double absMzError = Math.abs(mz - mzVal) / mzVal * 1e6;
										landmarkAbsMzErrorList.set(lastIndex, absMzError);
									}
								}
								findLandmark = true;
							}
							if (rt >= rtMin && rt <= rtMax && mz >= mzMin && mz <= mzMax) {
								extractLandmarkRt.add(rt);
								extractLandmarkIntensity.add(intensity);
							}
						}
					}
					// 如果最终未找到符合条件的，暂时填充为0.0
					if (!findLandmark) {
						landmarkRtList.add(0.0);
						landmarkIntensityList.add(0.0);
						landmarkRelMzErrorList.add(0.0);
						landmarkAbsMzErrorList.add(0.0);
					}
					
					// 二维list, 第一维度为rt, 第二维度为intensity
					extractLandmarkRtIntensityList.add(extractLandmarkRt);
					extractLandmarkRtIntensityList.add(extractLandmarkIntensity);
					// 三维list, 第一维度为landmark, 第二维度为rt, 第三维度为intensity
					extractRtIntensityList.add(extractLandmarkRtIntensityList);
				}
				String sampleName = outputFile.substring(outputFile.lastIndexOf(File.separator) + 1,
						outputFile.length() - 6);
				landmarkRtMap.put(sampleName, landmarkRtList);
				landmarkIntensityMap.put(sampleName, landmarkIntensityList);
				landmarkRelMzErrorMap.put(sampleName, landmarkRelMzErrorList);
				landmarkAbsMzErrorMap.put(sampleName, landmarkAbsMzErrorList);
				extractIonCurrentMap.put(sampleName, extractRtIntensityList);
				processedFileOfLandmarks.add(outputFile);
				source.close();
				// clean mzXML file
				FileUtil.deleteDir(outputFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("unknown exception");
		}
		result.put("landmarkRt", transpose(landmarkRtMap, sample));
		result.put("landmarkIntensity", transpose(landmarkIntensityMap, sample));
		result.put("landmarkRelMzError", transpose(landmarkRelMzErrorMap, sample));
		result.put("landmarkAbsMzError", transpose(landmarkAbsMzErrorMap, sample));
		result.put("extractIonCurrent", transpose2(extractIonCurrentMap));
		result.put("landmarkPeakArea", transpose3(extractIonCurrentMap));
		return result;
	}
	
	
	@Override
	public void run() {
		state = TaskState.RUNNING;
		while (state == TaskState.RUNNING) {
			try {
				state = TaskState.RUNNING;
				// raw data 格式转换
				String cmd = "msconvert.exe";
				String output = "-o";
				String filter = "filter";
				String peakPickingParams = "\"peakPicking true 1-\"";
				
				List<String> rawDataPathList = FileUtil.listDir(rawDataDir, ".raw", ".RAW");
				// 根据本次扫描结果更新数据列表
				List<String> newFoundData = new ArrayList<>();
				for (String rawData : rawDataPathList) {
					if (!rawDataFiles.contains(rawData)) {
						newFoundData.add(rawData);
						// 每次只处理一个
						break;
					}
				}
				
				for (int i = 0; i < newFoundData.size(); i++) {
					String rawDataPath = newFoundData.get(i);
					ProcessBuilder builder = new ProcessBuilder(pwizHomePath + cmd, rawDataPath,
							"--" + filter, peakPickingParams,
							"--" + outputFormat, output, resultPath);
					Process p = builder.start();
					int exitCode = p.waitFor();
					if (exitCode > 0) {
						log.error("msconvert execute error, exitCode: {}", exitCode);
					} else {
						rawDataFiles.add(rawDataPath);
						progress = String.format("%d/%d", rawDataFiles.size(), rawDataPathList.size());
						log.info("msconvert execute finish, current progress: {}", progress);
					}
				}
				Thread.sleep(5 * 1000);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	@Override
	public void suspend() {
		state = TaskState.SUSPEND;
	}
	
	@Override
	public void stop() {
		
	}

}
