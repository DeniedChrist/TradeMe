package crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import crypto.model.Credential;
import crypto.service.manager.UserManager;

@Controller
@RequestMapping("/nrm")
public class TestControl {

	
	@Autowired
	private UserManager userManager;
	
	@GetMapping("/x")
	public String firstPage() {
		System.out.println("In Wellcome");
		return "welcome";
	}
    /*@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/add")
    public String add(Model map) {
    	System.out.println("get add req");
    	map.addAttribute("msg", "Hi");
    	return "add";
    }*/
    @GetMapping("/add")
    public ResponseEntity<?> add(){
    	Credential c=new Credential("pratik","1234");
    	System.out.println("get add req");
    	try {
			return new ResponseEntity<Credential>(userManager.addNewUser(c),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.EXPECTATION_FAILED);
		}
    }
}
