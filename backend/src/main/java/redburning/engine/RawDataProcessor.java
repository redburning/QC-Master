package redburning.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import redburning.Application;
import redburning.dto.Landmark;
import redburning.entity.MonitorFilesEntity;
import redburning.service.MonitorFilesService;
import redburning.util.FileUtil;
import umich.ms.fileio.exceptions.FileParsingException;

@Component
public class RawDataProcessor implements FileObserver {

	private static final Logger logger = LoggerFactory.getLogger(RawDataProcessor.class);
	
	private static final String pwizHomePath = Application.home() + File.separator + "assets" + File.separator
			+ "proteowizard" + File.separator;
	
	private static final String resultPath = Application.home() + File.separator + "output" + File.separator;
	
	private static final int maxRetries = 20;
	
	private String taskId;
	
	@Autowired
	private MzXmlDataParser mzXmlDataParser;
	
	@Autowired
	private MonitorFilesService monitorFilesService;
	
	public void setLandmarks(List<Landmark> landmarks) {
		mzXmlDataParser.setLandmarks(landmarks);
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
		mzXmlDataParser.setTaskId(taskId);
		initSampleList();
	}
	
	public void setEmitter(SseEmitter emitter) {
		mzXmlDataParser.setEmitter(emitter);
	}	
	
	public void initSampleList() {
		MonitorFilesEntity monitorFilesEntity = monitorFilesService.getEntity(taskId);
		if (monitorFilesEntity != null) {
			List<String> monitorFileList = new ArrayList<>(monitorFilesEntity.getFiles());
			List<String> sampleList = monitorFileList.stream()
					.map(e -> e.substring(e.lastIndexOf(File.separator) + 1, e.length() - 4))
					.collect(Collectors.toList());
			mzXmlDataParser.setSampleList(sampleList);
		}
	}
	
	@Override
	public void onNewFile(Path rawDataPath) {
		boolean isNewFile = true;
		MonitorFilesEntity monitorFilesEntity = monitorFilesService.getEntity(taskId);
		if (monitorFilesEntity != null) {
			Set<String> monitorFileSet = new LinkedHashSet<>(monitorFilesEntity.getFiles());
			String absolutePath = rawDataPath.toAbsolutePath().toString();
			if (!monitorFileSet.contains(absolutePath)) {
				monitorFileSet.add(absolutePath);
				monitorFilesEntity.setFiles(new ArrayList<>(monitorFileSet));
			} else {
				isNewFile = false;
			}
		} else {
			monitorFilesEntity = new MonitorFilesEntity(taskId, rawDataPath.toAbsolutePath().toString());
		}
		
		if (isNewFile) {
			for (int i = 0; i < maxRetries; i++) {
				try {
					BasicFileAttributes attributes = Files.readAttributes(rawDataPath, BasicFileAttributes.class);
					long modifiedTime = attributes.lastModifiedTime().toMillis();
					long lifeTime = System.currentTimeMillis() - modifiedTime;
					// 只处理生成超过15min的文件, 因为raw data从开始写入到写入完成大约要持续15min
					// 不完整的raw data是无法完成转换的
					long minLifeTime = 15 * 60 * 1000;
					if (lifeTime < minLifeTime) {
						logger.info("found newly file {}, wait for {}s to convert it.", rawDataPath.getFileName(), (minLifeTime - lifeTime) / 1000);
						Thread.sleep(minLifeTime - lifeTime);
					}
					
					attemptConvert(rawDataPath);
					logger.info("convert file finished: {}", rawDataPath.getFileName());
					
					String rawFileName = rawDataPath.getFileName().toString();
					String format = rawFileName.substring(rawFileName.lastIndexOf(".") + 1);
					String mzXmlFilePath = resultPath + rawFileName.replace(format, "mzXML");
					parseMzXmlData(mzXmlFilePath);
					
					// 所有都处理完成再保存处理进度信息
					monitorFilesService.saveOrUpdateEntity(monitorFilesEntity);
					
					logger.info("parse file finished: {}",  rawDataPath.getFileName());
					break;
				} catch (Exception e) {
					try {
						logger.warn("convert file {} occured error, wait for {}s and retry.", rawDataPath.getFileName(), 10 * (i + 1), e);
	                    Thread.sleep(10 * (i + 1) * 1000);
	                } catch (InterruptedException ex) {
	                    Thread.currentThread().interrupt();
	                }
				}
			}
		} else {
			logger.info("found processed file: {}, skip it.", rawDataPath.getFileName());
		}
	}
	
	private void attemptConvert(Path rawDataPath) throws Exception {
		ProcessBuilder builder = new ProcessBuilder(pwizHomePath + "msconvert.exe", 
				rawDataPath.toAbsolutePath().toString(),
				"--filter",
				"\"peakPicking true 1-\"",
				"--mzXML", 
				"-o", resultPath);
		Process p = builder.start();
		int exitCode = p.waitFor();
		if (exitCode > 0) {
			throw new Exception("msconvert execute error");
		}
	}
	
	private void parseMzXmlData(String filePath) throws FileParsingException, IOException {
		mzXmlDataParser.parseSampleName(filePath);
		mzXmlDataParser.parseBasePeakCurrent(filePath);
		mzXmlDataParser.parseTotalIonCurrent(filePath);
		mzXmlDataParser.parseExtractIonCurrent(filePath);
		mzXmlDataParser.extractLandmarks(filePath);
		// clean mzXML file
		FileUtil.removeFile(filePath);
	}
	
}
