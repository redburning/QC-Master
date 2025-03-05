package redburning.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class DirectoryWatcher {

	private volatile boolean isMonitoring = false;
	private WatchService watchService;
	private ExecutorService executorService;
	
    private Path targetDir;
    private String[] formats;
    private List<FileObserver> observers = new ArrayList<>();

    public void setTargetDir(String dirPath) {
    	this.targetDir = Paths.get(dirPath);
    }
    
    public void setTargetFormats(String... formats) {
    	this.formats = formats;
    }
    
    public void addObserver(FileObserver observer) {
    	this.observers.add(observer);
    }
    
    public void setObserver(FileObserver observer) {
    	List<FileObserver> observerList = new ArrayList<>();
    	observerList.add(observer);
    	this.observers = observerList;
    }

    public void startWatching() {
    	try {
    		this.watchService = FileSystems.getDefault().newWatchService();
            this.targetDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            this.isMonitoring = true;
            
            this.executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
            	// processing existing files
                List<Path> existingFilePaths = scanExistingFiles();
                for (Path fileName : existingFilePaths) {
                	if (!isMonitoring) {
                		break;
                	}
                	notifyObservers(fileName);
                }
            	
                // watching newly added files
            	while (isMonitoring) {
            		try {
                        // 获取下一个文件变化事件
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            WatchEvent.Kind<?> kind = event.kind();
                            if (kind == StandardWatchEventKinds.OVERFLOW) {
                                continue;
                            }
                            
                            // 获取文件名
                            Path fileName = (Path) event.context();
                            if (isTargetFormat(fileName)) {
                            	notifyObservers(Paths.get(targetDir + File.separator + fileName.getFileName().toString()));
                            }
                        }
                        // 重置 WatchKey
                        boolean valid = key.reset();
                        if (!valid) {
                            break; // 目录不可访问时退出
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
            	}
            });
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void stopWatching() {
    	isMonitoring = false;
    	try {
    		if (watchService != null) {
        		watchService.close();
        	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	if (executorService != null) {
    		executorService.shutdown(); // 关闭线程池
    	}
    }

    /**
     * 扫描存量文件
     * 
     * @return
     */
    private List<Path> scanExistingFiles() {
    	List<Path> filePathList = new ArrayList<>();
    	File file = targetDir.toFile();
    	if (file.isDirectory()) {
    		File[] files = file.listFiles();
    		
    		// 按照文件的修改时间排序
    		Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					return (Long.valueOf(o1.lastModified()).compareTo(o2.lastModified()));
				}
			});
    		
    		for (File f : files) {
    			Path path = f.toPath();
    			if (isTargetFormat(path)) {
    				filePathList.add(path);
    			}
    		}
    	}
    	return filePathList;
    }
    
    private boolean isTargetFormat(Path filePath) {
    	if (formats == null || formats.length == 0) {
    		return true;
    	}
    	for (String format : formats) {
    		if (filePath.getFileName().toString().endsWith(format)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private void notifyObservers(Path filePath) {
    	for (FileObserver observer : observers) {
    		observer.onNewFile(filePath);
    	}
    }
}
