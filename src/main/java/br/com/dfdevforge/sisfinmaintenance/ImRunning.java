package br.com.dfdevforge.sisfinmaintenance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ImRunning {
	@GetMapping
	public String imRunning() {
		return "Sisfin Maintenance is running";
	}
}
