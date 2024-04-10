package com.auth.authenticationservice;

import com.auth.authenticationservice.model.UserDetails;
import com.auth.authenticationservice.repository.UserRepo;
import com.auth.authenticationservice.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

	@Mock
	private UserRepo userRepo;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoginUserValidCredentials() {
		String username = "testuser";
		String password = "testpassword";
		String encodedPassword = new BCryptPasswordEncoder().encode(password);

		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword(encodedPassword);

		when(userRepo.findByUsername(username)).thenReturn(userDetails);

		assertTrue(userService.loginUser(username, password));
	}

	@Test
	void testLoginUserInvalidCredentials() {
		String username = "testuser";
		String password = "testpassword";
		String incorrectPassword = "incorrectpassword";

		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword(new BCryptPasswordEncoder().encode(password));

		when(userRepo.findByUsername(username)).thenReturn(userDetails);

		assertFalse(userService.loginUser(username, incorrectPassword));
	}

	@Test
	void testLoginUserUserNotFound() {
		String username = "nonexistentuser";
		String password = "testpassword";

		when(userRepo.findByUsername(username)).thenReturn(null);

		assertFalse(userService.loginUser(username, password));
	}

	@Test
	void testGetRoleByUserAndPass() {
		String username = "testuser";
		String password = "testpassword";
		String role = "ROLE_USER";

		UserDetails userDetails = new UserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword(new BCryptPasswordEncoder().encode(password));
		userDetails.setRole(role);

		when(userRepo.findByUsername(username)).thenReturn(userDetails);

		assertEquals(role, userService.getRoleByUserAndPass(username, password));
	}

	@Test
	void testGetRoleByUserAndPassUserNotFound() {
		String username = "nonexistentuser";
		String password = "testpassword";
		when(userRepo.findByUsername(username)).thenReturn(null);

		assertNull(userService.getRoleByUserAndPass(username, password));
	}
}
