package redburning.engine.task;

import java.util.Map;

public interface ITask extends Runnable {

	/**
	 * 获取task的id
	 * @return
	 */
	int id();
	
	
	/**
	 * 获取任务的状态
	 * @return
	 */
	TaskState state();
	
	/**
	 * 获取监控文件目录
	 * @return
	 */
	String getMonitorDir();
	
	Map<String, Object> fetchIonCurrent(String sample);
	
	Map<String, Object> fetchLandmarks(String landmark, String sample);
	
	/**
	 * 挂起任务
	 */
	void suspend();
	
	/**
	 * 终止任务执行
	 */
	void stop();
	
}
