package crypto.access;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import crypto.config.SessionConfig;
import crypto.model.Account;
import crypto.model.CryptoUser;


@Transactional
@Repository
public class AccountAccess {
	
	@Autowired
	private SessionConfig sf;

	public AccountAccess() {
		super();
		System.out.println("In account access");
	}

	public ConcurrentHashMap<Integer, Account> getUserAccounts(CryptoUser u) {
		String jpql = "select a from Account a where a.userr=:u";
		ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();
		sf.getNewSession().createQuery(jpql, Account.class).setParameter("u", u).getResultList()
				.forEach(a -> accounts.put(a.getId(), a));
		return accounts;
	}

	public Integer addAccount(Account account) {
		return (Integer) sf.getNewSession().save(account);
	}

	public void removeAccount(Account account) {
		sf.getNewSession().remove(account);
	}

}
