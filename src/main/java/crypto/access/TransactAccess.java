package crypto.access;

import java.util.Vector;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.Transact;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class TransactAccess {

	@Autowired
	private SessionConfig sf;

	public TransactAccess() {
		super();
		System.out.println("In Transact access");
	}

	public Vector<Transact> getAllTransacts() {
		String jpql = "select t from Transact t";
		return new Vector<>(sf.getNewSession().createQuery(jpql, Transact.class).getResultList());
	}

	public Integer addTransact(Transact transact) {
		return (Integer) sf.getNewSession().save(transact);
	}

	public void addOrUpdateTransact(Transact transact) {
		sf.getNewSession().saveOrUpdate(transact);
	}

	public void removeTransact(Transact transact) {
		sf.getNewSession().remove(transact);
	}

	public void updateTransact(Transact transact) {
		sf.getNewSession().update(transact);
	}

}
