package com.auth.authenticationservice.service;


import com.auth.authenticationservice.model.UserDetails;
import com.auth.authenticationservice.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.config.types.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepo userRepo;

	@Autowired
	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public boolean loginUser(String username, String password) {

		UserDetails userDetails = userRepo.findByUsername(username);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		log.info(password);
		log.info(userDetails.getPassword());
		log.info(encoder.matches(password, userDetails.getPassword())+"");
		if(userDetails!=null && encoder.matches(password, userDetails.getPassword()))
		{
			log.info(username+"--service--"+password);
			return true;
		}

		return false;
	}

	@Override
	public String getRoleByUserAndPass(String username, String password) {
		String r = userRepo.findByUsername(username).getRole();
		log.info(r+"role---");
		return r;
	}
}














