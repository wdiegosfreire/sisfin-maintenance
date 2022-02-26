package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@Service
public class UserExecuteSearchService extends UserBaseService implements CommonService {
	private UserEntity userAuthenticated;

	@Autowired
	private final UserRepository userRepository;

	public UserExecuteSearchService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findUserByIdentity();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("userAuthenticated", this.userAuthenticated);
		return super.returnBusinessData();
	}

	private void findUserByIdentity() throws BaseException {
		this.userAuthenticated = this.userRepository.findByIdentity(this.userParam.getIdentity()).orElse(null);

		if (this.userAuthenticated == null)
			throw new UserNotFoundException();
	}
}