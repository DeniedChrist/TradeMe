package crypto.model.comparator;

import java.util.Comparator;

import crypto.model.Order;


//Singleton
public class CompareOrderByRate implements Comparator<Order> {

	private static CompareOrderByRate instance;

	@Override
	public int compare(Order o1, Order o2) {
		return ((Double) o1.getRate()).compareTo(o2.getRate());
	}

	public static CompareOrderByRate getInstance() {
		if (instance == null)
			instance = new CompareOrderByRate();
		return instance;
	}
}
