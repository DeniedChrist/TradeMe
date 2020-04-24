package crypto.access;

import java.util.Vector;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.model.Ledger;
import crypto.config.SessionConfig;
import crypto.model.CryptoUser;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class TransactionAccess {

	@Autowired
	private SessionConfig sf;

	public TransactionAccess() {
		super();
		System.out.println("In Transactional access");
	}

	public Vector<Ledger> getUserLedgers(CryptoUser u) {
		String jpql = "select l from Ledger l where l.buyWallet.user=:u or l.sellWallet.user=:u";
		return new Vector<>(
				sf.getNewSession().createQuery(jpql, Ledger.class).setParameter("u", u).getResultList());
	}

}
