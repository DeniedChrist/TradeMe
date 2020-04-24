package crypto.access;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import crypto.config.SessionConfig;
import crypto.model.Credential;
import crypto.model.CryptoUser;
@Transactional
@Repository
public class UserAccess {

	
	@Autowired
	private SessionConfig sf;

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CredentialDao credentialDao;
	public UserAccess() {
		super();
		System.out.println("In User access");
	}

	public boolean isUsernameAvailable(String username) {
		Credential c=credentialDao.findByUsername(username);
		return c == null;
	}

	public void addUser(CryptoUser u, Credential c) {
		System.out.println("In save dao");
		c.setPass(encoder.encode(c.getPass()));//encryption!
		sf.getNewSession().save(u);
		credentialDao.save(c);
	}
	public void addUser(Credential c) {
	
		credentialDao.save(c);
	}

	public boolean validateIdentity(Credential c) {
		
		Credential cred = credentialDao.findByUsername(c.getUsername());
		return cred != null;
	}

	public void removeUser(Credential c) {
		if(validateIdentity(c)==true) {
			
			sf.getNewSession().remove(c.getUserr());
			credentialDao.delete(c);
		}
		
	}

	public void updateCredential(Credential c,Credential upd) {

		if(validateIdentity(c)!=true) {
			throw new RuntimeException("NO Such User Found!");
		}
		
		else if (c.getUserr() != null) {
			c.setUserr(upd.getUserr());
		}
		if (c.getUsername() != null) {
			c.setUsername(upd.getUsername());
		}
		if (c.getPass() != null) {
			c.setPass(upd.getPass());
		}
		sf.getNewSession().update(c);
		
	}

	public CryptoUser getUser(Integer id) {
		return sf.getNewSession().get(CryptoUser.class, id);
	}

	public CryptoUser getUser(String username) {
		return credentialDao.findByUsername(username).getUserr();
	}

	public boolean isValidUser(CryptoUser u) {
		CryptoUser usr = sf.getNewSession().get(CryptoUser.class, u.getId());
		if (usr == null) {
			return false;
		}
		if (usr.getEmail() != u.getEmail()) {
			return false;
		}
		return true;
	}

}
