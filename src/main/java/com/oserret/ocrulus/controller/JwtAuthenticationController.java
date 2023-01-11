package com.oserret.ocrulus.controller;

import ch.qos.logback.classic.Logger;
import com.oserret.ocrulus.config.JwtTokenUtil;
import com.oserret.ocrulus.entity.UserEntity;
import com.oserret.ocrulus.entity.UserSessionEntity;
import com.oserret.ocrulus.exception.UserMessageException;
import com.oserret.ocrulus.model.JwtRequest;
import com.oserret.ocrulus.model.JwtResponse;
import com.oserret.ocrulus.service.UserService;
import com.oserret.ocrulus.service.UserSessionService;
import com.oserret.ocrulus.utils.Globals;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

	Logger logger = (Logger) LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		logger.info("Call: /api/auth/authenticate POST SERVICE");

		logger.info("Authenticating the user and the password");
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		logger.info("Loading user");
		UserEntity userEntity = userService.loadUserByUsername(authenticationRequest.getUsername());
		final UserDetails userDetails;

		logger.info("Validating the password provided against the database password");
		if(bCryptPasswordEncoder.matches(authenticationRequest.getPassword(),userEntity.getPassword())) {
			userDetails = new User(userEntity.getUserName(), userEntity.getPassword(), new ArrayList<>());
		}else {
			throw new Exception(Globals.USER_DOES_NOT_EXIST);
		}

		logger.info("Generating token with the user information");
		final String token = jwtTokenUtil.generateToken(userDetails);

		logger.info("Generating the user session");
		UserSessionEntity userSessionEntity = this.create(userEntity.getId());

		logger.info("Storing the user session");
		userSessionService.save(userSessionEntity);

		logger.info("Response: /api/auth/authenticate POST SERVICE");
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception(Globals.USER_DISABLED, e);
		} catch (BadCredentialsException e) {
			throw new Exception(Globals.USER_INVALID_CREDENTIALS, e);
		}
	}

	public UserSessionEntity create(long userId) throws UserMessageException {
		Optional<UserEntity> user;
		UserSessionEntity userSession;

		user = this.userService.load(userId);
		if (user == null)
			throw new UserMessageException(userId + Globals.USER_DOES_NOT_EXIST);

		userSession = new UserSessionEntity();
		userSession.setUser(user.get());
		userSession.setSessionId(UUID.randomUUID().toString());
		userSession.setCreationTime(Calendar.getInstance().getTime());

		return userSession;
	}
}
