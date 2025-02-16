package redburning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import redburning.service.ProjectService;

@Component
public class CustomApplicationRunner implements ApplicationRunner {

	@Autowired
	private ProjectService projectService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		projectService.updateRunningProjectsToStopped();
	}

}
