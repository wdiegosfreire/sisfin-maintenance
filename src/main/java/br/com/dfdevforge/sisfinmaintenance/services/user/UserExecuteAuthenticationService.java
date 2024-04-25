package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.services.CommonService;
import br.com.dfdevforge.common.utils.Utils;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@Service
public class UserExecuteAuthenticationService extends UserBaseService implements CommonService {
	private String token;
	private UserEntity userAuthenticated;

	@Autowired private UserRepository userRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findUserByEmail();
		this.checkIfPasswordIsCorrect();
		this.generateSessionToken();
		this.encryptSessionToken();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("userAuthenticated", this.userAuthenticated);
		return super.returnBusinessData();
	}

	private void findUserByEmail() throws BaseException {
		this.userAuthenticated = this.userRepository.findByEmail(this.userParam.getEmail()).orElse(null);

		if (this.userAuthenticated == null || this.userAuthenticated.getIdentity() == null || this.userAuthenticated.getEmail() == null)
			throw new UserNotFoundException();
	}

	private void checkIfPasswordIsCorrect() throws BaseException {
		if (!this.userAuthenticated.getPassword().equals(this.userParam.getPassword()))
			throw new UserUnauthorizedException();
	}

	private void generateSessionToken() {
		this.token = JWT
			.create()
			.withSubject(this.userAuthenticated.getEmail())
			.withExpiresAt(new Date(System.currentTimeMillis() + 3000000))
			.withClaim("userIdentity", this.userAuthenticated.getIdentity())
			.sign(Algorithm.HMAC512(System.getenv("SISFIN_BACKEND_JWT_SECRET")));
	}

	private void encryptSessionToken() {
		this.setSessionToken(Utils.encrypt.toBase64(this.token));
	}
}