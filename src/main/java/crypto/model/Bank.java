package crypto.model;

import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bank")
public class Bank {

	private Integer id;
	private String namee;

	private List<Account> accounts;

	public Bank() {
		super();
	}

	public Bank(String namee) {
		super();
		this.namee = namee;
	}

	public Bank(Integer id, String namee) {
		super();
		this.id = id;
		this.namee = namee;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "bank_name")
	public String getNamee() {
		return namee;
	}

	public void setNamee(String namee) {
		this.namee = namee;
	}

	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
	public List<Account> getAccounts() {
		return accounts;
	}

	

	public void setAccounts(Vector<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", namee=" + namee + "]";
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
		Bank other = (Bank) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
	}

}
