package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.common.utils.Utils;
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

	private void checkRequiredFields() throws RequiredFieldNotFoundException {
		List<String> errorList = new ArrayList<>();

		if (Utils.value.notExists(this.userParam.getEmail()))
			errorList.add("Please, enter email.");
		if (Utils.value.notExists(this.userParam.getPassword()))
			errorList.add("Please, enter password.");
		if (Utils.value.notExists(this.userParam.getName()))
			errorList.add("Please, enter name.");

		if (errorList != null && !errorList.isEmpty())
			throw new RequiredFieldNotFoundException("Required Field Not Found", errorList);
	}

	private void checkIfEmailIsAlreadyRegistered() throws EmailAlreadyRegisteredException {
		UserEntity userFound = this.userRepository.findByEmail(this.userParam.getEmail()).orElse(null);

		if (userFound != null)
			throw new EmailAlreadyRegisteredException();
	}

	private void saveUserOnMaintenance() throws BaseException {
		this.userRepository.save(this.userParam);
	}
}