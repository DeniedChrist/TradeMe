package crypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class EngineInitializer {

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		System.out.println("In Thread Initialzer Config!");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("MyThread");
        executor.initialize();
        return executor;
    }
	
}