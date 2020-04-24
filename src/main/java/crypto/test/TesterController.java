package crypto.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TesterController {
	
	@GetMapping("/exchange")
	public String getExchangePage() {
		return "exchange";
	}
	
	@GetMapping("/chart")
	public String getChart(@RequestParam String trade, @RequestParam String currency, Model map) {
		System.out.println("recieved request for chart of " + trade + "ing " + currency);
		map.addAttribute("trade", trade);
		map.addAttribute("currency", currency);
		return "chart";
	}
	
	@GetMapping("/history")
	public String getHistory() {
		return "history";
	}

}
