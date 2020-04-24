package crypto.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import crypto.model.Credential;
import crypto.service.manager.UserManager;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

	
	@Autowired
	private UserManager userManager;
	
	    @PreAuthorize("hasAnyRole('ADMIN')")
	    @RequestMapping("/secured")
	    public String secured(){
	        System.out.println("Inside secured");
	        return "Hello user !!! : " + new Date();
	    }
	
	    @PreAuthorize("hasAnyRole('USER')")
	    @RequestMapping("/hello")
	    public String sayHello(){
	        return "Hello User!";
	    }
	    
		@RequestMapping("/welcome")
		public String firstPage() {
			return "welcome";
		}
	    //@PreAuthorize("hasAnyRole('ADMIN')")
	    @GetMapping("/add")
	    public ModelAndView add() {
	    	System.out.println("get add req");
	    	
	    	return new ModelAndView("add","msg","Hi");
	    }
	    @PostMapping("/add")
	    public ResponseEntity<?> add(@RequestBody Credential c){
	    	
	    	try {
				return new ResponseEntity<Credential>(userManager.addNewUser(c),HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>("Something Went Wrong",HttpStatus.EXPECTATION_FAILED);
			}
	    }
	    
	
}
