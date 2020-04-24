package crypto.model;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import crypto.value.CurrencyType;


@Entity
@Table(name = "user")
public class CryptoUser {

	
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileCode;
	private long mobileNumber;
	

	private Map<Integer, Account> accounts=new ConcurrentHashMap<>();
	private Map<CurrencyType, Wallet> wallets=new ConcurrentHashMap<>();
	private List<Ledger> ledgers=new Vector<>();
	private List<Order> orders=new Vector<>();
	

	



	public CryptoUser() {
		super();
	}
	

	public CryptoUser(String firstName, String lastName, String email, String mobileCode, long mobileNumber,
			Map<Integer, Account> accounts, Map<CurrencyType, Wallet> wallets, List<Ledger> ledgers,
			List<Order> orders) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileCode = mobileCode;
		this.mobileNumber = mobileNumber;
		this.accounts = accounts;
		this.wallets = wallets;
		this.ledgers = ledgers;
		this.orders = orders;
	}
	
	

	public CryptoUser(Integer id, String firstName, String lastName, String email, String mobileCode, long mobileNumber,
			Map<Integer, Account> accounts, Map<CurrencyType, Wallet> wallets, List<Ledger> ledgers,
			List<Order> orders) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileCode = mobileCode;
		this.mobileNumber = mobileNumber;
		this.accounts = accounts;
		this.wallets = wallets;
		this.ledgers = ledgers;
		this.orders = orders;
	}

	
	


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
	public Integer getId() {
		return id;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "firstname")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "lastname")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "mobile_code")
	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	@Column(name = "mobile_number")
	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@OneToMany(mappedBy = "userr", cascade = CascadeType.ALL)
	@MapKey(name = "id")
	public Map<Integer, Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(Map<Integer, Account> accounts) {
		this.accounts = accounts;
	}
	@OneToMany(mappedBy = "userr", cascade = CascadeType.ALL)
	@MapKey(name = "currency")
	public Map<CurrencyType, Wallet> getWallets() {
		return wallets;
	}

	


	public void setWallets(Map<CurrencyType, Wallet> wallets) {
		this.wallets = wallets;
	}

	@Transient
	public List<Ledger> getLedgers() {
		return ledgers;
	}

	public void setLedgers(List<Ledger> ledgers) {
		this.ledgers = ledgers;
	}

	@OneToMany(mappedBy = "userr", cascade = CascadeType.ALL)
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "CryptoUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileCode=" + mobileCode + ", mobileNumber=" + mobileNumber + "]";
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
		CryptoUser other = (CryptoUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Wallet getWallet(CurrencyType cType) {
		return wallets.get(cType);
	}

	public void addAccount(Account account) throws Exception {
		if (!equals(account.getUserr())) {
			throw new Exception();
		}
		if (account.getId() == null) {
			throw new Exception();
		}
		accounts.put(account.getId(), account);
	}

}
