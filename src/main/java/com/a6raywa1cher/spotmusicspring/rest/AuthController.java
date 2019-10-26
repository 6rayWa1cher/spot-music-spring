package com.a6raywa1cher.spotmusicspring.rest;

import com.a6raywa1cher.spotmusicspring.config.security.TokenAuthentication;
import com.a6raywa1cher.spotmusicspring.config.security.TokenService;
import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.converter.UserConverter;
import com.a6raywa1cher.spotmusicspring.rest.modelview.UserView;
import com.a6raywa1cher.spotmusicspring.util.ValidationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Value("classpath:firebase.json")
	Resource resourceFile;

	@Autowired
	UserConverter userConverter;

	@Autowired
	UserRepository userService;

	@Autowired
	TokenService tokenService;


	@GetMapping("/test")
	public ResponseEntity testCreateUser() {
		User user = new User();
		user.setName("Александр");
		user.setCompleteRegistration(true);
		user.setPhoneNumber("79268434151");
		userService.save(user);
		return ResponseEntity.ok().build();
	}

//    @PostMapping("/login")
//    public ResponseEntity<JwtResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
//        try {
//
//            User user = userService.findByLogin(loginRequest.getLogin());
//            if(user == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//
//            if(!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//
//
//            return ResponseEntity.ok(new JwtResponse(tokenService.getToken(user)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

	@GetMapping("/firebase")
	public ResponseEntity<JwtResponse> loginFirebase(@RequestParam("token") String access_token) {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(resourceFile.getInputStream()))
					.build();
			if (FirebaseApp.getApps().size() == 0) {
				FirebaseApp.initializeApp(options);
			}
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(access_token);
			String number = decodedToken.getUid();
			UserRecord userRecord = FirebaseAuth.getInstance().getUser(number);
			String tel = ValidationUtils.normalizeTel(userRecord.getPhoneNumber());

			User user = userService.findByPhoneNumber(tel);

			if (user == null) {
				User newUser = new User();
				newUser.setPhoneNumber(tel);
				user = userService.save(newUser);
			}

			if (!user.getCompleteRegistration()) {
				return ResponseEntity.status(201).body(new JwtResponse(tokenService.getToken(user)));
			}

			return ResponseEntity.ok(new JwtResponse(tokenService.getToken(user)));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(value = "/me")
	public ResponseEntity me(@ApiIgnore TokenAuthentication tokenAuthentication) {
		Optional<User> optional = userService.findById(tokenAuthentication.getUser().getId());
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User requestUser = optional.get();
		return ResponseEntity.status(requestUser.getCompleteRegistration() ? 200 : 201).body(userConverter.convert(requestUser));
	}

	@GetMapping(value = "/test_jwt")
	public ResponseEntity<?> getTestJwt() {
		User user = userService.findByPhoneNumber("79268434151");
		return ResponseEntity.ok().body(new ObjectMapper().createObjectNode().put("jwt", tokenService.getToken(user)).toString());
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<UserView> register(@Validated
	                                         @RequestBody
			                                         UserRegisterRequest userEditCreateDTO,
	                                         @ApiIgnore TokenAuthentication tokenAuthentication) {
		Optional<User> optional = userService.findById(tokenAuthentication.getUser().getId());
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user = optional.get();
		if (user.getCompleteRegistration()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		user.setName(userEditCreateDTO.getName());
		user.setCompleteRegistration(true);
		userService.save(user);
		return ResponseEntity.ok(Objects.requireNonNull(userConverter.convert(user)));
	}

	@Data
	public static class JwtResponse {
		private String jwt;

		JwtResponse(String jwt) {
			this.jwt = jwt;
		}
	}

	@Data
	public static class UserRegisterRequest {
		private String name;
	}

	@Data
	public static class LoginRequest {
		@NotEmpty
		private String login;
		@NotEmpty
		private String password;
	}
}
