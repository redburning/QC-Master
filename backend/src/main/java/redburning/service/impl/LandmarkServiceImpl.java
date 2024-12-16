package redburning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redburning.entity.LandmarkEntity;
import redburning.repository.LandmarkRepository;
import redburning.service.LandmarkService;

@Service
public class LandmarkServiceImpl implements LandmarkService {

	@Autowired
	private LandmarkRepository landmarkRepository;
	
	public LandmarkEntity saveOrUpdateLandmark(LandmarkEntity landmark) {
		LandmarkEntity savedEntity = landmarkRepository.save(landmark);
		return savedEntity;
	}

	@Override
	public List<LandmarkEntity> getAllLandmarks() {
		return landmarkRepository.findAll();
	}

	@Override
	public void removeLandmark(String id) {
		landmarkRepository.deleteById(id);
	}

	@Override
	public void removeAllLandmarks() {
		landmarkRepository.deleteAll();
	}
	
	@Override
	public LandmarkEntity addOrUpdateLandmark(LandmarkEntity landmark) {
		LandmarkEntity savedLandmark = landmarkRepository.save(landmark);
		return savedLandmark;
	}

}
