package crypto.service.manager;

import java.util.Date;
import java.util.EnumSet;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import crypto.access.TransactAccess;
import crypto.config.TransactVector;
import crypto.model.CurrencyTrade;
import crypto.model.Log;
import crypto.model.Transact;
import crypto.service.engine.TransactionEngine;
import crypto.value.CurrencyType;
import crypto.value.LogType;
import crypto.value.Trade;
import crypto.value.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionManager {

	@Autowired
	private TransactAccess xactAcc;
	@Autowired
	private TransactVector xactQ;
	@Autowired
	private LogManager logMgr;
	@Autowired
	private WalletManager walletMgr;
	private Vector<Transact> pending;

	public TransactionManager() {
		super();
		System.out.println("In Transaction Manager constr");
	}
	@EventListener(ApplicationReadyEvent.class)
	public void populateTransacts() {
		System.out.println("Populating Transacts");
		pending = xactAcc.getAllTransacts();
	}
	public Vector<Transact> getPendingTransactions() {
		return pending;
	}

	public void addNewTransaction(Transact t) throws Exception {
		updateStatus(t, TransactionStatus.NEW);
	}

	public void updateStatus(Transact t, TransactionStatus status) throws Exception {
		if (t == null) {
			throw new Exception();
		}
		switch (status) {

		/*
		 * if not in queue add to queue
		 */
		case NEW:
			if (pending.contains(t)) {
				throw new Exception();
			}
			pending.add(t);
			xactAcc.addOrUpdateTransact(t);
			xactQ.getVector().addElement(t);
			break;

		case PENDING:
			//
			break;

		/*
		 * request new attempt, add to queue again
		 */
		case FAILURE:
			if (t.isAttemptAtLimit()) {
				CurrencyType cType = t.getBuyOrder().getCurrency().getTypee();
				walletMgr.revertTransactionMarking(t.getBuyOrder().getUserr().getWallet(cType), t);
				walletMgr.revertTransactionMarking(t.getSellOrder().getUserr().getWallet(cType), t);
				/*
				 * 
				 * alert admin and related users
				 * 
				 */
			} else {
				t.newAttempt();
				xactAcc.updateTransact(t);
				xactQ.getVector().addElement(t);
			}
			break;

		/*
		 * revert, remove transact from list
		 */
		case CANCELLED:
			// orderMgr.revert(t.getBuyOrder(), t.getRate(), t.getQuantity());
			// orderMgr.revert(t.getSellOrder(), t.getRate(), t.getQuantity());
			/*
			 * 
			 * alert related users
			 *
			 */
			xactQ.getVector().addElement(t);
			break;

		/*
		 * log, remove transact from list
		 */
		case SUCCESS:
			Log buyLog = new Log(t.getRate(), t.getQuantity(), status, LogType.BUY, new Date(),
					t.getBuyOrder().getCurrency(), t.getBuyOrder().getUserr());
			Log sellLog = new Log(t.getRate(), t.getQuantity(), status, LogType.SELL, new Date(),
					t.getSellOrder().getCurrency(), t.getSellOrder().getUserr());
			logMgr.createLog(sellLog);
			logMgr.createLog(buyLog);
			t.getSellOrder().addLog(sellLog);
			t.getBuyOrder().addLog(buyLog);
			/*
			 * alert user
			 */
			pending.remove(t);
			xactAcc.removeTransact(t);
			break;

		/*
		 * log, alert admin and related users lock wallets if instructed
		 */
		case UNAUTHORIZED:
			Log l = new Log(t.getRate(), t.getQuantity(), TransactionStatus.UNAUTHORIZED, LogType.SELL, new Date(),
					t.getSellOrder().getCurrency(), t.getSellOrder().getUserr());
			logMgr.createLog(l);
			t.getBuyOrder().addLog(l);
			l = new Log(t.getRate(), t.getQuantity(), TransactionStatus.UNAUTHORIZED, LogType.BUY, new Date(),
					t.getBuyOrder().getCurrency(), t.getBuyOrder().getUserr());
			logMgr.createLog(l);
			t.getSellOrder().addLog(l);
			/*
			 * alert breach freeze wallet
			 */
			break;
		}
	}

	public void rollbackTransaction(Transact t, boolean inOrder) {
		/*
		 * 
		 * Revert TRANSACTION add quantity back to both orders if wished revert any
		 * currency exchange taken place
		 * 
		 */
	}
}
