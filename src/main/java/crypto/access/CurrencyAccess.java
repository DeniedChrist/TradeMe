package crypto.access;

import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.Currency;
import crypto.value.CurrencyType;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class CurrencyAccess {

	@Autowired
	private SessionConfig sf;

	public CurrencyAccess() {
		super();
		System.out.println("In currency access");
	}
	@Transactional
	public ConcurrentHashMap<CurrencyType, Currency> getCurrencyMap() {
		//System.out.println("In Currencies Access GetCurrencyMap");
		String jpql = "select c from Currency c";
		ConcurrentHashMap<CurrencyType, Currency> currencies = new ConcurrentHashMap<>();
		sf.getNewSession().createQuery(jpql, Currency.class).getResultList()
				.forEach(c -> currencies.put(c.getTypee(), c));
		return currencies;
	}

	public void setNewRate(CurrencyType cType, Double rate) {
		Currency c = sf.getNewSession().get(Currency.class, cType);
		c.setRate(rate);
		sf.getNewSession().update(c);
	}
}
