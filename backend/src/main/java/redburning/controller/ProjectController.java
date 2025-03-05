package redburning.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redburning.entity.MonitorFilesEntity;
import redburning.entity.ProjectEntity;
import redburning.service.MonitorFilesService;
import redburning.service.ProjectService;
import redburning.vo.ProjectDataVo;
import redburning.vo.ProjectVo;
import redburning.vo.Result;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MonitorFilesService monitorFilesService;
	
	@PutMapping(value = "/project/{id}")
	public Result updateProject(@PathVariable(value = "id") String id, @RequestBody ProjectEntity project) {
		try {
			String name = project.getName();
			ProjectEntity projectEntity = projectService.getProject(id);
			projectEntity.setName(name);
			projectService.saveOrUpdateProject(projectEntity);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/project/{id}")
	public Result loadProjectData(@PathVariable(value = "id") String id) {
		try {
			ProjectDataVo projectDataVo = projectService.loadProjectData(id);
			return Result.success(projectDataVo);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/projects")
	public Result getAllProjects() {
		try {
			List<ProjectVo> projectVoList = new ArrayList<>();
			List<ProjectEntity> projectList = projectService.getAllProjects();
			for (ProjectEntity project : projectList) {
				String id = project.getId();
				MonitorFilesEntity monitorFilesEntity = monitorFilesService.getEntity(id);
				if (monitorFilesEntity != null) {
					int sampleNumber = monitorFilesEntity.getFiles().size();
					ProjectVo projectVo = new ProjectVo(project);
					projectVo.setSampleNumber(sampleNumber);
					projectVoList.add(projectVo);
				}
			}
			return Result.success(projectVoList);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/project/{id}")
	public Result removeProject(@PathVariable(value = "id") String id) {
		try {
			projectService.deleteProject(id);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
}
