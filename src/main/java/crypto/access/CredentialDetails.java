package crypto.access;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import crypto.model.Credential;




	public class CredentialDetails extends Credential implements UserDetails {

	    public CredentialDetails(Credential user) {
	        super();
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return getRoles()
	                .stream()
	                .map(role-> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))
	                .collect(Collectors.toList());
	    }

	    @Override
	    public String getPassword() {
	        return super.getPass();
	    }

	    @Override
	    public String getUsername() {
	        return super.getUsername();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
	}