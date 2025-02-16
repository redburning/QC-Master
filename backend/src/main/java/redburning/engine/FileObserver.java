package redburning.engine;

import java.nio.file.Path;

/**
 * 监视目标路径的文件变化（新增，删除，更新）
 */
public interface FileObserver {

	void onNewFile(Path filePath);
	
}
