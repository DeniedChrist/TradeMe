package crypto.service.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crypto.model.Exchange;
import crypto.model.Wallet;
import crypto.service.engine.TransactionEngine;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressManager {

	@Autowired
	private TransactionEngine xactEngine;
	ConcurrentHashMap<String, Exchange> addresses;

	public AddressManager() {
		System.out.println("In adress manager constr");
		addresses = new ConcurrentHashMap<>();
	}

	public void addAddress(String address, Wallet wallet, Double rate, Double quantity) {
		addresses.put(address, new Exchange(address, rate, quantity, null, wallet));
	}

	public void serveAddress(String address, Wallet wallet, Double rate, Double quantity) throws Exception {
		Exchange ex = addresses.get(address);
		if (ex == null) {
			throw new Exception();
		}
		if (ex.getRate() != rate) {
			throw new Exception();
		}
		if (ex.getQuantity() != quantity) {
			throw new Exception();
		}
		ex.setBuyerWallet(wallet);
		try {
			xactEngine.exchange(ex);
			addresses.remove(address);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
		}
	}

}
