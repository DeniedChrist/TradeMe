package crypto.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crypto.access.MarkerAccess;
import crypto.access.WalletAccess;
import crypto.model.Log;
import crypto.model.Marker;
import crypto.model.Order;
import crypto.model.Transact;
import crypto.model.Wallet;
import crypto.value.CurrencyType;
import crypto.value.LogType;
import crypto.value.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletManager {

	@Autowired
	private WalletAccess walletAcc;
	@Autowired
	private AddressManager addrMgr;
	@Autowired
	private MarkerAccess markerAcc;

	public WalletManager() {
		super();
		System.out.println("In Wallet Manager");
		// TODO Auto-generated constructor stub
	}

	public String prepare(Wallet wallet, Double rate, Double quantity) throws Exception {
		String address = wallet.prepareAddress(rate, quantity);
		Marker marker = wallet.getMarker(-rate);
		if (marker == null) {
			marker = new Marker(-rate, 0, wallet);
			markerAcc.addMarker(marker);
			wallet.addMarker(marker);
		}
		marker.setQuantity(marker.getQuantity() + quantity);
		markerAcc.updateMarker(marker);
		marker = wallet.getMarker(rate);
		marker.setQuantity(marker.getQuantity() - quantity);
		markerAcc.updateMarker(marker);
		return address;
	}

	public void add(Wallet wallet, String address, Double rate, Double quantity) throws Exception {
		wallet.addAddress(address);
		Wallet cWallet = wallet.getUserr().getWallet(CurrencyType.CONTRACT);
		Marker marker = cWallet.getMarker(-rate);
		if (marker == null) {
			marker = new Marker(-rate, 0, cWallet);
			markerAcc.addMarker(marker);
			wallet.addMarker(marker);
		}
		marker.setQuantity(marker.getQuantity() + quantity);
		markerAcc.updateMarker(marker);
		marker = cWallet.getMarker(rate);
		marker.setQuantity(marker.getQuantity() - quantity);
		markerAcc.updateMarker(marker);
		addrMgr.serveAddress(address, wallet, rate, quantity);
	}

	public void addContracts(Wallet wallet, Log log) throws Exception {
		if (!log.getUserr().equals(wallet.getUserr())) {
			throw new Exception();
		}
		if (wallet.getCurrency().getTypee() != CurrencyType.CONTRACT) {
			throw new Exception();
		}
		if (log.getStatuss() != TransactionStatus.SUCCESS) {
			throw new Exception();
		}
		if (log.getTypee() != LogType.SELL && log.getTypee() != LogType.PURCHESS) {
			throw new Exception();
		}
		wallet.add(log.getQuantity());
		Marker marker = wallet.getMarker(0.0);
		markerAcc.updateMarker(marker);
		walletAcc.updateWallet(wallet);
	}

	public void withdwawContracts(Wallet wallet, Log log) throws Exception {
		if (!log.getUserr().equals(wallet.getUserr())) {
			throw new Exception();
		}
		if (wallet.getCurrency().getTypee() != CurrencyType.CONTRACT) {
			throw new Exception();
		}
		if (log.getStatuss() != TransactionStatus.SUCCESS) {
			throw new Exception();
		}
		if (log.getTypee() != LogType.BUY && log.getTypee() != LogType.WITHDRAW) {
			throw new Exception();
		}
		wallet.withdraw(log.getRate(), log.getQuantity());
		Marker marker = wallet.getMarker(log.getRate());
		markerAcc.updateMarker(marker);
		walletAcc.updateWallet(wallet);
	}

	public void markForOrder(Wallet wallet, Order order) throws Exception {
//		walletAcc.mark(wallet, order.getRate(), order.getQuantity());
		Marker marker = wallet.getMarker(order.getRate());
		if (marker == null) {
			marker = new Marker(order.getRate(), order.getQuantity(), wallet);
			markerAcc.addMarker(marker);
			wallet.addMarker(marker);
		}
		Marker markerC = wallet.getMarker(0.0);
		if (markerC.getQuantity() < order.getQuantity()) {
			throw new Exception();
		}
		markerC.setQuantity(markerC.getQuantity() - order.getQuantity());
		marker.setQuantity(marker.getQuantity() + order.getQuantity());
		markerAcc.updateMarker(markerC);
		markerAcc.updateMarker(marker);
	}

	public void unmarkFromOrder(Wallet wallet, Order order) throws Exception {
		Marker marker = wallet.getMarker(0.0);
		if (marker == null) {
			marker = new Marker(0.0, 0, wallet);
			markerAcc.addMarker(marker);
			wallet.addMarker(marker);
		}
		Marker markerC = wallet.getMarker(order.getRate());
		if (markerC.getQuantity() < order.getQuantity()) {
			throw new Exception();
		}
		markerC.setQuantity(markerC.getQuantity() - order.getQuantity());
		marker.setQuantity(marker.getQuantity() + order.getQuantity());
		markerAcc.updateMarker(markerC);
		markerAcc.updateMarker(marker);
	}

	public void revertTransactionMarking(Wallet wallet, Transact t) throws Exception {
		Marker marker = wallet.getMarker(t.getRate());
		if (marker == null) {
			marker = new Marker(t.getRate(), 0, wallet);
			markerAcc.addMarker(marker);
			wallet.addMarker(marker);
		}
		Marker markerC = wallet.getMarker(-t.getRate());
		if (markerC.getQuantity() < t.getQuantity()) {
			throw new Exception();
		}
		markerC.setQuantity(markerC.getQuantity() - t.getQuantity());
		marker.setQuantity(marker.getQuantity() + t.getQuantity());
		markerAcc.updateMarker(markerC);
		markerAcc.updateMarker(marker);
	}

}
