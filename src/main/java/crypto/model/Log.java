package crypto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import crypto.value.LogType;
import crypto.value.TransactionStatus;



@Entity
@Table(name = "log")
public class Log {

	private Integer id;
	private double rate;
	private double quantity;
	private TransactionStatus statuss;
	private LogType typee;
	private Date timestampp;

	private Currency currency;
	private CryptoUser userr;

	public Log() {
		super();
	}

	public Log(double rate, double quantity, TransactionStatus statuss, LogType typee, Date timestampp, Currency currency,
			CryptoUser userr) {
		super();
		this.rate = rate;
		this.quantity = quantity;
		this.statuss = statuss;
		this.typee = typee;
		this.timestampp = timestampp;
		this.currency = currency;
		this.userr = userr;
	}

	public Log(Integer id, double rate, double quantity, TransactionStatus statuss, LogType typee, Date timestampp,
			Currency currency, CryptoUser userr) {
		super();
		this.id = id;
		this.rate = rate;
		this.quantity = quantity;
		this.statuss = statuss;
		this.typee = typee;
		this.timestampp = timestampp;
		this.currency = currency;
		this.userr = userr;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
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
	@Column(name = "transaction_status")
	public TransactionStatus getStatuss() {
		return statuss;
	}

	public void setStatuss(TransactionStatus statuss) {
		this.statuss = statuss;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "log_type")
	public LogType getTypee() {
		return typee;
	}

	

	public void setTypee(LogType typee) {
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

	@OneToOne
	@JoinColumn(name = "currency_type",referencedColumnName="currency_type")
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	public CryptoUser getUserr() {
		return userr;
	}

	public void setUserr(CryptoUser userr) {
		this.userr = userr;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", rate=" + rate + ", quantity=" + quantity + ", statuss=" + statuss + ", typee=" + typee
				+ ", timestampp=" + timestampp + ", currency=" + currency.getTypee() + ", userr=" + userr.getId() + "]";
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
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
