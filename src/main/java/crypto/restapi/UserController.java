package crypto.restapi;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestParam String user) {
		System.out.println(user);
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		
		//System.out.println(user.get("username"));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
}
