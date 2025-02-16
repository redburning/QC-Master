package redburning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.LandmarkEntity;
import redburning.repository.LandmarkRepository;

@Service
public class LandmarkService {

	@Autowired
	private LandmarkRepository landmarkRepository;
	
	public LandmarkEntity saveOrUpdateLandmark(LandmarkEntity landmark) {
		LandmarkEntity savedEntity = landmarkRepository.save(landmark);
		return savedEntity;
	}

	public List<LandmarkEntity> getAllLandmarks() {
		return landmarkRepository.findAll();
	}

	public void removeLandmark(String id) {
		landmarkRepository.deleteById(id);
	}

	public void removeAllLandmarks() {
		landmarkRepository.deleteAll();
	}
	
	public LandmarkEntity addOrUpdateLandmark(LandmarkEntity landmark) {
		LandmarkEntity savedLandmark = landmarkRepository.save(landmark);
		return savedLandmark;
	}

}
