package crypto.restapi;



import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketController {
	
	@GetMapping("/{cType}/initial_graph")
	public ResponseEntity<JSONObject> getInitialGraph(@PathVariable String cType, @RequestParam int columns) {
		return null;
	}
	
	@GetMapping("/{cType}/graph_update")
	public ResponseEntity<JSONObject> getGraphUpdate(@PathVariable String cType, @RequestParam long timestamp, @RequestParam int columns) {
		return null;
	}
	
	@GetMapping("/{tradeType}/{cType}/initial_graph")
	public ResponseEntity<JSONObject> getInitialChart(@PathVariable String tradeType, @PathVariable String cType, @RequestParam int columns) {
		return null;
	}
	
	@GetMapping("/{tradeType}/{cType}/graph_update")
	public ResponseEntity<JSONObject> getChartUpdate(@PathVariable String tradeType, @PathVariable String cType, @RequestParam long timestamp, @RequestParam int columns) {
		return null;
	}
	
}
