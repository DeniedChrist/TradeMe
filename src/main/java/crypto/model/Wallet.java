package crypto.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import crypto.value.CurrencyType;


@Entity
@Table(name = "wallet")
public class Wallet {

	private Integer id;
	private double balance;

	private Currency currency;
	private CryptoUser userr;

	private Map<Double, Marker> markers=new ConcurrentHashMap<>();

	@Transient
	private Vector<String> addresses;

	public Wallet() {
		super();
	}

	public Wallet(double balance, Currency currency, CryptoUser userr) {
		super();
		this.balance = balance;
		this.currency = currency;
		this.userr = userr;
	}

	public Wallet(Integer id, double balance, Currency currency, CryptoUser userr) {
		super();
		this.id = id;
		this.balance = balance;
		this.currency = currency;
		this.userr = userr;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "balance")
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@ManyToOne
	@JoinColumn(name = "currency_type", referencedColumnName="currency_type")
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	public CryptoUser getUserr() {
		return userr;
	}

	public void setUserr(CryptoUser userr) {
		this.userr = userr;
	}
	

	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
	@MapKey(name = "rate")
	public Map<Double, Marker> getMarkers() {
		return markers;
	}

	

	public void setMarkers(ConcurrentHashMap<Double, Marker> markers) {
		this.markers = markers;
	}

	@Override
	public String toString() {
		return "Wallet [id=" + id + ", balance=" + balance + ", currency=" + currency.getTypee() + ", userr="
				+ userr.getId() + "]";
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
		Wallet other = (Wallet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addMarker(Marker marker) {
		markers.put(marker.getRate(), marker);
	}

	public Marker getMarker(double rate) {
		return markers.get(rate);
	}

	public String prepareAddress(double rate, double quantity) {
		String address = "@" + id + "*" + rate + "$" + quantity;
		addresses.add(address);
		return address;
	}

	public void removeAddress(String address) throws Exception {
		if (!addresses.contains(address)) {
			throw new Exception();
		}
		addresses.remove(address);
	}

	public boolean containsAddress(String address) {
		return addresses.contains(address);
	}

	public void addAddress(String address) throws Exception {
		if (address.contains("@" + id)) {
			throw new Exception();
		} else if (markers.containsKey(new Double(address.substring(address.indexOf('$') + 1)))) {
			throw new Exception();
		}
		addresses.add(address);
	}

	public void withdraw(String address) throws Exception {
		Double quantity = Double.parseDouble(address.substring(address.indexOf('$') + 1));
		Double rate = new Double(address.substring(address.indexOf('*') + 1, address.indexOf('$')));
		if (!addresses.contains(address)) {
			throw new Exception();
		} else if (!address.contains("@" + id)) {
			throw new Exception();
		} else if (markers.get(-rate).getQuantity() < quantity) {
			throw new Exception();
		}
		balance -= quantity;
		markers.get(-rate).setQuantity(markers.get(rate).getQuantity() - quantity);
	}

	public void add(String address) throws Exception {
		if (!addresses.contains(address)) {
			throw new Exception();
		} else if (address.contains("@" + id)) {
			throw new Exception();
		}
		Double quantity = Double.parseDouble(address.substring(address.indexOf('$') + 1));
		balance += quantity;
		markers.get(0.0).setQuantity(markers.get(0.0).getQuantity() + quantity);
	}

	public void add(double quantity) throws Exception {
		if (currency.getTypee() != CurrencyType.CONTRACT) {
			throw new Exception();
		}
		balance += quantity;
		markers.get(0.0).setQuantity(markers.get(0.0).getQuantity() + quantity);
	}

	public void withdraw(double rate, double quantity) throws Exception {
		if (currency.getTypee() != CurrencyType.CONTRACT) {
			throw new Exception();
		}
		if (markers.get(-rate).getQuantity() < quantity) {
			throw new Exception();
		}
		balance -= quantity;
		markers.get(-rate).setQuantity(markers.get(-rate).getQuantity() - quantity);
	}
}
