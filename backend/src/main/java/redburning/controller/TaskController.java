package redburning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import redburning.dto.TaskDTO;
import redburning.service.TaskService;
import redburning.vo.Result;

@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@PostMapping("/task/create")
	public Result createTask(@RequestBody TaskDTO taskDTO) {
		return taskService.createTask(taskDTO);
	}
	
	@GetMapping("/task/suspend/{taskId}")
	public Result suspendTask(@PathVariable("taskId") int taskId) {
		return taskService.suspendTask(taskId);
	}
	
	@GetMapping("/task/data/ionflow/{taskId}")
	public Result fetchIonFlow(@PathVariable(value = "taskId") int taskId, @RequestParam(value = "sample") String sample) {
		return taskService.fetchIonFlow(taskId, sample);
	}
	
	@GetMapping("/task/data/landmarks/{taskId}")
	public Result fetchLandmarkRt(@PathVariable(value = "taskId") int taskId,
			@RequestParam(value = "landmark") String landmark, @RequestParam(value = "sample") String sample) {
		return taskService.fetchLandmarks(taskId, landmark, sample);
	}
	
}
