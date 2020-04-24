package crypto.access;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



import crypto.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Integer> {
	Optional<Credential> findByUsername(String username);
}
