package com.a6raywa1cher.spotmusicspring.rest.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostCreateEvent {
	private List<Long> songs = new ArrayList<>();

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	private LocalDateTime dateTime;

	@NotNull
	private Duration duration;
}
