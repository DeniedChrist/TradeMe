package crypto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "marker")
public class Marker {

	private Integer id;
	private double rate;
	private double quantity;

	Wallet wallet;

	public Marker() {
		super();
	}

	public Marker(double rate, double quantity, Wallet wallet) {
		super();
		this.rate = rate;
		this.quantity = quantity;
		this.wallet = wallet;
	}

	public Marker(Integer id, double rate, double quantity, Wallet wallet) {
		super();
		this.id = id;
		this.rate = rate;
		this.quantity = quantity;
		this.wallet = wallet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "marker_id")
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

	@ManyToOne
	@JoinColumn(name = "wallet_id", referencedColumnName="wallet_id")
	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "Marker [id=" + id + ", rate=" + rate + ", quantity=" + quantity + ", wallet=" + wallet.getId() + "]";
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
		Marker other = (Marker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
