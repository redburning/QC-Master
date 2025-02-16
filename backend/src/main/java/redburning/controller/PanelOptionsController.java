package redburning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.entity.PanelOptionsEntity;
import redburning.service.PanelOptionsService;
import redburning.vo.Result;

@RestController
public class PanelOptionsController {
	
	@Autowired
	private PanelOptionsService panelOptionsService;
	
	@PutMapping(value = "/paneloptions")
	public Result savePanelOptions(@RequestBody PanelOptionsEntity panelOptionsEntity) {
		try {
			PanelOptionsEntity savedEntity = panelOptionsService.savePanelOptions(panelOptionsEntity);
			return Result.success(savedEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/paneloptions/{id}")
	public Result getPanelOptions(@PathVariable(value = "id") String id) {
		try {
			PanelOptionsEntity entity = panelOptionsService.getPanelOptions(id);
			return Result.success(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
}
