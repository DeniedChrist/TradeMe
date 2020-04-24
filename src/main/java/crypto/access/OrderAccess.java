package crypto.access;

import java.util.Vector;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crypto.config.SessionConfig;
import crypto.model.CryptoUser;
import crypto.model.Currency;
import crypto.model.Order;
import crypto.model.comparator.CompareOrderByRate;
import crypto.value.Trade;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class OrderAccess {

	@Autowired
	private SessionConfig sf;

	public OrderAccess() {
		super();
		System.out.println("In Order Access ");
	}

	public PriorityBlockingQueue<Order> getOrdersFor(Currency currency, Trade trade) {
		//System.out.println(currency +" "+trade);
		String jpql = "select o from Order o where currency=:curr and typee=:trade";
		PriorityBlockingQueue<Order> queue;
		try {
			queue= new PriorityBlockingQueue<Order>(1, new CompareOrderByRate());
			queue.addAll(sf.getNewSession().createQuery(jpql, Order.class).setParameter("curr", currency)
					.setParameter("trade", trade).getResultList());
		} catch (Exception e) {
			queue = new PriorityBlockingQueue<>();
			//System.out.println(e.getMessage());
		}
		//System.out.println(currency.getTypee() + " " + trade + " " + queue);
		return queue;
	}

	public Vector<Order> getAllOrders() {
		return null;
	}

	public Integer addOrder(Order order) {
		return (Integer) sf.getNewSession().save(order);
	}

	public void removeOrder(Order order) {
		sf.getNewSession().remove(order);
	}

	public Vector<Order> getUserOrders(CryptoUser u) {
		String jpql = "select o from Order o where o.userr=:u";
		return new Vector<>(sf.getNewSession().createQuery(jpql, Order.class).setParameter("u", u).getResultList());
	}

}
