package crypto.service.manager;

import org.springframework.beans.factory.annotation.Autowired;

import crypto.access.AdminAccess;
import crypto.model.Credential;

public class AdminManager {

	@Autowired
	private AdminAccess admAcc;
	
	public String changeFee(double f) {
		admAcc.changeFee(f);
		return "Updated!";
	}
	
	public String addPolicies() {
		return admAcc.addPolicy();
		
	}
	public String removePolicies() {
		return  admAcc.removePolicy();
	}
	public String changePolicies() {
		return  admAcc.changePolicy();
	}
	public String removeUser(Credential c) {
		return admAcc.removeUser(c);
	}
	
}
