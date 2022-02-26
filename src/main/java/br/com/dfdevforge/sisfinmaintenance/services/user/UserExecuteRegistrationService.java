package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.EmailAlreadyRegisteredException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@Service
public class UserExecuteRegistrationService extends UserBaseService implements CommonService {
	@Autowired private UserRepository userRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.checkRequiredFields();
		this.checkIfEmailIsAlreadyRegistered();
		this.saveUserOnMaintenance();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("userRegistered", this.userParam);
		return super.returnBusinessData();
	}

	private void checkIfEmailIsAlreadyRegistered() throws EmailAlreadyRegisteredException {
		UserEntity userFound = this.userRepository.findByEmail(this.userParam.getEmail()).orElse(null);

		if (userFound != null)
			throw new EmailAlreadyRegisteredException();
	}

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (this.userParam.getEmail() == null)
			errorList.add("Please, enter email.");
		if (this.userParam.getPassword() == null)
			errorList.add("Please, enter password.");
		if (this.userParam.getName() == null || this.userParam.getName().equals(""))
			errorList.add("Please, enter name.");

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void saveUserOnMaintenance() throws BaseException {
		this.userRepository.save(this.userParam);
	}
}