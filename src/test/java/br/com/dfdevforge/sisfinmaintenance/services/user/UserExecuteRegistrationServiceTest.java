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
import br.com.dfdevforge.sisfinmaintenance.TestConfig;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.exceptions.EmailAlreadyRegisteredException;
import br.com.dfdevforge.sisfinmaintenance.exceptions.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfinmaintenance.repositories.UserRepository;

@DisplayName("UserExecuteRegistrationServiceTest")
public class UserExecuteRegistrationServiceTest extends TestConfig {
	@Autowired private UserExecuteRegistrationService userExecuteRegistrationService;

	@MockBean private UserRepository userRepository;

	@Test
	@DisplayName("Must execute registration correctly")
	public void mustExecuteRegistrationCorrectly() throws BaseException {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getIdentity()).thenReturn(1L);
		Mockito.when(userMock.getName()).thenReturn("Fake Name");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");

		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.empty());
		this.userExecuteRegistrationService.setEntity(userMock);

		this.userExecuteRegistrationService.execute();
	}

	@Test
	@DisplayName("Must execute registration and throw RequiredFieldNotFoundException with empty email")
	public void mustExecuteRegistrationAndThrowRequiredFieldNotFoundExceptionWithEmptyEmail() {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getIdentity()).thenReturn(1L);
		Mockito.when(userMock.getName()).thenReturn("Fake Name");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");
		
		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.empty());
		this.userExecuteRegistrationService.setEntity(userMock);

		RequiredFieldNotFoundException requiredFieldNotFoundException = Assertions.assertThrows(
			RequiredFieldNotFoundException.class,
			() -> this.userExecuteRegistrationService.execute()
		);

		Assertions.assertTrue(requiredFieldNotFoundException.getMessage().contains("Please, enter email"));
	}

	@Test
	@DisplayName("Must execute registration and throw RequiredFieldNotFoundException with empty password")
	public void mustExecuteRegistrationAndThrowRequiredFieldNotFoundExceptionWithEmptyPassword() {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getIdentity()).thenReturn(1L);
		Mockito.when(userMock.getName()).thenReturn("Fake Name");
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		
		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.empty());
		this.userExecuteRegistrationService.setEntity(userMock);
		
		RequiredFieldNotFoundException requiredFieldNotFoundException = Assertions.assertThrows(
			RequiredFieldNotFoundException.class,
			() -> this.userExecuteRegistrationService.execute()
		);

		Assertions.assertTrue(requiredFieldNotFoundException.getMessage().contains("Please, enter password"));
	}

	@Test
	@DisplayName("Must execute registration and throw RequiredFieldNotFoundException with empty name")
	public void mustExecuteRegistrationAndThrowRequiredFieldNotFoundExceptionWithEmptyName() {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getIdentity()).thenReturn(1L);
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		
		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.empty());
		this.userExecuteRegistrationService.setEntity(userMock);
		
		RequiredFieldNotFoundException requiredFieldNotFoundException = Assertions.assertThrows(
			RequiredFieldNotFoundException.class,
			() -> this.userExecuteRegistrationService.execute()
		);

		Assertions.assertTrue(requiredFieldNotFoundException.getMessage().contains("Please, enter name"));
	}

	@Test
	@DisplayName("Must execute registration and throw EmailAlreadyRegisteredException with empty name")
	public void mustExecuteRegistrationAndThrowEmailAlreadyRegisteredException() {
		UserEntity userMock = Mockito.mock(UserEntity.class);
		Mockito.when(userMock.getIdentity()).thenReturn(1L);
		Mockito.when(userMock.getName()).thenReturn("Fake Name");
		Mockito.when(userMock.getPassword()).thenReturn("fake.password");
		Mockito.when(userMock.getEmail()).thenReturn("fake.email@email.com");
		
		Mockito.when(this.userRepository.findByEmail(ArgumentMatchers.eq(userMock.getEmail()))).thenReturn(Optional.of(userMock));
		this.userExecuteRegistrationService.setEntity(userMock);
		
		Assertions.assertThrows(
			EmailAlreadyRegisteredException.class,
			() -> this.userExecuteRegistrationService.execute()
		);
	}
}