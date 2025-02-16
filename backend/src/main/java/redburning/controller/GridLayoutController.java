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
			return Result.success(savedLandmark);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/gridlayout")
	public Result getAllLayout() {
		try {
			List<GridLayoutEntity> layouts = layoutService.getAllLayout();
			return Result.success(layouts);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/gridlayout/{id}")
	public Result getLayout(@PathVariable(value = "id") String id) {
		try {
			GridLayoutEntity layout = layoutService.getLayout(id);
			return Result.success(layout.getLayout());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
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
