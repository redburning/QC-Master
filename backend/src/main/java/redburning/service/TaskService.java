package redburning.service;

import java.io.IOException;

import redburning.dto.TaskDTO;
import redburning.vo.Result;

public interface TaskService {

	/**
	 * 创建任务
	 * @param taskDTO
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Result createTask(TaskDTO taskDTO);
	
	public Result stopTask(int taskId);
	
	/**
	 * 挂起任务
	 * @param taskId
	 * @return
	 */
	public Result suspendTask(int taskId);
	
	public Result fetchIonFlow(int taskId, String sample);
	
	public Result fetchLandmarks(int taskId, String landmark, String sample);
	
}
