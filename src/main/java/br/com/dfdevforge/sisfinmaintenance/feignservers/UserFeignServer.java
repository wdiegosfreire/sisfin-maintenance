package br.com.dfdevforge.sisfinmaintenance.feignservers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.common.entities.ResourceDataEntity;
import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteSearchService;

@RestController
@RequestMapping(value = "/userfeignserver")
public class UserFeignServer {
	private ResourceDataEntity resourceData = new ResourceDataEntity();

	@Autowired private UserExecuteSearchService userExecuteSearchService;

	@GetMapping(value = "/{identity}")
	public ResponseEntity<ResourceDataEntity> findByIdentity(@PathVariable long identity) throws BaseException {
		UserEntity user = new UserEntity();
		user.setIdentity(identity);

		this.userExecuteSearchService.setEntity(user);
		this.resourceData.setMap(this.userExecuteSearchService.execute());
		
		return ResponseEntity.ok(this.resourceData);
	}
}