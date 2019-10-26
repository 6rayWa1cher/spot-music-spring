package com.a6raywa1cher.spotmusicspring.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Event {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<Song> songs;

	@ManyToOne
	private User user;

	@Column(precision = 8)
	private Double latitude;

	@Column(precision = 8)
	private Double longitude;

	@Column
	private LocalDateTime dateTime;

	@Column
	private Duration duration;

	@Column
	private BigDecimal money;
}
