package crypto.service.manager;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crypto.access.AccountAccess;
import crypto.access.OrderAccess;
import crypto.access.TransactionAccess;
import crypto.access.UserAccess;
import crypto.access.WalletAccess;
import crypto.model.Credential;
import crypto.model.CryptoUser;
import crypto.model.Wallet;
import crypto.value.CurrencyType;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManager {

	@Autowired
	private UserAccess userAcc;
	@Autowired
	private WalletAccess walletAcc;
	@Autowired
	private AccountAccess accAcc;
	@Autowired
	private TransactionAccess xactionAcc;
	@Autowired
	private OrderAccess orderAcc;
	@Autowired
	private CurrencyManager currMgr;

	public UserManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CryptoUser addNewUser(CryptoUser u, Credential c) throws Exception {
		if (!userAcc.isUsernameAvailable(c.getUsername())) {
			throw new Exception();
		}
		userAcc.addUser(u, c);
		EnumSet.allOf(CurrencyType.class).forEach(cType -> {
			ConcurrentHashMap<CurrencyType, Wallet> wallets = new ConcurrentHashMap<>();
			wallets.put(cType, new Wallet(0.0, currMgr.getCurrency(cType), u));
			u.setWallets(wallets);
		});
		return u;
	}
	public Credential addNewUser(Credential c) throws Exception {
		if (!userAcc.isUsernameAvailable(c.getUsername())) {
			throw new Exception();
		}
		userAcc.addUser(c);
		
		return c;
	}
	public void removeUser(Credential c) throws Exception {
		if (!userAcc.validateIdentity(c)) {
			throw new Exception();
		}
		userAcc.removeUser(c);
	}

	public void updatePassword(Credential c, String pass) throws Exception {
		
		
		userAcc.updateCredential(c,new Credential(c.getUsername(),pass));
	}

	public CryptoUser getUser(Integer id) throws Exception {
		CryptoUser u = userAcc.getUser(id);
		if (u == null) {
			throw new Exception();
		}
		return u;
	}

	public CryptoUser getUser(String username) throws Exception {
		CryptoUser u = userAcc.getUser(username);
		if (u == null) {
			throw new Exception();
		}
		return u;
	}

	public CryptoUser fillUserWallets(CryptoUser u) throws Exception {
		if (!userAcc.isValidUser(u)) {
			throw new Exception();
		}
		u.setWallets(walletAcc.getUserWallets(u));
		return u;
	}

	public CryptoUser fillUserAccounts(CryptoUser u) throws Exception {
		if (!userAcc.isValidUser(u)) {
			throw new Exception();
		}
		u.setAccounts(accAcc.getUserAccounts(u));
		return u;
	}

	public CryptoUser fillUserLedgers(CryptoUser u) throws Exception {
		if (!userAcc.isValidUser(u)) {
			throw new Exception();
		}
		u.setLedgers(xactionAcc.getUserLedgers(u));
		return u;
	}

	public CryptoUser fillUserOrders(CryptoUser u) throws Exception {
		if (!userAcc.isValidUser(u)) {
			throw new Exception();
		}
		u.setOrders(orderAcc.getUserOrders(u));
		return u;
	}
}
