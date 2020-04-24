package crypto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "transact")
public class Transact {

	private Integer id;
	private Date timestampp;
	private double rate;
	private double quantity;
	private int attempt;

	private Order buyOrder;
	private Order sellOrder;

	public Transact() {
		super();
	}

	public Transact(Date timestampp, double rate, double quantity, Order buyOrder, Order sellOrder) {
		super();
		this.timestampp = timestampp;
		this.rate = rate;
		this.quantity = quantity;
		this.attempt = 1;
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
	}

	public Transact(Date timestampp, double rate, double quantity, int attempt, Order buyOrder, Order sellOrder) {
		super();
		this.timestampp = timestampp;
		this.rate = rate;
		this.quantity = quantity;
		this.attempt = attempt;
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
	}

	public Transact(Integer id, Date timestampp, double rate, double quantity, int attempt, Order buyOrder,
			Order sellOrder) {
		super();
		this.id = id;
		this.timestampp = timestampp;
		this.rate = rate;
		this.quantity = quantity;
		this.attempt = attempt;
		this.buyOrder = buyOrder;
		this.sellOrder = sellOrder;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transact_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "timestampp")
	public Date getTimestampp() {
		return timestampp;
	}

	public void setTimestampp(Date timestampp) {
		this.timestampp = timestampp;
	}

	@Column(name = "rate")
	public double getRate() {
		return rate;
	}

	

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Column(name = "quantity")
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Column(name = "attempt")
	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	@ManyToOne
	@JoinColumn(name = "buy_order_id", referencedColumnName="order_id")
	public Order getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(Order buyOrder) {
		this.buyOrder = buyOrder;
	}

	@ManyToOne
	@JoinColumn(name = "sell_order_id", referencedColumnName="order_id")
	public Order getSellOrder() {
		return sellOrder;
	}

	public void setSellOrder(Order sellOrder) {
		this.sellOrder = sellOrder;
	}

	@Override
	public String toString() {
		return "Transact [id=" + id + ", timestampp=" + timestampp + ", rate=" + rate + ", quantity=" + quantity
				+ ", attempt=" + attempt + ", buyOrder=" + buyOrder.getId() + ", sellOrder=" + sellOrder.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Transact other = (Transact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Transient
	public boolean isAttemptAtLimit() {
		return attempt <= 5;
	}

	public void newAttempt() throws Exception {
		if (attempt >= 5)
			throw new Exception();
		attempt++;
	}
}
