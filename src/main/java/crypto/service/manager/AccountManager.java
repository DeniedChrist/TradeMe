package crypto.service.manager;

import java.util.Date;
import java.util.Vector;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crypto.access.AccountAccess;
import crypto.model.Account;
import crypto.model.Log;
import crypto.model.CryptoUser;
import crypto.value.CurrencyType;
import crypto.value.LogType;
import crypto.value.TransactionStatus;


@Service
public class AccountManager {

	@Autowired
	private AccountAccess accAcc;
	@Autowired
	private WalletManager walletMgr;
	
	@Autowired
	private LogManager logMgr;
	@Autowired
	private CurrencyManager currMgr;

	public AccountManager() {
		super();
		System.out.println("In account manager constr");
		// TODO Auto-generated constructor stub
	}

	public void pay(Account account, Double amount) {
		Double contractRate = currMgr.getCurrency(CurrencyType.CONTRACT).getRate();
		Log log = new Log(contractRate, amount, TransactionStatus.SUCCESS, LogType.PURCHESS, new Date(),
				currMgr.getCurrency(CurrencyType.CONTRACT), account.getUserr());
		logMgr.createLog(log);
		try {
			walletMgr.addContracts(account.getUserr().getWallet(CurrencyType.CONTRACT), log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void withdraw(Account account, Double amount) {
		Double contractRate = currMgr.getCurrency(CurrencyType.CONTRACT).getRate();
		Log log = new Log(contractRate, amount, TransactionStatus.SUCCESS, LogType.WITHDRAW, new Date(),
				currMgr.getCurrency(CurrencyType.CONTRACT), account.getUserr());
		logMgr.createLog(log);
		try {
			walletMgr.withdwawContracts(account.getUserr().getWallet(CurrencyType.CONTRACT), log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean addAcccount(CryptoUser userr, Account account) {
		if (!verifyAccount(account)) {
			return false;
		}
		try {
			accAcc.addAccount(account);
			userr.addAccount(account);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void removeAccount(CryptoUser userr, Integer accId) {
		accAcc.removeAccount(userr.getAccounts().get(accId));
	}

	public boolean verifyAccount(Account account) {
		return true;
	}

	public Vector<Account> getUserAccounts(CryptoUser userr) {
		return new Vector<>(userr.getAccounts().values());
	}

}
