package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@Service
public class UserExecuteAuthenticationService extends UserBaseService implements CommonService {
	private UserEntity userAuthenticated;

	@Autowired
	private final UserRepository userRepository;

	public UserExecuteAuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findUserByEmail();
		this.checkIfPasswordIsCorrect();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("userAuthenticated", this.userAuthenticated);
		return super.returnBusinessData();
	}

	private void findUserByEmail() throws BaseException {
		this.userAuthenticated = this.userRepository.findByEmail(this.userParam.getEmail());

		if (this.userAuthenticated == null)
			throw new UserNotFoundException();
	}

	private void checkIfPasswordIsCorrect() throws BaseException {
		if (!this.userAuthenticated.getPassword().equals(this.userParam.getPassword()))
			throw new UserUnauthorizedException();
	}
}