package br.com.dfdevforge.sisfinmaintenance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/imrunning")
public class ImRunning {
	private static final String BREAK = "<br>";

	@Autowired
	BuildProperties buildProperties;

	@GetMapping
	public String imRunning() {
		StringBuilder statusMessage = new StringBuilder();

		statusMessage.append("Project: Sisfin Maintenance" + BREAK);
		statusMessage.append("Version: " + String.format("v%s", this.buildProperties.getVersion()) + BREAK);
		statusMessage.append("Check Date: " + new Date());

		return statusMessage.toString();
	}
}