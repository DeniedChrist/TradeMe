package crypto.config;

import java.util.Vector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import crypto.model.Transact;

@Configuration
public class TransactVector {
	
	public TransactVector() {
		System.out.println("In Transact Vector Provider config");
	}
	@Bean
	@Scope("singleton")
	public Vector<Transact> getVector(){
		return new Vector<Transact>();
	}
	
}
