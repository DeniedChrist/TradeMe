package crypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import crypto.service.manager.TransactionManager;

@Configuration
public class TransactionManagerProvider {
	
	@Bean
	public TransactionManager getTransactionManager() {
		System.out.println("In TransactionManager Config");
		return new TransactionManager();
	}
}
