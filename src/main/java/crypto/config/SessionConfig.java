package crypto.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Configuration

public class SessionConfig {

	
	public SessionConfig() {
		super();
		
		System.out.println("In Session config");
	}
	
	
	@Autowired
	private EntityManager em;
	
	@Transactional
	public Session getNewSession() {
		
		em= em.getEntityManagerFactory().createEntityManager();
		System.out.println("Returning session");
		return em.unwrap(Session.class);
	}
}
