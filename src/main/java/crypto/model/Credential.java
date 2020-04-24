package crypto.model;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;




@Entity
@Table(name = "credential")
public class Credential {

	private String username;
	
	private String pass;
	
	private CryptoUser userr;
	
	
	
    
	
	
	public Credential(String username, String pass) {
		super();
		this.username = username;
		this.pass = pass;
	}




	public Credential() {
		super();
	}
	
	
	
	
	@Id
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public Credential(String username, String pass, CryptoUser userr) {
		super();
		this.username = username;
		this.pass = pass;
		this.userr = userr;
		
	}




	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password")
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	

	

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	public CryptoUser getUserr() {
		return userr;
	}

	public void setUserr(CryptoUser userr) {
		this.userr = userr;
	}
	
	
	/*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	 public Set<Role> getRoles() {
	        return roles;
	    }

	    public void setRoles(Set<Role> roles) {
	        this.roles = roles;
	    }
	*/
	
	@Override
	public String toString() {
		return "Credential [username=" + username + ", pass=" + pass + ", user=" + userr.getId() + "]";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Credential other = (Credential) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
