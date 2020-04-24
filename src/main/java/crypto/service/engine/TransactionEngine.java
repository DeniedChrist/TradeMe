package crypto.service.engine;

import java.util.Date;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import crypto.config.TransactVector;
import crypto.model.Exchange;
import crypto.model.Log;
import crypto.model.Order;
import crypto.model.Transact;
import crypto.model.Wallet;
import crypto.service.manager.CurrencyManager;
import crypto.service.manager.LogManager;
import crypto.service.manager.TransactionManager;
import crypto.service.manager.WalletManager;
import crypto.value.CurrencyType;
import crypto.value.LogType;
import crypto.value.TransactionStatus;


@Component
public class TransactionEngine implements Runnable {

	
	@Autowired
	private CurrencyManager currMgr;
	@Autowired
	private WalletManager walletMgr;
	@Autowired
	private LogManager logMgr;
	@Autowired
	private TransactionManager xactionMgr;
	@Autowired
	private TransactVector xactQ; // queue of transactions to be processed

	
	public TransactionEngine() {
		super();
		System.out.println("In Transaction Engine constr");
		
		//xactQ = xactionMgr.getPendingTransactions();
	}

	public void run() { // runs on a separate thread constantly looking for any transaction to be
						// processed and processes it
		while (true) {
			if (!xactQ.getVector().isEmpty()) {
				Transact t = xactQ.getVector().get(0);
				Order buyOrder = t.getBuyOrder();
				Order sellOrder = t.getSellOrder();
				Wallet buyWallet = buyOrder.getUserr().getWallet(t.getBuyOrder().getCurrency().getTypee());
				Wallet sellWallet = sellOrder.getUserr().getWallet(t.getSellOrder().getCurrency().getTypee());
				Double rate = t.getRate();
				Double quantity = t.getQuantity();
				/*
				 * PROCESS TRANSACTION
				 */
				try {
					/*
					 * throws Exception if user not placed sell order or rate mismatches
					 */
					String addr = walletMgr.prepare(sellWallet, rate, quantity);
					/*
					 * throws Exception if user not placed buy order rate mismatches or transaction
					 * fails
					 */
					walletMgr.add(buyWallet, addr, rate, quantity);
					xactionMgr.updateStatus(t, TransactionStatus.SUCCESS);
					/*
					 * LOG TRANSACTION
					 */
					walletMgr.withdwawContracts(buyWallet, buyOrder.getLastLog());
					walletMgr.addContracts(sellWallet, sellOrder.getLastLog());
					Log buyLog = new Log(t.getRate(), t.getQuantity(), TransactionStatus.SUCCESS, LogType.BUY,
							new Date(), currMgr.getCurrency(CurrencyType.CONTRACT), t.getBuyOrder().getUserr());
					Log sellLog = new Log(t.getRate(), t.getQuantity(), TransactionStatus.SUCCESS, LogType.SELL,
							new Date(), currMgr.getCurrency(CurrencyType.CONTRACT), t.getSellOrder().getUserr());
					logMgr.createLog(sellLog);
					logMgr.createLog(buyLog);
					t.getSellOrder().addLog(sellLog);
					t.getBuyOrder().addLog(buyLog);
				} catch (Exception e) {
					try {
						xactionMgr.updateStatus(t, TransactionStatus.FAILURE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				xactQ.getVector().remove(0);
			}

		}
	}

	// a method for TradeEngine to add data of transaction to be processed in queue
	/*public void addTransaction(Transact t) {
		xactQ.getVector().addElement(t);
	}*/

	public void exchange(Exchange ex) throws Exception {
		ex.getSellerWallet().withdraw(ex.getAddress());
		ex.getBuyerWallet().add(ex.getAddress());
	}

}
