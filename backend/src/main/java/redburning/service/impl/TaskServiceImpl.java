package redburning.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import redburning.dto.TaskDTO;
import redburning.engine.task.ITask;
import redburning.engine.task.ITaskManager;
import redburning.engine.task.TaskManager;
import redburning.service.TaskService;
import redburning.vo.Result;


@Service
public class TaskServiceImpl implements TaskService {

	private static final String TASK_ID = "taskid";
	
	private ITaskManager taskManager = new TaskManager();
	
	public Result createTask(TaskDTO taskDTO) {
		try {
			ITask task = taskManager.createTask(taskDTO);
			Map<String, Object> data = new HashMap<>();
			data.put(TASK_ID, task.id());
			return new Result(200, "success", data);
		} catch (Exception e) {
			return new Result(400, "fail", e.getMessage());
		}
	}

	@Override
	public Result stopTask(int taskId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result suspendTask(int taskId) {
		try {
			taskManager.suspendTask(taskId);
			return new Result(200, "success");
		} catch (Exception e) {
			return new Result(401, "fail", e.getMessage());
		}
	}

	@Override
	public Result fetchIonFlow(int taskId, String sample) {
		try {
			ITask task = taskManager.task(taskId);
			Map<String, Object> data = task.fetchIonCurrent(sample);
			return new Result(200, "success", data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(402, "fail", e.getMessage());
		}
	}
	
	@Override
	public Result fetchLandmarks(int taskId, String landmark, String sample) {
		try {
			ITask task = taskManager.task(taskId);
			Map<String, Object> data = task.fetchLandmarks(landmark, sample);
			return new Result(200, "success", data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(403, "fail", e.getMessage());
		}
	}
	
}
