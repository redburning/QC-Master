package redburning.engine.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import redburning.dto.TaskDTO;

public class TaskManager implements ITaskManager {

	private final AtomicInteger nextId = new AtomicInteger(1);
	private final Map<Integer, Task> managedTasks = new ConcurrentHashMap<>();
	
	public TaskManager() {}

	@Override
	public ITask createTask(TaskDTO options) {
		for (ITask task : tasks()) {
			if (task.getMonitorDir().equals(options.getRawDataDir().trim())) {
				Thread thread = new Thread(task);
				thread.start();
				return task;
			}
		}
		int id = nextId.getAndIncrement();
		Task task = new Task(id, options);
		Thread thread = new Thread(task);
		thread.start();
		managedTasks.put(id, task);
		return task;
		
	}

	@Override
	public ITask task(int id) {
		return managedTasks.get(id);
	}

	@Override
	public List<ITask> tasks() {
		return new ArrayList<>(managedTasks.values());
	}

	@Override
	public void suspendTask(int id) {
		if (managedTasks.containsKey(id)) {
			Task task = managedTasks.get(id);
			task.suspend();
		}
	}
	
	@Override
	public void stopTask(int id) {
		if (managedTasks.containsKey(id)) {
			Task task = managedTasks.get(id);
			task.stop();
		}
	}

	@Override
	public TaskState state(int id) {
		if (managedTasks.containsKey(id)) {
			Task task = managedTasks.get(id);
			return task.state();
		}
		return null;
	}
	
}
