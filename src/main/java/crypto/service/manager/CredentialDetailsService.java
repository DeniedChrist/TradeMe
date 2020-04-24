package crypto.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import crypto.access.CredentialDao;
import crypto.model.Credential;
import crypto.model.CredentialDetails;
@Service("userDetailsService")
public class CredentialDetailsService implements UserDetailsService{

	@Autowired
	private CredentialDao credentialDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Credential credential=credentialDao.findByUsername(username);
		if(credential== null)
			throw new UsernameNotFoundException("User by name "+username+" not found!");
		return new CredentialDetails(credential);
	}

}
