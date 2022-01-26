package br.com.dfdevforge.sisfinmaintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ImRunning {
	@Autowired
	BuildProperties buildProperties;

	@GetMapping
	public String imRunning() {
		return String.format("Sisfin Maintenance v%s is running", this.buildProperties.getVersion());
	}
}