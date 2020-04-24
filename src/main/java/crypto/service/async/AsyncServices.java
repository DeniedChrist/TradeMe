package crypto.service.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import crypto.service.engine.TradeEngine;
import crypto.service.engine.TransactionEngine;

@Service
public class AsyncServices {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@EventListener(ApplicationReadyEvent.class)
	public void executeAsync() {
		System.out.println("In async execution");
		TradeEngine tr = applicationContext.getBean(TradeEngine.class);
		TransactionEngine te= applicationContext.getBean(TransactionEngine.class);
		taskExecutor.execute(tr);
		taskExecutor.execute(te);
	}
}
