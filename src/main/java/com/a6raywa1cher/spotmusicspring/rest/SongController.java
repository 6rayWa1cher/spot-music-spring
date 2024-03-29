package com.a6raywa1cher.spotmusicspring.rest;

import com.a6raywa1cher.spotmusicspring.dao.repositories.SongRepository;
import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.Song;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.converter.SongConverter;
import com.a6raywa1cher.spotmusicspring.rest.modelview.SongView;
import com.a6raywa1cher.spotmusicspring.rest.request.PostCreateSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/songs")
public class SongController {
	@Autowired
	SongRepository repository;

	@Autowired
	SongConverter songConverter;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/id/{id}")
	public ResponseEntity<SongView> getById(@PathVariable Long id) {
		Optional<Song> optionalSong = repository.findById(id);
		if (!optionalSong.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(songConverter.convert(optionalSong.get()));
	}

	@PostMapping("/")
	public ResponseEntity<SongView> createSong(@RequestBody @Valid PostCreateSong dto, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Song song = new Song();
		song.setName(dto.getName());
		song.setAlbumName(dto.getAlbumName());
		song.setCoverPhotoUrl(dto.getCoverPhotoUrl());
		song.setEvents(Collections.emptyList());
		song.setUser(user);
		Song saved = repository.save(song);
		return ResponseEntity.ok(songConverter.convert(saved));
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<List<SongView>> getByUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user1 = user.get();
		return ResponseEntity.ok(user1.getSongs().stream().map(songConverter::convert).collect(Collectors.toList()));
	}

}
