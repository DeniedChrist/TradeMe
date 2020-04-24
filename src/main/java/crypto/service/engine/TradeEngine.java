package crypto.service.engine;

import java.util.Date;
import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import crypto.model.Order;
import crypto.model.Transact;
import crypto.service.manager.OrderManager;
import crypto.service.manager.TransactionManager;
import crypto.value.CurrencyType;
import crypto.value.Trade;

@Component
public class TradeEngine implements Runnable {
	@Autowired
	private OrderManager orderMgr;
	@Autowired
	private TransactionManager xactMgr;
	// map of all currencies supported by application (each currency holds maximum
	// buy rate and minimum sell rate unprocessed)
	// private HashMap<CurrencyType, Currency> currencies;
	// queue of orders placed by customers
	// private LinkedList<Order> orderQ;
	
	public TradeEngine() {
		System.out.println("In Trade Engine");
		// currencies = currMgr.getTradeCurrencies();
		// orderQ = orderMgr.getNewOrders();
	}

	public void run() { // a separate thread that constantly accepts orders placed by customers and
						// process it
		/*
		 * checks if new order placed has higher rate to buy than previous maximum or
		 * lower rate to sell than previous minimum, if there is, then check if any
		 * transaction can be processed
		 */
		while(!orderMgr.isQueueReady()) {}
		while (true) {
			//System.out.println("trying to trade");
			EnumSet.allOf(CurrencyType.class).forEach(cType -> {
			//	System.out.println("in trade engine "+cType);
				Order buyOrder = orderMgr.getOrder(cType, Trade.BUY);
			//	System.out.println("received orders " + buyOrder);
				Order sellOrder = orderMgr.getOrder(cType, Trade.SELL);
			//	System.out.println("received orders " +sellOrder);
				if(buyOrder != null && sellOrder != null) {
					if (buyOrder.getRate() == sellOrder.getRate()) {
						process(cType); // can be processed
					}
				}
			});
		}
	}

	/*
	 * if a currency has match of buy and sell rate then add them to
	 * TransactionEngine queue to be processed and update maximum buy rate and
	 * minimum sell rate cases may arrive that not all quantity is sold or bought
	 * then order still remains in list for buy/sell but with less quantity on
	 * demand
	 */
	void process(CurrencyType cType) {
		try {
			Order buyOrder = orderMgr.getOrder(cType, Trade.BUY);
			Order sellOrder = orderMgr.getOrder(cType, Trade.SELL);
			Double quantity;
			if (buyOrder.getQuantity() < sellOrder.getQuantity()) {
				quantity = buyOrder.getQuantity();
				xactMgr.addNewTransaction(new Transact(new Date(), buyOrder.getRate(), quantity, buyOrder, sellOrder));
				orderMgr.getNextOrder(cType, Trade.BUY);
				orderMgr.partialTransaction(sellOrder, quantity);
			} else if (buyOrder.getQuantity() > sellOrder.getQuantity()) {
				quantity = sellOrder.getQuantity();
				xactMgr.addNewTransaction(new Transact(new Date(), buyOrder.getRate(), quantity, buyOrder, sellOrder));
				orderMgr.partialTransaction(buyOrder, quantity);
				orderMgr.getNextOrder(cType, Trade.SELL);
			} else {
				quantity = buyOrder.getQuantity();
				xactMgr.addNewTransaction(new Transact(new Date(), buyOrder.getRate(), quantity, buyOrder, sellOrder));
				orderMgr.getNextOrder(cType, Trade.BUY);
				orderMgr.getNextOrder(cType, Trade.SELL);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// Add newly generated orders to order queue
	/*
	 * void addOrder(Order o) { orderQ.addLast(o); }
	 * 
	 * void CancleOrder(Order o) { orderQ.remove(o); }
	 */
}
