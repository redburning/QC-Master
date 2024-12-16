package redburning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.entity.GridLayoutEntity;
import redburning.service.GridLayoutService;
import redburning.vo.Acknowledged;
import redburning.vo.Result;

@RestController
public class GridLayoutController {

	
	@Autowired
	private GridLayoutService layoutService;
	
	@PutMapping(value = "/gridlayout")
	public Result addOrUpdateLayout(@RequestBody GridLayoutEntity layout) {
		try {
			GridLayoutEntity savedLandmark = layoutService.saveOrUpdateLayout(layout);
			return new Result(200, "success", savedLandmark);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(510, "fail", e.getMessage());
		}
	}
	
	@GetMapping(value = "/gridlayout")
	public Result getAllLayout() {
		try {
			List<GridLayoutEntity> layouts = layoutService.getAllLayout();
			return new Result(200, "success", layouts);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(411, "fail", e.getMessage());
		}
	}
	
	@GetMapping(value = "/gridlayout/{id}")
	public Result getLayout(@PathVariable(value = "id") String id) {
		try {
			GridLayoutEntity layout = layoutService.getLayout(id);
			return new Result(200, "success", layout.getLayout());
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(411, "fail", e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/gridlayout/{id}")
	public Acknowledged removeLayout(@PathVariable(value = "id") String id) {
		try {
			layoutService.removeLayout(id);
			return new Acknowledged(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Acknowledged(false, e.getMessage());
		}
	}
	
}
