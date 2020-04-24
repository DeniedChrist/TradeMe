package crypto.test;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public class TesterApiController {
	@RequestMapping("/hi")
	public String hi() {
		return "hello there";
	}
}
