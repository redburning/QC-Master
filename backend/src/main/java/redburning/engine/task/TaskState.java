package redburning.engine.task;

public enum TaskState {

	/**
	 * 运行中
	 */
	RUNNING,
	/**
	 * 执行成功结束
	 */
	SUCCESS,
	/**
	 * 执行中被挂起
	 */
	SUSPEND,
	/**
	 * 执行出错终止
	 */
	ERROR,
	/**
	 * 执行时被终止
	 */
	TERMINATED;
	
}
