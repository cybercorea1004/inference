package kr.co.qwave.inference;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class QwaveInferenceApplication {
	private static final Logger logger = LoggerFactory.getLogger(QwaveInferenceApplication.class);

	private static final String CONSOLE_LOG_PATTERN = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} "
			+ "%clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} "
			+ "%clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} "
			+ "%clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}";

	private static final String FILE_LOG_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} "
			+ "${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}";
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		Path currentRelativePath = Paths.get("");
		String current_path = currentRelativePath.toAbsolutePath().toString();
		String OS = System.getProperty("os.name").toLowerCase();
		System.getProperties().put( "data_url", current_path );
		System.getProperties().put( "server.port", port );
		
		System.getProperties().put( "spring.datasource.driver-class-name", "com.mysql.jdbc.Driver" );
		System.getProperties().put( "spring.datasource.url", "jdbc:mysql://ai.qwave.co.kr:13306/dunamis?autoReconnect=true" );
		System.getProperties().put( "spring.datasource.username", "zester" );
		System.getProperties().put( "spring.datasource.password", "zester" );

		SpringApplication.run(QwaveInferenceApplication.class, args);
	}
	
	
}
