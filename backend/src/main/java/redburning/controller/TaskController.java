package redburning.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import redburning.dto.TaskDto;
import redburning.entity.ProjectEntity;
import redburning.service.ProjectService;
import redburning.service.TaskService;
import redburning.vo.Result;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskContext taskContext;
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/create")
	public Result createTask(@RequestBody TaskDto taskDTO) {
		try {
			SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
			
			ProjectEntity project = projectService.saveOrUpdateProject(
					new ProjectEntity(taskDTO.getRawDataDir(), "running", System.currentTimeMillis()));
			
			String taskId = project.getId();
			taskContext.saveSseEmitter(taskId, emitter);
			taskService.createTask(taskId, taskDTO, emitter);
			return Result.success(taskId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping("/stop/{id}")
	public Result stopTask(@PathVariable("id") String id) {
		try {
			ProjectEntity project = projectService.getProject(id);
			project.setStatus("stopped");
			projectService.saveOrUpdateProject(project);
			taskService.stopTask(id);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@PostMapping("/resume/{id}")
	public Result resumeTask(@PathVariable("id") String id, @RequestBody TaskDto taskDTO) {
		try {
			// 恢复任务状态为运行中
			ProjectEntity project = projectService.getProject(id);
			project.setStatus("running");
			projectService.saveOrUpdateProject(project);
			// 恢复任务
			SseEmitter emitter = taskContext.getSseEmitter(id);
			if (emitter == null) {
				emitter = new SseEmitter(Long.MAX_VALUE);
				taskContext.saveSseEmitter(id, emitter);
			}
			taskService.createTask(id, taskDTO, emitter);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/data/{taskId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter fetchData(@PathVariable("taskId") String taskId) {
		return taskContext.getSseEmitter(taskId);
	}
}


@Component
class TaskContext {
	
	private Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();
	
	public TaskContext() {
		this.sseEmitterMap = new ConcurrentHashMap<>();
	}
	
	public void saveSseEmitter(String taskId, SseEmitter sseEmitter) {
		sseEmitterMap.put(taskId, sseEmitter);
	}
	
	public SseEmitter getSseEmitter(String taskId) {
		return sseEmitterMap.get(taskId);
	}
}
