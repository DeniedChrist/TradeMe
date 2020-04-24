package crypto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

	private Integer id;
	private long numberr;
	private String ifsc;

	private Bank bank;
	public CryptoUser userr;
	private Card card;

	public Account() {
		super();
	}

	public Account(long numberr, String ifsc, Bank bank, CryptoUser userr, Card card) {
		super();
		this.numberr = numberr;
		this.ifsc = ifsc;
		this.bank = bank;
		this.userr = userr;
		this.card = card;
	}

	public Account(Integer id, long numberr, String ifsc, Bank bank, CryptoUser userr, Card card) {
		super();
		this.id = id;
		this.numberr = numberr;
		this.ifsc = ifsc;
		this.bank = bank;
		this.userr = userr;
		this.card = card;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "account_number")
	public long getNumberr() {
		return numberr;
	}

	public void setNumberr(long numberr) {
		this.numberr = numberr;
	}
	

	@Column(name = "ifsc")
	public String getIfsc() {
		return ifsc;
	}

	

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	@ManyToOne
	@JoinColumn(name = "bank_id",referencedColumnName="bank_id")
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	
	
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public CryptoUser getUserr() {
		return userr;
	}

	public void setUserr(CryptoUser userr) {
		this.userr = userr;
	}

	@OneToOne
	@JoinColumn(name = "card_id",referencedColumnName="card_id")
	public Card getCard() {
		return card;
	}

	

	public void setCard(Card card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", numberr=" + numberr + ", ifsc=" + ifsc + ", bank=" + bank.getId() + ", userr="
				+ userr.getId() + ", card=" + card.getId() + "]";
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
