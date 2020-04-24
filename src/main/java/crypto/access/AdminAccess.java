package crypto.access;

import org.springframework.beans.factory.annotation.Autowired;

import crypto.config.SessionConfig;
import crypto.model.Credential;
import crypto.model.CryptoUser;

public class AdminAccess {
	
	
	
	@Autowired
	private UserAccess usracc;
	
	private double fee;
	
	
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public AdminAccess() {
		System.out.println("In Admin Access");
	}
	
	public void changeFee(double f) {
		
		fee=f;
		System.out.println("New Fee : "+fee);
	}
	public String addPolicy() {
		//Add policies.
		return null;
	}
	public String removePolicy() {
		//Remove policies.
		return null;
	}
	public String changePolicy() {
		//Optional
		return null;
	}
	public String removeUser(Credential c) {
		try {
			if(usracc.isUsernameAvailable(c.getUsername())) {
				//CryptoUser c=usracc.getUser(c.getUsername());
				usracc.removeUser(c);
			}
			return "Removed User!";
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
}
