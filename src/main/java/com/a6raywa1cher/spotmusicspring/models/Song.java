package com.a6raywa1cher.spotmusicspring.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Song {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany(mappedBy = "songs")
	private List<Event> events;

	@ManyToOne
	private User user;

	@Column(length = 1023)
	private String coverPhotoUrl;

	@Column
	private String name;

	@Column
	private String albumName;
}
