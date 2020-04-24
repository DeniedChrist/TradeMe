package crypto.access;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.CryptoUser;
import crypto.model.Wallet;
import crypto.value.CurrencyType;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class WalletAccess {

	@Autowired
	private SessionConfig sf;

	public Vector<Wallet> getAllWallets(CryptoUser u) {
		String jpql = "select w from Wallet w where w.userr=:u";

		return new Vector<>(
				sf.getNewSession().createQuery(jpql, Wallet.class).setParameter("u", u).getResultList());
	}

	public WalletAccess() {
		super();
		System.out.println("In Wallet access");
	}

	public void addWallets(LinkedList<Wallet> wallets) {
		wallets.forEach(wallet -> sf.getNewSession().save(wallet));
	}

	void removeWallets(LinkedList<Wallet> wallets) {
		wallets.forEach(wallet -> sf.getNewSession().remove(wallet));
	}

	public void updateWallet(Wallet wallet) {
		sf.getNewSession().update(wallet);
	}

	public ConcurrentHashMap<CurrencyType, Wallet> getUserWallets(CryptoUser u) {
		String jpql = "select w from Wallet w where w.userr=:u";
		ConcurrentHashMap<CurrencyType, Wallet> wallets = new ConcurrentHashMap<>();
		sf.getNewSession().createQuery(jpql, Wallet.class).setParameter("u", u).getResultList()
				.forEach(w -> wallets.put(w.getCurrency().getTypee(), w));
		return wallets;
	}

}
