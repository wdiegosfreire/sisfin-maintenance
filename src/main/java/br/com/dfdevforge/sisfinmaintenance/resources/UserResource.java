package br.com.dfdevforge.sisfinmaintenance.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.common.entities.ResourceDataEntity;
import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteAuthenticationService;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteRegistrationService;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteSearchService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private UserExecuteSearchService userExecuteSearchService;
	@Autowired private UserExecuteRegistrationService userExecuteRegistrationService;
	@Autowired private UserExecuteAuthenticationService userExecuteAuthenticationService;

	@PostMapping(value = "/executeAuthentication")
	public ResponseEntity<ResourceDataEntity> executeAuthentication(@RequestBody UserEntity user) throws BaseException {
		this.userExecuteAuthenticationService.setEntity(user);
		this.resourceData.setMap(this.userExecuteAuthenticationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeLogout")
	public ResponseEntity<ResourceDataEntity> executeLogout(@RequestBody UserEntity user, HttpServletRequest request) throws BaseException {
		request.getSession().invalidate();
		
		return ResponseEntity.ok(this.resourceData);
	}

	@PostMapping(value = "/executeRegistration")
	public ResponseEntity<ResourceDataEntity> executeRegistration(@RequestBody UserEntity user) throws BaseException {
		this.userExecuteRegistrationService.setEntity(user);
		this.resourceData.setMap(this.userExecuteRegistrationService.execute());

		return ResponseEntity.ok(this.resourceData);
	}

	@GetMapping(value = "/{identity}")
	public ResponseEntity<ResourceDataEntity> findByIdentity(@PathVariable long identity) throws BaseException {
		UserEntity user = new UserEntity();
		user.setIdentity(identity);

		this.userExecuteSearchService.setEntity(user);
		this.resourceData.setMap(this.userExecuteSearchService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}
}