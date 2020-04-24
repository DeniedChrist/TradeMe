package crypto.access;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.CryptoUser;
import crypto.model.Log;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class LogAccess {

	@Autowired
	private SessionConfig sf;

	public LogAccess() {
		super();
		System.out.println("In Log access");
	}

	public ConcurrentHashMap<Integer, Log> getAllLogs() {
		String jpql = "select l from Log l";
		ConcurrentHashMap<Integer, Log> logs = new ConcurrentHashMap<>();
		sf.getNewSession().createQuery(jpql, Log.class).getResultList().forEach(l -> logs.put(l.getId(), l));
		return logs;
	}

	public Integer addLog(Log log) {
		return (Integer) sf.getNewSession().save(log);
	}

	public Vector<Log> getLogs(CryptoUser userr) {
		String jpql = "select l from Log l where l.userr=:u";
		return new Vector<>(
				sf.getNewSession().createQuery(jpql, Log.class).setParameter("u", userr).getResultList());
	}

}
