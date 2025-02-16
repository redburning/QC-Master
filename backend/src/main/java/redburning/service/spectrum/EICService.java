package redburning.service.spectrum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.spectrum.EICEntity;
import redburning.repository.spectrum.EICRepository;

@Service
public class EICService {

	@Autowired
	private EICRepository extractIonCurrentRepository;

	public List<EICEntity> getByTaskId(String taskId) {
		return extractIonCurrentRepository.findByTaskId(taskId);
	}

	public EICEntity getByTaskIdAndLandmark(String taskId, String landmark) {
		return extractIonCurrentRepository.findByTaskIdAndLandmark(taskId, landmark);
	}
	
	public EICEntity save(EICEntity entity) {
		return extractIonCurrentRepository.save(entity);
	}

	public void deleteByTaskId(String taskId) {
		extractIonCurrentRepository.deleteByTaskId(taskId);
	}
	
}
