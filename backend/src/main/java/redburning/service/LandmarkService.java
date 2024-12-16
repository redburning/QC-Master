package redburning.service;

import java.util.List;

import redburning.entity.LandmarkEntity;

public interface LandmarkService {

	public LandmarkEntity saveOrUpdateLandmark(LandmarkEntity landmark);
	
	public List<LandmarkEntity> getAllLandmarks();
	
	public void removeLandmark(String id);
	
	public void removeAllLandmarks();
	
	public LandmarkEntity addOrUpdateLandmark(LandmarkEntity landmark);
	
}
