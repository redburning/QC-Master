package redburning;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages={"redburning"})
public class Application implements ApplicationListener<ServletWebServerInitializedEvent> {

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

	@Override
	public void onApplicationEvent(ServletWebServerInitializedEvent event) {
		int port = event.getWebServer().getPort();
        String url = "http://127.0.0.1:" + port;
        try {
			openBrowse(url);
		} catch (IOException e) {
			log.warn("can not open browser", e);
			e.printStackTrace();
		}
	}
	
	private static void openBrowse(String url) throws IOException {
		String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows 系统
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (os.contains("mac")) {
            // Mac OS 系统
            Runtime.getRuntime().exec("open " + url);
        } else if (os.contains("nix") || os.contains("nux")) {
            // Linux 系统
            Runtime.getRuntime().exec("xdg-open " + url);
        }
    }
}
