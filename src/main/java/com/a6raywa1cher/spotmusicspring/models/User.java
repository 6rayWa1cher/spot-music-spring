package com.a6raywa1cher.spotmusicspring.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String photo;

	@Column
	private String phoneNumber;

	@Column
	private Boolean completeRegistration = false;

	@Column
	private String description;

	@Column(length = 8191)
	private String socialUrl;

	@OneToMany(mappedBy = "user")
	private List<Song> songs;

	@OneToMany(mappedBy = "user")
	private List<Event> events;
}
