package crypto.model;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import crypto.value.CurrencyType;



@Entity
@Table(name = "currency")
public class Currency implements Comparable<Currency> {

	private CurrencyType typee;
	private double rate;

	private List<Order> buyOrders=new Vector<>();
	private List<Order> sellOrders=new Vector<>();
	private List<Ledger> ledgers=new Vector<>();
	

	public Currency() {
		super();
	}

	public Currency(CurrencyType typee) {
		super();
		this.typee = typee;
	}

	public Currency(CurrencyType typee, double rate) {
		super();
		this.typee = typee;
		this.rate = rate;
	}

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "currency_type")
	public CurrencyType getTypee() {
		return typee;
	}

	public void setTypee(CurrencyType typee) {
		this.typee = typee;
	}
	
	
	
	
	
	

	@Column(name = "rate")
	public double getRate() {
		return rate;
	}

	

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Transient
	public List<Order> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(List<Order> buyOrders) {
		this.buyOrders = buyOrders;
	}

	@Transient
	public List<Order> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(List<Order> sellOrders) {
		this.sellOrders = sellOrders;
	}

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	public List<Ledger> getLedgers() {
		return ledgers;
	}

	public void setLedgers(List<Ledger> ledgers) {
		this.ledgers = ledgers;
	}

	@Override
	public String toString() {
		return "Currency [typee=" + typee + ", rate=" + rate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typee == null) ? 0 : typee.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (typee != other.typee)
			return false;
		return true;
	}

	public void addBuyOrder(Order order) {
		buyOrders.add(order);
	}

	public void removeBuyOrder(Order order) {
		buyOrders.remove(order);
	}

	public void addSellOrder(Order order) {
		sellOrders.add(order);
	}

	public void removeSellOrder(Order order) {
		sellOrders.remove(order);
	}

	public void addLedger(Ledger ledger) {
		ledgers.add(ledger);
	}

	@Override
	public int compareTo(Currency currency) {
		return this.typee.compareTo(currency.getTypee());
	}
}
