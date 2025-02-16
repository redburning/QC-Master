package redburning.service.spectrum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.spectrum.LandmarkDataEntity;
import redburning.repository.spectrum.LandmarkDataRepository;

@Service
public class LandmarkDataService {

	@Autowired
	private LandmarkDataRepository landmarkDataRepository;
	
	public List<LandmarkDataEntity> getByTaskId(String taskId) {
		return landmarkDataRepository.findByTaskId(taskId);
	}
	
	public LandmarkDataEntity getByTaskIdAndLandmarkName(String taskId, String landmarkName) {
		return landmarkDataRepository.findByTaskIdAndLandmarkName(taskId, landmarkName);
	}

	public LandmarkDataEntity save(LandmarkDataEntity entity) {
		return landmarkDataRepository.save(entity);
	}

	public void deleteByTaskId(String taskId) {
		landmarkDataRepository.deleteByTaskId(taskId);
	}
	
}
