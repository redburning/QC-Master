package redburning.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import redburning.dto.TaskDto;
import redburning.engine.DirectoryWatcher;
import redburning.engine.RawDataProcessor;
import redburning.entity.spectrum.BPCEntity;
import redburning.entity.spectrum.LandmarkDataEntity;
import redburning.entity.spectrum.TICEntity;
import redburning.service.spectrum.BPCService;
import redburning.service.spectrum.LandmarkDataService;
import redburning.service.spectrum.TICService;
import redburning.vo.BasePeakCurrentVo;
import redburning.vo.LandmarkDataVo;
import redburning.vo.Result;
import redburning.vo.TotalIonCurrentVo;


@Service
public class TaskService {

	@Autowired
	private DirectoryWatcher directoryWatcher;
	
	@Autowired
	private RawDataProcessor rawDataProcessor;
	
	@Autowired
	private BPCService bpcService;
	
	@Autowired
	private TICService ticService;
	
	@Autowired
	private LandmarkDataService landmarkRtService;
	
	public void createTask(String taskId, TaskDto taskDTO, SseEmitter emitter) {
		rawDataProcessor.setTaskId(taskId);
		rawDataProcessor.setLandmarks(taskDTO.getLandmarks());
		rawDataProcessor.setEmitter(emitter);
		directoryWatcher.setTargetDir(taskDTO.getRawDataDir());
		directoryWatcher.setTargetFormats("raw", "RAW");
		directoryWatcher.setObserver(rawDataProcessor);
		directoryWatcher.startWatching();
	}
	
	public void stopTask(String taskId) {
		directoryWatcher.stopWatching();
	}

	public Result fetchBasePeakCurrent(String taskId) {
		try {
			List<BasePeakCurrentVo> data = new ArrayList<>();
			List<BPCEntity> entityList = bpcService.getByTaskId(taskId);
			for (BPCEntity bpcEntity : entityList) {
				BasePeakCurrentVo bpcVo = new BasePeakCurrentVo(bpcEntity);
				data.add(bpcVo);
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	public Result fetchTotalIonCurrent(String taskId) {
		try {
			List<TotalIonCurrentVo> data = new ArrayList<>();
			List<TICEntity> entityList = ticService.getByTaskId(taskId);
			for (TICEntity entity: entityList) {
				TotalIonCurrentVo ticVo = new TotalIonCurrentVo(entity);
				data.add(ticVo);
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	public Result fetchLandmarkRt(String taskId) {
		try {
			List<LandmarkDataVo> data = new ArrayList<>();
			List<LandmarkDataEntity> entityList = landmarkRtService.getByTaskId(taskId);
			for (LandmarkDataEntity entity : entityList) {
				LandmarkDataVo landmarkVo = new LandmarkDataVo(entity);
				data.add(landmarkVo);
			}
			return Result.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
}
