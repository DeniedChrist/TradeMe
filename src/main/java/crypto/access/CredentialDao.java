package crypto.access;

import org.springframework.data.repository.CrudRepository;

import crypto.model.Credential;

public interface CredentialDao extends CrudRepository<Credential, Integer> {
	Credential findByUsername(String name);
}
