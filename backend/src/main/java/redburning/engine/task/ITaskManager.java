package redburning.engine.task;

import java.util.List;

import redburning.dto.TaskDTO;

public interface ITaskManager {

	/**
	 * 创建一个任务
	 * @param monitorDir
	 * @return
	 */
	ITask createTask(TaskDTO options);
	
	/**
	 * 根据task的id获取task
	 * @param id
	 * @return
	 */
	ITask task(int id);
	
	/**
	 * 获取全部的task列表
	 * @return
	 */
	List<ITask> tasks();
	
	/**
	 * 获取任务的执行状态
	 * @param id
	 * @return
	 */
	TaskState state(int id);
	
	/**
	 * 挂起一个任务
	 * @param id
	 */
	void suspendTask(int id);
	
	/**
	 * 终止一个任务的执行
	 * @param id
	 */
	void stopTask(int id);
	
}
