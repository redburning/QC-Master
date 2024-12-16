package redburning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.entity.LandmarkEntity;
import redburning.service.LandmarkService;
import redburning.vo.Acknowledged;
import redburning.vo.Result;

@RestController
public class LandmarkController {

	
	@Autowired
	private LandmarkService landmarkService;
	
	@PutMapping(value = "/landmark/upload")
	public Acknowledged uploadLandmarks(@RequestBody List<LandmarkEntity> landmarks) {
		try {
			landmarkService.removeAllLandmarks();
			for (LandmarkEntity landmark : landmarks) {
				landmarkService.saveOrUpdateLandmark(landmark);
			}
			return new Acknowledged(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Acknowledged(false, e.getMessage());
		}
	}
	
	@PutMapping(value = "/landmark")
	public Result addOrUpdateLandmark(@RequestBody LandmarkEntity landmark) {
		try {
			LandmarkEntity savedLandmark = landmarkService.saveOrUpdateLandmark(landmark);
			return new Result(200, "success", savedLandmark);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(410, "fail", e.getMessage());
		}
	}
	
	@GetMapping(value = "/landmark")
	public Result getAllLandmarks() {
		try {
			List<LandmarkEntity> landmarks = landmarkService.getAllLandmarks();
			return new Result(200, "success", landmarks);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(411, "fail", e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/landmark/{id}")
	public Acknowledged removeLandmark(@PathVariable(value = "id") String id) {
		try {
			landmarkService.removeLandmark(id);
			return new Acknowledged(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Acknowledged(false, e.getMessage());
		}
	}
	
}
