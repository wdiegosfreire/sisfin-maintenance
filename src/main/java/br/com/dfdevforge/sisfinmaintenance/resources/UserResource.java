package br.com.dfdevforge.sisfinmaintenance.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.common.entities.ResourceDataEntity;
import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteAuthenticationService;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteRegistrationService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	private final UserExecuteRegistrationService userExecuteRegistrationService;
	private final UserExecuteAuthenticationService userExecuteAuthenticationService;

	@Autowired
	public UserResource(UserExecuteRegistrationService userExecuteRegistrationService, UserExecuteAuthenticationService userExecuteAuthenticationService) {
		this.userExecuteRegistrationService = userExecuteRegistrationService;
		this.userExecuteAuthenticationService = userExecuteAuthenticationService;
	}

	@PostMapping(value = "/executeAuthentication")
	@Operation(description = "Performs user authentication.")
	public ResponseEntity<ResourceDataEntity> executeAuthentication(@RequestBody UserEntity user) throws BaseException {
		this.userExecuteAuthenticationService.setEntity(user);
		this.resourceData.setMap(this.userExecuteAuthenticationService.execute());
		this.resourceData.setToken(this.resourceData.getMap().get("token").toString());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeLogout")
	@Operation(description = "Performs user logout.")
	public ResponseEntity<ResourceDataEntity> executeLogout(@RequestBody UserEntity user, HttpServletRequest request) {
		request.getSession().invalidate();
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	@Operation(description = "Performs user registration.")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody UserEntity user) throws BaseException {
		this.userExecuteRegistrationService.setEntity(user);
		this.resourceData.setMap(this.userExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}
}