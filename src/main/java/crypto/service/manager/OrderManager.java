package crypto.service.manager;

import java.util.EnumSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import crypto.access.OrderAccess;
import crypto.model.CurrencyTrade;
import crypto.model.Order;
import crypto.value.CurrencyType;
import crypto.value.Trade;

@Service
public class OrderManager {

	

	@Autowired
	private OrderAccess orderAcc;
	@Autowired
	private CurrencyManager currMgr;
	@Autowired
	private WalletManager walletMgr;
	
	private ConcurrentHashMap<CurrencyTrade, PriorityBlockingQueue<Order>> orders;
	private boolean isQueueReady=false;
	public boolean isQueueReady() {
		return isQueueReady;
	}
	public void setQueueReady(boolean isQueueReady) {
		this.isQueueReady = isQueueReady;
	}
	
	public OrderManager() {
		super();
		System.out.println("In Order Manager!");
		
	}
	@EventListener(ApplicationReadyEvent.class)
	public void populateOrders() {
		
		//System.out.println("Populating Orders");
		orders = new ConcurrentHashMap<>();
		EnumSet.allOf(CurrencyType.class).forEach(cType -> {
			//System.out.println(cType);
			orders.put(new CurrencyTrade(cType, Trade.BUY),
					orderAcc.getOrdersFor(currMgr.getCurrency(cType), Trade.BUY));
			//System.out.println("buy to sell");
			orders.put(new CurrencyTrade(cType, Trade.SELL),
					orderAcc.getOrdersFor(currMgr.getCurrency(cType), Trade.SELL));
			//System.out.println("after sell");
			//System.out.println("saved orders" + orders);
			
		});
		isQueueReady=true;
	}
	public Vector<Order> getNewOrders() {
		return orderAcc.getAllOrders();
	}

	public Order getNextOrder(CurrencyType cType, Trade trade) {
		orderAcc.removeOrder(orders.get(new CurrencyTrade(cType, trade)).peek());
		return orders.get(new CurrencyTrade(cType, trade)).poll();
	}

	public void revert(Order order, Double rate, Double quantity) {
		/*
		 * 
		 * REVERT (RESTORE CURRENCY BACK IN WALLET/TAKE IT BACK) PARTIALLY PROCESSED
		 * TRANSACTIONS
		 * 
		 */
	}

	public void cancleOrder(Order order) throws Exception {
		// tradeEngine.CancleOrder(order);
		try {
			walletMgr.unmarkFromOrder(order.getUserr().getWallet(order.getCurrency().getTypee()), order);
			orders.get(new CurrencyTrade(order.getCurrency().getTypee(), order.getTypee())).remove(order);
			orderAcc.removeOrder(order);
			orderAcc.removeOrder(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addNewOrder(Order order) throws Exception {
		orders.get(new CurrencyTrade(order.getCurrency().getTypee(), order.getTypee())).add(order);
		orderAcc.addOrder(order);
		if (order.getTypee() == Trade.SELL) {
			walletMgr.markForOrder(order.getUserr().getWallet(order.getCurrency().getTypee()), order);
		} else if (order.getTypee() == Trade.BUY) {
			walletMgr.markForOrder(order.getUserr().getWallet(CurrencyType.CONTRACT), order);
		}
	}

	public Vector<Order> peakTopN(CurrencyType cType, Trade trade, int n) {
		Vector<Order> nOrders = new Vector<>();
		for (int i = 0; i < n; i++) {
			nOrders.add(orders.get(new CurrencyTrade(cType, trade)).poll());
		}
		for (int i = 0; i < n; i++) {
			orders.get(new CurrencyTrade(cType, trade)).add(nOrders.get(i));
		}

		return nOrders;
	}

	public Order getOrder(CurrencyType cType, Trade trade) {
		//System.out.println("trying to get order");
		//System.out.println("my order map: "+ orders);
		PriorityBlockingQueue<Order> q = orders.get(new CurrencyTrade(cType, trade));
		//System.out.println("order queue " + q);
		Order o=q.peek();
		//System.out.println("My order "+o);
		return o;
	}

	public void partialTransaction(Order order, Double quantity) throws Exception {
		if (quantity >= order.getQuantity()) {
			throw new Exception();
		}
		order.setQuantity(order.getQuantity() - quantity);
	}

}
