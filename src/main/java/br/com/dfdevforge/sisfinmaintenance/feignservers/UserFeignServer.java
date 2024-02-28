package br.com.dfdevforge.sisfinmaintenance.feignservers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.common.utils.Utils;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.SessionExpiredException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@RestController
@RequestMapping(value = "/userfeignserver")
public class UserFeignServer {
	private final UserRepository userRepository;

	@Autowired
	public UserFeignServer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping(value = "/{token}")
	public UserEntity validateToken(@PathVariable String token) throws BaseException {
		DecodedJWT decodedJwt = null ;

		try {
			token = Utils.decrypt.fromBase64(token);
			decodedJwt = JWT.require(Algorithm.HMAC512(System.getenv("SISFIN_BACKEND_JWT_SECRET"))).build().verify(token);
		}
		catch (TokenExpiredException e) {
			throw new SessionExpiredException();
		}
		catch (JWTDecodeException e) {
			throw new BaseException("The input is not a valid base 64 encoded string.");
		}
		catch (Exception e) {
			throw new BaseException("Exception not recognized.");
		}

		this.findUserByIdentity(Long.parseLong(decodedJwt.getClaim("userIdentity").toString()));
		
		return this.findUserByIdentity(Long.parseLong(decodedJwt.getClaim("userIdentity").toString()));
	}

	private UserEntity findUserByIdentity(long identity) throws BaseException {
		UserEntity user = new UserEntity();
		user.setIdentity(identity);

		return this.userRepository.findByIdentity(identity).get();
	}
}