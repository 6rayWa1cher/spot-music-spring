package com.a6raywa1cher.spotmusicspring.rest;

import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.converter.UserConverter;
import com.a6raywa1cher.spotmusicspring.rest.modelview.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
//
//	public ResponseEntity<>
}
