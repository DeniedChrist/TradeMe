package crypto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "card")
public class Card {

	private Integer id;
	private long numberr;
	private String holderName;
	private Date expiryDate;

	private Account account;

	public Card() {
		super();
	}

	public Card(long numberr, String holderName, Date expiryDate, Account account) {
		super();
		this.numberr = numberr;
		this.holderName = holderName;
		this.expiryDate = expiryDate;
		this.account = account;
	}

	public Card(Integer id, long numberr, String holderName, Date expiryDate, Account account) {
		super();
		this.id = id;
		this.numberr = numberr;
		this.holderName = holderName;
		this.expiryDate = expiryDate;
		this.account = account;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name="card_number")
	public long getNumberr() {
		return numberr;
	}

	public void setNumberr(long numberr) {
		this.numberr = numberr;
	}

	@Column(name = "holder_name")
	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "expiry_date")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName="account_id")
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", numberr=" + numberr + ", holderName=" + holderName + ", expiryDate=" + expiryDate
				+ ", account=" + account.getId() + "]";
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
		Card other = (Card) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
