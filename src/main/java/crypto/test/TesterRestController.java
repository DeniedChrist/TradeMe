package crypto.test;

import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class TesterRestController {
	
	@PostMapping("/{order}/{currency}/{rate}/{quantity}")
	public ResponseEntity<?> placeSellOrder(@PathVariable String order, @PathVariable String currency, @PathVariable double rate, @PathVariable double quantity) {
		//return new ResponseEntity<String>("Failed to place order! Try again",HttpStatus.OK);
		System.out.println("recieved request to place " + order + " order at rate " + rate + " for quantity of " + quantity);
		JSONObject orders = new JSONObject();
		orders.put("rate", rate);
		orders.put("quantity", quantity);
		return new ResponseEntity<JSONObject>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/initial_chart/{trade}/{currency}")
	public ResponseEntity<JSONObject> getOrders(@PathVariable String trade, @PathVariable String currency) {
		System.out.println("requesting " + "initial chart for " + trade + "ing currency " + currency);
		JSONObject trades = new JSONObject();
	    trades.put("233", "23233");
	    trades.put("324", "565");
	    trades.put("885", "986");
	    trades.put("last", "1992236");
	    return new ResponseEntity<JSONObject>(trades, HttpStatus.OK);
	}
	
	@GetMapping("/update/{trade}/{currency}/{last}")
	public ResponseEntity<JSONObject> getOrderUpdates(@PathVariable String trade, @PathVariable String currency, @PathVariable long last) {
	    //System.out.println("requesting " + "update chart for " + trade + "ing currency " + currency + " after " + last);
		JSONObject trades = new JSONObject();
	    trades.put((new Date()).getTime(), Math.random()*100);
	    return new ResponseEntity<JSONObject>(trades, HttpStatus.OK);
	}
	
	@GetMapping("/orders/cancellables/{order}")
	public ResponseEntity<JSONObject> getCancellableOrders(@PathVariable String order) {
		JSONObject trades = new JSONObject();
			if(order.equals("sell")) {
		    trades.put("213", "2233");
		    trades.put("443", "442");
		    trades.put("211", "432");
		    trades.put("331", "2232");
		} else if(order.equals("buy")) {
			trades.put("321", "7342");
		    trades.put("33", "6533");
		    trades.put("322", "9432");
		    trades.put("343", "193");
		}
		return new ResponseEntity<JSONObject>(trades, HttpStatus.OK);
	}
	
	@PostMapping("/orders/remove/{order}/{rate}")
	public ResponseEntity<JSONObject> getCancellableOrders(@PathVariable String order, @PathVariable double rate) {
		System.out.println("recieved remove request for " + order + " order of rate " + rate );
		JSONObject reply = new JSONObject();
		reply.put("status", "200");
		reply.put("rate", rate);
		reply.put("message", null);
		//reply.put("message", "no such order found");
		return new ResponseEntity<JSONObject>(reply, HttpStatus.OK);
	}
	
	@GetMapping("transaction_history")
	public ResponseEntity<JSONObject> getTransactions() {
		JSONObject transactions = new JSONObject();
		System.out.println("recieved request");
		for(int i=0; i<5; i++) {
			JSONObject trade = new JSONObject();
			trade.put("trade", (i%2==0)?"bought":"sold");
			trade.put("currency", (i%2==0)?"bitcoin":"etherium");
			trade.put("rate", Math.random()*60);
			trade.put("quantity", Math.random()*20);
			transactions.put(Math.floor(Math.random()*33), trade);
		}
		System.out.println(transactions);
		return new ResponseEntity<JSONObject>(transactions, HttpStatus.OK);
	}
	
}
