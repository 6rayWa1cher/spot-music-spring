package com.a6raywa1cher.spotmusicspring.rest.modelview;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventView {
	private Long id;

	private List<SongView> songs;

	private UserView user;

	private Double latitude;

	private Double longitude;

	private LocalDateTime dateTime;

	private Duration duration;

	private BigDecimal money;
}
