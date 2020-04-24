package crypto.service.manager;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import crypto.access.LogAccess;
import crypto.model.CryptoUser;
import crypto.model.Log;

@Service
public class LogManager {
	@Autowired
	private LogAccess logAcc;

	private ConcurrentHashMap<Integer, Log> logs;

	public LogManager() {
		super();
		System.out.println("In Log manager constr");
		
	}
	@EventListener(ApplicationReadyEvent.class)
	public void populateLogs(){
		System.out.println("Populating Logs");
		logs = logAcc.getAllLogs();
	}

	public Integer createLog(Log l) {
		logs.put(logAcc.addLog(l), l);
		return l.getId();
	}

	public Log getLog(Integer id) {
		return logs.get(id);
	}

	public Vector<Log> getUserLogs(CryptoUser userr) {
		return logAcc.getLogs(userr);
	}

}
