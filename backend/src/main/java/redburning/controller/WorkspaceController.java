package redburning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.entity.WorkspaceEntity;
import redburning.service.WorkspaceService;
import redburning.vo.Acknowledged;
import redburning.vo.Result;

@RestController
public class WorkspaceController {

	@Autowired
	private WorkspaceService workspaceService;
	
	@PutMapping(value = "/workspace")
	public Result saveOrUpdateWorkspace(@RequestBody WorkspaceEntity workspace) {
		try {
			WorkspaceEntity savedWorkspace = workspaceService.saveOrUpdateWorkspace(workspace);
			return new Result(200, "success", savedWorkspace);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(510, "fail", e.getMessage());
		}
	}
	
	@GetMapping(value = "/workspace/{id}")
	public Result getWorkspace(@PathVariable(value = "id") String id) {
		try {
			WorkspaceEntity workspace = workspaceService.getWorkspace(id);
			return new Result(200, "success", workspace);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(511, "fail", e.getMessage());
		}
	}
	
	@GetMapping(value = "/workspace")
	public Result getAllWorkspaces() {
		try {
			List<WorkspaceEntity> workspaceList = workspaceService.getAllWorkspaces();
			return new Result(200, "success", workspaceList);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(512, "fail", e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/workspace/{id}")
	public Acknowledged removeWorkspace(@PathVariable(value = "id") String id) {
		try {
			workspaceService.deleteWorkspace(id);
			return new Acknowledged(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new Acknowledged(false, e.getMessage());
		}
	}
	
}
