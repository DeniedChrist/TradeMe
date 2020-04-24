package crypto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ledger")
public class Ledger {

	private Integer id;
	private double rate;
	private double quantity;
	private Date timestampp;

	private Currency currency;
	private Wallet buyWallet;
	private Wallet sellWallet;

	public Ledger() {
		super();
	}

	public Ledger(double rate, double quantity, Date timestampp, Currency currency, Wallet buyWallet,
			Wallet sellWallet) {
		super();
		this.rate = rate;
		this.quantity = quantity;
		this.timestampp = timestampp;
		this.currency = currency;
		this.buyWallet = buyWallet;
		this.sellWallet = sellWallet;
	}

	public Ledger(Integer id, double rate, double quantity, Date timestampp, Currency currency, Wallet buyWallet,
			Wallet sellWallet) {
		super();
		this.id = id;
		this.rate = rate;
		this.quantity = quantity;
		this.timestampp = timestampp;
		this.currency = currency;
		this.buyWallet = buyWallet;
		this.sellWallet = sellWallet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ledger_id")
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

	@Temporal(TemporalType.DATE)
	@Column(name = "timestampp")
	public Date getTimestampp() {
		return timestampp;
	}

	public void setTimestampp(Date timestampp) {
		this.timestampp = timestampp;
	}

	@ManyToOne
	@JoinColumn(name = "currency_type",referencedColumnName="currency_type")
	public Currency getCurrency() {
		return currency;
	}

	

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@OneToOne
	@JoinColumn(name = "buy_wallet_id",referencedColumnName="wallet_id")
	public Wallet getBuyWallet() {
		return buyWallet;
	}

	public void setBuyWallet(Wallet buyWallet) {
		this.buyWallet = buyWallet;
	}

	@OneToOne
	@JoinColumn(name = "sell_wallet_id",referencedColumnName="wallet_id")
	public Wallet getSellWallet() {
		return sellWallet;
	}

	public void setSellWallet(Wallet sellWallet) {
		this.sellWallet = sellWallet;
	}

	@Override
	public String toString() {
		return "Ledger [id=" + id + ", rate=" + rate + ", quantity=" + quantity + ", timestampp=" + timestampp
				+ ", currency=" + currency.getTypee() + ", buyWallet=" + buyWallet.getId() + ", sellWallet="
				+ sellWallet.getId() + "]";
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
		Ledger other = (Ledger) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
