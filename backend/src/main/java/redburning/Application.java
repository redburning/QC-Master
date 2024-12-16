package redburning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages={"redburning"})
public class Application {

	private static final String QC_MASTER_HOME = "QC_Master.home";
	
	private static String home;
	
	public static String home() {
		return home;
	}
	
	public static void main(String[] args) {
		String home = System.getProperty(QC_MASTER_HOME);
		if (home == null) {
			log.info("{0} not pre-defined, there's going to use the <user.dir> path as the home path.", QC_MASTER_HOME);
			home = System.getProperty("user.dir");
			System.setProperty(QC_MASTER_HOME, home);
		}
		Application.home = home;
		SpringApplication.run(Application.class, args);
	}

}
