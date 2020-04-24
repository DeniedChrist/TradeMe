package crypto.model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import crypto.value.Trade;



@Entity
@Table(name = "orders")
public class Order {

	private Integer id;
	private double rate;
	private double quantity;
	private Trade typee;
	private Date timestampp;

	private Currency currency;
	private CryptoUser userr;

	private List<Log> logs;
	private List<Transact> transacts;

	public Order() {
		super();
	}

	public Order(double rate, double quantity, Trade typee, Date timestampp, Currency currency, CryptoUser userr) {
		super();
		this.rate = rate;
		this.quantity = quantity;
		this.typee = typee;
		this.timestampp = timestampp;
		this.currency = currency;
		this.userr = userr;
	}

	public Order(Integer id, double rate, double quantity, Trade typee, Date timestampp, Currency currency, CryptoUser userr) {
		super();
		this.id = id;
		this.rate = rate;
		this.quantity = quantity;
		this.typee = typee;
		this.timestampp = timestampp;
		this.currency = currency;
		this.userr = userr;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Enumerated(EnumType.STRING)
	@Column(name = "trade_type")
	public Trade getTypee() {
		return typee;
	}

	public void setTypee(Trade typee) {
		this.typee = typee;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "timestampp")
	public Date getTimestampp() {
		return timestampp;
	}

	public void setTimestampp(Date timestampp) {
		this.timestampp = timestampp;
	}

	@ManyToOne
	@JoinColumn(name = "currency_type", referencedColumnName="currency_type")
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	

	@Transient
	public List<Log> getLogs() {
		return logs;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	public CryptoUser getUserr() {
		return userr;
	}

	public void setUserr(CryptoUser userr) {
		this.userr = userr;
	}

	public void setLogs(Vector<Log> logs) {
		this.logs = logs;
	}

	@Transient
	public List<Transact> getTransacts() {
		return transacts;
	}

	public void setTransacts(Vector<Transact> transacts) {
		this.transacts = transacts;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", rate=" + rate + ", quantity=" + quantity + ", typee=" + typee + ", timestampp="
				+ timestampp + ", currency=" + currency.getTypee() + ", userr=" + userr.getId() + "]";
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addLog(Log log) {
		logs.add(log);
	}

	public void addTransact(Transact transact) {
		transacts.add(transact);
	}

	public void removeTransact(Transact transact) {
		transacts.remove(transact);
	}
	@Transient
	public Log getLastLog() {
		return logs.get(logs.size() - 1);
	}

}
