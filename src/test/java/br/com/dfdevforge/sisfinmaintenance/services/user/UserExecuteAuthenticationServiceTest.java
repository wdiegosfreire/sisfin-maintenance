package br.com.dfdevforge.sisfinmaintenance.services.user;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.dfdevforge.common.exceptions.BaseException;
import br.com.dfdevforge.sisfinmaintenance.configs.TestConfig;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.UserUnauthorizedException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@DisplayName("UserExecuteAuthenticationServiceTest")
class UserExecuteAuthenticationServiceTest extends TestConfig {
	@Autowired private UserExecuteAuthenticationService userExecuteAuthenticationService;

	@MockBean private UserRepository userRepository;

	@Test
	@DisplayName("Must execute authentication correctly")
	public void mustExecuteAuthenticationCorrectly() throws BaseException {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");

		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.of(userMock));

		this.userExecuteAuthenticationService.setEntity(userMock);
		this.userExecuteAuthenticationService.execute();
	}

	@Test
	@DisplayName("Must execute authentication and throw UserNotFoundException")
	void mustExecuteAuthenticationAndThrowUserNotFoundException() throws BaseException {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");

		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.empty());
		this.userExecuteAuthenticationService.setEntity(userMock);

		Assertions.assertThrows(
			UserNotFoundException.class,
			() -> this.userExecuteAuthenticationService.execute(),
			"{\"severity\":\"error\",\"summary\":\"User Not Fount\",\"messageList\":[\"The email entered was not found in the database.\"]}"
		);
	}

	@Test
	@DisplayName("Must execute authentication and throw UserUnauthorizedException")
	void mustExecuteAuthenticationAndThrowUserUnauthorizedException() throws BaseException {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");
		
		UserEntity userMockParameter = Mockito.mock(UserEntity.class);
		Mockito.when(userMockParameter.getEmail()).thenReturn("fake.email@email.com");
		Mockito.when(userMockParameter.getPassword()).thenReturn("wrong.password");

		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.of(userMock));
		this.userExecuteAuthenticationService.setEntity(userMockParameter);

		Assertions.assertThrows(
			UserUnauthorizedException.class,
			() -> this.userExecuteAuthenticationService.execute(),
			"{\"severity\":\"error\",\"summary\":\"User Unauthorized\",\"messageList\":[\"User credentials are incorrect. Please review your credentials and try again.\"]}"
		);
	}
}