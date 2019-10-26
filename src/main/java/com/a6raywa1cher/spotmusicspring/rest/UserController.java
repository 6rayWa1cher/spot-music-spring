package com.a6raywa1cher.spotmusicspring.rest;

import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.converter.UserConverter;
import com.a6raywa1cher.spotmusicspring.rest.modelview.UserView;
import com.a6raywa1cher.spotmusicspring.rest.request.PutUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserRepository repository;

	@Autowired
	UserConverter userConverter;

	@GetMapping("/id/{id}")
	public ResponseEntity<UserView> getById(@PathVariable Long id) {
		Optional<User> optionalUser = repository.findById(id);
		if (!optionalUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userConverter.convert(optionalUser.get()));
	}

	@PutMapping("/")
	public ResponseEntity<UserView> put(@RequestBody @Valid PutUser dto, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		user.setName(dto.getName());
		user.setCompleteRegistration(true);
		user.setDescription(dto.getDescription());
		user.setSocialUrl(dto.getSocialUrl());
		user.setPhoto(dto.getPhoto());
		User saved = repository.save(user);
		return ResponseEntity.ok(userConverter.convert(saved));
	}
}
