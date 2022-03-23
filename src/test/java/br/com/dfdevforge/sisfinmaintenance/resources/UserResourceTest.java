package br.com.dfdevforge.sisfinmaintenance.resources;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dfdevforge.sisfinmaintenance.configs.TestConfig;
import br.com.dfdevforge.sisfinmaintenance.entities.UserEntity;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteAuthenticationService;
import br.com.dfdevforge.sisfinmaintenance.services.user.UserExecuteRegistrationService;

@DisplayName("UserResourceTest")
public class UserResourceTest extends TestConfig {
	@MockBean private UserExecuteRegistrationService userExecuteRegistrationService;
	@MockBean private UserExecuteAuthenticationService userExecuteAuthenticationService;

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	@DisplayName("Must execute authentication correctly")
	public void mustExecuteAuthenticationCorrectly() throws Exception {
		Mockito.when(this.userExecuteAuthenticationService.execute()).thenReturn(getMockMap());

		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/user/executeAuthentication")
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(getMockUser())))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("Must execute logout correctly")
	public void mustExecuteLogoutCorrectly() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/user/executeLogout")
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(getMockUser())))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("Must execute registration correctly")
	public void mustExecuteRegistrationCorrectly() throws Exception {
		Mockito.when(this.userExecuteRegistrationService.execute()).thenReturn(getMockMap());

		this.mockMvc.perform(MockMvcRequestBuilders
			.post("/user/executeRegistration")
			.contentType(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(getMockUser())))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	private UserEntity getMockUser() {
		UserEntity user = new UserEntity();
		user.setEmail("fake.email@email.com");
		user.setPassword("fake.password");

		return user;
	}

	private Map<String, Object> getMockMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("token", "12345666");
		
		return map;
	}
}