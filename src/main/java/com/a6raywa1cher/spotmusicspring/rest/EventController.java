package com.a6raywa1cher.spotmusicspring.rest;

import com.a6raywa1cher.spotmusicspring.dao.repositories.EventRepository;
import com.a6raywa1cher.spotmusicspring.dao.repositories.SongRepository;
import com.a6raywa1cher.spotmusicspring.dao.repositories.UserRepository;
import com.a6raywa1cher.spotmusicspring.models.Event;
import com.a6raywa1cher.spotmusicspring.models.Song;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.converter.EventConverter;
import com.a6raywa1cher.spotmusicspring.rest.modelview.EventView;
import com.a6raywa1cher.spotmusicspring.rest.request.PostAddMoney;
import com.a6raywa1cher.spotmusicspring.rest.request.PostCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController {
	@Autowired
	EventConverter eventConverter;

	@Autowired
	EventRepository repository;

	@Autowired
	SongRepository songRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/all")
	public ResponseEntity<List<EventView>> getAllEvents(Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable).get()
				.map(eventConverter::convert)
				.collect(Collectors.toList()));
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<EventView> getById(@PathVariable Long id) {
		Optional<Event> optionalEvent = repository.findById(id);
		if (!optionalEvent.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(eventConverter.convert(optionalEvent.get()));
	}

	@PostMapping("/")
	public ResponseEntity<EventView> createEvent(@RequestBody @Valid PostCreateEvent dto, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Event event = new Event();
		event.setUser(user);
		List<Song> songs = new ArrayList<>();
		songRepository.findAllById(dto.getSongs()).forEach(songs::add);
		event.setSongs(songs);
		event.setLatitude(dto.getLatitude());
		event.setLongitude(dto.getLongitude());
		event.setDateTime(Optional.ofNullable(dto.getDateTime()).orElse(LocalDateTime.now()));
		event.setDuration(dto.getDuration());
		event.setMoney(BigDecimal.ZERO);
		Event saved = repository.save(event);
		return ResponseEntity.ok(eventConverter.convert(saved));
	}

	@PostMapping("/id/{id}/donate")
	public ResponseEntity<?> addMoney(@RequestBody @Valid PostAddMoney dto, @PathVariable Long id) {
		Optional<Event> optionalEvent = repository.findById(id);
		if (!optionalEvent.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		if (dto.getMoney().signum() != 1) {
			return ResponseEntity.badRequest().build();
		}
		Event event = optionalEvent.get();
		event.setMoney(event.getMoney().add(dto.getMoney()));
		Event saved = repository.save(event);
		return ResponseEntity.ok(eventConverter.convert(saved));
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<List<EventView>> getByUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user1 = user.get();
		return ResponseEntity.ok(user1.getEvents().stream().map(eventConverter::convert).collect(Collectors.toList()));
	}
}
